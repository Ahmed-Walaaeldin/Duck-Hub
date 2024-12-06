package duckHub.frontend.feed;

import duckHub.MainDuck;
import duckHub.backend.Post;
import duckHub.backend.User;
import duckHub.frontend.SizeConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainScene implements SizeConstants {
    // to test
    private Stage stage;

    // Displayed Scene
    private Scene scene;

    // Layouts
    private BorderPane root;
    private ScrollPane postsScrollPane;
    private ScrollPane storiesScrollPane;
    private HBox storiesHBox;
    private VBox allPostsVBox;
    private StackPane feedStackPane;

    // layout for both refresh button and stories hbox
    private HBox topContainer;


    // the specific reference of the user whose feed this is
    private User user;

    private void refreshWindow() {
        allPostsVBox.getChildren().clear();
        storiesHBox.getChildren().clear();
        showUserPhoto();
        showPeopleWithStories(MainDuck.users);
        showPosts(user);
    }

    private void layoutsInitializer() {

        // posts layout
        root = new BorderPane();
        postsScrollPane = new ScrollPane();
        allPostsVBox = new VBox();

        // stories layouts
        storiesHBox = new HBox();
        storiesHBox.setSpacing(0);
        storiesScrollPane = new ScrollPane();
        feedStackPane = new StackPane();
        topContainer = new HBox();

        storiesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        storiesScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        storiesScrollPane.setFitToHeight(true);
        storiesScrollPane.setContent(storiesHBox);
    }

    private void layoutsOrganizer() {
        postsScrollPane.setFitToWidth(true);
        postsScrollPane.setPrefViewportWidth(400);
        postsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        postsScrollPane.setContent(allPostsVBox);
        feedStackPane.getChildren().add(postsScrollPane);

        root.setLeft(feedStackPane);
        root.setTop(topContainer);
    }

    private void setScene() {
        stage = new Stage();
        scene = new Scene(root, 600, 500);
        stage.setScene(scene);
    }

    private void showPosts(User user) {
        convertPostsToNodes(user.getPosts(), allPostsVBox);
    }

    private void convertPostsToNodes(ArrayList<Post> posts, VBox layout) {
        // Sort posts by timestamp in descending order (most recent first)
        posts.sort((post1, post2) -> post2.getTimestamp().compareTo(post1.getTimestamp()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Post post : posts) {
            HBox nameAndPhotoLayout = new HBox();

            StackPane userPhotoRounded = user.roundedProfileImage(15, false);
            nameAndPhotoLayout.getChildren().add(userPhotoRounded);

            VBox postVBox = new VBox();
            Label authorName = new Label(post.getAuthorId());
            nameAndPhotoLayout.getChildren().add(authorName);
            postVBox.getChildren().addAll(nameAndPhotoLayout);

            if (!post.getContentText().isEmpty()) {
                TextArea textArea = new TextArea(post.getContentText());
                textArea.setEditable(false);
                textArea.setWrapText(true);
                textArea.setPrefWidth(postsScrollPane.getPrefViewportWidth() - 20); // for padding

                // Calculate the preferred height based on the content
                int lines = textArea.getText().split("\n").length;
                double lineHeight = 20; // Approximate height of a line in pixels
                textArea.setPrefHeight(lines * lineHeight);
                postVBox.getChildren().add(textArea);
            }
            Image imageContent = post.getContentImage();
            ImageView imageView = new ImageView(imageContent);
            if (imageContent != null) {
//                ImageView imageView = new ImageView(imageContent);

                //original image dimensions
                double originalWidth = imageContent.getWidth();
                double originalHeight = imageContent.getHeight();
                double aspectRatio = originalWidth / originalHeight;

                // Set maximum dimensions
                double maxWidth = postsScrollPane.getPrefViewportWidth() - 40;
                double maxHeight = 400;

                // Calculate new dimensions
                double newWidth = maxWidth;
                double newHeight = maxWidth / aspectRatio;

                // If height exceeds maximum, scale based on height instead
                if (newHeight > maxHeight) {
                    newHeight = maxHeight;
                    newWidth = maxHeight * aspectRatio;
                }

                imageView.setFitWidth(newWidth);
                imageView.setFitHeight(newHeight);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
            }
            Label timeStampLabel = new Label(formatter.format(post.getTimestamp()));
//            timeStampLabel.setAlignment(Pos.CENTER_RIGHT);

            if (imageContent != null) {
                postVBox.getChildren().add(imageView);
            }
            postVBox.getChildren().add(timeStampLabel);
            layout.getChildren().add(postVBox);
        }
    }

    private void showPeopleWithStories(ArrayList<User> users) {
        for (User user : users) {
            if (!user.getStories().isEmpty()) {
                StackPane stackPane = user.roundedProfileImage(25, true);
                Button userStoryButton = new Button();
                userStoryButton.setGraphic(stackPane);
                userStoryButton.getStyleClass().add("story-button");
                userStoryButton.setStyle("-fx-background-color: transparent;");
                userStoryButton.setOnAction(_ -> {
                    StoryWindow storyWindow = new StoryWindow();
                    storyWindow.display(user);
                });
                storiesHBox.getChildren().add(userStoryButton);
            }
        }
    }

    // The method that will be used to show and always fix the user photo in the top left of the page whether he has a story or no.
    private void showUserPhoto() {
        // The button into the user photo
        Button profileButton = new Button();
        Image userImage = user.getUserProfileImage();
        ImageView userImageView = new ImageView(userImage);
        ButtonCustomizer buttonCustomizer = new ButtonCustomizer();
        buttonCustomizer.roundedButtonImage(userImageView, profileButton, PROFILE_IMAGE_RADIUS);

        // add the button to the stories HBox
        storiesHBox.getChildren().add(profileButton);

        // the button handler
        profileButton.setOnAction(_ -> {
            // TODO: call the profile scene (Roupha's)

        });
    }

    private void showContentButton() {
        // Window buttons
        Button newContentButton = new Button();
        Button newPostButton = new Button();
        Button newStoryButton = new Button();

        Image newContentLogo = new Image("/duckhub/frontend/add-content.png");
        ImageView newContentLogoView = new ImageView(newContentLogo);
        Image newPostLogo = new Image("/duckhub/frontend/add-post.png");
        ImageView newPostLogoView = new ImageView(newPostLogo);
        Image newStoryLogo = new Image("/duckhub/frontend/add-story.png");
        ImageView newStoryLogoView = new ImageView(newStoryLogo);

        ButtonCustomizer buttonCustomizer = new ButtonCustomizer();
        buttonCustomizer.roundedButtonImage(newContentLogoView, newContentButton, ROUNDED_BUTTON_RADIUS);
        buttonCustomizer.roundedButtonImage(newPostLogoView, newPostButton, ROUNDED_BUTTON_RADIUS);
        buttonCustomizer.roundedButtonImage(newStoryLogoView, newStoryButton, ROUNDED_BUTTON_RADIUS);

        Rectangle overlay = new Rectangle(65, 120, Color.rgb(190, 190, 190));
        overlay.setArcHeight(15);
        overlay.setArcWidth(15);

        VBox newContentButtonsLayout = new VBox();
        newContentButtonsLayout.setAlignment(Pos.BOTTOM_RIGHT);
        newContentButtonsLayout.getChildren().addAll(newPostButton, newStoryButton);

        StackPane overlayNewContentLayout = new StackPane(overlay, newContentButtonsLayout);
        overlayNewContentLayout.setAlignment(Pos.BOTTOM_RIGHT);
        overlayNewContentLayout.setVisible(false);

        newContentButton.setOnAction(_ -> overlayNewContentLayout.setVisible(!overlayNewContentLayout.isVisible()));

        VBox newContentVBox = new VBox(overlayNewContentLayout, newContentButton);
        newContentVBox.setPadding(new Insets(0, 5, 10, 0));
        newContentVBox.setAlignment(Pos.BOTTOM_RIGHT);

        newContentVBox.setMouseTransparent(false);
        newContentVBox.setPickOnBounds(false);
        overlayNewContentLayout.setPickOnBounds(false);

        // Add the VBox to feedStackPane with proper alignment
        feedStackPane.getChildren().add(newContentVBox);
        StackPane.setAlignment(newContentVBox, Pos.BOTTOM_RIGHT);

        // Call the new post window
        newPostButton.setOnAction(_ -> {
            NewContent newPost = new NewContent();
            newPost.display(user, true);
        });

        newStoryButton.setOnAction(_ -> {
            NewContent newStory = new NewContent();
            newStory.display(user, false);
        });

        // Ensure buttons still receive mouse events
        newContentButton.setMouseTransparent(false);
        newPostButton.setMouseTransparent(false);
        newStoryButton.setMouseTransparent(false);
    }

    private void showRefreshButton() {
        Button refreshButton = new Button();
        Image refreshLogo = new Image("/duckhub/frontend/refresh.png");
        ImageView refreshLogoView = new ImageView(refreshLogo);

        ButtonCustomizer buttonCustomizer = new ButtonCustomizer();
        buttonCustomizer.rectangleButtonImage(refreshLogoView, refreshButton);

        // Position refresh button
        StackPane.setAlignment(refreshButton, Pos.TOP_RIGHT);
        StackPane.setMargin(refreshButton, new Insets(10, 10, 0, 0));

        // add both refresh button and stories layout in one final top layout
        topContainer.getChildren().addAll(storiesScrollPane, refreshButton);
        HBox.setHgrow(storiesScrollPane, Priority.ALWAYS);

        // Refresh button handler
        refreshButton.setOnAction(_ -> refreshWindow());
        refreshButton.setMouseTransparent(false);
    }

    public void displayScene(User user) {
        this.user = user;
        layoutsInitializer();
        layoutsOrganizer();
        setScene();
        showUserPhoto();
        showPeopleWithStories(MainDuck.users);
        showPosts(user);
        showContentButton();
        showRefreshButton();
        stage.show();
    }
}
