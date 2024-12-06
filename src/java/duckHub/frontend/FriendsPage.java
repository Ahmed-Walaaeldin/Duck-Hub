package duckHub.frontend;

import duckHub.MainDuck;
import duckHub.backend.User;
import duckHub.backend.database.Load;
import duckHub.backend.database.Save;
import duckHub.frontend.common.ContentConvertor;
import duckHub.frontend.titleBar.TitleBar;
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

public class FriendsPage implements Constants{
    private static MainDuck main = null;
    private static User mainUser;
    Scene scene;
    HBox mainLayout;
    VBox mainContainer;

    public void init(){

        mainContainer = new VBox();
        scene = new Scene(mainContainer, SCENE_WIDTH, SCENE_HEIGHT);

    }

    public void displayScene(MainDuck mainDuck, User user, String type) {
        main = mainDuck;
        mainUser = user;

        TitleBar titleBar = new TitleBar(mainDuck,user);

        mainLayout = new HBox();
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
        friendsLayout.setContent(convertor.populateList(user, user.getFriends().toArray(new String[0]), "friends"));
        ScrollPane blockedLayout = new ScrollPane();
        blockedLayout.setContent(convertor.populateList(user, user.getBlocked().toArray(new String[0]), "blocked"));
        ScrollPane receivedLayout = new ScrollPane();
        receivedLayout.setContent(convertor.populateList(user, user.getPendingReceived().toArray(new String[0]), "pending"));
        ScrollPane suggestedLayout = new ScrollPane();
        suggestedLayout.setContent(convertor.populateList(user, user.getSuggestedFriends(), "suggested"));
        stackPane.getChildren().addAll(friendsLayout, blockedLayout, receivedLayout, suggestedLayout);

        // Initially, show received
        switch (type) {
            case "pending" -> {
                listTitle.setText("Received Requests");
                receivedLayout.setVisible(true);
                suggestedLayout.setVisible(false);
                friendsLayout.setVisible(false);
                blockedLayout.setVisible(false);
            }
            case "suggested" -> {
                listTitle.setText("Suggested Friends");
                receivedLayout.setVisible(false);
                suggestedLayout.setVisible(true);
                friendsLayout.setVisible(false);
                blockedLayout.setVisible(false);
            }
            case "blocked" -> {
                listTitle.setText("Blocked List");
                receivedLayout.setVisible(false);
                suggestedLayout.setVisible(false);
                friendsLayout.setVisible(false);
                blockedLayout.setVisible(true);
            }
            case "friends" -> {
                listTitle.setText("Friends List");
                receivedLayout.setVisible(false);
                suggestedLayout.setVisible(false);
                friendsLayout.setVisible(true);
                blockedLayout.setVisible(false);
            }
        }

        Load load = new Load();
        // navigation
        receivedButton.setOnAction(_ -> {
            load.loadFromFile();
            mainDuck.showFriendsPage(user,"pending");
        });
        suggestedButton.setOnAction(_ -> {
            load.loadFromFile();
            mainDuck.showFriendsPage(user,"suggested");
        });
        friendsButton.setOnAction(_ -> {
            load.loadFromFile();
            mainDuck.showFriendsPage(user,"friends");
        });
        blockedButton.setOnAction(_ -> {
            load.loadFromFile();
            mainDuck.showFriendsPage(user,"blocked");
        });


        buttonLayout.setPrefWidth(300);
        listLayout.setPrefWidth(900);
//        stackPane.setPrefWidth(1000);
        HBox.setHgrow(friendsLayout, Priority.ALWAYS);
        HBox.setHgrow(stackPane, Priority.ALWAYS);


        buttonLayout.getChildren().addAll(receivedButton, suggestedButton, friendsButton, blockedButton);
        listLayout.getChildren().addAll(listTitle, stackPane);
        mainLayout.getChildren().addAll(buttonLayout, listLayout);


        // The main container where the title bar and the main layout exist
        VBox.setVgrow(mainLayout, Priority.ALWAYS);
        mainContainer.getChildren().addAll(titleBar.getTitleBar(), mainLayout);

        try{
            String styles = Objects.requireNonNull(getClass().getResource("/duckHub/frontend/FriendsPageStyles.css")).toExternalForm();
            scene.getStylesheets().add(styles);
        }catch (Exception e){
            System.out.println("StylesSheet unavailable");
        }
    }
    public Scene getScene() {
        return scene;
    }

    public static void refresh(String type){
        if (main != null)
            main.showFriendsPage(mainUser, type);
    }
    public void display(MainDuck mainDuck,User user,String type){
        init();
        displayScene(mainDuck,user,type);
    }
    public void refreshWindow(){
        mainContainer.getChildren().clear();
        mainLayout.getChildren().clear();
        displayScene(main,mainUser,"friends");

    }
}