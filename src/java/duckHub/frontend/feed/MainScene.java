package duckHub.frontend.feed;

import duckHub.MainDuck;
import duckHub.backend.Post;
import duckHub.backend.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private StackPane feedStackPane;

    // Window buttons
    private Button newContentButton;
    private Button newPostButton;
    private Button newStoryButton;
    private Button refreshButton;


    // the specific reference of the user whose feed this is
    private User user;

    private void refreshWindow() {
        allPostsVBox.getChildren().clear();
        storiesHBox.getChildren().clear();
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
        root.setTop(storiesScrollPane);
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
            postVBox.getChildren().addAll(authorName, textArea);
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
            StackPane stackPane = user.roundedProfileImage(25);
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

    private void showContentButton() {
        newContentButton = new Button();
        newPostButton = new Button();
        newStoryButton = new Button();

        Image newContentLogo = new Image("/duckhub/frontend/duck.jpg");
        ImageView newContentLogoView = new ImageView(newContentLogo);
        Image newPostLogo = new Image("/duckhub/frontend/duck.jpg");
        ImageView newPostLogoView = new ImageView(newPostLogo);
        Image newStoryLogo = new Image("/duckhub/frontend/duck.jpg");
        ImageView newStoryLogoView = new ImageView(newStoryLogo);

        ButtonCustomizer buttonCustomizer = new ButtonCustomizer();
        buttonCustomizer.roundedButtonImage(newContentLogoView, newContentButton);
        buttonCustomizer.roundedButtonImage(newPostLogoView, newPostButton);
        buttonCustomizer.roundedButtonImage(newStoryLogoView, newStoryButton);

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
           newPost.display(user,true);
        });

        newStoryButton.setOnAction(_ -> {
           NewContent newStory = new NewContent();
           newStory.display(user,false);
        });

        // Ensure buttons still receive mouse events
        newContentButton.setMouseTransparent(false);
        newPostButton.setMouseTransparent(false);
        newStoryButton.setMouseTransparent(false);
    }

    private void showRefreshButton() {
        refreshButton = new Button();
        Image refreshLogo = new Image("/duckhub/frontend/refresh.png");
        ImageView refreshLogoView = new ImageView(refreshLogo);

        ButtonCustomizer buttonCustomizer = new ButtonCustomizer();
        buttonCustomizer.roundedButtonImage(refreshLogoView, refreshButton);

        // Position refresh button
        StackPane.setAlignment(refreshButton, Pos.TOP_RIGHT);
        StackPane.setMargin(refreshButton, new Insets(10, 10, 0, 0));
        feedStackPane.getChildren().add(refreshButton);

        // Refresh button handler
        refreshButton.setOnAction(_ -> refreshWindow());
        refreshButton.setMouseTransparent(false);
    }

    public void displayScene(User user) {
        this.user = user;
        layoutsInitializer();
        layoutsOrganizer();
        setScene();
        showPeopleWithStories(MainDuck.users);
        showPosts(user);
        showContentButton();
        showRefreshButton();
        stage.show();
    }
}