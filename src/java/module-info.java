module LAB.duckHub {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.base;
    exports duckHub.frontend to javafx.graphics;
    opens duckHub;
}