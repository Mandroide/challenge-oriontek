package com.interview.oriontekchallenge.controller;

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
        String destination = "";
        if (event.getSource() == btnClientes) {
            String text = "Clientes";
            lblStatusMini.setText("/home/" + text.toLowerCase());
            lblStatus.setText(text);
            pnlStatus.setBackground(new Background(new BackgroundFill(
                    Color.rgb(113, 86, 221), CornerRadii.EMPTY, Insets.EMPTY
            )));
            destination = "fxml/cliente.fxml";
        } else if (event.getSource() == btnDirecciones) {
            String text = "Direcciones";
            lblStatusMini.setText("/home/" + text.toLowerCase());
            lblStatus.setText(text);
            pnlStatus.setBackground(new Background(new BackgroundFill(
                    Color.rgb(43, 63, 99), CornerRadii.EMPTY, Insets.EMPTY
            )));
            destination = "fxml/direccion.fxml";
        }
        loadOption(destination);
    }

    @FXML
    private void handleClose() {
        System.exit(0);
    }


    @FXML
    private void initialize() {
        loadOption("fxml/cliente.fxml");
    }

    /**
     * @param name name of the desired resource
     */
    private void loadOption(String name) {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(
                    com.interview.oriontekchallenge.Main.class.getResource(name)));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
