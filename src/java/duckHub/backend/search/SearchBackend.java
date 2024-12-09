package duckHub.backend.search;

import duckHub.backend.User;

import java.util.ArrayList;
//import duckHub.backend.Group;



public class SearchBackend {
    private static final SearchFacade searchFacade = new SearchFacade();

    public static ArrayList<User> searchUser(User loggedInUser, String query, String type) {
//*        ArrayList<User> searchResults = searchFacade.search(query, loggedInUser).getUsers();
        ArrayList<User> filteredResults = new ArrayList<>();

//        for (User user : searchResults) {
//            String userId = user.getUserId();
//            switch(type) {
//                case "friends" -> {
//                    if (loggedInUser.getFriends().contains(userId))
//                        filteredResults.add(user);
//                }
//                case "blocked" -> {
//                    if (loggedInUser.getBlocked().contains(userId))
//                        filteredResults.add(user);
//                }
//                case "pending" -> {
//                    if (loggedInUser.getPendingReceived().contains(userId))
//                        filteredResults.add(user);
//                }
//                case "suggested" -> {
//                    if (!loggedInUser.getFriends().contains(userId) &&
//                            !loggedInUser.getBlocked().contains(userId) &&
//                            !loggedInUser.getPendingReceived().contains(userId) &&
//                            !loggedInUser.getPendingSent().contains(userId) &&
//                            !user.getBlocked().contains(loggedInUser.getUserId()))
//                        filteredResults.add(user);
//                }
//            }
//        }
        return filteredResults;
    }
//    public static ArrayList<Group> searchGroups(User loggedInUser, String query, String type) {
//        switch(type) {
//            case "joined" -> {
//                return searchFacade.search(query, loggedInUser).getGroupSearcher().searchJoinedGroups(query, loggedInUser);
//            }
//            case "unjoined" -> {
//                return searchFacade.search(query, loggedInUser).getGroupSearcher().searchUnjoinedGroups(query, loggedInUser);
//            }
//            default -> {
//                return searchFacade.search(query, loggedInUser).getGroupSearcher().search(query, loggedInUser);
//            }
//        }
//    }
}
