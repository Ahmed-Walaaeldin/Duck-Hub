package duckHub.frontend.search;


import duckHub.backend.User;
import duckHub.backend.search.SearchBackend;
import duckHub.frontend.common.ContentConvertor;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class SearchPopup {
    public static void getSearchResult(User user,String query, VBox container) {

        SearchBackend backend = new SearchBackend();
        ArrayList<String> users = backend.searchUsers(user,query);
        // Group[] groups  = backend.searchGroups(user,query);

        ContentConvertor userConvertor = new ContentConvertor();

        ArrayList<String> friendsMatched = new ArrayList<>(users);
        friendsMatched.retainAll(user.getFriends());
        VBox friends = userConvertor.populateList(user,friendsMatched.toArray(new String[0]),"friends");

        ArrayList<String> suggestedMatched = new ArrayList<>(users);
        suggestedMatched.retainAll(user.getSuggestedFriends());
        VBox suggested = userConvertor.populateList(user,suggestedMatched.toArray(new String[0]),"suggested");

        ArrayList<String> blockedMatched = new ArrayList<>(users);
        blockedMatched.retainAll(user.getBlocked());
        VBox blocked = userConvertor.populateList(user,blockedMatched.toArray(new String[0]),"blocked");

        ArrayList<String> receivedMatched = new ArrayList<>(users);
        receivedMatched.retainAll(user.getPendingReceived());
        VBox received  = userConvertor.populateList(user,receivedMatched.toArray(new String[0]),"received");

//        ArrayList<String> sentMatched = new ArrayList<>(users);
//        sentMatched.retainAll(user.getPendingReceived());
//        VBox sent = userConvertor.populateList(user,sentMatched.toArray(new String[0]),"sent"); // we need to add this in content converter

        container.getChildren().clear();
        container.getChildren().addAll(friends,suggested,blocked,received); // add sent
    }
}
