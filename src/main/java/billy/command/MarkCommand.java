package billy.command;

import java.io.IOException;

import billy.tasks.TasksList;
import billy.ui.Ui;

public class MarkCommand extends Command {
    private int taskNumber;

    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TasksList tasksList, Ui ui) throws IOException {
        tasksList.markTaskAsDone(taskNumber - 1);

        ui.printToUser("\nMarked as done:\n"
            + (taskNumber) + ". " + tasksList.getTask(taskNumber - 1) + "\n");
        ui.printLine();
    }
}
