package app.Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String response = "{\"valid\":[{\"address\":\"NUS High Sch, Singapore\",\"lat\":1.3056505,\"long\":103.7699304,\"userID\":\"7870a32c-07ca-11ec-ad0a-000ec648bc44\"}]}";
        Pattern pattern = Pattern.compile("^\\{\"valid\":\\[(.+)]}$");
        Matcher matcher = pattern.matcher(response);
        matcher.find();
        response = matcher.group(1);
        pattern = Pattern.compile("\"address\":(.+?)\",");
        matcher = pattern.matcher(response);
        matcher.find();
        System.out.println(matcher.group(1));

    }
}
