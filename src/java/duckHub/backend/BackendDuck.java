package duckHub.backend;

import duckHub.backend.database.Load;

import java.util.ArrayList;

public class BackendDuck {
    private static final ArrayList<User> users = new ArrayList<>();
    public static void addUser(User user){users.add(user);}

    public static User[] getUsers() {
        return users.toArray(User[]::new);
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
