package billy.command;

import java.io.IOException;

import billy.tasks.Deadline;
import billy.tasks.TasksList;
import billy.ui.Ui;

/**
 * The DeadlineCommand class represents a command to add a deadline task.
 */
public class DeadlineCommand extends Command {
    private Deadline deadline;

    /**
     * Constructs a DeadlineCommand object.
     *
     * @param deadline The deadline task to be added.
     */
    public DeadlineCommand(Deadline deadline) {
        this.deadline = deadline;
    }

    @Override
    public String execute(TasksList tasksList, Ui ui) throws IOException {
        tasksList.addTask(deadline);

        return ui.printAddedTask(deadline, tasksList.getSize());
    }
}
