package duckHub.backend.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import duckHub.backend.*;
import duckHub.frontend.Constants;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Save implements Paths, Constants {
    public void saveToFile(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            String userDirectoryPath = USERS_DATABASE_PATH + user.getUserId();
            File userDirectory = new File(userDirectoryPath);

            // Create directories if they don't exist
            if (!userDirectory.exists()) {
                boolean created = userDirectory.mkdirs();
                if (!created) {
                    throw new IOException("Failed to create directory: " + userDirectoryPath);
                }
            }
            File file = new File(userDirectory, JSON_FILE_NAME);
            objectMapper.writeValue(file, user);
            System.out.println("Successfully saved " + user.getUserId() + " to " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error while saving user to file");
                System.out.println(e.getMessage());
            }
    }

    public void saveAllUsers() {
        ArrayList<User> users = BackendDuck.getUsers();
        System.out.println("Saving " + users.size() + " users");
        if (users.isEmpty()) {
            System.out.println("No users to be saved");
            return;
        }
        for (User user : users) {
            saveToFile(user);
        }
    }

    public String saveImageToDirectory(Image image, User user) {
        try {
            String userDirectoryPath = USERS_DATABASE_PATH + user.getUserId();
            File userDirectory = new File(userDirectoryPath);
            String format = determineImageFormat(image);
            String fileName = UUID.randomUUID() + "." + format;
            File imageFile = new File(userDirectory, fileName);

            // Create directory if it doesn't exist
            if (!userDirectory.exists()) {
                boolean created = userDirectory.mkdirs();
                if (!created) {
                    throw new IOException("Failed to create directory: " + userDirectoryPath);
                }
            }

            // Write image to file
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(bufferedImage, "png", imageFile);

            System.out.println("Successfully saved image: " + imageFile.getAbsolutePath());
            return fileName; // Return filename to be stored in user data

        } catch (IOException e) {
            System.out.println("Error while saving image");
            System.out.println(e.getMessage());
            return null;
        }

    }
    private String determineImageFormat(Image image) {
        String url = image.getUrl();
        if (url != null && !url.isEmpty()) {
            String lowercaseUrl = url.toLowerCase();
            if (lowercaseUrl.endsWith(".jpg") || lowercaseUrl.endsWith(".jpeg")) {
                return "jpg";
            } else if (lowercaseUrl.endsWith(".png")) {
                return "png";
            } else if (lowercaseUrl.endsWith(".gif")) {
                return "gif";
            } else if (lowercaseUrl.endsWith(".bmp")) {
                return "bmp";
            }
        }
        // Default to PNG if format cannot be determined
        return "png";
    }
}
