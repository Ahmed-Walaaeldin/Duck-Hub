package duckHub.backend;

import java.util.ArrayList;

public class FriendsManagement {
    private static User user;
    private final User[] users;


    public FriendsManagement(User user) {
        FriendsManagement.user = user;
        users = BackendDuck.getUsers();
    }

   public static boolean sendFriendRequest(String friendID) {
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

public static boolean acceptFriendRequest(String friendID) {
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

public static boolean declineFriendRequest(String friendID) {
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

public static boolean removeFriend(String friendID) {
    if (user.getFriends().contains(friendID)) {
        user.removeFriend(friendID);
        return true;
    }
    return false;
}

public static boolean block(String friendID) {
    if (user.getFriends().contains(friendID) && !user.getBlocked().contains(friendID)) {
        user.block(friendID);
        return true;
    }
    return false;
}

public static boolean unblock(String friendID) {
    if (user.getBlocked().contains(friendID)) {
        user.unblock(friendID);
        return true;
    }
    return false;
}


}