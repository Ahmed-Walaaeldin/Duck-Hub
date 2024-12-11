package duckHub.backend;

import duckHub.backend.database.Save;

public class FriendsBackend {
public static boolean sendFriendRequest(User user, String friendID) {
    if (!user.getFriends().contains(friendID) && !user.getBlocked().contains(friendID)) {
        User friend = BackendDuck.getUserByID(friendID);
        if (friend != null) {
            if (user.getPendingReceived().contains(friendID)) {
                user.getPendingReceived().remove(friendID);
                friend.getPendingSent().remove(user.getUserId());

                user.addFriend(friendID);
                friend.addFriend(user.getUserId());

                // Save both users individually first
                Save save = new Save();
                save.saveToFile(user);
                save.saveToFile(friend);
                save.saveAllUsers();
                return true;
            }

            if (!user.getPendingSent().contains(friendID)) {
                user.getPendingSent().add(friendID);
                friend.getPendingReceived().add(user.getUserId());

                // Save both users individually
                Save save = new Save();
                save.saveToFile(user);
                save.saveToFile(friend);
                save.saveAllUsers();
                return true;
            }
        }
    }
    return false;
}

public static boolean acceptFriendRequest(User user, String friendID) {
    if (user.getPendingReceived().contains(friendID)) {
        System.out.println("inside pending received true");
        User friend = BackendDuck.getUserByID(friendID);
        System.out.println(friend);
        if (friend != null) {
            // Clear pending requests
            user.getPendingReceived().remove(friendID);
            friend.getPendingSent().remove(user.getUserId());

            // Add as friends
            user.getFriends().add(friendID);
            friend.getFriends().add(user.getUserId());

            Save save = new Save();
            save.saveAllUsers();
            return true;
        }
    }
    return false;
}

    public static boolean declineFriendRequest(User user, String friendID) {
        if (user.getPendingReceived().contains(friendID)) {
            user.getPendingReceived().remove(friendID);

            User friend = BackendDuck.getUserByID(friendID);
            if (friend != null) {
                friend.getPendingSent().remove(user.getUserId());
                Save save = new Save();
                save.saveAllUsers();
                return true;
            }
        }
        return false;
    }

    public static boolean removeFriend(User user, String friendID) {
        if (user.getFriends().contains(friendID)) {
            user.removeFriend(friendID);
            Save save = new Save();
            save.saveAllUsers();
            return true;
        }
        return false;
    }

    public static boolean block(User user, String friendID) {
        if (user.getFriends().contains(friendID) && !user.getBlocked().contains(friendID)) {
            user.block(friendID);
            Save save = new Save();
            save.saveAllUsers();
            return true;
        }
        return false;
    }

    public static boolean unblock(User user, String friendID) {
        if (user.getBlocked().contains(friendID)) {
            user.unblock(friendID);
            Save save = new Save();
            save.saveAllUsers();
            return true;
        }
        return false;
    }


}