package duckHub.backend.database;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import duckHub.backend.BackendDuck;
import duckHub.backend.User;
import duckHub.frontend.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Load implements Paths, Constants {

    public void loadFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        File directory = new File(USERS_DATABASE_PATH);
        File[] userDirectories = directory.listFiles(File::isDirectory);

        if (userDirectories != null) {
            for (File userDir : userDirectories) {
                File userDataFile = new File(userDir, JSON_FILE_NAME);

                if (userDataFile.exists()) {
                    try {
                        User user = objectMapper.readValue(userDataFile, User.class);
                        System.out.println("User loaded: " + user.getUserId());
                        BackendDuck.addUser(user);
                    } catch (StreamReadException | DatabindException e) {
                        System.out.println("Error while reading file " + userDataFile.getName());
                        System.out.println(e.getMessage());
                    } catch (IOException e) {
                        System.out.println("IO Exception while reading file " + userDataFile.getName());
                        System.out.println(e.getMessage());
                        userDataFile.delete();
                    }
                }
            }
        }
    }

}
