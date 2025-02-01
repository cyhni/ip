package billy.command;

import billy.tasks.TasksList;
import billy.ui.Ui;

/**
 * The ListCommand class represents a command to list all tasks.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TasksList tasksList, Ui ui) {
        ui.printList(tasksList);
    }
}
