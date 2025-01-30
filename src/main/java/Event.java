import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getFileDescriptor() {
        return "E | " + super.getFileDescriptor() + " | " + this.from.format(formatterDateTime) + " | " + this.to.format(formatterDateTime);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "\n\t\tfrom: " + this.from.format(formatterDateTime) + "\n\t\tto: " + this.to.format(formatterDateTime);
    }
}
