package billy.command;

import java.io.IOException;

import billy.tasks.TasksList;
import billy.ui.Ui;

public abstract class Command {
    public abstract void execute(TasksList tasksList, Ui ui) throws IOException;
}
