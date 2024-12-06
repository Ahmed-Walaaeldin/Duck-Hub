package duckHub.backend;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

public class LoginBackend {
    public static User login(String username, String password) {
        ArrayList<User> users =  BackendDuck.getUsers();
        for(User user : users) {
            if(user.getUsername().equals(username) && isSamePassword(user.getPassword(),password) ) {
                System.out.println("Logged in as " + user.getUsername());
                return user;
            }
        }
        System.out.println("User not found");
        return null;
    }
    public static boolean isSamePassword(String hashed, String normal) {
        return BCrypt.checkpw(normal, hashed);
    }
}
