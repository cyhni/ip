import constants.DesignConstants;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public final void printIntroduction() {
        printToUser(
                DesignConstants.HORIZONTALLINE_STRING, 
                DesignConstants.LOGO_STRING, 
                DesignConstants.HORIZONTALLINE_STRING, 
                "\nWelcome to the world of Billy!\n" + "How can I help you?\n", 
                DesignConstants.HORIZONTALLINE_STRING);
    }

    public final String readCommand() {
        System.out.print("Enter your command: ");
        return scanner.nextLine();
    }

    public final void printBye() {
        printToUser(
                "\nBye bye.\n", 
                DesignConstants.HORIZONTALLINE_STRING);
        scanner.close();
    }

    public final void printError(String errorMessage) {
        printToUser( 
                "",
                errorMessage, 
                "",
                DesignConstants.HORIZONTALLINE_STRING);
    }

    public final void printList(ArrayList<Task> tasksList, int counter) {
        printToUser("\nHere are the items in your list:");
        for (int i = 0; i < counter; i++) {
            printToUser((i + 1) + ". " + tasksList.get(i));
        }
        printToUser("\n", DesignConstants.HORIZONTALLINE_STRING);
    }

    public final void printTaskAdded(Task task, int counter) {
        printToUser("\nAdded to the list:\n" + counter + ". " + task + "\n", 
                "There are currently " + counter + " task(s) in the list.\n",
                DesignConstants.HORIZONTALLINE_STRING);
    }

    public final void printLine() {
        printToUser(DesignConstants.HORIZONTALLINE_STRING);
    }  

    public void printToUser(String... message) {
        for (String m : message) {
            System.out.println(m);
        }
    }
}
