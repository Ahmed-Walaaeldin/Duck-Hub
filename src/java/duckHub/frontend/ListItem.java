package duckHub.frontend;

import duckHub.backend.BackendDuck;
import duckHub.backend.User;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ListItem {

    public static HBox createListItem(String userId){
        User user = BackendDuck.getUserByID(userId);
        HBox banner = new HBox();
        Label username = new Label(user.getUsername());
        Image photo = new Image("ducky.jpeg"); // get photo i guess from profile @rofa
        ImageView imageView = new ImageView(photo);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        banner.getChildren().add(imageView);
        banner.getChildren().add(username);
        banner.setAlignment(Pos.CENTER); // definitely it will need styling more than this
        return banner;
    }
}
