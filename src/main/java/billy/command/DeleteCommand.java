package billy.command;

import java.io.IOException;

import billy.tasks.Task;
import billy.tasks.TasksList;
import billy.ui.Ui;

public class DeleteCommand extends Command {
    private int index;
    private Task deletedTask;

    public DeleteCommand(int index, Task deletedTask) {
        this.index = index;
        this.deletedTask = deletedTask;
    }

    @Override
    public void execute(TasksList tasksList, Ui ui) throws IOException {
        tasksList.removeTask(index);

        ui.printToUser("\nRemoved from the list:\n" + (index + 1) + ". " + deletedTask + "\n",
                "There are currently " + tasksList.getSize() + " task(s) in the list.\n");
        ui.printLine();
    }
}
