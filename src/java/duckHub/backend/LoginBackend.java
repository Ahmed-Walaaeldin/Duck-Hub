package duckHub.backend;

import org.mindrot.jbcrypt.BCrypt;

public class LoginBackend {
    public static boolean login(String username, String password) {
        User[] users =  BackendDuck.getUsers();
        for(User user : users) {
            if(user.getUsername().equals(username) && isSamePassword(user.getPassword(),password) ) {
                System.out.println("Logged in as " + user.getUsername());
                return true;
            }
        }
        System.out.println("User not found");
        return false;
    }
    public static boolean isSamePassword(String hashed, String normal) {
        return BCrypt.checkpw(normal, hashed);
    }
}
