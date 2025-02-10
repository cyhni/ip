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
    public String execute(TasksList tasksList, Ui ui) {
        return ui.printFilteredList(tasksList, keyword);
    }
}
