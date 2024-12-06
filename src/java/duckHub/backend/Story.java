package duckHub.backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.UUID;
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Story extends Content {
    private boolean isExpired;

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
        return UUID.randomUUID().toString();
    }

    // deleting the story after 24h
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(getTimestamp().plusHours(24));
    }

    public boolean getExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        this.isExpired = expired;
    }
}

