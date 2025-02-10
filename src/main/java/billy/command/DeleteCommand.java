package billy.command;

import java.io.IOException;

import billy.tasks.Task;
import billy.tasks.TasksList;
import billy.ui.Ui;

/**
 * The DeleteCommand class represents a command to delete a task.
 */
public class DeleteCommand extends Command {
    private int index;
    private Task deletedTask;

    /**
     * Constructs a DeleteCommand object.
     *
     * @param index The index of the task to be deleted.
     * @param deletedTask The task to be deleted.
     */
    public DeleteCommand(int index, Task deletedTask) {
        this.index = index;
        this.deletedTask = deletedTask;
    }

    @Override
    public String execute(TasksList tasksList, Ui ui) throws IOException {
        tasksList.removeTask(index);

        return ui.printToUser("\nRemoved from the list:\n" + (index + 1) + ". " + deletedTask + "\n",
                "There are currently " + tasksList.getSize() + " task(s) in the list.\n");
    }
}
