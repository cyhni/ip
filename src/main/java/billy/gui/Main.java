package billy.gui;

import java.io.IOException;

import billy.Billy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * GUI for Billy using JavaFX.
 */
public class Main extends Application {
    private Billy billy;

    /**
     * Constructs a Main object.
     */
    public Main() {
        billy = new Billy();
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Billy");
            fxmlLoader.<MainWindow>getController().setBilly(billy);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
