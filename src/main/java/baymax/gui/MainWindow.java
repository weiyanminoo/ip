package baymax.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import baymax.Baymax;
import baymax.ui.DialogBox;

/**
 * The main window for the chatbot's GUI.
 */
public class MainWindow extends VBox {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Baymax baymax;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image baymaxImage = new Image(this.getClass().getResourceAsStream("/images/DaBaymax.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Baymax instance */
    public void setBaymax(Baymax b) {
        this.baymax = b;
    }

    /**
     * Handles user input from the text field.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = baymax.getResponse(input); // Use updated Baymax method
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, baymaxImage)
        );
        userInput.clear();
    }

    /**
     * Displays a message in the GUI.
     */
    public void displayMessage(String message) {
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(message, baymaxImage));
    }
}
