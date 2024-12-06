package duckHub.frontend.feed;

import duckHub.MainDuck;
import duckHub.backend.BackendDuck;
import duckHub.backend.User;
import duckHub.frontend.ContentConvertor;
import duckHub.frontend.SizeConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainScene implements SizeConstants {
    // to test
//    private Stage stage;

    // Displayed Scene
    private Scene scene;

    // Layouts
    private BorderPane root;
    private ScrollPane postsScrollPane;
    private ScrollPane storiesScrollPane;
    private HBox storiesHBox;
    private VBox allPostsVBox;
    private StackPane feedStackPane;
    private ScrollPane suggestedFriendScrollPane;

    // layout for both refresh button and stories hbox
    private HBox topContainer;


    // the specific reference of the user whose feed this is
    private User user;

    private void refreshWindow() {
        allPostsVBox.getChildren().clear();
        storiesHBox.getChildren().clear();
        showUserPhoto();
        showPeopleWithStories(BackendDuck.getUsers());
        showPosts(user);
        showSuggestedFriends();
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

        // suggested friends layout
        suggestedFriendScrollPane = new ScrollPane();

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

        root.setCenter(feedStackPane);
        root.setTop(topContainer);
        root.setRight(suggestedFriendScrollPane);
    }

    private void setScene() {
        scene = new Scene(root, 600, 500);
    }

    private void showPosts(User user) {
        ContentConvertor contentConvertor = new ContentConvertor();
        contentConvertor.convertPostsToNodes(user,postsScrollPane ,allPostsVBox);
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

    private void showSuggestedFriends(){
        ContentConvertor convertor = new ContentConvertor();
        suggestedFriendScrollPane.setContent(convertor.populateList(user.getSuggestedFriends(), "suggested"));

    }

    public Scene getScene(User user) {
        this.user = user;
        layoutsInitializer();
        layoutsOrganizer();
        setScene();
        showUserPhoto();
        showPeopleWithStories(BackendDuck.getUsers());
        showPosts(user);
        showSuggestedFriends();
        showContentButton();
        showRefreshButton();
        return scene;
    }
}
