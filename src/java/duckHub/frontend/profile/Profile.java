package duckHub.frontend.profile;

import duckHub.MainDuck;
import duckHub.backend.User;
import duckHub.frontend.ContentConvertor;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    private TextArea bioContent;

    private User user;


    private void layoutInitializer() {
        // root layout
        profileRootScrollPane = new ScrollPane();
        profileRootVBox = new VBox(20);
        // bio layout
        bioLayout = new HBox(20);
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
        profilePhotoView.setFitHeight(radius * 2);
        profilePhotoView.setFitWidth(radius * 2);
        Circle newContentButtonShape = new Circle(radius, radius, radius);
        profilePhotoView.setClip(newContentButtonShape);

        // bio content
        bioContent = new TextArea();
        bioContent.setEditable(Boolean.FALSE);
        bioContent.setWrapText(true);
        bioContent.setPrefHeight(2 * radius);
        bioContent.prefWidthProperty().bind(profileScene.widthProperty().subtract(2 * radius + 50));
//        bioContent.setText(user.get);

        bioLayout.getChildren().addAll(profilePhotoView, bioContent);
        bioLayout.setPadding(new Insets(0, 10, 0, 10));

        profileRootVBox.getChildren().add(bioLayout);
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
        showFriends();
        showPosts(user);
        return profileScene;
    }
}
