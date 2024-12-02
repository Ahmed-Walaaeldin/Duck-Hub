package duckHub;

import duckHub.frontend.LoginPage;
import duckHub.frontend.SignupPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainDuck extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("DuckHub");
        showLoginPage(); // Start with Login Page

        primaryStage.show();
    }
    public void showLoginPage() {
        System.out.println("Switching to Login Page");
        LoginPage loginPage = new LoginPage();
        Scene loginScene = loginPage.getScene(this);
        primaryStage.setScene(loginScene);
    }
    public void showSignupPage() {
        System.out.println("Switching to Signup Page");
        SignupPage signupPage = new SignupPage();
        Scene signupScene = signupPage.getScene(this);
        primaryStage.setScene(signupScene);
    }
}



//import org.mindrot.jbcrypt.BCrypt;
//
//public class PasswordHasher {
//    public static String hashPassword(String password) {
//        // Generate a salt and hash the password
//        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // 12 is the computational cost
//    }
//
//    public static boolean verifyPassword(String password, String hashedPassword) {
//        // Verify the password against the hashed value
//        return BCrypt.checkpw(password, hashedPassword);
//    }
//
//    public static void main(String[] args) {
//        String password = "mypassword123";
//
//        // Hash the password
//        String hashedPassword = hashPassword(password);
//        System.out.println("Hashed Password: " + hashedPassword);
//
//        // Verify the password
//        boolean isMatch = verifyPassword("mypassword123", hashedPassword);
//        System.out.println("Password matches: " + isMatch);
//    }
//}
