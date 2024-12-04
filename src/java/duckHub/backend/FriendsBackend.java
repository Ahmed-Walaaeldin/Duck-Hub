package duckHub.backend;

public class FriendsBackend {
   public static boolean sendFriendRequest(User user , String friendID) {
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

public static boolean acceptFriendRequest(User user, String friendID) {
    if (user.getPendingReceived().contains(friendID)) {
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

public static boolean declineFriendRequest(User user,String friendID) {
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

public static boolean removeFriend(User user,String friendID) {
    if (user.getFriends().contains(friendID)) {
        user.removeFriend(friendID);
        return true;
    }
    return false;
}

public static boolean block(User user,String friendID) {
    if (user.getFriends().contains(friendID) && !user.getBlocked().contains(friendID)) {
        user.block(friendID);
        return true;
    }
    return false;
}

public static boolean unblock(User user,String friendID) {
    if (user.getBlocked().contains(friendID)) {
        user.unblock(friendID);
        return true;
    }
    return false;
}


}