import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Deadline extends Task {
    private final DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    protected LocalDateTime by;

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
