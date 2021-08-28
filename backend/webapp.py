from flask import Flask, redirect, render_template, request, Response, jsonify
import googlemaps
import uuid

app = Flask(__name__)
app.config['SERVER_NAME'] = 'squiddy.me'

SUBDOMAIN = "hack21"

API_KEY = "AIzaSyBrxRIwvejsYIe0m_GVP3EOiaPgg5m6Z2s"
PASSWORD = "r3aw4g6es5rdhufymgihjgfbdvsefg5rhdtu"

gmaps = googlemaps.Client(key=API_KEY)

CUSTOMERS = {}
DRIVERS = {}
MATCHED = {}

CHARGERS = [(1.38564, 103.7463853), (1.3439433, 103.7076496), (1.4308045, 103.8330555), (1.306842, 103.78844), (1.3235028, 103.721767), (1.2892898, 103.7778211), (1.3385659, 103.7769836), (1.3308721, 103.7990665)]

MAX_RADIUS = 0.05

@app.route("/add-customer/", methods=["POST"], subdomain=SUBDOMAIN)
def getPaths():
    pwd = request.args.get("pass")
    if (pwd != PASSWORD): return Response("Invalid!", status=401)
    startLoc = request.args.get("sLoc")
    endLoc = request.args.get("eLoc")
    directions = gmaps.directions(startLoc, endLoc, mode="driving")
    # 192g CO2 per km
    leg1 = directions[0].get("legs")[0]
    length = int(leg1.get("distance").get("value"))
    co2 = length * 192 / 1000
    userID = uuid.uuid1()
    CUSTOMERS[str(userID)] = ((leg1.get("start_location").get("lat"), leg1.get("start_location").get("lng")), (leg1.get("end_location").get("lat"), leg1.get("end_location").get("lng")))
    return jsonify(
        {
            "dist": length,
            "pollution": co2,
            "userID": userID,
            "end": {
                "name": leg1.get("end_address"),
                "lat": leg1.get("end_location").get("lat"),
                "lng": leg1.get("end_location").get("lng")
            },
            "start": {
                "name": leg1.get("start_address"),
                "lat": CUSTOMERS[str(userID)][0],
                "lng": CUSTOMERS[str(userID)][1]
            }
        })


@app.route("/add-driver/", methods=["POST"], subdomain=SUBDOMAIN)
def addDriver():
    pwd = request.args.get("pass")
    if (pwd != PASSWORD): return Response("Invalid!", status=401)
    loc = request.args.get("loc")
    latlong = gmaps.geocode(loc)
    lat = latlong[0].get("geometry").get("location").get("lat")
    lng = latlong[0].get("geometry").get("location").get("lng")
    userID = uuid.uuid1()
    DRIVERS[str(userID)] = (lat, lng)
    return jsonify(
        {
            "lat": lat,
            "lng": lng,
            "address": latlong[0].get("formatted_address"),
            "userID": userID
        })


@app.route("/autocomplete/", subdomain=SUBDOMAIN)
def get_autocomplete():
    pwd = request.args.get("pass")
    if (pwd != PASSWORD): return Response("Invalid!", status=401)
    inpt = request.args.get("input")
    results = gmaps.places_autocomplete(inpt)
    descripts = []
    for result in results:
        descripts.append(result.get("description"))
    return jsonify(
        {
            "results": descripts
        })


@app.route("/maps/", subdomain=SUBDOMAIN)
def get_map():
    pwd = request.args.get("pass")
    if (pwd != PASSWORD): return Response("Invalid!", status=401)
    sLoc = request.args.get("sLoc")
    eLoc = request.args.get("eLoc")
    start = None
    end = None
    if (sLoc != None):
        slatlong = gmaps.geocode(sLoc)
        slat = slatlong[0].get("geometry").get("location").get("lat")
        slng = slatlong[0].get("geometry").get("location").get("lng")
        start = (slat, slng)
    if (eLoc != None):
        elatlong = gmaps.geocode(eLoc)
        elat = elatlong[0].get("geometry").get("location").get("lat")
        elng = elatlong[0].get("geometry").get("location").get("lng")
        end = (elat, elng)
    return render_template("showmap.html", API_KEY=API_KEY, markers=CHARGERS, start=start, end=end)


@app.route("/matchmaking/", subdomain=SUBDOMAIN)
def matchmaking():
    pwd = request.args.get("pass")
    if (pwd != PASSWORD): return Response("Invalid!", status=401)
    userID = request.args.get("userID")
    ctype = request.args.get("type")
    results = []
    if (ctype == "CUSTOMER"):
        k = MATCHED.get(userID)
        v = DRIVERS.get(k)
        loc = gmaps.reverse_geocode(v)
        results.append(
            {
                "userID": k,
                "lat": v[0],
                "long": v[1],
                "address": loc[0].get("formatted_address")
            })
    elif (ctype == "DRIVER"):
        latlng = DRIVERS.get(userID)
        # get all customers within a max radius
        for k, v in CUSTOMERS.items():
            if ((v[0][0] - latlng[0]) ** 2 + (v[0][1] - latlng[1]) ** 2) < MAX_RADIUS * MAX_RADIUS:
                sLoc = gmaps.reverse_geocode(v[0])
                eLoc = gmaps.reverse_geocode(v[1])
                results.append(
                    {
                        "userID": k,
                        "start": {
                            "lat": v[0][0],
                            "long": v[0][1],
                            "address": sLoc[0].get("formatted_address")
                        },
                        "end": {
                            "lat": v[1][0],
                            "long": v[1][1],
                            "address": eLoc[0].get("formatted_address")
                        }
                    })
    return jsonify(
        {
            "valid": results
        })


@app.route("/del-customer/", subdomain=SUBDOMAIN)
def delCustomer():
    pwd = request.args.get("pass")
    if (pwd != PASSWORD): return Response("Invalid!", status=401)
    userID = request.args.get("userID")
    del CUSTOMERS[userID]
    return jsonify(
        {
            "status": "OK"
        })


@app.route("/del-driver/", subdomain=SUBDOMAIN)
def delDriver():
    pwd = request.args.get("pass")
    if (pwd != PASSWORD): return Response("Invalid!", status=401)
    userID = request.args.get("userID")
    del DRIVERS[userID]
    return jsonify(
        {
            "status": "OK"
        })

@app.route('/choose-pickup/', subdomain=SUBDOMAIN)
def chooseCustomer():
    pwd = request.args.get("pass")
    if (pwd != PASSWORD): return Response("Invalid!", status=401)
    driverID = request.args.get("driverID")
    customerID = request.args.get("customerID")
    del CUSTOMERS[customerID]
    MATCHED[customerID] = driverID
    return jsonify(
        {
            "status": "OK"
        })