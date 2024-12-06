package duckHub.backend;

import javafx.scene.image.Image;

import java.time.LocalDateTime;

public class Story extends Content {
    private static int storyCounter = 0;

    public Story(){

    }

    public Story(String contentId, String authorId, String contentText, Image contentImage, LocalDateTime timestamp) {
        super(contentId, authorId, contentText, contentImage, timestamp);
    }

    // Factory method
    public static Story create(String authorId, String contentText, Image contentImage) {
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

