package billy.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import billy.tasks.TasksList;
import billy.tasks.Todo;

public class UiTest {
    @Test
    public void printList_noTasks_success() throws IOException {
        Ui ui = new Ui();
        TasksList tasksList = new TasksList();
        String expected = "Here are the items in your list:\n";
        assertEquals(expected, ui.printList(tasksList));
    }

    @Test
    public void printList_twoTasks_success() throws IOException {
        Ui ui = new Ui();
        TasksList tasksList = new TasksList();
        tasksList.addTask(new Todo("task1"));
        tasksList.addTask(new Todo("task2"));
        String expected = "Here are the items in your list:\n"
                + "1. [T][ ] task1\n"
                + "2. [T][ ] task2\n";
        assertEquals(expected, ui.printList(tasksList));
    }

    @Test
    public void printList_twoTasksOneMarkedDone_success() throws IOException {
        Ui ui = new Ui();
        TasksList tasksList = new TasksList();
        tasksList.addTask(new Todo("task1"));
        tasksList.addTask(new Todo("task2"));
        tasksList.getTask(0).markAsDone();
        String expected = "Here are the items in your list:\n"
                + "1. [T][X] task1\n"
                + "2. [T][ ] task2\n";
        assertEquals(expected, ui.printList(tasksList));
    }
}
