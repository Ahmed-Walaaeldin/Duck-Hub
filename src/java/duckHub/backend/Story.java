package duckHub.backend;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Story extends Content {
    private static int storyCounter = 0;

    private Story(String contentId, String authorId, String contentText, String contentImage, LocalDateTime timestamp) {
        super(contentId, authorId, contentText, contentImage, timestamp);
    }

    // Factory method
    public static Story create(String authorId, String contentText, String contentImage) {
        String contentId = generateId();
        LocalDateTime timestamp = LocalDateTime.now();
        return new Story(contentId, authorId, contentText, contentImage, timestamp);
    }

    // Overloaded factory method
    public static Story create(String authorId, String contentText) {
        return create(authorId, contentText, null);
    }

    private static String generateId() {
        return "story-" + storyCounter++;
    }

    // deleting the story after 24h
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(getTimestamp().plusHours(24));
    }
}

