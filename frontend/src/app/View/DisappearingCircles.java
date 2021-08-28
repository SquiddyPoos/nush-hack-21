package app.View;

import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Random;

public class DisappearingCircles {
    private double frequency;
    private long time;
    private double distance;

    private Text text;
    private Bounds bounds;

    private Timeline timeline = new Timeline();
    private Pane pane;

    private final static double difference = 0.2;
    private final static double radius = 20;

    public DisappearingCircles(double frequency, long time, double distance, Text text, Pane pane) {
        this.frequency = frequency;
        this.time = time;
        this.distance = distance;

        this.text = text;
        bounds = text.getBoundsInParent();

        System.out.println(bounds.getWidth() + " " + bounds.getHeight());

        this.pane = pane;
    }

    public void start() {
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000 / frequency), e -> {
            Random random = new Random();
            double sideProbability = random.nextDouble() * (bounds.getWidth() + bounds.getHeight());
            double startX;
            double startY;

            if (sideProbability < bounds.getWidth()) {  // above or belove text
                double differenceX = (bounds.getWidth()/2 * random.nextDouble() + radius * (random.nextDouble() + difference))
                        * (random.nextInt(2) * 2 - 1);
                startX = bounds.getCenterX() + differenceX;
                double differenceY = (bounds.getHeight()/2 + radius * (random.nextDouble() + difference))
                        * (random.nextInt(2) * 2 - 1);
                startY = bounds.getCenterY() + differenceY;
            } else {  // left or right of text
                double differenceX = (bounds.getWidth()/2 + radius * (random.nextDouble() + difference))
                        * (random.nextInt(2) * 2 - 1);
                startX = bounds.getCenterX() + differenceX;
                double differenceY = (bounds.getHeight()/2 * random.nextDouble() + radius * (random.nextDouble() + difference))
                        * (random.nextInt(2) * 2 - 1);
                startY = bounds.getCenterY() + differenceY;
            }

            double angle = angleTo(bounds.getCenterX(), bounds.getCenterY(), startX, startY) + random.nextDouble() * 30 - 15;
            double endX = startX + distance * Math.cos(Math.toRadians(angle));
            double endY = startY + distance * Math.sin(Math.toRadians(angle));

            int brightness = random.nextInt(50) + 100;
            Circle circle = new Circle(random.nextInt(10) + 10);
            circle.setFill(Color.grayRgb(brightness));

            TranslateTransition tt = new TranslateTransition(Duration.millis(time), circle);
            tt.setFromX(startX);
            tt.setFromY(startY);
            tt.setToX(endX);
            tt.setToY(endY);

            FadeTransition ft = new FadeTransition(Duration.millis(time), circle);
            ft.setFromValue(random.nextDouble() * 0.2 + 0.4);
            ft.setToValue(0);

            ScaleTransition st = new ScaleTransition(Duration.millis(time), circle);
            st.setFromX(1);
            st.setFromY(1);
            st.setToX(random.nextDouble() * 0.2 + 0.4);
            st.setToY(random.nextDouble() * 0.2 + 0.4);

            tt.setCycleCount(1);
            ft.setCycleCount(1);
            tt.play();
            ft.play();

            pane.getChildren().add(circle);
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    public static double angleTo(double startX, double startY, double endX, double endY) {
        if (startX == endX) {
            if (startY < endY) {
                return 90;
            } else {
                return 270;
            }
        }

        double basicAngle = Math.toDegrees(Math.atan((endY - startY) / (endX - startX)));
        if (endX > startX) {  // quadrant 1 or 4
            return basicAngle;
        } else {  // quadrant 2 or 3
            return 180 + basicAngle;
        }
    }
}
