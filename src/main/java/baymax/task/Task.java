package baymax.task;

import baymax.exception.BaymaxException;

/**
 * Represents an abstract task that can be managed by the Baymax application.
 * This class provides common attributes and methods for all task types.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    /**
     * Constructs a Task with the specified description and type.
     * By default, the task is set to not done.
     *
     * @param description The description of the task.
     * @param type        The type of task (e.g., TODO, DEADLINE, EVENT).
     */
    public Task(String description, TaskType type) {
        assert description != null && !description.trim().isEmpty() : "Task description cannot be null or empty";
        assert type != null : "Task type cannot be null";
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if the task is completed, otherwise a blank space.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public String getDescription() {
        return description;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Converts a line from the saved file back into a Task object.
     *
     * @param line The line from the file, formatted as "T | 0 | description".
     * @return The corresponding Task object.
     * @throws BaymaxException If the data format is invalid or corrupted.
     */
    public static Task fromFileFormat(String line) throws BaymaxException {
        assert line != null && !line.trim().isEmpty() : "Input line cannot be null or empty";
        String[] parts = line.split(" \\| ");
        assert parts.length >= 3 : "Invalid file format, must have at least 3 parts";

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
        case "T":
            Todo todo = new Todo(description);
            if (isDone) {
                todo.markAsDone();
            }
            return todo;
        case "D":
            assert parts.length >= 4 : "Invalid Deadline format";
            Deadline deadline = new Deadline(description, parts[3]);
            if (isDone) {
                deadline.markAsDone();
            }
            return deadline;
        case "E":
            assert parts.length >= 5 : "Invalid Event format";
            Event event = new Event(description, parts[3], parts[4], parts[5]);
            if (isDone) {
                event.markAsDone();
            }
            return event;
        default:
            throw new BaymaxException("Unknown task type: " + type);
        }
    }

    /**
     * Converts the task to a format suitable for saving to a file.
     *
     * @return The formatted string representation of the task.
     */
    public abstract String toFileFormat();

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns a string representation of the task, including its status and description.
     *
     * @return The formatted task string.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
