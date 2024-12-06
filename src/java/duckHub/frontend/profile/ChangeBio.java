package duckHub.frontend.profile;

import duckHub.backend.User;
import duckHub.frontend.common.ButtonCustomizer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangeBio {
    private User user;

    private Stage stage;

    // Window size
    private int width;
    private int height;

    // Layouts
    private VBox root;

    // Buttons
    private Button backButton;
    private Button submitButton;

    // Post Elements (Text and image)
    private TextArea newBioTextField;

    // flag to know if story or post
    private boolean permanent;

    private void layoutInitializer() {
        // set width and height
        width = 300;
        height = 250;

        // initialize
        root = new VBox();
    }

    private void setStage() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Change Bio");
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
    }

    private void elementsInitializer() {
        backButton = new Button();
        submitButton = new Button("Quack");

        // Make and customize the back button
        String imagePath = getClass().getResource("/duckhub/frontend/back-button.png").toExternalForm();
        Image backImage = new Image(imagePath);
        ImageView backImageView = new ImageView(backImage);
        ButtonCustomizer buttonCustomizer = new ButtonCustomizer();
        buttonCustomizer.rectangleButtonImage(backImageView, backButton);
    }

    private void addElementsToLayout() {
        layoutInitializer();

        // Text Field to add content text
        newBioTextField = new TextArea();
        newBioTextField.setPrefSize(width, height-50);
        newBioTextField.setWrapText(true); // Enable text wrapping
        newBioTextField.setPrefRowCount(3); // Set visible number of rows
        newBioTextField.setPrefColumnCount(20);

        root.getChildren().addAll(backButton, newBioTextField, submitButton);

        // Buttons Handlers
        submitButton.setOnAction(_ -> changeBio());
        backButton.setOnAction(_ -> stage.close());
    }

    // Will handle both stories and posts based on the flag passed.
    private void changeBio() {
        if (newBioTextField.getText().isEmpty()) {
            stage.close();
        } else {
            String contentText = newBioTextField.getText();
            user.setBioContent(contentText);
            stage.close();
        }
    }

    public void display(User user) {
        this.user = user;
        elementsInitializer();
        addElementsToLayout();
        setStage();
        stage.showAndWait();
    }
}
