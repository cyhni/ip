package billy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * GUI for Billy using JavaFX.
 */
public class Main extends Application {

    /**
     * Constructs a Main object.
     */
    public Main() {
        new Billy().run();
    }

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello World!");
        Scene scene = new Scene(label, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Billy");
        stage.show();
    }
}
