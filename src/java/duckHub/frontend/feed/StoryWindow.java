package duckHub.frontend.feed;

import duckHub.backend.Story;
import duckHub.backend.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StoryWindow {
    private Stage stage;
    private Scene scene;
    private int sceneWidth;
    private int sceneHeight;
    private StackPane storyPane;
    private StackPane userPane;
    private ArrayList<Story> stories;
    private HBox userDataLayout;
    private User user;
    private int storyCounter;

    private void setStage(){
        sceneWidth = 600;
        sceneHeight = 500;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(storyPane, sceneWidth, sceneHeight);
    }

    private void initializeLayouts(){
        storyPane = new StackPane();
        storyPane.setMaxWidth(sceneWidth);
        storyPane.setMaxHeight(sceneHeight);

        userDataLayout = new HBox();
    }

    private void showStory(Story story) {
        System.out.println("Story: " + story);
        // Set the user data
        Label userName = new Label(user.getUsername());
        userPane = user.RoundedProfileImage();
        System.out.println("userPane: " + userPane);
        userDataLayout.getChildren().addAll(userPane,userName);
        storyPane.getChildren().add(userDataLayout);
        System.out.println("storyPane: " + storyPane);
        userDataLayout.setAlignment(Pos.TOP_LEFT);

        // show the story
        Image image = story.getContentImage();
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(sceneHeight);
        imageView.setFitWidth(sceneWidth);
        storyPane.getChildren().add(imageView);
    }

    private void navigateStoriesButtons(){
        Button nextStoryButton = new Button("Next");
        storyPane.getChildren().add(nextStoryButton);
        nextStoryButton.setAlignment(Pos.CENTER_RIGHT);
        nextStoryButton.setOnAction(e -> {
            showStory(stories.get(storyCounter++));
        });

        Button previousStoryButton = new Button("Previous");
        storyPane.getChildren().add(previousStoryButton);
        previousStoryButton.setAlignment(Pos.CENTER_LEFT);
        previousStoryButton.setOnAction(e -> {
            showStory(stories.get(storyCounter--));
        });
    }

    public void display(User user) {
        this.user = user;
        stories = user.getStories();
        System.out.println("display");
        initializeLayouts();
        System.out.println("initialized layouts");

        System.out.println("stage set");
        showStory(stories.getFirst());
        navigateStoriesButtons();
        setStage();
        stage.showAndWait();
    }
}
