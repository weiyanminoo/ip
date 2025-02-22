package baymax.task;

import baymax.task.Task;
import baymax.task.TaskType;

/**
 * Represents a Todo task in the Baymax application.
 * A Todo task contains only a description and does not have a specific date or time.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task with the given description.
     *
     * @param description The description of the Todo task.
     */
    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    /**
     * Constructs a new Todo task with the given description and completion status.
     * This constructor is used when loading tasks from storage.
     *
     * @param description The description of the Todo task.
     * @param isDone Whether the task is marked as done.
     */
    public Todo(String description, boolean isDone) {
        super(description, TaskType.TODO);
        this.isDone = isDone;
    }

    /**
     * Converts the Todo task into a file-friendly format for storage.
     *
     * @return A formatted string representing the Todo task for file storage.
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a string representation of the Todo task.
     *
     * @return The formatted Todo task string.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
