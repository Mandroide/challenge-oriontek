package com.interview.oriontekchallenge;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static javafx.application.Platform.exit;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setOnCloseRequest((WindowEvent e) ->
                exit()
        );
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("fxml/main.fxml")));
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        root.setOnMousePressed(event -> {
            xOffset.set(event.getSceneX());
            yOffset.set(event.getSceneY());
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset.get());
            stage.setY(event.getScreenY() - yOffset.get());
        });
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Oriontek Challenge");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}