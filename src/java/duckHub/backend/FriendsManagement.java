package duckHub.backend;

import java.util.ArrayList;

public class FriendsManagement {
    private final User user;
    private final ArrayList<User> users;


    public FriendsManagement(User user) {
        this.user = user;
        users = BackendDuck.getUsers();
    }

   public boolean sendFriendRequest(String friendID) {
    if (!user.getFriends().contains(friendID) && !user.getBlocked().contains(friendID)) {
        if (!user.getPendingSent().contains(friendID)) {
            user.getPendingSent().add(friendID);
            User friend = BackendDuck.getUserByID(friendID);
            if (friend != null) {
                friend.getPendingReceived().add(user.getUserId());
                return true;
            }
        }
    }
    return false;
}

public boolean acceptFriendRequest(String friendID) {
    if (user.getPendingReceived().contains(friendID)) {
        user.getPendingReceived().remove(friendID);
        user.addFriend(friendID);

        User friend = BackendDuck.getUserByID(friendID);
        if (friend != null) {
            friend.getPendingSent().remove(user.getUserId());
            friend.addFriend(user.getUserId());
            return true;
        }
    }
    return false;
}

public boolean declineFriendRequest(String friendID) {
    if (user.getPendingReceived().contains(friendID)) {
        user.getPendingReceived().remove(friendID);

        User friend = BackendDuck.getUserByID(friendID);
        if (friend != null) {
            friend.getPendingSent().remove(user.getUserId());
            return true;
        }
    }
    return false;
}

public boolean removeFriend(String friendID) {
    if (user.getFriends().contains(friendID)) {
        user.removeFriend(friendID);
        return true;
    }
    return false;
}

public boolean block(String friendID) {
    if (user.getFriends().contains(friendID) && !user.getBlocked().contains(friendID)) {
        user.block(friendID);
        return true;
    }
    return false;
}

public boolean unblock(String friendID) {
    if (user.getBlocked().contains(friendID)) {
        user.unblock(friendID);
        return true;
    }
    return false;
}

    public void suggestFriends() {
        user.getSuggestedFriends().clear();

        for (User potentialFriend : users) {
            String potentialFriendId = potentialFriend.getUserId();

            if (!potentialFriendId.equals(user.getUserId()) &&
                    !user.getFriends().contains(potentialFriendId) &&
                    !user.getBlocked().contains(potentialFriendId) &&
                    !user.getPendingSent().contains(potentialFriendId) &&
                    !user.getPendingReceived().contains(potentialFriendId)) {

                user.getSuggestedFriends().add(potentialFriendId);
            }
        }
    }
}