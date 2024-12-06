package duckHub.frontend.profile;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class Profile extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        //cover photo
        Image coverPhoto = new Image(new File("path").toURI().toString());
        ImageView coverPhotoView = new ImageView(coverPhoto);

        //profile photo
        Image profilePhoto = new Image(new File("path").toURI().toString());
        ImageView profilePhotoView = new ImageView(profilePhoto);

        //Bio
        Label nameLabel = new Label();
        nameLabel.setText("Name Name");
        Label bioLabel = new Label();
        bioLabel.setText("Bio");
        VBox bioLayout = new VBox(bioLabel);

        //Friends Layout
        HBox friendsLayout = new HBox();
        friendsLayout.getChildren().addAll();
        
        //Posts Layout
        VBox postsLayout = new VBox();
        postsLayout.getChildren().addAll();

        VBox profileRootLayout = new VBox(coverPhotoView, profilePhotoView, bioLayout, friendsLayout, postsLayout);
        Scene profileScene = new Scene(profileRootLayout, 600, 700);
        window.setScene(profileScene);
        window.setTitle("Profile");
        window.show();
    }
}
