package duckHub.backend;

import java.util.ArrayList;

public class BackendDuck {
    private static final ArrayList<User> users = new ArrayList<>();
    public static void addUser(User user){users.add(user);}

    public static ArrayList<User> getUsers() {
        return (ArrayList<User>) users.clone();
    }

    public static User getUserByID(String userId) {
        for (User u : users) {
            if (u.getUserId().equals(userId)) {
                return u;
            }
        }
        return null;
    }
}
