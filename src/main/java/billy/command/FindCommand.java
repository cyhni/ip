package billy.command;

import billy.tasks.TasksList;
import billy.ui.Ui;

/**
 * Represents a command to find tasks in the task list.
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Creates a new FindCommand with the given keyword.
     *
     * @param keyword The keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override 
    public void execute(TasksList tasksList, Ui ui) {
        ui.printFilteredList(tasksList, keyword);
    }
}
