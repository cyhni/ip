package billy.tasks;

/**
 * The Todo class represents a todo task in the Billy application.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo object.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getFileDescriptor() {
        return "T | " + super.getFileDescriptor();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
