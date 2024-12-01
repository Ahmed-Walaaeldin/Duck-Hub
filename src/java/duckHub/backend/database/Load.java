package duckHub.backend.database;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import duckHub.backend.User;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Load   implements Paths{
    private ArrayList<User> users;

    public void loadFromFile() {
        users = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        File directory = new File(USERS_DATABASE_PATH);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null) {
            return;
        }else {
            for (File file : files) {
                try{
                    User user = objectMapper.readValue(file,User.class);
                    System.out.println("user loaded" + user);
                    users.add(user);
                } catch (StreamReadException | DatabindException e) {
                    System.out.println("Error while reading file " + file.getName());
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    System.out.println("IO Exception while reading file " + file.getName());
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
