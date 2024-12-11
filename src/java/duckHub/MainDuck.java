package duckHub;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import duckHub.backend.User;
import duckHub.backend.database.Load;
import duckHub.backend.database.Save;
import duckHub.frontend.friends.FriendsPage;
import duckHub.frontend.LoginPage;
import duckHub.frontend.SignupPage;
import duckHub.frontend.feed.FeedPage;
import duckHub.frontend.profile.Profile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MainDuck extends Application {
    private Stage primaryStage;
    private User user;
    private FeedPage feedPage;
    private FriendsPage friendsPage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        User testUser = new User();
        testUser.initializeLists();
        testUser.getFriends().add("friend1");
        testUser.getPendingReceived().add("request1");

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        try {
            String json = mapper.writeValueAsString(testUser);
            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                user.logOut();
            }
            Save save = new Save();
            save.saveAllUsers();
            primaryStage.close();
        }

    }

    public void showLoginPage() {
        System.out.println("Switching to Login Page");
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
        feedPage = new FeedPage();
        Scene feedScene = feedPage.getScene(this, user);
        primaryStage.setScene(feedScene);
        System.out.println("############################");
        System.out.println(user.getFriends());
        System.out.println(user.getPendingReceived());
        System.out.println(user.getPendingSent());
        System.out.println("############################");
    }

    public void showFriendsPage(User user, String type) {
        System.out.println("Switching to Friends Page");
        friendsPage = new FriendsPage();
        Scene friendsScene = friendsPage.display(this, user, type);
        primaryStage.setScene(friendsScene);
    }

    public void showProfilePage() {
        System.out.println("Switching to Profile Page");
        Profile profile = new Profile();
        Scene profileScene = profile.displayScene(this, user);
        primaryStage.setScene(profileScene);
    }

    public void refresh() {
        Load load = new Load();
        load.loadFromFile();
        feedPage.refreshWindow();
        friendsPage.refreshWindow();
    }
}