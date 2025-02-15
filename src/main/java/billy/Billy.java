package billy;

import java.io.IOException;
import java.time.DateTimeException;

import billy.command.Command;
import billy.exceptions.BillyException;
import billy.filemanager.FileManager;
import billy.parser.Parser;
import billy.tasks.TasksList;
import billy.ui.Ui;

/**
 * The Billy class represents the main entry point of the Billy application.
 * It initializes the necessary components and handles the main program loop.
 */
public class Billy {
    private TasksList tasksList;
    private Ui ui;
    private String commandType;

    /**
     * Constructs a Billy object.
     */
    public Billy() {
        this.ui = new Ui();
        this.tasksList = new TasksList();
        try {
            FileManager.startUp(this.tasksList.getTasksList());
        } catch (IOException | SecurityException e) {
            ui.printLine();
            ui.printError(e.getMessage());
        }
    }

    /**
     * Acts as the main entry point of the Billy application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new Billy();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String userCmd) {
        String response;
        if (userCmd.equals("bye")) {
            return ui.printBye();
        }
        try {
            Command c = Parser.parseCommand(userCmd, tasksList, ui);
            response = c.execute(tasksList, ui);
            commandType = c.getClass().getSimpleName();
        } catch (BillyException | DateTimeException | IOException e) {
            response = ui.printError(e.getMessage());
        }
        return response;
    }

    /**
     * Returns the type of command that was executed.
     *
     * @return The type of command that was executed
     */
    public String getCommandType() {
        return commandType;
    }
}
