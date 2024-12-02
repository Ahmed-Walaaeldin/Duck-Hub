package duckHub.frontend;

import duckHub.MainDuck;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginPage {
    public Scene getScene(MainDuck mainDuck) {
        VBox mainLayout = new VBox();
        // full screen window
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        // image
        Image logo = new Image("file:duck.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(200);
        logoView.setFitWidth(300);

        // main title
        Label appTitle = new Label("Welcome to Duck Hub!");

        
        // login title
        Label formTitle = new Label("Login");
        formTitle.getStyleClass().add("Duck-Hub");

        // login form elements
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 1);
        TextField passwordField = new TextField();
        grid.add(passwordField, 1, 1);

        // log-in button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            boolean status = login(usernameField.getText(),passwordField.getText());
            if(status) {
                // kind of flag to change to the next page
            }
        });
        // sign-up button
        Button signupButton = new Button("Don't have account? Sign up");
        signupButton.setOnAction(e -> mainDuck.showSignupPage());

        mainLayout.setAlignment(Pos.TOP_CENTER);


        // spacing
        Region spacer = new Region();
        spacer.setPrefHeight(80);
        mainLayout.getChildren().add(spacer);

        mainLayout.getChildren().add(logoView);
        mainLayout.getChildren().add(appTitle);

        // spacing
        Region spacer1 = new Region();
        spacer1.setPrefHeight(40);
        mainLayout.getChildren().add(spacer1);

        mainLayout.getChildren().add(formTitle);
        mainLayout.getChildren().add(grid);
        mainLayout.getChildren().add(loginButton);
        mainLayout.getChildren().add(signupButton);

        Scene scene = new Scene(mainLayout, 300, 200);
        try{
            String styles = Objects.requireNonNull(getClass().getResource("/duckHub/Styles.css")).toExternalForm();
            scene.getStylesheets().add(styles);
        }catch (Exception e){
            System.out.println("StylesSheet unavailable");
        }

        return new Scene(mainLayout, screenWidth, screenHeight);
    }
    private boolean login(String username, String password) {
        if(username.isEmpty() || password.isEmpty()) {
            PopUp.display(true,"Login Error","All fields are required");
            return false;
        }
        return Database.login(username,password);

    }
}