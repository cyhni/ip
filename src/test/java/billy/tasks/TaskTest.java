package billy.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaskTest {
    @Test
    public void task_taskConstructor_success() {
        Task task = new Task("description");
        assertEquals("description", task.description);

        assertEquals(false, task.isDone);
    }

    @Test
    public void markAsDone_markTaskDone_success() {
        Task task = new Task("description");
        task.markAsDone();
        assertEquals(true, task.isDone);
    }

    @Test
    public void markAsUndone_markTaskDoneToUndone_success() {
        Task task = new Task("description");
        task.markAsDone();
        assertEquals(true, task.isDone);
        task.markAsUndone();
        assertEquals(false, task.isDone);
    }

    @Test
    public void getFileDescriptor_taskDone_success() {
        Task task = new Task("description");
        task.markAsDone();
        assertEquals(true, task.isDone);
        assertEquals("1 | description", task.getFileDescriptor());
    }

    @Test
    public void getFileDescriptor_taskUndone_success() {
        Task task = new Task("description");
        task.markAsUndone();
        assertEquals(false, task.isDone);
        assertEquals("0 | description", task.getFileDescriptor());
    }

    @Test
    public void toString_taskDone_success() {
        Task task = new Task("description");
        task.markAsDone();
        assertEquals(true, task.isDone);
        assertEquals("[X] description", task.toString());
    }

    @Test
    public void toString_taskUndone_success() {
        Task task = new Task("description");
        task.markAsUndone();
        assertEquals(false, task.isDone);
        assertEquals("[ ] description", task.toString());
    }
}
