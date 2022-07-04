module com.interview.oriontekchallenge {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires java.persistence;
    requires spring.data.jpa;


    opens com.interview.oriontekchallenge to javafx.fxml, spring.core;
    exports com.interview.oriontekchallenge;
    exports com.interview.oriontekchallenge.model;
    exports com.interview.oriontekchallenge.controller;
    opens com.interview.oriontekchallenge.controller to javafx.fxml;
}