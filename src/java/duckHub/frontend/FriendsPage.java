package duckHub.frontend;

import duckHub.MainDuck;
import duckHub.backend.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.util.Objects;

public class FriendsPage {
    private static MainDuck main;
    private static User mainUser;
    public Scene getScene(MainDuck mainDuck, User user, String type) {
        main = mainDuck;
        mainUser = user;
        //TODO find where we need to refresh the suggested List of friends
        //? when to user.suggestFriends()

        HBox mainLayout = new HBox();
        mainLayout.setAlignment(Pos.CENTER);

        VBox buttonLayout = new VBox();
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setSpacing(10);
        Button receivedButton = new Button("Received");
        Button suggestedButton = new Button("Suggested");
        Button friendsButton = new Button("Friends");
        Button blockedButton = new Button("Blocked");

        VBox listLayout = new VBox();
        listLayout.setAlignment(Pos.CENTER);

        Label listTitle = new Label("Received Requests");
        listTitle.setId("listTitleLabel");

        ContentConvertor convertor = new ContentConvertor();
        StackPane stackPane = new StackPane();
        ScrollPane friendsLayout = new ScrollPane();
        friendsLayout.setContent(convertor.populateList(user.getFriends().toArray(new String[0]), "friends"));
        ScrollPane blockedLayout = new ScrollPane();
        blockedLayout.setContent(convertor.populateList(user.getBlocked().toArray(new String[0]), "blocked"));
        ScrollPane receivedLayout = new ScrollPane();
        receivedLayout.setContent(convertor.populateList(user.getPendingReceived().toArray(new String[0]), "pending"));
        ScrollPane suggestedLayout = new ScrollPane();
        suggestedLayout.setContent(convertor.populateList(user.getSuggestedFriends(), "suggested"));
        stackPane.getChildren().addAll(friendsLayout, blockedLayout, receivedLayout, suggestedLayout);

        // Initially, show received
        if (type.equals("pending")) {
            listTitle.setText("Received Requests");
            receivedLayout.setVisible(true);
            suggestedLayout.setVisible(false);
            friendsLayout.setVisible(false);
            blockedLayout.setVisible(false);
        } else if (type.equals("suggested")) {
            listTitle.setText("Suggested Friends");
            receivedLayout.setVisible(false);
            suggestedLayout.setVisible(true);
            friendsLayout.setVisible(false);
            blockedLayout.setVisible(false);
        } else if (type.equals("blocked")) {
            listTitle.setText("Blocked List");
            receivedLayout.setVisible(false);
            suggestedLayout.setVisible(false);
            friendsLayout.setVisible(false);
            blockedLayout.setVisible(true);

        } else if (type.equals("friends")) {
            listTitle.setText("Friends List");
            receivedLayout.setVisible(false);
            suggestedLayout.setVisible(false);
            friendsLayout.setVisible(true);
            blockedLayout.setVisible(false);
        }


        // navigation
        receivedButton.setOnAction(_ -> {
            listTitle.setText("Received Requests");
            receivedLayout.setVisible(true);
            suggestedLayout.setVisible(false);
            friendsLayout.setVisible(false);
            blockedLayout.setVisible(false);
        });
        suggestedButton.setOnAction(_ -> {
            listTitle.setText("Suggested Friends");
            receivedLayout.setVisible(false);
            suggestedLayout.setVisible(true);
            friendsLayout.setVisible(false);
            blockedLayout.setVisible(false);
        });
        friendsButton.setOnAction(_ -> {
            listTitle.setText("Friends List");
            receivedLayout.setVisible(false);
            suggestedLayout.setVisible(false);
            friendsLayout.setVisible(true);
            blockedLayout.setVisible(false);
        });
        blockedButton.setOnAction(_ -> {
            listTitle.setText("Blocked List");
            receivedLayout.setVisible(false);
            suggestedLayout.setVisible(false);
            friendsLayout.setVisible(false);
            blockedLayout.setVisible(true);
        });


        buttonLayout.setPrefWidth(300);
        listLayout.setPrefWidth(900);
        stackPane.setPrefWidth(1000);
        HBox.setHgrow(friendsLayout, Priority.ALWAYS);
        HBox.setHgrow(stackPane, Priority.ALWAYS);


        buttonLayout.getChildren().addAll(receivedButton, suggestedButton, friendsButton, blockedButton);
        listLayout.getChildren().addAll(listTitle, stackPane);
        mainLayout.getChildren().addAll(buttonLayout, listLayout);



        // full screen window
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();


        Scene scene = new Scene(mainLayout, screenWidth, screenHeight);
        try{
            String styles = Objects.requireNonNull(getClass().getResource("/duckHub/frontend/FriendsPageStyles.css")).toExternalForm();
            scene.getStylesheets().add(styles);
        }catch (Exception e){
            System.out.println("StylesSheet unavailable");
        }
        return scene;
    }

}