package billy.command;

import java.io.IOException;

import billy.tasks.Event;
import billy.tasks.TasksList;
import billy.ui.Ui;

public class EventCommand extends Command {
    private Event event;

    public EventCommand(Event event) {
        this.event = event;
    }

    @Override
    public void execute(TasksList tasksList, Ui ui) throws IOException {
        tasksList.addTask(event);

        ui.printTaskAdded(event, tasksList.getSize());
    }
}
