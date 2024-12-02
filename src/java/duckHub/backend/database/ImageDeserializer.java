package duckHub.backend.database;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageDeserializer extends JsonDeserializer<Image> {
    @Override
    public Image deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String imagePath = jsonParser.getText();
        return new Image(imagePath);
    }
}