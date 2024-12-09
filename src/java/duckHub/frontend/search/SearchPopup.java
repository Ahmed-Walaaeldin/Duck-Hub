package duckHub.frontend.search;

import duckHub.backend.SearchBackend;
import duckHub.backend.User;
import javafx.scene.layout.VBox;

public class SearchPopup {
    public static void getSearchResult(String query, VBox container) {
        User[] users  = SearchBackend.getSerchUserList(String query);
        // Group[] groups  = SearchBackend.getSerchGroupList(String query);
    }
}
