package duckHub.frontend;

import duckHub.MainDuck;
import duckHub.backend.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.util.Objects;

public class FriendsPage {

    public Scene getScene(MainDuck mainDuck, User user) {
        VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // full screen window
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        ScrollPane friendsLayout = new ScrollPane();
        friendsLayout.setContent(populateList(user.getFriends().toArray(new String[0])));
        mainLayout.getChildren().add(friendsLayout);

        ScrollPane blockedLayout = new ScrollPane();
        blockedLayout.setContent(populateList(user.getBlocked().toArray(new String[0])));
        mainLayout.getChildren().add(blockedLayout);

        ScrollPane pendingLayout = new ScrollPane();
        pendingLayout.setContent(populateList(user.getPendingReceived().toArray(new String[0])));
        mainLayout.getChildren().add(pendingLayout);

        // @zoz law get t3mel keda fy el suggested friends hatla2y b2a enha mosh f nafs el makan, mosh user.getSuggested


        Scene scene = new Scene(mainLayout, screenWidth, screenHeight);
        try{
            String styles = Objects.requireNonNull(getClass().getResource("/duckHub/Styles.css")).toExternalForm();
            scene.getStylesheets().add(styles);
        }catch (Exception e){
            System.out.println("StylesSheet unavailable");
        }
        return scene;
    }

    private VBox populateList(String[] ids) {
        VBox contentBox = new VBox();
        for (String id : ids) {
            HBox banner = ListItem.createListItem(id);
            contentBox.getChildren().add(banner);
        }
        return contentBox;
    }

}