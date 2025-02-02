package billy.tasks;

/**
 * The Task class represents a task in the Billy application.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task object.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /*
     * Returns the description of the task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as undone.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     * The status icon is used to represent the status of the task.
     *
     * @return The status icon of the task.
     */
    private String getStatusIcon() {
        return (this.isDone ? "X" : " ");
    }

    /**
     * Returns the file descriptor of the task.
     * The file descriptor is used to save the task to the hard disk.
     *
     * @return The file descriptor of the task.
     */
    public String getFileDescriptor() {
        return (this.isDone ? "1" : "0") + " | " + this.description;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
