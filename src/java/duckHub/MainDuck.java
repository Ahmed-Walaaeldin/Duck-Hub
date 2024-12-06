package duckHub;

import duckHub.backend.BackendDuck;
import duckHub.backend.User;
import duckHub.backend.database.Load;
import duckHub.frontend.FriendsPage;
import duckHub.frontend.LoginPage;
import duckHub.frontend.SignupPage;
import duckHub.frontend.feed.MainScene;
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

        Load load = new Load();
        load.loadFromFile();

        showLoginPage();

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
    public void showNewsfeed(User user){
        System.out.println("Switching to Newsfeed Page");
        MainScene feedPage = new MainScene();
        Scene feedScene = feedPage.getScene(user);
        primaryStage.setScene(feedScene);
    }
    public void showFriendsPage(User user,String type){
        System.out.println("Switching to Friends Page");
        FriendsPage friendsPage = new FriendsPage();
        Scene friendsScene = friendsPage.getScene(this, user, type);
        primaryStage.setScene(friendsScene);
    }
}