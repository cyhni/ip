package billy.command;

import java.io.IOException;

import billy.tasks.TasksList;
import billy.ui.Ui;

public class UnmarkCommand extends Command {
    private int taskNumber;

    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TasksList tasksList, Ui ui) throws IOException {
        tasksList.markTaskAsUndone(taskNumber - 1);

        ui.printToUser("\nMarked as undone:\n"
            + (taskNumber) + ". " + tasksList.getTask(taskNumber - 1) + "\n");
        ui.printLine();
    }
}
