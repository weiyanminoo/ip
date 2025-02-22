package baymax.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.IOException;

/**
 * Represents a dialog box for displaying messages.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox.
     *
     * @param text      The message text.
     * @param img       The image associated with the message.
     * @param isUser    True if the message is from the user; false for Baymax.
     */
    private DialogBox(String text, Image img, boolean isUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setText(text);
        displayPicture.setImage(img);
        styleMessage(isUser);

        if (!isUser) {  // Flip Baymax messages so that the image appears on the left
            flip();
        }
    }

    /**
     * Adjusts the appearance of the dialog box based on whether it's from the user.
     *
     * @param isUser True if the message is from the user, false otherwise.
     */
    private void styleMessage(boolean isUser) {
        if (isUser) {
            dialog.setStyle("-fx-background-color: lightblue; -fx-padding: 10px; -fx-background-radius: 10;");
            this.setAlignment(Pos.CENTER_RIGHT);
        } else {
            dialog.setStyle("-fx-background-color: lightgray; -fx-padding: 10px; -fx-background-radius: 10;");
            this.setAlignment(Pos.CENTER_LEFT);
        }
        dialog.setMinHeight(Region.USE_PREF_SIZE);
    }

    /**
     * Flips the dialog box so that the image is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box for user input.
     *
     * @param text The user input.
     * @param img  The user's image.
     * @return A dialog box for user messages.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, true);
    }

    /**
     * Creates a dialog box for Baymax's normal response.
     *
     * @param text The message from Baymax.
     * @param img  The Baymax image.
     * @return A dialog box for Baymax messages.
     */
    public static DialogBox getBaymaxDialog(String text, Image img) {
        return new DialogBox(text, img, false);
    }

    /**
     * Creates a dialog box for error messages.
     *
     * @param text The error message.
     * @param img  The Baymax image.
     * @return A dialog box styled for error messages.
     */
    public static DialogBox getErrorDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img, false);
        // Override style for errors
        db.dialog.setTextFill(Color.RED);
        db.dialog.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        db.dialog.setStyle("-fx-background-color: #ffcccc; -fx-padding: 10px; -fx-background-radius: 10;");
        return db;
    }
}
