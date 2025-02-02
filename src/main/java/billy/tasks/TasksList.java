package billy.tasks;

import java.io.IOException;
import java.util.ArrayList;

import billy.filemanager.FileManager;

/**
 * The TasksList class manages a list of Task objects using an ArrayList.
 * It provides methods to add, remove, and retrieve tasks, as well as mark them as done or undone.
 * The class also ensures that any changes to the tasks list are updated in the file system using the FileManager.
 *
 * This class uses the ArrayList type to store Task objects, which allows dynamic resizing and provides
 * efficient access to elements by their index. ArrayList is part of the Java Collections Framework and
 * is a resizable array implementation of the List interface.
 */
public class TasksList {
    private ArrayList<Task> tasksList;

    /**
     * Constructs a TasksList object with an empty ArrayList of tasks.
     */
    public TasksList() {
        this.tasksList = new ArrayList<>();
    }

    /**
     * Adds a task to the tasks list.
     * Updates the file with the updated list of tasks.
     *
     * @param task The task to be added.
     * @throws IOException If an I/O error occurs while updating the file.
     */
    public void addTask(Task task) throws IOException {
        tasksList.add(task);
        FileManager.updateFile(task);
    }

    /**
     * Removes a task from the tasks list at the specified index.
     * Updates the file with the updated list of tasks.
     *
     * @param index The index of the task to be removed.
     * @throws IOException If an I/O error occurs while updating the file.
     */
    public void removeTask(int index) throws IOException {
        tasksList.remove(index);
        FileManager.updateFile(tasksList);
    }

    /**
     * Retrieves a task from the tasks list at the specified index.
     *
     * @param index The index of the task to be retrieved.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return tasksList.get(index);
    }

    /**
     * Returns the number of tasks in the tasks list.
     *
     * @return The number of tasks in the tasks list.
     */
    public int getSize() {
        return tasksList.size();
    }

    /**
     * Marks a task in the tasks list as done.
     * Updates the file with the updated list of tasks.
     *
     * @param index The index of the task to be marked as done.
     * @throws IOException If an I/O error occurs while updating the file.
     */
    public void markTaskAsDone(int index) throws IOException {
        tasksList.get(index).markAsDone();
        FileManager.updateFile(tasksList);
    }

    /**
     * Marks a task in the tasks list as undone.
     * Updates the file with the updated list of tasks.
     *
     * @param index The index of the task to be marked as undone.
     * @throws IOException If an I/O error occurs while updating the file.
     */
    public void markTaskAsUndone(int index) throws IOException {
        tasksList.get(index).markAsUndone();
        FileManager.updateFile(tasksList);
    }

    /**
     * Returns the list of tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasksList() {
        return tasksList;
    }
}
