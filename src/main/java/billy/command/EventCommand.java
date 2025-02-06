package billy.command;

import java.io.IOException;

import billy.tasks.Event;
import billy.tasks.TasksList;
import billy.ui.Ui;

/**
 * The EventCommand class represents a command to add an event task.
 */
public class EventCommand extends Command {
    private Event event;

    /**
     * Constructs an EventCommand object.
     *
     * @param event The event task to be added.
     */
    public EventCommand(Event event) {
        this.event = event;
    }

    @Override
    public void execute(TasksList tasksList, Ui ui) throws IOException {
        tasksList.addTask(event);

        ui.printAddedTask(event, tasksList.getSize());
    }
}
