package duckHub.frontend;

import duckHub.MainDuck;
import duckHub.backend.SignupBackend;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Objects;

public class SignupPage {
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
        appTitle.getStyleClass().add("Duck-Hub");

        // login title
        Label formTitle = new Label("Sign up");

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

        Label dateOfBirthLabel = new Label("Date of Birth:");
        grid.add(dateOfBirthLabel, 0, 1);
        DatePicker datePicker = new DatePicker();
        datePicker.editableProperty().set(false);

        grid.add(datePicker, 1, 1);


        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 2);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 2);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 3);
        TextField passwordField = new TextField();
        grid.add(passwordField, 1, 3);

        // sign-up button
        Button signupButton = new Button("Sign Up");
        signupButton.setOnAction(e -> {
            boolean state = signup(emailField.getText(),datePicker.getValue(),usernameField.getText(),passwordField.getText());
            if(state) {
                mainDuck.showNewsfeed(); // need to pass username ?
            }
            else{
                PopUp.display(true,"Error","Something went wrong");
            }
;        });

        // back to login page
        Button backButton = new Button("Already have account? login");
        backButton.setOnAction(e -> {
            mainDuck.showLoginPage();
        });

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
        mainLayout.getChildren().add(signupButton);
        mainLayout.getChildren().add(backButton);

        Scene scene = new Scene(mainLayout, screenWidth, screenHeight);
        try{
            String styles = Objects.requireNonNull(getClass().getResource("/duckHub/Styles.css")).toExternalForm();
            scene.getStylesheets().add(styles);
        }catch (Exception e){
            System.out.println("StylesSheet unavailable");
        }

        return scene;
    }
    private boolean signup(String email,LocalDate dateOfBirth,String username,String password) {
        if(email.isEmpty()||username.isEmpty()||password.isEmpty()|| dateOfBirth == null) {
            PopUp.display(true,"Error","All fields are required");
            return false;
        }
        if(!isEmailValid(email)) {
            PopUp.display(true,"Error","Email is invalid");
            return false;
        }
        if(dateOfBirth.isAfter(LocalDate.now())) {
            PopUp.display(true,"Error","Date of birth is invalid");
            return false;
        }
        return SignupBackend.signup(email,dateOfBirth,username,password);
    }

    public boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}