package baymax.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import baymax.Baymax;

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
        // Show welcome message when GUI loads
        String welcomeMessage = "Hey there :) I'm Baymax. How can I assist you today?";
        dialogContainer.getChildren().add(DialogBox.getBaymaxDialog(welcomeMessage, baymaxImage));
    }

    public void setBaymax(Baymax b) {
        this.baymax = b;
    }

    /**
     * Handles user input from the text field.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = baymax.getResponse(input);
        DialogBox userDialog = DialogBox.getUserDialog(input, userImage);

        if (response.startsWith("Error:")) { // Show error messages differently
            DialogBox errorDialog = DialogBox.getErrorDialog(response, baymaxImage);
            dialogContainer.getChildren().addAll(userDialog, errorDialog);
        } else {
            DialogBox baymaxDialog = DialogBox.getBaymaxDialog(response, baymaxImage);
            dialogContainer.getChildren().addAll(userDialog, baymaxDialog);
        }
        userInput.clear();
    }
}
