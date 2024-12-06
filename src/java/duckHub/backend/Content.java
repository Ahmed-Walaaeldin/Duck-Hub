package duckHub.backend;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import duckHub.backend.database.ImageDeserializer;
import duckHub.backend.database.ImageSerializer;
import javafx.scene.image.Image;

import java.time.LocalDateTime;

public abstract class Content {
    private String contentId;
    private String authorId;
    private String contentText;
//    @JsonIgnore
    @JsonSerialize(using = ImageSerializer.class)
    @JsonDeserialize(using = ImageDeserializer.class)
    private Image contentImage;
    private LocalDateTime timestamp;

    public Content(String contentId, String authorId, String contentText, Image contentImage, LocalDateTime timestamp) {
        this.contentId = contentId;
        this.authorId = authorId;
        this.contentText = contentText;
        this.contentImage = contentImage;
        this.timestamp = timestamp;
    }
    public Content(String contentId, String authorId, String contentText, LocalDateTime timestamp) {
        this(contentId, authorId, contentText, null, timestamp);
    }

    public Content() {

    }

    // getters
    public String getContentId() {
        return contentId;
    }
    public String getAuthorId() {
        return authorId;
    }
    public String getContentText() {
        return contentText;
    }
    public Image getContentImage() {
        return contentImage;}
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
