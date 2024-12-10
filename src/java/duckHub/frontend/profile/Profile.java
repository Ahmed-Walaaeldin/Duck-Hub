package duckHub.frontend.profile;

import duckHub.MainDuck;
import duckHub.backend.User;
import duckHub.frontend.Constants;
import duckHub.frontend.common.ContentConvertor;
import duckHub.frontend.common.ImageLoader;
import duckHub.frontend.titleBar.TitleBar;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Objects;


public class Profile implements Constants {
    // Main Duck
    private MainDuck mainDuck;

    // Current user profile
    private User user;

    // to test
    private Stage stage;

    // Displayed Scene
    private Scene scene;

    // layouts
    private ScrollPane profileRootScrollPane;
    private VBox profileRootVBox;
    private HBox bioLayout;
    private VBox profile_nameVBoxLayout;
    private HBox editButtonsHBoxLayout;
    private ScrollPane allPostsScrollPane;
    private VBox postsVBoxLayout;
    private ScrollPane allFriendsScrollPane;
    private BorderPane feedLayout;
    private ImageView coverPhotoView;

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
        // profile picture and username layout
        profile_nameVBoxLayout = new VBox(20);
        // edit buttons layout
        editButtonsHBoxLayout = new HBox(20);
        // posts layout
        allPostsScrollPane = new ScrollPane();
        postsVBoxLayout = new VBox();
        // friends layout
        allFriendsScrollPane = new ScrollPane();

        feedLayout = new BorderPane();

        // title bar
        titleBar = new TitleBar(mainDuck,user);
    }

    private void layoutOrganizer() {
        // hold title bar and other main layout
        VBox.setVgrow(profileRootScrollPane, Priority.ALWAYS);
        mainContainer.getChildren().addAll(titleBar.getTitleBar(), profileRootScrollPane);

        feedLayout.setRight(allFriendsScrollPane);
        feedLayout.setCenter(allPostsScrollPane);

        profileRootVBox.getChildren().addAll(coverPhotoView, bioLayout, editButtonsHBoxLayout, feedLayout);

        profileRootScrollPane.setContent(profileRootVBox);

        allPostsScrollPane.setFitToWidth(true);
        allPostsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        allPostsScrollPane.setContent(postsVBoxLayout);
    }

    private void setScene() {
        scene = new Scene(mainContainer, SCENE_WIDTH, SCENE_HEIGHT);
        try{
            String styles = Objects.requireNonNull(getClass().getResource("/duckHub/frontend/FriendsPageStyles.css")).toExternalForm();
            scene.getStylesheets().add(styles);
        }catch (Exception e){
            System.out.println("StylesSheet unavailable");
        }
        stage = new Stage();
        stage.setScene(scene);
        profileRootScrollPane.setFitToWidth(true);
    }

    private void showCoverPhoto(User user) {
        Image coverPhoto = user.getUserCoverImage();
        coverPhotoView = new ImageView(coverPhoto);
        coverPhotoView.fitWidthProperty().bind(scene.widthProperty());
        coverPhotoView.setFitHeight(120);
    }

    private void showBioContent(User user) {
        // profile photo
        double radius = 70;
        Image profilePhoto = user.getUserProfileImage();
        ImageView profilePhotoView = new ImageView(profilePhoto);
        profilePhotoView.setFitHeight(radius*2);
        profilePhotoView.setFitWidth(radius*2);
        Circle newContentButtonShape = new Circle(radius, radius, radius);
        profilePhotoView.setClip(newContentButtonShape);

        // user name
        Label usernameLabel = new Label(user.getUsername());
        usernameLabel.setStyle("-fx-font-weight: bold");

        profile_nameVBoxLayout.getChildren().clear();
        profile_nameVBoxLayout.getChildren().addAll(profilePhotoView, usernameLabel);
        profile_nameVBoxLayout.setAlignment(Pos.CENTER);

        // bio content
        TextArea bioContent = new TextArea();
        bioContent.setEditable(Boolean.FALSE);
        bioContent.setWrapText(true);
        bioContent.setPrefHeight(2 * radius);
        bioContent.prefWidthProperty().bind(scene.widthProperty().subtract(2 * radius + 70));
        bioContent.setText(user.getBioContent());

        bioLayout.setPadding(new Insets(0, 10, 0, 10));
        bioLayout.getChildren().addAll(profile_nameVBoxLayout, bioContent);
    }

    private void showProfileEdits(User user) {
        ImageLoader imageLoader = new ImageLoader();
        ChangeBio changeBio = new ChangeBio();

        // change Cover Picture Button
        Button changeCoverButton = new Button("Change Cover Photo");
        changeCoverButton.setOnAction(_ -> {
            Image newImage = imageLoader.loadContentImage();
            if (newImage != null) {
//                Save save = new Save();
//                save.saveImageToDirectory(newImage,user);
                user.setUserCoverImage(newImage);
                coverPhotoView.setImage(newImage);
            }
        });

        // change Profile Picture Button
        Button changeProfileButton = new Button("Change Profile Photo");
        changeProfileButton.setOnAction(_ -> {
            Image newImage = imageLoader.loadContentImage();
            if (newImage != null) {
//                Save save = new Save();
//                save.saveImageToDirectory(newImage,user);
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
        ContentConvertor convertor = new ContentConvertor();
        allFriendsScrollPane.setContent(convertor.populateList(user, user.getFriends().toArray(new String[0]), "friends"));
    }

    private void showPosts(User user) {
        ContentConvertor contentConvertor = new ContentConvertor();
        contentConvertor.convertPostsToNodes(user,allPostsScrollPane , postsVBoxLayout);
        allPostsScrollPane.setPadding(new Insets(0, 10, 0, 10));
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
        return scene;
    }
}
