module com.interview.oriontekchallenge {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;


    opens com.interview.oriontekchallenge to javafx.fxml;
    exports com.interview.oriontekchallenge;
    exports com.interview.oriontekchallenge.model;
    exports com.interview.oriontekchallenge.controller;
    exports com.interview.oriontekchallenge.dao;
    opens com.interview.oriontekchallenge.controller to javafx.fxml;
    exports com.interview.oriontekchallenge.service;
    opens com.interview.oriontekchallenge.service to javafx.fxml;
}