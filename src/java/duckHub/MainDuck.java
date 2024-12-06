package duckHub;

import duckHub.backend.BackendDuck;
import duckHub.backend.User;
import duckHub.frontend.LoginPage;
import duckHub.frontend.SignupPage;
import duckHub.frontend.profile.Profile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;

public class MainDuck extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("DuckHub");
        showLoginPage(); // Start with Login Page
        primaryStage.show();
    }

    public void showLoginPage() {
        System.out.println("Switching to Login Page");
        LoginPage loginPage = new LoginPage();
        Scene loginScene = loginPage.getScene(this);
        primaryStage.setScene(loginScene);
    }

    public void showSignupPage() {
        System.out.println("Switching to Signup Page");
        SignupPage signupPage = new SignupPage();
        Scene signupScene = signupPage.getScene(this);
        primaryStage.setScene(signupScene);
    }

    public void showNewsfeed() {
        System.out.println("Switching to Newsfeed Page");
        // yalla ya nigo
    }

    public void showProfilePage() {
        System.out.println("Switching to Profile Page");
        Profile profile = new Profile();
        Scene profileScene = profile.displayScene(this, user);
        primaryStage.setScene(profileScene);
    }
}