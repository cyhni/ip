package billy.ui;

import java.util.Scanner;

import billy.constants.DesignConstants;
import billy.tasks.Task;
import billy.tasks.TasksList;

/**
 * The Ui class deals with interactions with the user.
 * It prints messages to the user and reads input from the user.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Constructs a Ui object.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints the introduction message.
     */
    public final void printIntroduction() {
        printToUser(
                DesignConstants.HORIZONTALLINE_STRING,
                DesignConstants.LOGO_STRING,
                DesignConstants.HORIZONTALLINE_STRING,
                "\nWelcome to the world of Billy!\n" + "How can I help you?\n",
                DesignConstants.HORIZONTALLINE_STRING);
    }

    /**
     * Reads the command from the user.
     *
     * @return The command entered by the user.
     */
    public final String readCommand() {
        System.out.print("Enter your command: ");
        return scanner.nextLine();
    }

    /**
     * Prints the goodbye message.
     */
    public final void printBye() {
        printToUser(
                "\nBye bye.\n",
                DesignConstants.HORIZONTALLINE_STRING);
        scanner.close();
    }

    /**
     * Prints an error message.
     *
     * @param errorMessage The error message to be printed.
     */
    public final void printError(String errorMessage) {
        printToUser(
                "",
                errorMessage,
                "",
                DesignConstants.HORIZONTALLINE_STRING);
    }

    /**
     * Prints the list of tasks.
     *
     * @param tasksList The list of tasks to be printed.
     */
    public final void printList(TasksList tasksList) {
        printToUser("\nHere are the items in your list:");
        for (int i = 0; i < tasksList.getSize(); i++) {
            printToUser((i + 1) + ". " + tasksList.getTask(i));
        }
        printToUser("", DesignConstants.HORIZONTALLINE_STRING);
    }

    /**
     * Prints the task added.
     *
     * @param task The task to be printed.
     * @param counter The number of tasks in the list.
     */
    public final void printTaskAdded(Task task, int counter) {
        printToUser("\nAdded to the list:\n" + counter + ". " + task + "\n",
                "There are currently " + counter + " task(s) in the list.\n",
                DesignConstants.HORIZONTALLINE_STRING);
    }

    /**
     * Prints a line.
     */
    public final void printLine() {
        printToUser(DesignConstants.HORIZONTALLINE_STRING);
    }

    /**
     * Prints the specified list of {@code String} messages to the user.
     * 
     * @param message The list of messages to be printed.
     */
    public void printToUser(String... message) {
        for (String m : message) {
            System.out.print(m + "\n");
        }
    }
}
