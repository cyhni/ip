package billy.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import billy.constants.DesignConstants;
import billy.tasks.TasksList;
import billy.tasks.Todo;

public class UiTest {
    private final ByteArrayOutputStream outText = new ByteArrayOutputStream();
    private final PrintStream stdOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outText));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(stdOut);
    }

    @Test
    public void printIntroduction_introductionMessage_success() throws IOException {
        Ui ui = new Ui();
        ui.printIntroduction();;
        String expected = DesignConstants.HORIZONTALLINE_STRING + "\n"
                + DesignConstants.LOGO_STRING + "\n"
                + DesignConstants.HORIZONTALLINE_STRING + "\n"
                + "\nWelcome to the world of Billy!\n"
                + "How can I help you?\n" + "\n"
                + DesignConstants.HORIZONTALLINE_STRING + "\n";
        assertEquals(expected, outText.toString());
    }

    @Test
    public void printList_noTasks_success() throws IOException {
        Ui ui = new Ui();
        TasksList tasksList = new TasksList();
        ui.printList(tasksList);
        String expected = "\nHere are the items in your list:\n"
                + "\n" + DesignConstants.HORIZONTALLINE_STRING + "\n";
        assertEquals(expected, outText.toString());
    }

    @Test
    public void printList_twoTasks_success() throws IOException {
        Ui ui = new Ui();
        TasksList tasksList = new TasksList();
        tasksList.addTask(new Todo("task1"));
        tasksList.addTask(new Todo("task2"));
        ui.printList(tasksList);
        String expected = "\nHere are the items in your list:\n"
                + "1. [T][ ] task1\n"
                + "2. [T][ ] task2\n"
                + "\n" + DesignConstants.HORIZONTALLINE_STRING + "\n";
        assertEquals(expected, outText.toString());
    }

    @Test
    public void printList_twoTasksOneMarkedDone_success() throws IOException {
        Ui ui = new Ui();
        TasksList tasksList = new TasksList();
        tasksList.addTask(new Todo("task1"));
        tasksList.addTask(new Todo("task2"));
        tasksList.getTask(0).markAsDone();
        ui.printList(tasksList);
        String expected = "\nHere are the items in your list:\n"
                + "1. [T][X] task1\n"
                + "2. [T][ ] task2\n"
                + "\n" + DesignConstants.HORIZONTALLINE_STRING + "\n";
        assertEquals(expected, outText.toString());
    }
}
