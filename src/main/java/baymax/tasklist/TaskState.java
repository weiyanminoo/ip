package baymax.tasklist;

/**
 * Stores the previous state of a task for the undo feature.
 */
public class TaskState {
    private final int index;
    private final boolean wasDone;

    /**
     * Constructs a TaskState instance.
     *
     * @param index The index of the task in the list.
     * @param wasDone The previous completion status of the task.
     */
    public TaskState(int index, boolean wasDone) {
        this.index = index;
        this.wasDone = wasDone;
    }

    /**
     * Gets the index of the task in the list.
     *
     * @return The task index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Gets the previous completion status of the task.
     *
     * @return True if the task was done, false otherwise.
     */
    public boolean getPreviousState() {
        return wasDone;
    }
}
