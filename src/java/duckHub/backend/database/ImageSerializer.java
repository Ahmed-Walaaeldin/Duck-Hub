package duckHub.backend.database;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import javafx.scene.image.Image;
import java.io.IOException;

public class ImageSerializer extends JsonSerializer<Image> {
    @Override
    public void serialize(Image image, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // Assuming the image URL is a file path
        String imagePath = image.getUrl();
        jsonGenerator.writeString(imagePath);
    }
}