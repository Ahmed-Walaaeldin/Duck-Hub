package duckHub.backend.search;

import duckHub.backend.BackendDuck;
import duckHub.backend.User;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchBackend {
    public ArrayList<String> searchUsers(User loggedInUser, String query) {
        ArrayList<String> results = new ArrayList<>();
        String lowercaseQuery = query.toLowerCase();
        for (User user : BackendDuck.getUsers()) {
            if (contains(user.getUsername(), lowercaseQuery) && !user.getUserId().equals(loggedInUser.getUserId())) {
                results.add(user.getUserId());
            }
        }
        return results;
    }

//    public ArrayList<String> searchGroups(User loggedInUser, String query) {
//        ArrayList<String> results = new ArrayList<>();
//        String lowercaseQuery = query.toLowerCase();
//
//        for (Group group : BackendDuck.getGroups()) {
//            if (contains(group.getGroupNmae(), lowercaseQuery)) {
//                results.add(group.getGroupId());
//            }
//        }
//        return results;
//    }

    private boolean contains(String username, String query) {
        Pattern pattern = Pattern.compile(Pattern.quote(query));
        Matcher matcher = pattern.matcher(username.toLowerCase());
        return matcher.find();
    }
}
