package duckHub.backend.search;

import duckHub.backend.User;

import java.util.ArrayList;

public class GroupSearcher {
//    ! this implementation is based on the user not having a list or a variable
//    ! relating his existence in a group
//    public ArrayList<Group> search(String query, User loggedInUser) {
//        ArrayList<Group> results = new ArrayList<>();
//        String lowercaseQuery = query.toLowerCase();
//
//        for (Group group : BackendDuck.getGroups()) {
//            if (matches(group, lowercaseQuery)) {
//                results.add(group);
//            }
//        }
//        return results;
//    }
//
//    public ArrayList<Group> searchJoinedGroups(String query, User loggedInUser) {
//        ArrayList<Group> results = new ArrayList<>();
//        String lowercaseQuery = query.toLowerCase();
//
//        for (Group group : BackendDuck.getGroups()) {
//            if (matches(group, lowercaseQuery) &&
//                    group.getMembers().contains(loggedInUser.getUserId())) {
//                results.add(group);
//            }
//        }
//        return results;
//    }
//
//    public ArrayList<Group> searchUnjoinedGroups(String query, User loggedInUser) {
//        ArrayList<Group> results = new ArrayList<>();
//        String lowercaseQuery = query.toLowerCase();
//
//        for (Group group : BackendDuck.getGroups()) {
//            if (matches(group, lowercaseQuery) &&
//                    !group.getMembers().contains(loggedInUser.getUserId())) {
//                results.add(group);
//            }
//        }
//        return results;
//    }
//
//    private boolean matches(Group group, String query) {
//        return group.getName().toLowerCase().contains(query);
//    }
}
