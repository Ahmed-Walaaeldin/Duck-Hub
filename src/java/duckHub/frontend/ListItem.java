package duckHub.frontend;

import duckHub.backend.BackendDuck;
import duckHub.backend.User;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.Objects;

public class ListItem {

    public static HBox createListItem(String userId, String type){
        User user = BackendDuck.getUserByID(userId);
        HBox banner = new HBox();
        banner.setAlignment(Pos.CENTER);
        banner.setSpacing(10);
        Label username = new Label(user.getUsername());
        username.setStyle("-fx-text-fill: black;");

        Image photo = null;
        try {
            photo = new Image(String.valueOf(Objects.requireNonNull(ListItem.class.getResource("/duckHub/frontend/ducky.jpeg"))));
        } catch (Exception e) {
            System.out.println("Image not found: " + e.getMessage());
        }
        ImageView imageView = new ImageView(photo);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        Region spacer = new Region();
        spacer.setPrefWidth(70);
        HBox.setHgrow(spacer, Priority.ALWAYS); // Allow the spacer to grow
        banner.getChildren().addAll(imageView, username, spacer);
        banner.setAlignment(Pos.CENTER); // definitely it will need styling more than this

        // adding buttons
        switch (type) {
            case "pending" -> {
                Button acceptButton = new Button("Accept");
                Button declineButton = new Button("Decline");
                acceptButton.setOnAction(e -> {
                });
                declineButton.setOnAction(e -> {
                });
                banner.getChildren().addAll(acceptButton, declineButton);
            }
            case "friends" -> {
                Button removeButton = new Button("Remove");
                Button blockButton = new Button("Block");
                removeButton.setOnAction(e -> {
                });
                blockButton.setOnAction(e -> {
                });
                banner.getChildren().addAll(removeButton, blockButton);
            }
            case "suggested" -> {
                Button addButton = new Button("Add");
                addButton.setOnAction(e -> {
                });
                banner.getChildren().add(addButton);
            }
            case "block" -> {
                Button unblockButton = new Button("Unblock");
                unblockButton.setOnAction(e -> {
                });
                banner.getChildren().add(unblockButton);
            }
        }
        return banner;
    }
}
