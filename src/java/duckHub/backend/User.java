package duckHub.backend;

import java.time.LocalDate;
import java.util.ArrayList;

public class User {
    private static int userCounter = 0;
    private final String userId;
    private final String email;
    private String username;
    private String password;
    private final LocalDate dateOfBirth;
    private boolean status;
    private final ArrayList<String> friends;
    private final ArrayList<String> blocked;
    private final ArrayList<Post> posts;
    private final ArrayList<Story> stories;

    public User(String email, String username, String password, LocalDate dateOfBirth) {
        userId = generateId();
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        status = true;
        friends = new ArrayList<>();
        blocked = new ArrayList<>();
        posts = new ArrayList<>();
        stories = new ArrayList<>();
    }
    private static String generateId(){
        return "user-" + userCounter++;
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
    public ArrayList<Post> getPosts() {
        return posts;
    }
    public ArrayList<Story> getStories() {
        return stories;
    }


    // helper methods
    public void addFriend(String friendId) {
       friends.add(friendId);
    }
    public void removeFriend(String friendId) {
        friends.remove(friendId);
    }
    public void block(String blockedId) {
        friends.remove(blockedId);
        blocked.add(blockedId);
    }
    public void createContent(boolean permanent,String contentText) {
        if(permanent) {
            Post post = Post.create(userId,contentText);
            posts.add(post);
        }else{
            Story story = Story.create(userId,contentText);
            stories.add(story);
        }
    }
    public void createContent(boolean permanent,String contentText, String contentImage) {
        if(permanent) {
            Post post = Post.create(userId,contentText,contentImage);
            posts.add(post);
        }else{
            Story story = Story.create(userId,contentText,contentImage);
            stories.add(story);
        }
    }
}
