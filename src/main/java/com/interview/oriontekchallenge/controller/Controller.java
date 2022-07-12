package com.interview.oriontekchallenge.controller;

import com.interview.oriontekchallenge.Main;
import com.interview.oriontekchallenge.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    @FXML
    private URL location;
    @FXML
    private ResourceBundle resources;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnDirecciones;

    @FXML
    private Pane pnlStatus;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblStatusMini;

    @FXML
    private void handleClicks(javafx.event.ActionEvent event) {
        Resources rs = Resources.DEFAULT;
        if (event.getSource() == btnClientes) {
            pnlStatus.setBackground(new Background(new BackgroundFill(
                    Color.rgb(113, 86, 221), CornerRadii.EMPTY, Insets.EMPTY
            )));
            rs = Resources.CLIENTE;
        } else if (event.getSource() == btnDirecciones) {
            pnlStatus.setBackground(new Background(new BackgroundFill(
                    Color.rgb(43, 63, 99), CornerRadii.EMPTY, Insets.EMPTY
            )));
            rs = Resources.DIRECCION;
        }
        loadOption(rs);
    }

    @FXML
    private void handleClose() {
        System.exit(0);
    }


    @FXML
    private void initialize() {
        loadOption(Resources.CLIENTE);
    }

    private void loadOption(Resources rs) {
        try {
            resources = ResourceBundle.getBundle(
                    Main.class.getPackageName()+ ".bundle." + rs.getBundlePath());
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(
                    com.interview.oriontekchallenge.Main.class.getResource("fxml/" + rs.getFxmlPath())),
                    resources);
            contentArea.getChildren().removeAll();
            lblStatus.setText(resources.getString("lblStatus.text"));
            lblStatusMini.setText(resources.getString("lblStatusMini.text"));
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}
