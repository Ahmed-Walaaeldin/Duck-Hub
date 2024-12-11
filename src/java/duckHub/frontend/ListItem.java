package duckHub.frontend;

import duckHub.backend.BackendDuck;
import duckHub.backend.FriendsBackend;
import duckHub.backend.User;
import duckHub.backend.database.Save;
import duckHub.frontend.friends.FriendsPage;
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

    public static HBox createListItem(User user, String friendId, String type) {
        User friend = BackendDuck.getUserByID(friendId);
        HBox banner = new HBox();
        banner.setAlignment(Pos.CENTER);
        banner.setSpacing(10);
        Label username = new Label(friend.getUsername());
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
        banner.getChildren().addAll(imageView, username);
        banner.setAlignment(Pos.CENTER); // definitely it will need styling more than this

        // adding buttons
        switch (type) {
            case "pending" -> {
                Button acceptButton = new Button("Accept");
                Button declineButton = new Button("Decline");
                acceptButton.setOnAction(_ -> {
                    if (!FriendsBackend.acceptFriendRequest(user, friendId)) {
                        PopUp.display(true, "Error", "Error accepting friend request");
                    } else{
                        FriendsPage.refresh(type);
                    }


                });
                declineButton.setOnAction(_ -> {
                    if (!FriendsBackend.declineFriendRequest(user, friendId)) {
                        PopUp.display(true, "Error", "Error declining friend request");
                    } else{
                        FriendsPage.refresh(type);
                    }
                });
                banner.getChildren().addAll(spacer, acceptButton, declineButton);
            }
            case "friends" -> {
                Button removeButton = new Button("Remove");
                Button blockButton = new Button("Block");
                removeButton.setOnAction(_ -> {
                    if (!FriendsBackend.removeFriend(user, friendId))
                        PopUp.display(true, "Error", "Error removing friend");
                    else{
                        FriendsPage.refresh(type);
                    }
                });
                blockButton.setOnAction(_ -> {
                    if (!FriendsBackend.block(user, friendId))
                        PopUp.display(true, "Error", "Error blocking friend");
                    else{
                        FriendsPage.refresh(type);
                    }
                });

                ImageView statusImageView = new ImageView();
                if (friend.getStatus()) {
                    Image onlineImage = new Image(Objects.requireNonNull(ListItem.class.getResource("/duckHub/frontend/online.png")).toString());
                    statusImageView.setImage(onlineImage);
                } else {
                    Image offlineImage = new Image(Objects.requireNonNull(ListItem.class.getResource("/duckHub/frontend/offline.png")).toString());
                    statusImageView.setImage(offlineImage);
                }
                statusImageView.setFitWidth(20);
                statusImageView.setFitHeight(20);

                banner.getChildren().addAll(statusImageView, spacer, removeButton, blockButton);
                Save save = new Save();
                save.saveAllUsers();
            }
            case "suggested" -> {
                Button addButton = new Button("Add");
                addButton.setOnAction(_ -> {
                    if (!FriendsBackend.sendFriendRequest(user, friendId))
                        PopUp.display(true, "Error", "Error sending friend request");
                    else{
                        FriendsPage.refresh(type);
                    }
                });

                banner.getChildren().addAll(spacer, addButton);
            }
            case "blocked" -> {
                Button unblockButton = new Button("Unblock");
                unblockButton.setOnAction(_ -> {
                    if (!FriendsBackend.unblock(user, friendId))
                        PopUp.display(true, "Error", "Error unblocking user");
                    else{
                        FriendsPage.refresh(type);
                    }
                });
                banner.getChildren().addAll(spacer, unblockButton);
            }
        }
        return banner;
    }

}
