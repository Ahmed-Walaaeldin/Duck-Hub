package duckHub;

import duckHub.backend.User;
import duckHub.backend.database.Load;
import duckHub.backend.database.Save;
import duckHub.frontend.FriendsPage;
import duckHub.frontend.LoginPage;
import duckHub.frontend.SignupPage;
import duckHub.frontend.feed.FeedPage;
import duckHub.frontend.profile.Profile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class MainDuck extends Application {
    private Stage primaryStage;
    private User user;
    private Stage mainStage;
    private FeedPage feedPage;

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
        if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
            if (user != null) {
                // set the status to offline and save the data.
                user.setStatus(false);
            }
            Save save = new Save();
            save.saveAllUsers();

            // Close both stages
            if (mainStage != null) {
                mainStage.close();
            }
            primaryStage.close();
        }

    }

    public void showLoginPage() {
        System.out.println("Switching to Login Page");
        if (mainStage != null) {
            mainStage.hide();
        }
        LoginPage loginPage = new LoginPage();
        Scene loginScene = loginPage.getScene(this);
        primaryStage.setScene(loginScene);
        primaryStage.show();
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

        // Create new stage for the main application
        if (mainStage == null) {
            mainStage = new Stage();
            mainStage.initStyle(StageStyle.UNDECORATED);
            mainStage.setOnCloseRequest(this::handleCloseRequest);
        }

        feedPage = new FeedPage();
        Scene feedScene = feedPage.getScene(this, user);
        mainStage.setScene(feedScene);
        primaryStage.hide();
        mainStage.show();
    }

    public void showFriendsPage(User user, String type) {
        System.out.println("Switching to Friends Page");
        FriendsPage friendsPage = new FriendsPage();
        Scene friendsScene = friendsPage.getScene(this, user, type);
        mainStage.setScene(friendsScene);
    }

    public void showProfilePage() {
        System.out.println("Switching to Profile Page");
        Profile profile = new Profile();
        Scene profileScene = profile.displayScene(this, user);
        mainStage.setScene(profileScene);
    }

    public void refresh() {
        feedPage.refreshWindow();
    }
}