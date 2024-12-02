package duckHub.frontend;

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

public class LoginPage extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage IntroductionStage) throws Exception {
        IntroductionStage.setTitle("Duck-Hub");

        // full screen window
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        IntroductionStage.setWidth(screenWidth);
        IntroductionStage.setHeight(screenHeight);

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

        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 0);
        TextField emailField = new TextField();
        grid.add(emailField, 1, 0);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 1);
        TextField passwordField = new TextField();
        grid.add(passwordField, 1, 1);

        // log-in button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            boolean status = login(emailField.getText(),passwordField.getText());
            if(status) {
                // kind of flag to change to the next page
            }
        });
        // sign-up button
        Button signupButton = new Button("Don't have account? Sign up");

        VBox mainLayout = new VBox();
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

        IntroductionStage.setScene(scene);
        IntroductionStage.show();
    }
    private boolean login(String email, String password) {
        if(email.isEmpty() || password.isEmpty()) {
            PopUp.display(true,"Login Error","All fields are required");
            return false;
        }
        if(!isEmailValid(email)){
            PopUp.display(true,"Login Error","Invalid email format");
            return false;
        }
        return Database.login(email,password);
    }
    public boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}