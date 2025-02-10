package billy.ui;

import billy.constants.DesignConstants;
import billy.tasks.Task;
import billy.tasks.TasksList;

/**
 * The Ui class deals with interactions with the user.
 * It prints messages to the user and reads input from the user.
 */
public class Ui {

    /**
     * Prints the introduction message.
     */
    public final String printIntroduction() {
        return printToUser(
                DesignConstants.HORIZONTALLINE_STRING,
                DesignConstants.LOGO_STRING,
                DesignConstants.HORIZONTALLINE_STRING,
                "\nWelcome to the world of Billy!\n" + "How can I help you?\n",
                DesignConstants.HORIZONTALLINE_STRING);
    }

    // /**
    //  * Reads the command from the user.
    //  *
    //  * @return The command entered by the user.
    //  */
    // public final String readCommand() {
    //     System.out.print("Enter your command: ");
    // }

    /**
     * Prints the goodbye message.
     */
    public final String printBye() {
        return printToUser("Bye bye.");
    }

    /**
     * Prints an error message.
     *
     * @param errorMessage The error message to be printed.
     */
    public final String printError(String errorMessage) {
        return printToUser("Error: ", errorMessage);
    }

    /**
     * Prints the list of tasks.
     *
     * @param tasksList The list of tasks to be printed.
     */
    public final String printList(TasksList tasksList) {
        StringBuilder builder = new StringBuilder();
        builder.append(printToUser("Here are the items in your list:"));
        for (int i = 0; i < tasksList.getSize(); i++) {
            builder.append(printToUser((i + 1) + ". " + tasksList.getTask(i)));
        }
        return builder.toString();
    }

    /**
     * Prints the filtered list of tasks containing the keyword.
     *
     * @param tasksList The list of tasks to filter.
     * @param keyword The keyword to filter the list with.
     */
    public final String printFilteredList(TasksList tasksList, String keyword) {
        StringBuilder builder = new StringBuilder();
        builder.append(printToUser("Here are the items in your list:"));
        for (int i = 0; i < tasksList.getSize(); i++) {
            if (tasksList.getTask(i).getDescription().contains(keyword)) {
                builder.append(printToUser((i + 1) + ". " + tasksList.getTask(i)));
            }
        }
        return builder.toString();
    }

    /**
     * Prints the task added.
     *
     * @param task The task to be printed.
     * @param counter The number of tasks in the list.
     */
    public final String printAddedTask(Task task, int counter) {
        return printToUser("Added to the list:\n" + counter + ". " + task + "\n",
                "There are currently " + counter + " task(s) in the list.");
    }

    /**
     * Prints a line.
     */
    public final String printLine() {
        return printToUser(DesignConstants.HORIZONTALLINE_STRING);
    }

    /**
     * Prints the specified list of {@code String} messages to the user.
     *
     * @param message The list of messages to be printed.
     */
    public String printToUser(String... message) {
        StringBuilder builder = new StringBuilder();
        for (String m : message) {
            builder.append(m).append("\n");
        }
        return builder.toString();
    }
}
