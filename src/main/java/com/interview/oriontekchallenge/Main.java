package com.interview.oriontekchallenge;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;

public class Main extends Application {
    public static Parent rootNode;
    private double xOffset;
    private double yOffset;
    private ResourceBundle resources;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() throws Exception {
        resources = ResourceBundle.getBundle(
                Main.class.getPackageName()+ ".bundle."
                        + Resources.MAIN.getBundlePath());
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(
                Main.class.getResource("fxml/"+ Resources.MAIN.getFxmlPath())),
                resources);
        rootNode = fxmlLoader.load();
        rootNode.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
    }

    public static ResourceBundle getDaoImplBundle(){
        return ResourceBundle.getBundle(
                Main.class.getPackageName()+ ".bundle.daoimpl.DaoImpl");
    }

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(Objects.requireNonNull(
                Main.class.getResource("img/square-font-awesome.svg"))
                .toString(),
                true));
        stage.setOnCloseRequest((WindowEvent e) ->
                exit()
        );
        rootNode.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.setTitle(resources.getString("stage.title"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }
}