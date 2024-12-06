package duckHub.frontend.feed;

import duckHub.MainDuck;
import duckHub.backend.BackendDuck;
import duckHub.backend.User;
import duckHub.frontend.common.ButtonCustomizer;
import duckHub.frontend.common.ContentConvertor;
import duckHub.frontend.Constants;
import duckHub.frontend.common.Refreshable;
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
import duckHub.frontend.titleBar.TitleBar;
import java.util.ArrayList;

public class FeedPage implements Constants, Refreshable {
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

    // layout to hold the title bar and all the other content
    private VBox mainContainer;

    // the specific reference of the user whose feed this is
    private User user;

    // MainDuck to call the profile
    private MainDuck mainDuck;

    // add the title bar
    private TitleBar titleBar;

    @Override
    public void refreshWindow() {
        allPostsVBox.getChildren().clear();
        storiesHBox.getChildren().clear();
        showUserPhoto();
        showPeopleWithStories(user.getFriends());
        showPosts(user);
        showSuggestedFriends();
    }

    private void layoutsInitializer() {
        // main container
        mainContainer = new VBox();

        // posts layout
        root = new BorderPane();
        postsScrollPane = new ScrollPane();
        allPostsVBox = new VBox();

        // stories layouts
        storiesHBox = new HBox();
        storiesHBox.setSpacing(0);
        storiesScrollPane = new ScrollPane();
        feedStackPane = new StackPane();

        // suggested friends layout
        suggestedFriendScrollPane = new ScrollPane();

        storiesScrollPane.setFitToHeight(true);
        storiesScrollPane.setContent(storiesHBox);

        // initialize title bar
        titleBar = new TitleBar(mainDuck,user);
    }

    private void layoutsOrganizer() {
        // add title bar and all others
        VBox.setVgrow(root, Priority.ALWAYS);
        mainContainer.getChildren().addAll(titleBar.getTitleBar(), root);

        postsScrollPane.setFitToWidth(true);
        postsScrollPane.setPrefViewportWidth(400);
        postsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        postsScrollPane.setContent(allPostsVBox);
        feedStackPane.getChildren().add(postsScrollPane);
        storiesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        root.setCenter(feedStackPane);
        root.setTop(storiesScrollPane);
        root.setRight(suggestedFriendScrollPane);
    }

    private void setScene() {
        scene = new Scene(mainContainer, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private void showPosts(User user) {
        ContentConvertor contentConvertor = new ContentConvertor();
        contentConvertor.convertPostsToNodes(user,postsScrollPane ,allPostsVBox);

        ArrayList<String> friendIds = user.getFriends();
        for (String id : friendIds){
            User friend = BackendDuck.getUserByID(id);
            contentConvertor.convertPostsToNodes(friend,postsScrollPane,allPostsVBox);
        }

    }

    private void showPeopleWithStories(ArrayList<String> friendsIds) {
        for (String id : friendsIds) {
            User user = BackendDuck.getUserByID(id);
//            assert user != null;
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
            mainDuck.showProfilePage();
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

    private void showSuggestedFriends(){
        ContentConvertor convertor = new ContentConvertor();
        suggestedFriendScrollPane.setContent(convertor.populateList(user, user.getSuggestedFriends(), "suggested"));
    }

    public Scene getScene(MainDuck mainDuck,User user) {
        this.mainDuck = mainDuck;
        this.user = user;
        layoutsInitializer();
        layoutsOrganizer();
        setScene();
        showUserPhoto();
        showPeopleWithStories(user.getFriends());
        showPosts(user);
        showSuggestedFriends();
        showContentButton();
        return scene;
    }
}
