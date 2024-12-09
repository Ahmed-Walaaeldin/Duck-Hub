package duckHub.backend.search;

import duckHub.backend.BackendDuck;
import duckHub.backend.User;

import java.util.ArrayList;

public class UserSearcher {
    public ArrayList<User> search(String query, User loggedInUser) {
        ArrayList<User> results = new ArrayList<>();
        String lowercaseQuery = query.toLowerCase();

        for (User user : BackendDuck.getUsers()) {
            if (matches(user, lowercaseQuery) && !user.getUserId().equals(loggedInUser.getUserId())) {
                results.add(user);
            }
        }
        return results;
    }

    private boolean matches(User user, String query) {
        return user.getUsername().toLowerCase().contains(query);
    }
}
