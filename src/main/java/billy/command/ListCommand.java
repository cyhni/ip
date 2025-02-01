package billy.command;

import billy.tasks.TasksList;
import billy.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TasksList tasksList, Ui ui) {
        ui.printList(tasksList);
    }
}
