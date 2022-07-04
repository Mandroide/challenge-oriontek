package com.interview.oriontekchallenge.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Controller {

    @FXML
    private FontAwesomeIconView btnClose;

    @FXML
    private Button btnStudents;

    @FXML
    private Button btnTeachers;

    @FXML
    private Button btnFees;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnSettings;

    @FXML
    private GridPane pnFees;

    @FXML
    private GridPane pnSettings;

    @FXML
    private GridPane pnStudents;

    @FXML
    private GridPane pnTeachers;

    @FXML
    private GridPane pnUsers;

    @FXML
    private Pane pnlStatus;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblStatusMini;

    @FXML
    private TableView<?> tableView;

    @FXML
    private void handleClicks(javafx.event.ActionEvent event) {
        if (event.getSource() == btnStudents) {
            String text = "Students";
            lblStatusMini.setText("/home/" + text.toLowerCase());
            lblStatus.setText(text);
            pnlStatus.setBackground(new Background(new BackgroundFill(
                    Color.rgb(113, 86, 221), CornerRadii.EMPTY, Insets.EMPTY
            )));
            pnStudents.toFront();
        } else if (event.getSource() == btnTeachers) {
            String text = "Teachers";
            lblStatusMini.setText("/home/" + text.toLowerCase());
            lblStatus.setText(text);
            pnlStatus.setBackground(new Background(new BackgroundFill(
                    Color.rgb(43, 63, 99), CornerRadii.EMPTY, Insets.EMPTY
            )));
            pnTeachers.toFront();
        } else if (event.getSource() == btnFees){
            String text = "Fees";
            lblStatusMini.setText("/home/" + text.toLowerCase());
            lblStatus.setText(text);
            pnlStatus.setBackground(new Background(new BackgroundFill(
                    Color.rgb(43, 99, 63), CornerRadii.EMPTY, Insets.EMPTY
            )));
            pnFees.toFront();
        } else if (event.getSource() == btnUsers){
            String text = "Users";
            lblStatusMini.setText("/home/" + text.toLowerCase());
            lblStatus.setText(text);
            pnlStatus.setBackground(new Background(new BackgroundFill(
                    Color.rgb(99, 43, 63), CornerRadii.EMPTY, Insets.EMPTY
            )));
            pnUsers.toFront();
        } else if (event.getSource() == btnSettings){
            String text = "Settings";
            lblStatusMini.setText("/home/" + text.toLowerCase());
            lblStatus.setText(text);
            pnlStatus.setBackground(new Background(new BackgroundFill(
                    Color.rgb(42, 28, 66), CornerRadii.EMPTY, Insets.EMPTY
            )));
            pnSettings.toFront();
        }
    }

    @FXML
    private void handleClose(){
        System.exit(0);
    }
}
