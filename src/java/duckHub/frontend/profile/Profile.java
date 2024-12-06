package duckHub.frontend.profile;

import duckHub.MainDuck;
import duckHub.backend.User;
import duckHub.frontend.ContentConvertor;
import duckHub.frontend.SizeConstants;
import duckHub.frontend.feed.ButtonCustomizer;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class Profile {
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

    private TextArea bioContent;

    Button changeCoverButton;
    Button changeProfileButton;
    Button changeBioButton;

    private User user;


    private void layoutInitializer() {
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
    }

    private void layoutOrganizer() {
        profileRootScrollPane.setContent(profileRootVBox);

        allPostsScrollPane.setFitToWidth(true);
        allPostsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        allPostsScrollPane.setContent(postsVBoxLayout);

        allFriendsScrollPane.setContent(friendsHBoxLayout);
    }

    private void setScene() {
        profileScene = new Scene(profileRootScrollPane, 600, 500);
        stage = new Stage();
        stage.setScene(profileScene);
        profileRootScrollPane.setFitToWidth(true);
    }

    private void showCoverPhoto(User user) {
        Image coverPhoto = user.getUserProfileImage();
        ImageView coverPhotoView = new ImageView(coverPhoto);
        coverPhotoView.fitWidthProperty().bind(profileScene.widthProperty());
        coverPhotoView.setFitHeight(70);

        profileRootVBox.getChildren().add(coverPhotoView);
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

        // bio content
        bioContent = new TextArea();
        bioContent.setEditable(Boolean.FALSE);
        bioContent.setWrapText(true);
        bioContent.setPrefHeight(2 * radius);
        bioContent.prefWidthProperty().bind(profileScene.widthProperty().subtract(2 * radius + 70));
//        bioContent.setText(user.get);

        bioLayout.getChildren().addAll(profilePhotoView, bioContent);
        bioLayout.setPadding(new Insets(0, 10, 0, 10));

        profileRootVBox.getChildren().add(bioLayout);
    }

    private void showProfileEdits(){
        changeCoverButton = new Button("Change Cover Photo");
        changeProfileButton = new Button("Change Profile Photo");
        changeBioButton = new Button("Change Bio Content");
        editButtonsHBoxLayout.getChildren().addAll(changeCoverButton, changeProfileButton, changeBioButton);
        editButtonsHBoxLayout.setAlignment(Pos.CENTER);
        profileRootVBox.getChildren().add(editButtonsHBoxLayout);
    }

    private void showFriends() {
        // yala ya zoz
        profileRootVBox.getChildren().add(allFriendsScrollPane);
    }

    private void showPosts(User user) {
        ContentConvertor contentConvertor = new ContentConvertor();
        contentConvertor.convertPostsToNodes(user,allPostsScrollPane , postsVBoxLayout);
        allPostsScrollPane.setPadding(new Insets(0, 10, 0, 10));
        profileRootVBox.getChildren().add(allPostsScrollPane);
    }

    public Scene displayScene(MainDuck mainDuck, User user) {
        this.user = user;
        layoutInitializer();
        layoutOrganizer();
        setScene();
        showCoverPhoto(user);
        showBioContent(user);
        showProfileEdits();
        showFriends();
        showPosts(user);
        return profileScene;
    }
}
