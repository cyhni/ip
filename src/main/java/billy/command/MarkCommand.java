package billy.command;

import java.io.IOException;

import billy.tasks.TasksList;
import billy.ui.Ui;

/**
 * The MarkCommand class represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private int taskNumber;

    /**
     * Constructs a MarkCommand object.
     *
     * @param taskNumber The index of the task to be marked as done.
     */
    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public String execute(TasksList tasksList, Ui ui) throws IOException {
        tasksList.markTaskAsDone(taskNumber - 1);

        return ui.printToUser("\nMarked as done:\n"
            + (taskNumber) + ". " + tasksList.getTask(taskNumber - 1) + "\n");
    }
}
