package billy.tasks;

import java.io.IOException;
import java.util.ArrayList;

import billy.filemanager.FileManager;

public class TasksList {
    private ArrayList<Task> tasksList;

    public TasksList() {
        this.tasksList = new ArrayList<>();
    }

    public void addTask(Task task) throws IOException {
        tasksList.add(task);
        FileManager.updateFile(task);
    }

    public void removeTask(int index) throws IOException {
        tasksList.remove(index);
        FileManager.updateFile(tasksList);
    }

    public Task getTask(int index) {
        return tasksList.get(index);
    }

    public int getSize() {
        return tasksList.size();
    }

    public void markTaskAsDone(int index) throws IOException {
        tasksList.get(index).markAsDone();
        FileManager.updateFile(tasksList);
    }

    public void markTaskAsUndone(int index) throws IOException {
        tasksList.get(index).markAsUndone();
        FileManager.updateFile(tasksList);
    }

    public ArrayList<Task> getTasksList() {
        return tasksList;
    }
}
