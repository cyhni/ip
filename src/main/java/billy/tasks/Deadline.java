package billy.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Deadline class represents a deadline task.
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    private final DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");

    /**
     * Constructs a Deadline object.
     *
     * @param description The description of the deadline task.
     * @param by The deadline of the task.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getFileDescriptor() {
        return "D | " + super.getFileDescriptor() + " | " + this.by.format(formatterDateTime);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "\n\t\tby: " + this.by.format(formatterDateTime);
    }
}
