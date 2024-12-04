package duckHub.frontend.feed;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ButtonCustomizer {
    public void roundedButtonImage(ImageView newContentImgView, Button newContentButton) {
        double radius = 25;
        newContentImgView.setFitHeight(radius*2);
        newContentImgView.setFitWidth(radius*2);
        Circle newContentButtonShape = new Circle(radius, radius, radius, Color.PALEGOLDENROD);

        newContentImgView.setClip(newContentButtonShape);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(newContentImgView);

        newContentButton.setGraphic(newContentImgView);
        newContentButton.setStyle("-fx-background-color: transparent;");
    }

    public void rectangleButtonImage(ImageView newContentImgView, Button newContentButton) {
        double width = 15;
        double height = 15;
        newContentImgView.setFitWidth(width);
        newContentImgView.setFitHeight(height);

        Rectangle newContentButtonShape = new Rectangle(width, height, width/2, height/2);
        newContentImgView.setClip(newContentButtonShape);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(newContentImgView);
        newContentButton.setGraphic(newContentImgView);
        newContentButton.setStyle("-fx-background-color: transparent;");
    }
}
