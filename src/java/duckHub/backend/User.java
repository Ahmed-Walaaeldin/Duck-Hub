package duckHub.backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import duckHub.backend.database.ImageDeserializer;
import duckHub.backend.database.ImageSerializer;
import duckHub.backend.database.Save;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import duckHub.frontend.Constants;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Constants {

    private String userId;
    private String email;
    private String username;
    private String password;
    private String bioContent;
    private LocalDate dateOfBirth;

    @JsonSerialize(using = ImageSerializer.class)
    @JsonDeserialize(using = ImageDeserializer.class)
    private Image userProfileImage;
    @JsonSerialize(using = ImageSerializer.class)
    @JsonDeserialize(using = ImageDeserializer.class)
    private Image userCoverImage;
    private boolean status;
    private transient ArrayList<String> suggestedFriends;
    private ArrayList<String> friends;
    private ArrayList<String> blocked;
    private ArrayList<String> pendingSent;
    private ArrayList<String> pendingReceived;
    private ArrayList<Post> posts;
    private ArrayList<Story> stories;

    public User() {

    }

    public User(String email, String username, String password, LocalDate dateOfBirth) {
        userId = generateId();
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        status = true;
        try {
            URL profileImageUrl = getClass().getResource(Constants.DEFAULT_PROFILE_IMAGE_PATH);
            if(profileImageUrl != null) {
                this.userProfileImage = new Image(profileImageUrl.toString());
            }

            Save save = new Save();
            save.saveImageToDirectory(this.userProfileImage, this);

            URL coverImageUrl = getClass().getResource(Constants.DEFAULT_COVER_IMAGE_PATH);
            if(coverImageUrl != null) {
                this.userCoverImage = new Image(coverImageUrl.toString());
            }
            save.saveImageToDirectory(this.userCoverImage, this);
        } catch (Exception e) {
            System.out.println("Default image not found");
        }
        initializeLists();
    }

    private void initializeLists() {
        friends = new ArrayList<>();
        blocked = new ArrayList<>();
        pendingSent = new ArrayList<>();
        pendingReceived = new ArrayList<>();
        suggestedFriends = new ArrayList<>();
        posts = new ArrayList<>();
        stories = new ArrayList<>();
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    // setters & getters ( as needed )
    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBioContent() {
        return bioContent;
    }

    public void setBioContent(String bioContent) {
        this.bioContent = bioContent;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<String> getBlocked() {
        return blocked;
    }

    public ArrayList<String> getPendingSent() {
        return pendingSent;
    }

    public ArrayList<String> getPendingReceived() {
        return pendingReceived;
    }

    public ArrayList<String> getSuggestedFriends() {
        if (suggestedFriends == null) {
            suggestedFriends = new ArrayList<>();
        }
        suggestedFriends.clear();
        ArrayList<User> users = BackendDuck.getUsers();
        for (User potentialFriend : users) {
            String potentialFriendId = potentialFriend.getUserId();

            if (!potentialFriendId.equals(this.getUserId()) &&
                    !friends.contains(potentialFriendId) &&
                    !potentialFriend.getBlocked().contains(userId) &&
                    !blocked.contains(potentialFriendId) &&
                    !pendingSent.contains(potentialFriendId) &&
                    !pendingReceived.contains(potentialFriendId)&&
                    !suggestedFriends.contains(potentialFriendId)) {

                suggestedFriends.add(potentialFriendId);
            }
        }
        return suggestedFriends;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<Story> getStories() {
        return stories;
    }

    public void setUserProfileImage(Image userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public Image getUserProfileImage() {
        return userProfileImage;
    }

    public Image getUserCoverImage() {
        return userCoverImage;
    }

    public void setUserCoverImage(Image userCoverImage) {
        this.userCoverImage = userCoverImage;
    }

    // helper methods
    public void addFriend(String friendId) {
        friends.add(friendId);
    }

    public void removeFriend(String friendId) {
        friends.remove(friendId);
        BackendDuck.getUserByID(friendId).removeFriend(userId);
    }

    public void block(String blockedId) {
        friends.remove(blockedId);
        User blockedFriend = BackendDuck.getUserByID(blockedId);
        blockedFriend.removeFriend(userId);
        blocked.add(blockedId);
    }

    public void unblock(String blockedId) {
        blocked.remove(blockedId);
    }


    public void createContent(boolean permanent, String contentText) {
        if (permanent) {
            Post post = Post.create(userId, contentText);
            posts.add(post);
        } else {
            Story story = Story.create(userId, contentText);
            stories.add(story);
        }
    }

    public void createContent(boolean permanent, String contentText, Image contentImage) {
        if (permanent) {
            Post post = Post.create(userId, contentText, contentImage);
            posts.add(post);
        } else {
            Story story = Story.create(userId, contentText, contentImage);
            stories.add(story);
        }
    }

    public StackPane roundedProfileImage(double radius, boolean framed) {
        Image userImage = userProfileImage;
        ImageView userImageView = new ImageView(userImage);

        userImageView.setFitHeight(radius * 2);
        userImageView.setFitWidth(radius * 2);

        Circle userImageClip = new Circle(radius, radius, radius);
        userImageView.setClip(userImageClip);

        StackPane stackPane = new StackPane();

        // create the border
        if (framed) {
            Circle userImageBorder = new Circle(radius + 2);
            userImageBorder.setStroke(Color.PURPLE);
            userImageBorder.setStrokeWidth(3);
            userImageBorder.setFill(null);
            stackPane.getChildren().add(userImageBorder);
        }

        stackPane.getChildren().add(userImageView);
        return stackPane;
    }

}
