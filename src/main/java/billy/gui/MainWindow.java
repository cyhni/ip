package billy.gui;

import billy.Billy;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {

    private static final String WELCOME_STRING = "Welcome to the world of Billy!\n" + "How can I help you?";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Billy billy;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/Billy.png"));

    /**
     * Initializes the MainWindow.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(DialogBox.getBillyDialog(WELCOME_STRING, dukeImage));
    }

    /**
     * Sets the Billy object for the MainWindow.
     *
     * @param b The Billy object to be set.
     */
    public void setBilly(Billy b) {
        billy = b;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = billy.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBillyDialog(response, dukeImage)
        );
        userInput.clear();
    }
}
