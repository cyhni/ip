package billy.command;

import java.io.IOException;

import billy.tasks.TasksList;
import billy.ui.Ui;

/**
 * The Command class represents a command that can be executed by the user.
 * This is an abstract class that requires the implementation of the execute method.
 *
 * <p>Classes that extend Command must implement the execute method to define the
 * specific behavior of the command.</p>
 */
public abstract class Command {
    /**
     * Executes the command.
     *
     * @param tasksList The list of tasks.
     * @param ui The user interface.
     * @throws IOException If an I/O error occurs.
     */
    public abstract String execute(TasksList tasksList, Ui ui) throws IOException;
}
