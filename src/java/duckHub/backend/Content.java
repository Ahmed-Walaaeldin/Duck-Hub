package duckHub.backend;

import java.time.LocalDateTime;

public abstract class Content {
    private final String contentId;
    private final String authorId;
    private final String contentText;
    private final String contentImage;
    private final LocalDateTime timestamp;

    public Content(String contentId, String authorId, String contentText, String contentImage, LocalDateTime timestamp) {
        this.contentId = contentId;
        this.authorId = authorId;
        this.contentText = contentText;
        this.contentImage = contentImage;
        this.timestamp = timestamp;
    }
    public Content(String contentId, String authorId, String contentText, LocalDateTime timestamp) {
        this(contentId, authorId, contentText, null, timestamp);
    }

    // getters
    public String getContentId() {
        return contentId;
    }
    public String getAuthor() {
        return authorId;
    }
    public String getContentText() {
        return contentText;
    }
    public String getContentImage() {
        return contentImage;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
