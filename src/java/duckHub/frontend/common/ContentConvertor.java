package duckHub.frontend.common;

import duckHub.backend.BackendDuck;
import duckHub.backend.Post;
import duckHub.backend.User;
import duckHub.frontend.ListItem;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ContentConvertor {

    public void convertPostsToNodes(User user, ScrollPane postsScrollPane, VBox layout) {
        ArrayList<Post> posts = user.getPosts();
        // Sort posts by timestamp in descending order (most recent first)
        posts.sort((post1, post2) -> post2.getTimestamp().compareTo(post1.getTimestamp()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Post post : posts) {
            HBox nameAndPhotoLayout = new HBox();

            StackPane userPhotoRounded = user.roundedProfileImage(15, false);
            nameAndPhotoLayout.getChildren().add(userPhotoRounded);

            VBox postVBox = new VBox();
            String username = BackendDuck.getUserByID(post.getAuthorId()).getUsername();
            Label authorName = new Label(username);
            nameAndPhotoLayout.getChildren().add(authorName);
            postVBox.getChildren().addAll(nameAndPhotoLayout);

            if (!post.getContentText().isEmpty()) {
                TextArea textArea = new TextArea(post.getContentText());
                textArea.setEditable(false);
                textArea.setWrapText(true);
                textArea.setPrefWidth(postsScrollPane.getPrefViewportWidth() - 20); // for padding

                // Calculate the preferred height based on the content
                int lines = textArea.getText().split("\n").length;
                double lineHeight = 20; // Approximate height of a line in pixels
                textArea.setPrefHeight(lines * lineHeight);
                postVBox.getChildren().add(textArea);
            }

            Image imageContent = post.getContentImage();

            ImageView imageView = new ImageView(imageContent);
            if (imageContent != null) {
//                ImageView imageView = new ImageView(imageContent);

                //original image dimensions
                double originalWidth = imageContent.getWidth();
                double originalHeight = imageContent.getHeight();
                double aspectRatio = originalWidth / originalHeight;

                // Set maximum dimensions
                double maxWidth = postsScrollPane.getPrefViewportWidth() - 40;
                double maxHeight = 400;

                // Calculate new dimensions
                double newWidth = maxWidth;
                double newHeight = maxWidth / aspectRatio;

                // If height exceeds maximum, scale based on height instead
                if (newHeight > maxHeight) {
                    newHeight = maxHeight;
                    newWidth = maxHeight * aspectRatio;
                }

                imageView.setFitWidth(newWidth);
                imageView.setFitHeight(newHeight);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
            }
            Label timeStampLabel = new Label(formatter.format(post.getTimestamp()));
//            timeStampLabel.setAlignment(Pos.CENTER_RIGHT);

            if (imageContent != null) {
                postVBox.getChildren().add(imageView);
            }
            postVBox.getChildren().add(timeStampLabel);
            layout.getChildren().add(postVBox);
        }
    }

    public VBox populateList(User user, String[] ids, String type) {
        VBox contentBox = new VBox();
        contentBox.setSpacing(10);
        contentBox.setAlignment(Pos.CENTER);
        for (String id : ids) {
            HBox banner = ListItem.createListItem(user, id, type);
            contentBox.getChildren().add(banner);
        }
        return contentBox;
    }
}