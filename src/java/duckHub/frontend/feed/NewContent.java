package duckHub.frontend.feed;

import duckHub.MainDuck;
import duckHub.backend.User;
import duckHub.frontend.PopUp;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class NewContent {
    private Stage stage;

    // Window size
    private int width;
    private int height;

    // The user
    private User user;

    // Layouts
    private VBox root; // Main Layout
    private HBox buttonsLayout; // for post and add image buttons

    // Buttons
    private Button postButton;
    private Button backButton;
    private Button addImageButton;

    // Post Elements (Text and image)
    private TextField newContentTextField;
    private Image newContentImage;

    // flag to know if story or post
    private boolean permanent;

    private void layoutInitializer() {
        // set width and height
        width = 300;
        height = 250;

        root = new VBox();
        buttonsLayout = new HBox();
        // Configure buttons layout
        buttonsLayout.setSpacing(10);
        buttonsLayout.setPrefWidth(width);
        buttonsLayout.setAlignment(Pos.CENTER);

//        buttonsLayout.setSpacing(30);
    }

    private void setStage() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Content");
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
    }

    private void elementsInitializer() {
        postButton = new Button("Post");
        backButton = new Button();
        String imagePath = getClass().getResource("/duckhub/frontend/back-button.png").toExternalForm();
        Image backImage = new Image(imagePath);
        ImageView backImageView = new ImageView(backImage);
        ButtonCustomizer buttonCustomizer = new ButtonCustomizer();
        buttonCustomizer.roundedButtonImage(backImageView, backButton);
        addImageButton = new Button("Add Image");

    }

    private void addElementsToLayout() {
        layoutInitializer();

        // add back button first element.
        root.getChildren().addAll(backButton);

        // Text Field to add content text
        newContentTextField = new TextField();
        newContentTextField.setPromptText("What's on your mind?");
        newContentTextField.setPrefSize(width, height-50);

        root.getChildren().add(newContentTextField);

        // add image button
        buttonsLayout.getChildren().add(addImageButton);

        // Spacer between the two buttons
        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        buttonsLayout.getChildren().add(spacer);

        // add post button
        buttonsLayout.getChildren().add(postButton);

        // add both buttons with their layouts to the root layout (the VBox)
        root.getChildren().add(buttonsLayout);

        // Buttons Handlers
        postButton.setOnAction(e -> {
            addContent();
        });
        backButton.setOnAction(e -> {
            stage.close();
        });
        addImageButton.setOnAction(e -> {
            setContentImage();
        });
    }

    private void setContentImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                newContentImage = new Image(selectedFile.toURI().toString());
            } catch (Exception e) {
                System.out.println("Unable to load image" + e.getMessage());
            }
        }
    }

    // Will handle both stories and posts based on the flag passed.
    private void addContent() {
        if (newContentTextField.getText().isEmpty() && newContentImage == null) {
            stage.close();
        } else {
            String contentText = newContentTextField.getText();
            if (newContentImage != null) {
                user.createContent(permanent, contentText, newContentImage);
                stage.close();
            } else {
                user.createContent(permanent, contentText);
                stage.close();
            }
        }
    }

    public void display(User user,boolean permanent) {
        this.permanent = permanent;
        this.user = user;
        elementsInitializer();
        addElementsToLayout();
        setStage();
        stage.show();
    }
}
