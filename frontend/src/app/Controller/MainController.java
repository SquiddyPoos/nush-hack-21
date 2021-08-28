package app.Controller;

import app.View.AutoCompleteTextField;
import app.View.Toast;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private WebView webView;
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane introPane;
    @FXML
    private HBox imgPane;
    @FXML
    private HBox welcomePane;
    @FXML
    private Text welcome;
    @FXML
    private VBox vBox;
    @FXML
    private ImageView imgView;
    @FXML
    private Text click;
    @FXML
    private Text sLocText;
    @FXML
    private Text eLocText;
    @FXML
    private Button find;
    @FXML
    private AnchorPane selectPane;
    @FXML
    private Button user;
    @FXML
    private Button driver;
    @FXML
    private Text select;
    @FXML
    private AnchorPane driverPane;
    @FXML
    private Button addDriver;
    @FXML
    private Text yourLocText;
    @FXML
    private GridPane gridPane1;
    @FXML
    private WebView webView1;
    @FXML
    private Text infoText;
    @FXML
    private VBox driverVBox;
    @FXML
    private VBox userVBox;
    @FXML
    private Text infoText1;


    private AutoCompleteTextField sLoc;
    private AutoCompleteTextField eLoc;
    private AutoCompleteTextField yourLoc;
    private static AutoCompleteTextField sLocStatic;
    private static AutoCompleteTextField eLocStatic;
    private static AutoCompleteTextField yourLocStatic;
    private static WebView webViewStatic;
    private static WebView webViewStatic1;
    private final String URL = "https://hack21.squiddy.me";
    private String userID;
    private boolean intro = true;
    private Timeline timeline = new Timeline();
    private boolean isUser = true;
    private boolean isMatching = false;
    private double saved;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.mainPane.setVisible(false);
        this.selectPane.setVisible(false);
        this.driverPane.setVisible(false);


        this.sLoc = new AutoCompleteTextField();
        this.eLoc = new AutoCompleteTextField();
        this.yourLoc = new AutoCompleteTextField();
        this.sLoc.setId("sLoc");
        this.eLoc.setId("eLoc");
        this.yourLoc.setId("yourLoc");
        setUpTF();
        this.gridPane.add(this.sLoc, 1, 0);
        this.gridPane.add(this.eLoc, 1, 1);
        this.gridPane1.add(this.yourLoc, 1, 0);
        sLocStatic = sLoc;
        eLocStatic = eLoc;
        yourLocStatic = yourLoc;
        webViewStatic = webView;
        webViewStatic1 = webView1;


        this.welcome.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg_bold.ttf"),54));
        this.sLocText.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 12));
        this.eLocText.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 12));
        this.find.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 12));
        this.sLoc.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 12));
        this.eLoc.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 12));
        this.yourLoc.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 12));
        this.yourLocText.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 12));
        this.addDriver.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 12));
        this.select.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg_bold.ttf"), 48));
        this.user.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 30));
        this.driver.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 30));
        this.infoText.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 20));
        this.infoText1.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 20));

        try {
            this.imgView.setImage(new Image(new FileInputStream("src/app/res/img/car2.png")));
        } catch (FileNotFoundException e) {
            Text text = new Text("Logo file not found!");
            text.setWrappingWidth(250);
            text.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 20));
            new Toast().makeText(text, 3000);
        }


        ParallelTransition parallelTransition = new ParallelTransition();

        FadeTransition fade = new FadeTransition(Duration.millis(1500), this.welcomePane);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.setCycleCount(1);
        parallelTransition.getChildren().add(fade);

        int i = 0;
        for (Node child : this.vBox.getChildren()) {

            Path path = new Path();
            path.getElements().add(new MoveTo(i % 2 == 0 ? -1500 : 1500, 50));
            path.getElements().add(new LineTo(489, 50));
            i++;
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(1500));
            pathTransition.setPath(path);
            pathTransition.setNode(child);
            pathTransition.setCycleCount(1);
            parallelTransition.getChildren().add(pathTransition);
        }

        parallelTransition.setCycleCount(1);
        parallelTransition.play();

        parallelTransition.setOnFinished(e -> {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), this.click);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setAutoReverse(true);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.play();
        });

    }

    public void setUpTF() {
        this.sLoc.textProperty().addListener((o, o1, o2) -> {
            if (!o2.equals("")) {
                try {
                    String finalUrl = String.format("%s/autocomplete/?pass=%s&input=%s",
                            URL, URLEncoder.encode("r3aw4g6es5rdhufymgihjgfbdvsefg5rhdtu", StandardCharsets.UTF_8),
                            URLEncoder.encode(this.sLoc.getText(), StandardCharsets.UTF_8));
                    String result = makeRequest(finalUrl, false);
                    SortedSet<String> places = new TreeSet<>();
                    for (String str : Arrays.stream(result.split("[\\]\\[]")[1].split("\",\"")).toList()) {
                        places.add(str.replaceAll("\"", ""));
                    }
                    this.sLoc.setEntries(places);
                } catch (MalformedURLException malformedURLException) {
                    Text text = new Text("URL invalid!");
                    text.setWrappingWidth(250);
                    text.setFont(new Font(20));
                    new Toast().makeText(text, 3000);
                } catch (IOException ioException) {
                    Text text = new Text("Error opening URL!");
                    text.setWrappingWidth(250);
                    text.setFont(new Font(20));
                    new Toast().makeText(text, 3000);
                }
            }
        });
        this.eLoc.textProperty().addListener((o, o1, o2) -> {
            if (!o1.equals("")) {
                try {
                    String finalUrl = String.format("%s/autocomplete/?pass=%s&input=%s",
                            URL, URLEncoder.encode("r3aw4g6es5rdhufymgihjgfbdvsefg5rhdtu", StandardCharsets.UTF_8),
                            URLEncoder.encode(this.eLoc.getText(), StandardCharsets.UTF_8));
                    String result = makeRequest(finalUrl, false);
                    SortedSet<String> places = new TreeSet<>();
                    places.addAll(Arrays.stream(result.split("[\\]\\[]")[1].split("\",\"")).toList());
                    this.eLoc.setEntries(places);
                } catch (MalformedURLException malformedURLException) {
                    Text text = new Text("URL invalid!");
                    text.setWrappingWidth(250);
                    text.setFont(new Font(20));
                    new Toast().makeText(text, 3000);
                } catch (IOException ioException) {
                    Text text = new Text("Error opening URL!");
                    text.setWrappingWidth(250);
                    text.setFont(new Font(20));
                    new Toast().makeText(text, 3000);
                }
            }
        });
        this.yourLoc.textProperty().addListener((o, o1, o2) -> {
            if (!o1.equals("")) {
                try {
                    String finalUrl = String.format("%s/autocomplete/?pass=%s&input=%s",
                            URL, URLEncoder.encode("r3aw4g6es5rdhufymgihjgfbdvsefg5rhdtu", StandardCharsets.UTF_8),
                            URLEncoder.encode(this.yourLoc.getText(), StandardCharsets.UTF_8));
                    String result = makeRequest(finalUrl, false);
                    SortedSet<String> places = new TreeSet<>();
                    places.addAll(Arrays.stream(result.split("[\\]\\[]")[1].split("\",\"")).toList());
                    this.yourLoc.setEntries(places);
                } catch (MalformedURLException malformedURLException) {
                    Text text = new Text("URL invalid!");
                    text.setWrappingWidth(250);
                    text.setFont(new Font(20));
                    new Toast().makeText(text, 3000);
                } catch (IOException ioException) {
                    Text text = new Text("Error opening URL!");
                    text.setWrappingWidth(250);
                    text.setFont(new Font(20));
                    new Toast().makeText(text, 3000);
                }
            }
        });
    }

    private String makeRequest(String finalUrl, boolean post) throws MalformedURLException, IOException {
        String responseString;
        URL url = new URL(finalUrl);
        if (!post) {
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response);
            responseString = scanner.useDelimiter("\\A").next();
        } else {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response);
            responseString = scanner.useDelimiter("\\A").next();
        }


        return responseString;
    }

    public static void setStartLoc(String who) {
        if (who.equals("sLoc") || who.equals("eLoc")) {
            String finalUrl = String.format("%s?pass=%s&loc=%s",
                    "https://hack21.squiddy.me/maps/", URLEncoder.encode("r3aw4g6es5rdhufymgihjgfbdvsefg5rhdtu", StandardCharsets.UTF_8),
                    URLEncoder.encode(sLocStatic.getText(), StandardCharsets.UTF_8));
            webViewStatic.getEngine().load(finalUrl);
        } else {
            String finalUrl = String.format("%s?pass=%s&loc=%s",
                    "https://hack21.squiddy.me/maps/", URLEncoder.encode("r3aw4g6es5rdhufymgihjgfbdvsefg5rhdtu", StandardCharsets.UTF_8),
                    URLEncoder.encode(yourLocStatic.getText(), StandardCharsets.UTF_8));
            webViewStatic1.getEngine().load(finalUrl);
        }
    }

    public void find(MouseEvent mouseEvent) {
        if (!this.isMatching) {
            try {
                String finalUrl = String.format("%s/add-customer/?pass=%s&sLoc=%s&eLoc=%s",
                        URL, URLEncoder.encode("r3aw4g6es5rdhufymgihjgfbdvsefg5rhdtu", StandardCharsets.UTF_8),
                        URLEncoder.encode(this.sLoc.getText(), StandardCharsets.UTF_8),
                        URLEncoder.encode(this.eLoc.getText(), StandardCharsets.UTF_8));
                String result = makeRequest(finalUrl, true);
                String[] temp = result.split(",");
                for (String str : temp) {
                    if (str.contains("userID")) {
                        this.userID = str.split("userID")[1].replaceAll("[\":}\n]", "");
                        System.out.println(this.userID);
                    } else if (str.contains("pollution")) {
                        saved = Double.parseDouble(str.split("pollution")[1].replaceAll("[\":\n ]", ""));
                        System.out.println(saved);
                    }
                }
                Text text = new Text("You were added as a customer!");
                text.setWrappingWidth(250);
                text.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 20));
                new Toast().makeText(text, 3000);
                this.find.setText("Stop finding");
                this.userVBox.setVisible(true);
                startMatchmaking();
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
                Text text = new Text("URL invalid!");
                text.setWrappingWidth(250);
                text.setFont(new Font(20));
                new Toast().makeText(text, 3000);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                Text text = new Text("Error opening URL!");
                text.setWrappingWidth(250);
                text.setFont(new Font(20));
                new Toast().makeText(text, 3000);
            }
        } else {
            try {
                String finalUrl = String.format("%s/del-customer/?pass=%s&userID=%s",
                        URL, URLEncoder.encode("r3aw4g6es5rdhufymgihjgfbdvsefg5rhdtu", StandardCharsets.UTF_8),
                        this.userID);
                String result = makeRequest(finalUrl, false);
                System.out.println(result);
                Text text = new Text("You were removed as a customer!");
                text.setWrappingWidth(250);
                text.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 20));
                new Toast().makeText(text, 3000);
                this.userVBox.setVisible(false);
                this.find.setText("Find driver");
                this.isMatching = false;
                this.timeline.stop();
                this.timeline.getKeyFrames().clear();
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
                Text text = new Text("URL invalid!");
                text.setWrappingWidth(250);
                text.setFont(new Font(20));
                new Toast().makeText(text, 3000);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                Text text = new Text("Error opening URL!");
                text.setWrappingWidth(250);
                text.setFont(new Font(20));
                new Toast().makeText(text, 3000);
            }
        }
    }

    public void click(MouseEvent mouseEvent) {
        if (intro) {
            this.selectPane.setVisible(true);
            ParallelTransition parallelTransition = new ParallelTransition();

            FadeTransition fade = new FadeTransition(Duration.millis(1500), this.welcomePane);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setCycleCount(1);
            parallelTransition.getChildren().add(fade);

            fade = new FadeTransition(Duration.millis(1500), this.selectPane);
            fade.setFromValue(0.0);
            fade.setToValue(1.0);
            fade.setCycleCount(1);
            parallelTransition.getChildren().add(fade);

            int i = 0;
            for (Node child : this.vBox.getChildren()) {

                Path path = new Path();
                path.getElements().add(new MoveTo(489, 50));
                path.getElements().add(new LineTo(i % 2 == 0 ? -1500 : 1500, 50));
                i++;
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(1500));
                pathTransition.setPath(path);
                pathTransition.setNode(child);
                pathTransition.setCycleCount(1);
                parallelTransition.getChildren().add(pathTransition);
            }

            parallelTransition.setCycleCount(1);

            parallelTransition.play();

            parallelTransition.setOnFinished(e -> {
                this.introPane.setVisible(false);
            });
            intro = false;
        }
    }

    public void beUser(MouseEvent mouseEvent) {
        this.mainPane.setVisible(true);
        ParallelTransition parallelTransition = new ParallelTransition();
        FadeTransition fade = new FadeTransition(Duration.millis(1500), this.mainPane);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.setCycleCount(1);
        parallelTransition.getChildren().add(fade);

        fade = new FadeTransition(Duration.millis(1500), this.selectPane);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setCycleCount(1);
        parallelTransition.getChildren().add(fade);

        parallelTransition.setCycleCount(1);
        parallelTransition.play();

        parallelTransition.setOnFinished(e -> {
            this.selectPane.setVisible(false);
        });
    }

    public void beDriver(MouseEvent mouseEvent) {
        this.driverVBox.setVisible(false);
        this.driverPane.setVisible(true);
        ParallelTransition parallelTransition = new ParallelTransition();
        FadeTransition fade = new FadeTransition(Duration.millis(1500), this.driverPane);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.setCycleCount(1);
        parallelTransition.getChildren().add(fade);

        fade = new FadeTransition(Duration.millis(1500), this.selectPane);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setCycleCount(1);
        parallelTransition.getChildren().add(fade);

        parallelTransition.setCycleCount(1);
        parallelTransition.play();

        parallelTransition.setOnFinished(e -> {
            this.selectPane.setVisible(false);
            this.isUser = false;
        });
    }

    public void addDriver(MouseEvent mouseEvent) {
        if (!this.isMatching) {
            try {
                String finalUrl = String.format("%s/add-driver/?pass=%s&loc=%s",
                        URL, URLEncoder.encode("r3aw4g6es5rdhufymgihjgfbdvsefg5rhdtu", StandardCharsets.UTF_8),
                        URLEncoder.encode(yourLoc.getText(), StandardCharsets.UTF_8));
                String result = makeRequest(finalUrl, true);

                String[] temp = result.split(",");
                for (String str : temp) {
                    if (str.contains("userID")) {
                        this.userID = str.split("userID")[1].replaceAll("[\":}\n]", "");
                        System.out.println(this.userID);
                    }
                }
                Text text = new Text("You were added as a driver!");
                text.setWrappingWidth(250);
                text.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 20));
                new Toast().makeText(text, 3000);
                this.addDriver.setText("Stop finding");
                this.driverVBox.setVisible(true);
                startMatchmaking();
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
                Text text = new Text("URL invalid!");
                text.setWrappingWidth(250);
                text.setFont(new Font(20));
                new Toast().makeText(text, 3000);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                Text text = new Text("Error opening URL!");
                text.setWrappingWidth(250);
                text.setFont(new Font(20));
                new Toast().makeText(text, 3000);
            }
        } else {
            try {
                String finalUrl = String.format("%s/del-driver/?pass=%s&userID=%s",
                        URL, URLEncoder.encode("r3aw4g6es5rdhufymgihjgfbdvsefg5rhdtu", StandardCharsets.UTF_8),
                        this.userID);
                String result = makeRequest(finalUrl, false);
                System.out.println(result);
                Text text = new Text("You were removed as a driver!");
                text.setWrappingWidth(250);
                text.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 20));
                new Toast().makeText(text, 3000);
                this.driverVBox.setVisible(false);
                this.addDriver.setText("Add driver");
                this.isMatching = false;
                this.timeline.stop();
                this.timeline.getKeyFrames().clear();
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
                Text text = new Text("URL invalid!");
                text.setWrappingWidth(250);
                text.setFont(new Font(20));
                new Toast().makeText(text, 3000);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                Text text = new Text("Error opening URL!");
                text.setWrappingWidth(250);
                text.setFont(new Font(20));
                new Toast().makeText(text, 3000);
            }
        }
    }

    public void startMatchmaking() {

        this.timeline.getKeyFrames().clear();

        this.isMatching = true;

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(10), e -> {
            try {
                String finalUrl = String.format("%s/matchmaking/?pass=%s&userID=%s&type=%s",
                        URL, URLEncoder.encode("r3aw4g6es5rdhufymgihjgfbdvsefg5rhdtu", StandardCharsets.UTF_8),
                        URLEncoder.encode(this.userID, StandardCharsets.UTF_8),
                        this.isUser ? "CUSTOMER" : "DRIVER");
                String response = makeRequest(finalUrl, false);
                System.out.println(response);
                if (!this.isUser){
                    this.driverVBox.getChildren().clear();
                    this.driverVBox.setSpacing(20);
                    this.driverVBox.getChildren().add(this.infoText);
                    ArrayList<String> userIDs = new ArrayList<>();
                    ArrayList<String> starts = new ArrayList<>();
                    ArrayList<String> ends = new ArrayList<>();
                    Pattern pattern = Pattern.compile("^\\{\"valid\":\\[(.+)]}$");
                    Matcher matcher = pattern.matcher(response);
                    matcher.find();
                    response = matcher.group(1);
                    pattern = Pattern.compile("\"userID\":(.+?)}");
                    matcher = pattern.matcher(response);
                    while (matcher.find()) {
                        userIDs.add(matcher.group(1));
                    }
                    pattern = Pattern.compile("\"start\":\\{(.+?)}");
                    matcher = pattern.matcher(response);
                    while (matcher.find()) {
                        starts.add(matcher.group(1));
                    }
                    pattern = Pattern.compile("\"end\":\\{(.+?)}");
                    matcher = pattern.matcher(response);
                    while (matcher.find()) {
                        ends.add(matcher.group(1));
                    }
                    int index = 0;
                    for (String userID: userIDs) {
                        Text text = new Text(starts.get(index).split("\"")[3] + " to " + ends.get(index).split("\"")[3]);
                        text.setWrappingWidth(280);
                        text.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg/ttf"), 12));
                        this.driverVBox.getChildren().add(text);
                        index ++;
                        text.setOnMouseClicked(me -> {
                            try {
                                String finalUrl1 = String.format("%s/choose-pickup/?pass=%s&driverID=%s&customerID=%s",
                                        URL, URLEncoder.encode("r3aw4g6es5rdhufymgihjgfbdvsefg5rhdtu", StandardCharsets.UTF_8),
                                        this.userID, userID.replaceAll("\"", ""));
                                String result = makeRequest(finalUrl1, false);
                                System.out.println(result);
                                Text text1 = new Text("Successfully matched with user!");
                                text1.setWrappingWidth(250);
                                text1.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg.ttf"), 20));
                                new Toast().makeText(text1, 3000);
                                this.driverVBox.setVisible(false);
                                this.addDriver.setText("Add driver");
                                this.isMatching = false;
                                this.timeline.stop();
                                this.timeline.getKeyFrames().clear();
                            } catch (MalformedURLException malformedURLException) {
                                malformedURLException.printStackTrace();
                                Text text1 = new Text("URL invalid!");
                                text1.setWrappingWidth(250);
                                text1.setFont(new Font(20));
                                new Toast().makeText(text1, 3000);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                                Text text1 = new Text("Error opening URL!");
                                text1.setWrappingWidth(250);
                                text1.setFont(new Font(20));
                                new Toast().makeText(text1, 3000);
                            }
                        });
                    }
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/View/matchedInformation.fxml"));
                        Parent parent = loader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(parent));
                        stage.show();
                        MatchedInformation matchedInformation = loader.getController();
                        Pattern pattern = Pattern.compile("^\\{\"valid\":\\[(.+)]}$");
                        Matcher matcher = pattern.matcher(response);
                        matcher.find();
                        response = matcher.group(1);
                        pattern = Pattern.compile("\"address\":(.+?)\",");
                        matcher = pattern.matcher(response);
                        matcher.find();
                        String match = matcher.group(1);
                        match.replaceAll("\"", "");
                        matchedInformation.setInfo(match, saved);
                        this.timeline.stop();
                    } catch (IOException ie) {
                        Text text = new Text("File not found invalid!");
                        text.setWrappingWidth(250);
                        text.setFont(new Font(20));
                        new Toast().makeText(text, 3000);
                    }
                }
            } catch (MalformedURLException malformedURLException) {
                Text text = new Text("URL invalid!");
                text.setWrappingWidth(250);
                text.setFont(new Font(20));
                new Toast().makeText(text, 3000);
            } catch (IOException ioException) {
                if (!ioException.getMessage().contains("Server returned HTTP response code: 500 for URL:")) {
                    Text text = new Text("Error opening URL!");
                    text.setWrappingWidth(250);
                    text.setFont(new Font(20));
                    new Toast().makeText(text, 3000);
                } else {
                    System.out.println("Error!");
                }
            }
        });
        this.timeline.getKeyFrames().add(keyFrame);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }


    public static void printPascal(int n) {
        for (int line = 0; line < n; line++) {
            for (int i = 0; i < n - line; i++) {
                System.out.print("   ");
            }
            for (int i = 0; i <= line; i++)
                System.out.print(String.format("%5s", binomialCoefficient(line, i)) + " ");
            System.out.println();
        }
    }

    public static int binomialCoefficient(int n, int k) {
        int res = 1;

        if (k > n - k)
            k = n - k;

        for (int i = 0; i < k; ++i) {
            res *= (n - i);
            res /= (i + 1);
        }
        return res;
    }
}
