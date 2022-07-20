module com.interview.oriontekchallenge {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;


    exports com.interview.oriontekchallenge to javafx.graphics;
    exports com.interview.oriontekchallenge.controller to javafx.fxml;
    opens com.interview.oriontekchallenge.controller to javafx.fxml;
    opens com.interview.oriontekchallenge.model to javafx.base;
}