package duckHub.frontend.friendsInterface;

import duckHub.backend.BackendDuck;
import duckHub.backend.FriendsManagement;
import duckHub.backend.User;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FriendsScene {

    FriendsManagement friendsManagement;
    // to test
    private Stage stage;

    // Displayed Scene
    private Scene scene;

    // ArrayList of all the posts that will be shown
    private ArrayList<VBox> friendsList;

    // Layouts
    private FlowPane rootFlowPane;
    private BorderPane root;
    private ScrollPane friendsScrollPane;
    private FlowPane friendsFlowPane;
    private VBox allFriendsVBox;
    private VBox friendVBox;


    private void layoutsInitializer() {
        root = new BorderPane();
        friendsScrollPane = new ScrollPane();
        allFriendsVBox = new VBox();
    }

    private void layoutsOrganizer() {
        friendsScrollPane.setContent(allFriendsVBox);
        root.setLeft(friendsScrollPane);
    }

    private void setScene() {
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    private void showFriends(User user) {
        convertFriendsToNodes(user.getFriends());
    }

    private void convertFriendsToNodes(ArrayList<String> friends) {
        for (String friendID : friends) {
            User friend = BackendDuck.getUserByID(friendID);

            Label friendUsername = new Label(friend.getUsername());
            Label friendStatus;
            if (friend.getStatus())
                friendStatus = new Label("online");
            else
                friendStatus = new Label("offline");


//            Image imageContent = post.getContentImage();
//            ImageView imageView = new ImageView(imageContent);
//            // ###########################
//            // TODO: set size for the image
//            imageView.setFitHeight(100);
//            imageView.setFitWidth(100);
//            // ###########################
//            Label timeStampLabel = new Label(formatter.format(post.getTimestamp()));
//            VBox postVBox = new VBox();
//            postVBox.getChildren().addAll(authorName, textArea);
//            if (imageContent != null) {
//                postVBox.getChildren().add(imageView);
//            }
//            postVBox.getChildren().add(timeStampLabel);
//            allFriendsVBox.getChildren().add(postVBox);
        }
    }

    public void displayScene(User user) {
        layoutsInitializer();
        layoutsOrganizer();
        setScene();
        friendsManagement = new FriendsManagement(user);
        showFriends(user);
        stage.show();
    }
}
