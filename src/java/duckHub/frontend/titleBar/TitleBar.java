package duckHub.frontend.titleBar;

import duckHub.frontend.Constants;
import duckHub.MainDuck;
import duckHub.backend.User;
import duckHub.frontend.common.ButtonCustomizer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

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
        Image homeImage = new Image("/duckhub/frontend/home.png");
        Button homeButton = createNavigationButton(homeImage,
                _ -> mainDuck.showNewsfeed(user));

        // Profile button
        Image profileImage = new Image("/duckhub/frontend/profile.png");
        Button profileButton = createNavigationButton(profileImage,
                _ -> mainDuck.showProfilePage());

        // Friends button
        Image friendsImage = new Image("/duckhub/frontend/friends.png");
        Button friendsButton = createNavigationButton(friendsImage,
                _ -> mainDuck.showFriendsPage(user, "friends"));

        // Logout button
        Image logoutImage = new Image("/duckhub/frontend/images/logout.png");
        Button logoutButton = createNavigationButton(logoutImage, _ -> mainDuck.showLoginPage());

        Image refreshImage = new Image("/duckhub/frontend/images/refresh-button.png");
        Button refreshButton = createNavigationButton(refreshImage, _ -> mainDuck.refresh());
        // Spacer to push logout to the right
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        titleBar.getChildren().addAll(homeButton, profileButton, friendsButton, spacer, logoutButton, refreshButton);
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
