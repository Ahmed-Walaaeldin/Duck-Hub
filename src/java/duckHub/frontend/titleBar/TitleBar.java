package duckHub.frontend.titleBar;

import duckHub.backend.BackendDuck;
import duckHub.backend.database.Save;
import duckHub.frontend.Constants;
import duckHub.MainDuck;
import duckHub.backend.User;
import duckHub.frontend.common.ButtonCustomizer;
import duckHub.frontend.search.SearchPopup;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TitleBar implements Constants {
    private final HBox titleBar;
    private final MainDuck mainDuck;
    private final User user;

    public TitleBar(MainDuck mainDuck, User user) {
        this.mainDuck = mainDuck;
        this.user = user;
        this.titleBar = new HBox();
        setupTitleBar();
    }

    private void setupTitleBar() {
        titleBar.setStyle("-fx-padding: 5px; -fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setSpacing(10);

        // Home button
        Image feedImage = new Image("/duckhub/frontend/feed2.png");
        Button feedButton = createNavigationButton(feedImage,
                _ -> mainDuck.showNewsfeed(user));

        // Profile button
        Image profileImage = new Image("/duckhub/frontend/profile.png");
        Button profileButton = createNavigationButton(profileImage,
                _ -> mainDuck.showProfilePage());

        // Friends button
        Image friendsImage = new Image("/duckhub/frontend/friends.png");
        Button friendsButton = createNavigationButton(friendsImage,
                _ -> mainDuck.showFriendsPage(user, "friends"));

        // Search field and button
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");

        // Create a Popup to display the VBox
        VBox searchResultsBox = new VBox();
        searchResultsBox.setStyle("-fx-background-color: white; -fx-border-color: lightgray;");
        searchResultsBox.setSpacing(5);
        Popup searchPopup = new Popup();
        searchPopup.getContent().add(searchResultsBox);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            SearchPopup.getSearchResult(newValue,searchResultsBox);
            // Show the popup if there are results
            if (!searchResultsBox.getChildren().isEmpty()) {
                if (!searchPopup.isShowing()) {
                    searchPopup.show(searchField, searchField.localToScreen(searchField.getBoundsInLocal()).getMinX(),
                            searchField.localToScreen(searchField.getBoundsInLocal()).getMaxY());
                }
            } else {
                searchPopup.hide();
            }

        });

        Image searchImage = new Image("/duckhub/frontend/search.png");
        Button searchButton = createNavigationButton(searchImage, _ -> {
            String query = searchField.getText();
            // mainDuck.showSearch(query);
        });

        // Logout button
        Image logoutImage = new Image("/duckhub/frontend/logout.png");
        Button logoutButton = createNavigationButton(logoutImage, _ -> {
            mainDuck.showLoginPage();
            // Set the user as offline
            user.setStatus(false);

            // Save all users data
            Save save = new Save();
            save.saveAllUsers();
        });

        Image refreshImage = new Image("/duckhub/frontend/refresh.png");
        Button refreshButton = createNavigationButton(refreshImage, _ -> mainDuck.refresh());

        // Close button
        Image closeImage = new Image("/duckhub/frontend/close.png");
        Button closeButton = createNavigationButton(closeImage, e -> {
            user.setStatus(false);
            Save save = new Save();
            save.saveAllUsers();
            Stage stage = (Stage) titleBar.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

        });

        // Spacer
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox spacer2 = new HBox();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        titleBar.getChildren().addAll(feedButton, profileButton, friendsButton,spacer,searchField,searchButton,spacer2,logoutButton, refreshButton, closeButton);
    }

    private Button createNavigationButton(Image logo, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        Button button = new Button();
        ImageView iconView = new ImageView(logo);
        iconView.setFitHeight(20);
        iconView.setFitWidth(20);

        ButtonCustomizer customizer = new ButtonCustomizer();
        customizer.roundedButtonImage(iconView, button, 15);

        button.setOnAction(handler);
        return button;
    }

    public HBox getTitleBar() {
        return titleBar;
    }

}
