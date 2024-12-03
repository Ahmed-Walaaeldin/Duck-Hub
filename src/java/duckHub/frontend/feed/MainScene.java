package duckHub.frontend.feed;

import duckHub.MainDuck;
import duckHub.backend.Post;
import duckHub.backend.User;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainScene {
    // to test
    private Stage stage;

    // Displayed Scene
    private Scene scene;

    // ArrayList of all the posts that will be shown
    private ArrayList<VBox> posts;

    // Layouts
//    private FlowPane rootFlowPane;
    private BorderPane root;
    private ScrollPane postsScrollPane;
//    private FlowPane postsFlowPane;
    private ScrollPane storiesScrollPane;
    private HBox storiesHBox;
    private VBox allPostsVBox;
    private VBox postVBox;


    private void layoutsInitializer(){
        // posts layout
        root = new BorderPane();
        postsScrollPane = new ScrollPane();
        allPostsVBox = new VBox();
        // stories layouts
        storiesHBox = new HBox();
        storiesHBox.setSpacing(0);
        storiesScrollPane = new ScrollPane();
        storiesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        storiesScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        storiesScrollPane.setFitToHeight(true);
        storiesScrollPane.setContent(storiesHBox);
    }

    private void layoutsOrganizer(){
        postsScrollPane.setContent(allPostsVBox);
        root.setLeft(postsScrollPane);
        root.setTop(storiesScrollPane);
    }

    private void setScene() {
        stage = new Stage();
        scene = new Scene(root,600,500);
        stage.setScene(scene);
    }

    private void showPosts(User user) {
        convertPostsToNodes(user.getPosts(),allPostsVBox);
    }

    private void convertPostsToNodes(ArrayList<Post> posts,VBox layout) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Post post : posts) {
            Label authorName = new Label(post.getAuthorId());
            TextArea textArea = new TextArea(post.getContentText());
            textArea.setEditable(false);
            textArea.setWrapText(true);

            // Calculate the preferred height based on the content
            int lines = textArea.getText().split("\n").length;
            double lineHeight = 20; // Approximate height of a line in pixels
            textArea.setPrefHeight(lines * lineHeight);

            Image imageContent = post.getContentImage();
            ImageView imageView = new ImageView(imageContent);
            // ###########################
            // TODO: set size for the image
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            // ###########################
            Label timeStampLabel = new Label(formatter.format(post.getTimestamp()));
            VBox postVBox = new VBox();
            postVBox.getChildren().addAll(authorName,textArea);
            if (imageContent != null) {
                postVBox.getChildren().add(imageView);
            }
            postVBox.getChildren().add(timeStampLabel);
            layout.getChildren().add(postVBox);
        }
    }

    private void showPeopleWithStories(ArrayList<User> users) {
        for (User user : users) {
//            if (!user.getStories().isEmpty()) {
                StackPane stackPane = user.RoundedProfileImage();
                Button userStoryButton = new Button();
                userStoryButton.setGraphic(stackPane);
                userStoryButton.getStyleClass().add("story-button");
                userStoryButton.setStyle("-fx-background-color: transparent;");
                userStoryButton.setOnAction(_ -> {
                    StoryWindow storyWindow = new StoryWindow();
                    storyWindow.display(user);
                });
                storiesHBox.getChildren().add(userStoryButton);
//            }
        }
    }

    public void displayScene(User user) {
        layoutsInitializer();
        layoutsOrganizer();
        setScene();
        showPeopleWithStories(MainDuck.users);
        showPosts(user);
        stage.show();
    }
}
