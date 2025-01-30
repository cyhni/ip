public class Todo extends Task {
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
