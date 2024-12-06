module LAB.duckHub {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.base;
    exports duckHub.frontend to javafx.graphics;
    requires jdk.compiler;
    requires jdk.httpserver;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires jbcrypt;
    exports duckHub.backend.database;
    exports duckHub.backend;
    opens duckHub.backend to com.fasterxml.jackson.databind;
    opens duckHub;
}