package billy.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Event class represents an event task.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private final DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");

    /**
     * Constructs an Event object.
     *
     * @param description The description of the event task.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getFileDescriptor() {
        return "E | " + super.getFileDescriptor()
                + " | " + this.from.format(formatterDateTime)
                + " | " + this.to.format(formatterDateTime);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + "\n\t\tfrom: " + this.from.format(formatterDateTime)
                + "\n\t\tto: " + this.to.format(formatterDateTime);
    }
}
