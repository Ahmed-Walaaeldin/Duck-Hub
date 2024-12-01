package duckHub.backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.scene.image.Image;
import java.time.LocalDateTime;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Post extends Content {
    private static int postCounter = 0;

    public Post(){
        super();

    }
    public Post(String contentId, String authorId, String contentText, Image contentImage, LocalDateTime timestamp) {
        super(contentId, authorId, contentText, contentImage, timestamp);
    }

    // Factory method
    public static Post create(String authorId, String contentText, Image contentImage) {
        String contentId = generateId();
        LocalDateTime timestamp = LocalDateTime.now();
        return new Post(contentId, authorId, contentText, contentImage, timestamp);
    }

    // Overloaded factory method
    public static Post create(String authorId, String contentText) {
        return create(authorId, contentText, null);
    }

    private static String generateId() {
        return "post-" + postCounter++;
    }
}
