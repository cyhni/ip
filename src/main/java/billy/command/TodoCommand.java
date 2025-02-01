package billy.command;

import java.io.IOException;

import billy.tasks.TasksList;
import billy.tasks.Todo;
import billy.ui.Ui;

public class TodoCommand extends Command {
    private Todo todo;

    public TodoCommand(Todo todo) {
        this.todo = todo;
    }

    @Override
    public void execute(TasksList tasksList, Ui ui) throws IOException {
        tasksList.addTask(todo);

        ui.printTaskAdded(todo, tasksList.getSize());
    }
}
