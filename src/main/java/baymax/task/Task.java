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
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new BaymaxException("Corrupted data file: " + line);
        }

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
                if (parts.length < 4) throw new BaymaxException("Corrupted deadline data: " + line);
                Deadline deadline = new Deadline(description, parts[3]);
                if (isDone) {
                    deadline.markAsDone();
                }
                return deadline;
            case "E":
                if (parts.length < 5) throw new BaymaxException("Corrupted event data: " + line);
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
