package duckHub.frontend.feed;

import duckHub.backend.Story;
import duckHub.backend.User;
import javafx.geometry.Insets;
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
import jdk.swing.interop.SwingInterOpUtils;

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
    private Button nextStoryButton;
    private Button previousStoryButton;
    private ImageView imageView;

    private void setStage(){
        sceneWidth = 600;
        sceneHeight = 500;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(storyPane, sceneWidth, sceneHeight);
        stage.setScene(scene);
    }

    private void initializeLayouts(){
        storyPane = new StackPane();
        storyPane.setMaxWidth(sceneWidth);
        storyPane.setMaxHeight(sceneHeight);
        userDataLayout = new HBox();
    }

    private void organizeUserDataLayout(){
        // Set the user data
        Label userName = new Label(user.getUsername());
        userPane = user.RoundedProfileImage();
        System.out.println("userPane: " + userPane);
        userDataLayout.getChildren().addAll(userPane,userName);
        storyPane.getChildren().add(userDataLayout);
        StackPane.setAlignment(userDataLayout, Pos.CENTER_RIGHT);
        System.out.println("storyPane: " + storyPane);
    }

    private void showStory(Story story) {
        storyPane.getChildren().clear();
        System.out.println("Story: " + story);
        // show the story
        Image image = story.getContentImage();
        imageView = new ImageView(image);
        imageView.setFitHeight(sceneHeight);
        imageView.setFitWidth(sceneWidth);
        storyPane.getChildren().add(imageView);
    }

    private void navigateStoriesButtons(){
        nextStoryButton = new Button("Next");
        storyPane.getChildren().add(nextStoryButton);
        StackPane.setAlignment(nextStoryButton, Pos.CENTER_RIGHT);
        nextStoryButton.setOnAction(e -> {
            if (storyCounter < stories.size()){
//                storyPane.getChildren().remove(imageView);
                System.out.println("story counter: " + storyCounter);
                System.out.println("Story Image" + stories.get(storyCounter));
                showStory(stories.get(storyCounter++));
                organizeUserDataLayout();
                navigateStoriesButtons();
            }else {
                stage.close();
            }
        });

        previousStoryButton = new Button("Previous");
        storyPane.getChildren().add(previousStoryButton);
        StackPane.setAlignment(previousStoryButton, Pos.CENTER_LEFT);
        previousStoryButton.setOnAction(e -> {
            if (storyCounter < stories.size()){
//                storyPane.getChildren().remove(imageView);
                System.out.println("story counter: " + storyCounter);
                System.out.println("Story Image" + stories.get(storyCounter));
                showStory(stories.get(storyCounter--));
                organizeUserDataLayout();
                navigateStoriesButtons();
            }else {
                stage.close();
            }
        });
    }

    public void display(User user) {
        this.user = user;
        stories = user.getStories();
        System.out.println("display");
        initializeLayouts();
        System.out.println("initialized layouts");

        System.out.println("stage set");
        organizeUserDataLayout();
        showStory(stories.getFirst());
        storyCounter++;
        navigateStoriesButtons();
        setStage();
        stage.showAndWait();
    }
}
