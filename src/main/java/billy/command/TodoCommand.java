package billy.command;

import java.io.IOException;

import billy.tasks.TasksList;
import billy.tasks.Todo;
import billy.ui.Ui;

/**
 * The TodoCommand class represents a command to add a todo task.
 */
public class TodoCommand extends Command {
    private Todo todo;

    /**
     * Constructs a TodoCommand object.
     *
     * @param todo The todo task to be added.
     */
    public TodoCommand(Todo todo) {
        this.todo = todo;
    }

    @Override
    public void execute(TasksList tasksList, Ui ui) throws IOException {
        tasksList.addTask(todo);

        ui.printAddedTask(todo, tasksList.getSize());
    }
}
