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
    private int sceneWidth;
    private int sceneHeight;
    private StackPane storyPane;
    private ArrayList<Story> stories;
    private HBox userDataLayout;
    private User user;
    private int storyCounter;

    private void setStage() {
        sceneWidth = 600;
        sceneHeight = 500;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(storyPane, sceneWidth, sceneHeight);
        stage.setScene(scene);
    }

    private void initializeLayouts() {
        storyPane = new StackPane();
        storyPane.setMaxWidth(sceneWidth);
        storyPane.setMaxHeight(sceneHeight);
        userDataLayout = new HBox();
    }

    private void showStoryContent(Story story) {
        // clear old
        storyPane.getChildren().clear();
        userDataLayout.getChildren().clear();


        Label userName = new Label(user.getUsername());
        userName.setMinHeight(5);
        StackPane userPane = user.roundedProfileImage(10, false);
        userPane.setMaxWidth(20);
        userPane.setMaxHeight(20);
        userDataLayout.getChildren().addAll(userPane, userName);
        userDataLayout.setAlignment(Pos.TOP_LEFT);
        Image image = story.getContentImage();
        ImageView imageView = new ImageView(image);

        //original image dimensions
        double originalWidth = image.getWidth();
        double originalHeight = image.getHeight();
        double aspectRatio = originalWidth / originalHeight;

        // Set the width to match the scene width
        double newWidth = sceneWidth;
        double newHeight = sceneWidth / aspectRatio;

        // If height exceeds scene height, scale based on height instead
        if (newHeight > sceneHeight) {
            newHeight = sceneHeight;
            newWidth = sceneHeight * aspectRatio;
        }

        imageView.setFitWidth(newWidth);
        imageView.setFitHeight(newHeight);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        // Center the image in the stack pane
        StackPane.setAlignment(imageView, Pos.CENTER);

        storyPane.getChildren().addAll(imageView, userDataLayout);
        navigateStoriesButtons();
    }

    private void navigateStoriesButtons() {
        Button nextStoryButton = new Button("Next");
        storyPane.getChildren().add(nextStoryButton);
        StackPane.setAlignment(nextStoryButton, Pos.CENTER_RIGHT);
        nextStoryButton.setOnAction(_ -> {
            if (storyCounter < stories.size()-1) {
                showStoryContent(stories.get(++storyCounter));
            } else {
                stage.close();
            }
        });

        Button previousStoryButton = new Button("Previous");
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
