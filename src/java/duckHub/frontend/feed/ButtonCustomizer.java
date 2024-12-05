package duckHub.frontend.feed;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ButtonCustomizer {
    public void roundedButtonImage(ImageView newContentImgView, Button newContentButton,double radius) {
        newContentImgView.setFitHeight(radius*2);
        newContentImgView.setFitWidth(radius*2);
        Circle newContentButtonShape = new Circle(radius, radius, radius, Color.BLACK);

        newContentImgView.setClip(newContentButtonShape);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(newContentImgView);

        newContentButton.setGraphic(newContentImgView);
        newContentButton.setStyle("-fx-background-color: transparent;");
    }

    public void rectangleButtonImage(ImageView newContentImgView, Button newContentButton) {
        double width = 30;
        double height = 30;
        newContentImgView.setFitWidth(width);
        newContentImgView.setFitHeight(height);
        newContentImgView.setPreserveRatio(true);

        Rectangle newContentButtonShape = new Rectangle(width, height);
        newContentButtonShape.setArcWidth(10);  // Rounded corners
        newContentButtonShape.setArcHeight(10);
        newContentImgView.setClip(newContentButtonShape);

        StackPane stackPane = new StackPane(newContentImgView);
        stackPane.setMaxSize(width, height);
        stackPane.setMinSize(width, height);


        // Configure button
        newContentButton.setGraphic(stackPane);
        newContentButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        newContentButton.setMaxSize(width, height);
        newContentButton.setMinSize(width, height);
//        newContentButton.setGraphic(newContentImgView);
//        newContentButton.setStyle("-fx-background-color: transparent;");
    }
}
