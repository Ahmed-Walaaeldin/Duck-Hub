package duckHub.backend.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import duckHub.backend.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Save implements Paths {
    public void saveToFile(ArrayList<User> users) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        for (User user : users)
            try {
                File file = new File(USERS_DATABASE_PATH + user.getUserId() + ".json");
                objectMapper.writeValue(file, user);
                System.out.println("Successfully saved " + user.getUserId() + " to " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error while saving user to file");
                System.out.println(e.getMessage());
            }
        System.out.println("User saved successfully");
    }
}
