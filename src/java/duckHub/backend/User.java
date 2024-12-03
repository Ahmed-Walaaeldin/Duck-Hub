package duckHub.backend;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;

public class User {
    private static int userCounter = 0;
    private String userId;
    private String email;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private boolean status;
    private ArrayList<String> friends;
    private ArrayList<String> blocked;
    private ArrayList<String> pendingSent;
    private ArrayList<String> pendingReceived;
    private ArrayList<String> suggestedFriends;
    private ArrayList<Post> posts;
    private ArrayList<Story> stories;

    public User(){

    }
    public User(String email, String username, String password, LocalDate dateOfBirth) {
        userId = generateId();
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        status = true;
        friends = new ArrayList<>();
        blocked = new ArrayList<>();
        pendingSent = new ArrayList<>();
        pendingReceived = new ArrayList<>();
        suggestedFriends = new ArrayList<>();
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
    public ArrayList<String> getPendingSent() {
        return pendingSent;
    }
    public ArrayList<String> getPendingReceived() {
        return pendingReceived;
    }

    public ArrayList<String> getSuggestedFriends() {
        return suggestedFriends;
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
    public void unblock(String blockedId) {
        blocked.remove(blockedId);
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
    public void createContent(boolean permanent,String contentText, Image contentImage) {
        if(permanent) {
            Post post = Post.create(userId,contentText,contentImage);
            posts.add(post);
        }else{
            Story story = Story.create(userId,contentText,contentImage);
            stories.add(story);
        }
    }

}
