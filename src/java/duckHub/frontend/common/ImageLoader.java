package duckHub.frontend.common;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;

public class ImageLoader {
    public Image loadContentImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Image");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                return new Image(selectedFile.toURI().toString());
            } catch (Exception e) {
                System.out.println("Unable to load image" + e.getMessage());
            }
        }
        return null;
    }
}
