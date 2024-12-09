package duckHub.backend.search;

import duckHub.backend.User;

import javax.naming.directory.SearchResult;

public class SearchFacade {
    private final UserSearcher userSearcher;
    private final GroupSearcher groupSearcher;

    public SearchFacade() {
        this.userSearcher = new UserSearcher();
        this.groupSearcher = new GroupSearcher();
    }

//    public SearchResult search(String query, User loggedInUser) {
//        return new SearchResult(userSearcher.search(query, loggedInUser), groupSearcher.search(query, loggedInUser));
//    }
}
