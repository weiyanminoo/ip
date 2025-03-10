package baymax.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import baymax.Baymax;
import java.io.IOException;

/**
 * The main entry point for the JavaFX GUI application.
 */
public class Main extends Application {

    private Baymax baymax = new Baymax("./data/tasks.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setBaymax(baymax);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
