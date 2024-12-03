package duckHub;

import duckHub.backend.User;
import duckHub.frontend.FriendsPage;
import duckHub.frontend.LoginPage;
import duckHub.frontend.SignupPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainDuck extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("DuckHub");
//TODO        showFriendsPage(); // Start with Login Page

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
    public void showNewsfeed(){
        System.out.println("Switching to Newsfeed Page");
        // yalla ya nigo
    }
    public void showFriendsPage(User user){
        System.out.println("Switching to Friends Page");
        FriendsPage friendsPage = new FriendsPage();
        Scene friendsScene = friendsPage.getScene(this,user);
        primaryStage.setScene(friendsScene);
    }
}