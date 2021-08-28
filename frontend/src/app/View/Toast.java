package app.View;

import app.Controller.Main;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public final class Toast {
    private double startX = 150;
    private final Timeline timeline = new Timeline();
    private ChangeListener<Number> changeListener;

    public void makeText(Node content, int timeDelay) {
        Main.NUM_TOASTS.set(Main.NUM_TOASTS.get() + 1);
        Stage toastStage = new Stage();
        toastStage.setAlwaysOnTop(true);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);
        toastStage.setX(970);
        toastStage.setY(550 - 130 * (Main.NUM_TOASTS.get() - 1));
        toastStage.setWidth(300);
        toastStage.setHeight(110);

        changeListener = (observableValue, o, t1) -> {
            if (t1.intValue() - o.intValue() < 0) {
                toastStage.setY(toastStage.getY() + 130);
            }
        };

        Main.NUM_TOASTS.addListener(changeListener);

        Text x = new Text("x");
        x.setFont(Font.font("System", FontWeight.BOLD, 14));
        StackPane root = new StackPane(content, x);
        content.setTranslateY(content.getTranslateX() - 5);
        x.translateXProperty().set(135);
        x.translateYProperty().set(-40);
        x.setOnMouseClicked(e -> destroy(toastStage, root));
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(0.0);
        progressBar.setPrefWidth(280);
        root.getChildren().add(progressBar);
        progressBar.setTranslateY(45);
        progressBar.setPrefHeight(10);
        root.setOpacity(1);
        root.setStyle("-fx-background-color: lightgray; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: gray");
        root.setOnMousePressed(e -> this.startX = e.getX());
        root.setOnMouseDragged(e -> {
            if (e.getX() - this.startX > 0) {
                root.setLayoutX(- this.startX + e.getX());
            }
        });
        root.setOnMouseReleased(e -> {
            if (e.getX() - this.startX > 100) {
                destroy(toastStage, root);
            } else {
                root.setLayoutX(0);
            }
        });

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

        Path path = new Path();
        path.getElements().add(new MoveTo(500, 55));
        path.getElements().add(new LineTo(150,55));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setPath(path);
        pathTransition.setNode(root);
        pathTransition.setDuration(Duration.millis(500));
        pathTransition.setCycleCount(1);
        pathTransition.play();

        KeyFrame keyFrame = new KeyFrame(Duration.millis(timeDelay/200.), e -> progressBar.setProgress(progressBar.getProgress() + 0.0053));
        this.timeline.getKeyFrames().add(keyFrame);
        this.timeline.setCycleCount(200);
        this.timeline.play();
        this.timeline.setOnFinished(e -> destroy(toastStage, root));
    }

    private void destroy(Stage toastStage, StackPane root) {
        this.timeline.stop();
        Path path = new Path();
        path.getElements().add(new MoveTo(150, 55));
        path.getElements().add(new LineTo(500,55));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setPath(path);
        pathTransition.setNode(root);
        pathTransition.setDuration(Duration.millis(500));
        pathTransition.setCycleCount(1);
        pathTransition.play();
        pathTransition.setOnFinished(actionEvent -> {
            toastStage.close();
            Main.NUM_TOASTS.set(Main.NUM_TOASTS.get() - 1);
            Main.NUM_TOASTS.removeListener(changeListener);
        });
    }
}