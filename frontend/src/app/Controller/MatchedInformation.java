package app.Controller;

import app.View.DisappearingCircles;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;

public class MatchedInformation {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView logo;
    @FXML
    private Text matchedText;
    @FXML
    private Text carbonDioxideSaved;

    @FXML
    private void initialize() {
        try {
            logo.setImage(new Image(new FileInputStream("src/app/res/img/car2.png")));
            matchedText.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg_bold.ttf"),40));
            carbonDioxideSaved.setFont(Font.loadFont(getClass().getResourceAsStream("/app/res/fonts/zekton_rg_bold.ttf"),30));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        DisappearingCircles dc = new DisappearingCircles(5, 10000, 10, carbonDioxideSaved, anchorPane);
        dc.start();
    }
    public void setInfo(String match, double co2){
        this.matchedText.setText("You have successfully matched with a driver at " + match);
        this.carbonDioxideSaved.setText(String.format("You saved %.1fg of carbon dioxide!", co2));
    }
}
