package duckHub.frontend.feed;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
}
