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
    private Button nextStoryButton;
    private Button previousStoryButton;
    private ImageView imageView;

    private void setStage() {
        sceneWidth = 600;
        sceneHeight = 500;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(storyPane, sceneWidth, sceneHeight);
        stage.setScene(scene);
    }

    private void initializeLayouts() {
        storyPane = new StackPane();
        storyPane.setMaxWidth(sceneWidth);
        storyPane.setMaxHeight(sceneHeight);
        userDataLayout = new HBox();
    }

    private void showStoryContent(Story story) {
        storyPane.getChildren().clear();
        userDataLayout.getChildren().clear();
        Label userName = new Label(user.getUsername());
        userName.setMinHeight(5);
        userPane = user.roundedProfileImage(10,false);
        userPane.setMaxWidth(20);
        userPane.setMaxHeight(20);
        userDataLayout.getChildren().addAll(userPane, userName);
        userDataLayout.setAlignment(Pos.TOP_LEFT);
        Image image = story.getContentImage();
        imageView = new ImageView(image);
        storyPane.getChildren().addAll(imageView, userDataLayout);
        navigateStoriesButtons();
    }

    private void navigateStoriesButtons() {
        nextStoryButton = new Button("Next");
        storyPane.getChildren().add(nextStoryButton);
        StackPane.setAlignment(nextStoryButton, Pos.CENTER_RIGHT);
        nextStoryButton.setOnAction(_ -> {
            if (storyCounter < stories.size()-1) {
                showStoryContent(stories.get(++storyCounter));
            } else {
                stage.close();
            }
        });

        previousStoryButton = new Button("Previous");
        if(storyCounter > 0)
            storyPane.getChildren().add(previousStoryButton);
        StackPane.setAlignment(previousStoryButton, Pos.CENTER_LEFT);
        previousStoryButton.setOnAction(_ -> {
            if (storyCounter == 0)
                return;
            else if (storyCounter < stories.size()) {
                showStoryContent(stories.get(--storyCounter));
            } else {
                stage.close();
            }
        });
    }

    public void display(User user) {
        this.user = user;
        stories = user.getStories();
        initializeLayouts();
        showStoryContent(stories.getFirst());
        setStage();
        stage.showAndWait();
    }
}
