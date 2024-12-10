package duckHub.backend.groups;

import duckHub.backend.Post;
import duckHub.backend.User;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Group {
    private String name;
    private String description;
    private Image groupImage;
    private Owner owner;
    private ArrayList<Admin> admins;
    private ArrayList<Member> members;
    // Owner, Admins and members will have their posts here
    private ArrayList<Post> posts;
    private ArrayList<User> requests;


    // Getters
    public void addMember(Member member) {
        members.add(member);
    }

    public void removeMember(Member member) {
        members.remove(member);
    }

    public void addAdmin(Admin admin) {
        admins.add(admin);
    }
    public void removeAdmin(Admin admin) {
        admins.remove(admin);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getGroupImage() {
        return groupImage;
    }

    public Owner getOwner() {
        return owner;
    }

    public Admin[] getAdmins() {
        return admins.toArray(new Admin[0]);
    }

    public Member[] getMembers() {
        return members.toArray(new Member[0]);
    }

    // Search For a User

    public ArrayList<User> getRequests() {
        return requests;
    }

    public void removeRequest(User user) {
        requests.remove(user);
    }

    public Post[] getPosts() {
        return posts.toArray(new Post[0]);
    }

    public void removePost(Post post) {
        posts.remove(post);
    }
    public void addPost(Post post) {
        posts.add(post);
    }
//    public User searchMember(String memberID) {
//
//    }
}
