package duckHub.frontend.profile;

import duckHub.MainDuck;
import duckHub.backend.User;
import duckHub.backend.database.Save;
import duckHub.frontend.Constants;
import duckHub.frontend.common.ContentConvertor;
import duckHub.frontend.common.ImageLoader;
import duckHub.frontend.titleBar.TitleBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class Profile implements Constants {
    // Main Duck
    private MainDuck mainDuck;

    // Current user profile
    private User user;

    // to test
    private Stage stage;

    // Displayed Scene
    private Scene profileScene;

    // layouts
    private ScrollPane profileRootScrollPane;
    private VBox profileRootVBox;
    private HBox bioLayout;
    private ScrollPane allFriendsScrollPane;
    private HBox friendsHBoxLayout;
    private ScrollPane allPostsScrollPane;
    private VBox postsVBoxLayout;
    private HBox editButtonsHBoxLayout;

    private Image coverPhoto;
    private ImageView coverPhotoView;

    private Image profilePhoto;

    // Title bar
    private TitleBar titleBar;

    // main container to store title bar and all other layouts
    private VBox mainContainer;


    private void layoutInitializer() {
        // mainContainer
        mainContainer = new VBox();

        // root layout
        profileRootScrollPane = new ScrollPane();
        profileRootVBox = new VBox(20);
        // bio layout
        bioLayout = new HBox(20);
        // edit buttons layout
        editButtonsHBoxLayout = new HBox(20);
        // friends layout
        allFriendsScrollPane = new ScrollPane();
        friendsHBoxLayout = new HBox();
        // posts layout
        allPostsScrollPane = new ScrollPane();
        postsVBoxLayout = new VBox();
        // title bar
        titleBar = new TitleBar(mainDuck,user);
    }

    private void layoutOrganizer() {
        // hold title bar and other main layout
        mainContainer.getChildren().addAll(titleBar.getTitleBar(), profileRootScrollPane);

        profileRootVBox.getChildren().addAll(coverPhotoView, bioLayout, editButtonsHBoxLayout, allFriendsScrollPane, allPostsScrollPane);

        profileRootScrollPane.setContent(profileRootVBox);

        allPostsScrollPane.setFitToWidth(true);
        allPostsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        allPostsScrollPane.setContent(postsVBoxLayout);

        allFriendsScrollPane.setContent(friendsHBoxLayout);
    }

    private void setScene() {
        profileScene = new Scene(mainContainer, SCENE_WIDTH, SCENE_HEIGHT);
        stage = new Stage();
        stage.setScene(profileScene);
        profileRootScrollPane.setFitToWidth(true);
    }

    private void showCoverPhoto(User user) {
        coverPhoto = user.getUserCoverImage();
        coverPhotoView = new ImageView(coverPhoto);
        coverPhotoView.fitWidthProperty().bind(profileScene.widthProperty());
        coverPhotoView.setFitHeight(70);
    }

    private void showBioContent(User user) {
        // profile photo
        double radius = 70;
        profilePhoto = user.getUserProfileImage();
        ImageView profilePhotoView = new ImageView(profilePhoto);
        profilePhotoView.setFitHeight(radius*2);
        profilePhotoView.setFitWidth(radius*2);
        Circle newContentButtonShape = new Circle(radius, radius, radius);
        profilePhotoView.setClip(newContentButtonShape);

        // bio content
        TextArea bioContent = new TextArea();
        bioContent.setEditable(Boolean.FALSE);
        bioContent.setWrapText(true);
        bioContent.setPrefHeight(2 * radius);
        bioContent.prefWidthProperty().bind(profileScene.widthProperty().subtract(2 * radius + 70));
        bioContent.setText(user.getBioContent());

        bioLayout.setPadding(new Insets(0, 10, 0, 10));
        bioLayout.getChildren().addAll(profilePhotoView, bioContent);
    }

    private void showProfileEdits(User user) {
        ImageLoader imageLoader = new ImageLoader();
        ChangeBio changeBio = new ChangeBio();

        // change Cover Picture Button
        Button changeCoverButton = new Button("Change Cover Photo");
        changeCoverButton.setOnAction(_ -> {
            Image newImage = imageLoader.loadContentImage();
            if (newImage != null) {
                Save save = new Save();
                save.saveImageToDirectory(newImage,user);
                user.setUserCoverImage(newImage);
                coverPhotoView.setImage(newImage);
            }
        });

        // change Profile Picture Button
        Button changeProfileButton = new Button("Change Profile Photo");
        changeProfileButton.setOnAction(_ -> {
            Image newImage = imageLoader.loadContentImage();
            if (newImage != null) {
                Save save = new Save();
                save.saveImageToDirectory(newImage,user);
                user.setUserProfileImage(newImage);
                bioLayout.getChildren().clear();
                showBioContent(user);
            }
        });

        // change Bio Button
        Button changeBioButton = new Button("Change Bio Content");
        changeBioButton.setOnAction(_ -> {
            changeBio.display(user);
            bioLayout.getChildren().clear();
            showBioContent(user);
        });

        editButtonsHBoxLayout.getChildren().addAll(changeCoverButton, changeProfileButton, changeBioButton);
        editButtonsHBoxLayout.setAlignment(Pos.CENTER);
    }

    private void showFriends() {
        // yala ya zoz
    }

    private void showPosts(User user) {
        ContentConvertor contentConvertor = new ContentConvertor();
        contentConvertor.convertPostsToNodes(user,allPostsScrollPane , postsVBoxLayout);
        allPostsScrollPane.setPadding(new Insets(0, 10, 0, 10));
//        profileRootVBox.getChildren().add(allPostsScrollPane);
    }

    public Scene displayScene(MainDuck mainDuck,User user) {
        this.user = user;
        this.mainDuck = mainDuck;
        layoutInitializer();
        setScene();
        showCoverPhoto(user);
        layoutOrganizer();
        showBioContent(user);
        showProfileEdits(user);
        showFriends();
        showPosts(user);
        return profileScene;
    }
}
