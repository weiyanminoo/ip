public class Todo extends Task {

    // Constructor for user input
    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    // Constructor for storage
    public Todo(String description, boolean isDone) {
        super(description, TaskType.TODO);
        this.isDone = isDone;
    }

    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
