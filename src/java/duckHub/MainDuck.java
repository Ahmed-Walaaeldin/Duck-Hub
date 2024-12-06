package duckHub;

import duckHub.backend.BackendDuck;
import duckHub.backend.User;
import duckHub.backend.database.Load;
import duckHub.backend.database.Save;
import duckHub.frontend.FriendsPage;
import duckHub.backend.BackendDuck;
import duckHub.backend.User;
import duckHub.frontend.LoginPage;
import duckHub.frontend.SignupPage;
import duckHub.frontend.feed.MainScene;
import duckHub.frontend.profile.Profile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.time.LocalDate;

public class MainDuck extends Application {
    private Stage primaryStage;
    private User user;

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

        // close button handler
        primaryStage.setOnCloseRequest(this::handleCloseRequest);

        primaryStage.show();
    }

    public void handleCloseRequest(WindowEvent event) {
        if (user != null) {
            // set the status to offline and save the data.
            user.setStatus(false);
        }
        Save save = new Save();
        save.saveAllUsers();
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

    public void showNewsfeed(User user) {
        this.user = user;
        System.out.println("Switching to Newsfeed Page");
        MainScene feedPage = new MainScene();
        Scene feedScene = feedPage.getScene(this, user);
        primaryStage.setScene(feedScene);
    }

    public void showFriendsPage(User user, String type) {
        System.out.println("Switching to Friends Page");
        FriendsPage friendsPage = new FriendsPage();
        Scene friendsScene = friendsPage.getScene(this, user, type);
        primaryStage.setScene(friendsScene);
    }

    public void showProfilePage() {
        System.out.println("Switching to Profile Page");
//        File imageFile = new File("src/Resources/duckHub/frontend/duckStory1.jpeg");
//        Image profileImage = new Image(imageFile.toURI().toString());
//        user.setUserProfileImage(profileImage);
//        user.createContent(true, "enta al kbir ya walaa");
//        user.createContent(true, "enta al kbir ya walaa");
//        user.createContent(true, "enta al kbir ya walaa");
//        user.createContent(true, "enta al kbir ya walaa");
//        user.createContent(true, "enta al kbir ya walaa");
//        user.createContent(true, "enta al kbir ya walaa");
//        user.createContent(true, "enta al kbir ya walaa");
//        user.createContent(true, "enta al kbir ya walaa");
//        user.setBioContent("hello asfadsf\nhello walaa");
        Profile profile = new Profile();
        Scene profileScene = profile.displayScene(this,user);
        primaryStage.setScene(profileScene);
    }

    public void refresh(){

    }
}