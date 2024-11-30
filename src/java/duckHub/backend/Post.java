package duckHub.backend;

import java.time.LocalDateTime;

public class Post extends Content {
    private static int postCounter = 0;

    private Post(String contentId, String authorId, String contentText, String contentImage, LocalDateTime timestamp) {
        super(contentId, authorId, contentText, contentImage, timestamp);
    }

    // Factory method
    public static Post create(String authorId, String contentText, String contentImage) {
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
