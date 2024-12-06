package duckHub.backend;

import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;

public class SignupBackend {

    public static User signup(String email, LocalDate dateOfBirth, String username, String password) {
        if(LoginBackend.login(username, password) == null){ // it's not already a member
            User user = new User(email,username,hashPassword(password),dateOfBirth);
            BackendDuck.addUser(user);
            System.out.println("User created");
            return user;
        }
        System.out.println("User already exists");
        return null;
    }

    // Hashing a password
    public static String hashPassword(String password) {
        // Generate a salt and hash the password
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // 12 is the computational cost
    }
}
