package duckHub.backend;

import java.util.ArrayList;

public class FriendsManagement {
    private User user;
    private ArrayList<User> users;
    private ArrayList<String> suggestedFriends;

    public FriendsManagement(User user) {
        this.user = user;
        suggestedFriends = new ArrayList<>();
        users = new BackendDuck().getUsers();
    }

    public void sendFriendRequest(String friendID) {
        user.getPendingSent().add(friendID);
        User friend = getUserByID(friendID);
        if (friend != null)
            friend.getPendingReceived().add(user.getUserId());
    }
    public void acceptFriendRequest(String friendID) {
//?        if (user.getPendingReceived().contains(friendID)) {
            user.getPendingReceived().remove(friendID);
            user.getFriends().add(friendID);

            User friend = getUserByID(friendID);
            if (friend != null) {
                friend.getPendingSent().remove(user.getUserId());
                friend.getFriends().add(user.getUserId());
            }
//?        }
    }
    public void declineFriendRequest(String friendID) {
//?        if (user.getPendingReceived().contains(friendID)) {
            user.getPendingReceived().remove(friendID);

            User friend = getUserByID(friendID);
            if (friend != null) friend.getPendingSent().remove(user.getUserId());

//?        }
    }

    public void removeFriend(String friendID) {
        user.removeFriend(friendID);
    }
    public void block(String friendID) {
        user.block(friendID);
    }
    public void unblock(String friendID) {
        user.unblock(friendID);
    }

    public ArrayList<String> getSuggestedFriends() {
        suggestedFriends.clear();

        for (User potentialFriend : users) {
            String potentialFriendId = potentialFriend.getUserId();

            if (!potentialFriendId.equals(user.getUserId()) &&
                    !user.getFriends().contains(potentialFriendId) &&
                    !user.getBlocked().contains(potentialFriendId) &&
                    !user.getPendingSent().contains(potentialFriendId) &&
                    !user.getPendingReceived().contains(potentialFriendId)) {

                suggestedFriends.add(potentialFriendId);
            }
        }
        return suggestedFriends;
    }

    private User getUserByID(String userId) {
        for (User u : users) {
            if (u.getUserId().equals(userId)) {
                return u;
            }
        }
        return null;
    }

}
