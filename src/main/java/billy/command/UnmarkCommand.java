package billy.command;

import java.io.IOException;

import billy.tasks.TasksList;
import billy.ui.Ui;

/**
 * The UnmarkCommand class represents a command to unmark a task as done.
 */
public class UnmarkCommand extends Command {
    private int taskNumber;

    /**
     * Constructs an UnmarkCommand object.
     *
     * @param taskNumber The index of the task to be marked as undone.
     */
    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public String execute(TasksList tasksList, Ui ui) throws IOException {
        tasksList.markTaskAsUndone(taskNumber - 1);

        return ui.printToUser("\nMarked as undone:\n"
            + (taskNumber) + ". " + tasksList.getTask(taskNumber - 1) + "\n");
    }
}
