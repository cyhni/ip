package billy.command;

import java.io.IOException;

import billy.tasks.Deadline;
import billy.tasks.TasksList;
import billy.ui.Ui;

public class DeadlineCommand extends Command {
    private Deadline deadline;

    public DeadlineCommand(Deadline deadline) {
        this.deadline = deadline;
    }

    @Override
    public void execute(TasksList tasksList, Ui ui) throws IOException {
        tasksList.addTask(deadline);

        ui.printTaskAdded(deadline, tasksList.getSize());
    }
}
