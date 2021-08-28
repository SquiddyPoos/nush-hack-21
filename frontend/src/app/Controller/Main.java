package app.Controller;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static SimpleIntegerProperty NUM_TOASTS = new SimpleIntegerProperty(0);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/View/Main.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        try {
            primaryStage.getIcons().add(new Image(new FileInputStream("src/app/res/img/car2.png")));
        } catch (FileNotFoundException e) {
            Text text = new Text("File not found!");
            text.setWrappingWidth(250);
            text.setFont(new Font(20));
            new Toast().makeText(text, 3000);
        }

//        MainController.printPascal(15);
    }
}
