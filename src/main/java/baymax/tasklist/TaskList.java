package baymax.tasklist;

import baymax.exception.BaymaxException;
import baymax.task.Event;
import baymax.task.Deadline;
import baymax.task.Task;
import baymax.task.Todo;
import baymax.storage.Storage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Manages a list of tasks and provides methods to modify and retrieve tasks.
 */
public class TaskList {

    private ArrayList<Task> tasks;
    // A stack to store previous states
    private Stack<ArrayList<Task>> history;
    // A stack to store the state of tasks modified by markTask()
    private Stack<TaskState> taskStateHistory = new Stack<>();
    private Storage storage;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HHmm");

    /**
     * Constructs a TaskList with storage integration.
     *
     * @param storage The storage system to save and load tasks.
     */
    public TaskList(Storage storage) {
        this.storage = storage;
        this.tasks = new ArrayList<>(loadTasksFromStorage());
        this.history = new Stack<>();
        this.taskStateHistory = new Stack<>();
    }

    /**
     * Constructs an empty TaskList (used for testing).
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        this.history = new Stack<>();
        this.taskStateHistory = new Stack<>();
    }

    /**
     * Loads tasks from storage.
     *
     * @return A list of previously added tasks.
     */
    private ArrayList<Task> loadTasksFromStorage() {
        try {
            return new ArrayList<>(storage.loadTasks());
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Adds a ToDo task to the list.
     *
     * @param input The full input command.
     * @throws BaymaxException If the input is invalid.
     */
    public String addTodo(String input) throws BaymaxException {
        assert input != null : "Input cannot be null";

        if (input.trim().equals("todo")) {
            throw new BaymaxException("Please give me a description of your todo task!");
        }

        try {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new BaymaxException("Please give me a description of your todo task!");
            }
            saveState();
            Task todo = new Todo(description);
            tasks.add(todo);
            assert tasks.contains(todo) : "Task was not added successfully";
            saveTasks();
            return "I have added the task:\n  " + todo + "\nNow you have " + tasks.size() + " tasks in the list.";
        } catch (StringIndexOutOfBoundsException e) {
            throw new BaymaxException("Please use this format: todo [description]");
        }
    }

    /**
     * Adds a Deadline task to the list.
     *
     * @param input The full input command containing the description and deadline date.
     * @throws BaymaxException If the input format is invalid.
     */
    public String addDeadline(String input) throws BaymaxException {
        assert input != null : "Input cannot be null";

        if (input.trim().equals("deadline")) {
            throw new BaymaxException("Please give me a description of your deadline task!");
        }

        try {
            String description = input.substring(9).trim();
            if (description.isEmpty()) {
                throw new BaymaxException("Please give me a description of your deadline task!");
            }

            String[] parts = description.split(" /by ", 2);
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new BaymaxException("Please use this format: deadline [description] /by [yyyy-MM-dd HHmm]");
            }

            saveState();
            LocalDateTime deadline = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            Task deadlineTask = new Deadline(parts[0], parts[1]);
            tasks.add(deadlineTask);
            assert tasks.contains(deadlineTask) : "Task was not added successfully";
            saveTasks();
            return "I have added the task:\n  " + deadlineTask + "\nNow you have " + tasks.size() + " tasks in the list.";

        } catch (DateTimeParseException e) {
            throw new BaymaxException("Please use this format: deadline [description] /by [yyyy-MM-dd HHmm]");
        } catch (StringIndexOutOfBoundsException e) {
            throw new BaymaxException("Please use this format: deadline [description] /by [yyyy-MM-dd HHmm]");
        }
    }

    /**
     * Adds an Event task to the list.
     *
     * @param input The full input command containing the description, date, and time range.
     * @throws BaymaxException If the input format is invalid.
     */
    public String addEvent(String input) throws BaymaxException {
        assert input != null : "Input cannot be null";

        if (input.trim().equals("event")) {  // Check if only "event" is provided
            throw new BaymaxException("Please give me a description of your event task!");
        }

        try {
            String description = input.substring(6).trim();
            if (description.isEmpty()) {
                throw new BaymaxException("Please give me a description of your event task!");
            }

            String[] parts = description.split(" /on | /from | /to ", 4);
            if (parts.length < 4 || parts[1].trim().isEmpty() || parts[2].trim().isEmpty() || parts[3].trim().isEmpty()) {
                throw new BaymaxException("Please use this format: event [description] /on [yyyy-MM-dd] /from [HHmm] /to [HHmm]");
            }

            saveState();
            LocalDate date = LocalDate.parse(parts[1], DATE_FORMAT);
            LocalTime fromTime = LocalTime.parse(parts[2], TIME_FORMAT);
            LocalTime toTime = LocalTime.parse(parts[3], TIME_FORMAT);

            if (fromTime.isAfter(toTime)) {
                throw new BaymaxException("Hey your start time must be before end time.");
            }

            Task event = new Event(parts[0], date.toString(), fromTime.toString(), toTime.toString());
            tasks.add(event);
            assert tasks.contains(event) : "Task was not added successfully";
            saveTasks();
            return "I have added the task:\n  " + event + "\nNow you have " + tasks.size() + " tasks in the list.";

        } catch (DateTimeParseException e) {
            throw new BaymaxException("Please use this format: event [description] /on [yyyy-MM-dd] /from [HHmm] /to [HHmm]");
        } catch (StringIndexOutOfBoundsException e) {
            throw new BaymaxException("Please use this format: event [description] /on [yyyy-MM-dd] /from [HHmm] /to [HHmm]");
        }
    }

    /**
     * Deletes a task from the list.
     *
     * @param index The index of the task to delete.
     * @throws BaymaxException If the index is out of range.
     */
    public String deleteTask(int index) throws BaymaxException {
        if (index < 0 || index >= tasks.size()) {
            throw new BaymaxException("There are only " + tasks.size() + " total tasks!");
        }
        saveState();
        Task removedTask = tasks.remove(index);
        saveTasks();
        return "I have removed the task:\n  " + removedTask + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Marks a task as done or not done.
     *
     * @param index The index of the task.
     * @param isDone True if marking as done, false if unmarking.
     * @throws BaymaxException If the index is out of range.
     */
    public String markTask(int index, boolean isDone) throws BaymaxException {
        if (index < 0 || index >= tasks.size()) {
            throw new BaymaxException("There are only " + tasks.size() + " total tasks!");
        }
        Task task = tasks.get(index);
        taskStateHistory.push(new TaskState(index, task.isDone()));
        if (isDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
        saveTasks();
        return isDone ? "Good job on completing this task:\n  " + task
                : "Oh seems like you have not completed this task:\n  " + task;
    }

    /**
     * Displays the list of tasks.
     */
    public String listTasks() {
        if (tasks.isEmpty()) {
            return "You have no tasks in your list!";
        }
        StringBuilder sb = new StringBuilder("Here are all your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return True if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Retrieves the list of tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Prints the list of tasks to the console.
     */
    public void printTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Saves the task list to storage.
     */
    private void saveTasks() {
        if (storage == null) {
            return;
        }
        try {
            storage.saveTasks(tasks);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Finds and returns tasks that contain the given keyword in their description.
     *
     * @param keyword The keyword to search for.
     * @return A string representation of the matching tasks.
     */
    public String findTask(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        if (matchingTasks.isEmpty()) {
            return "There aren't any matching tasks!";
        }
        StringBuilder response = new StringBuilder("Here are all the matching tasks:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            response.append((i + 1)).append(". ").append(matchingTasks.get(i)).append("\n");
        }
        return response.toString();
    }

    /**
     * Saves the current state before making any modifications.
     */
    private void saveState() {
        history.push(new ArrayList<>(tasks)); // Save a copy of the current task list
    }

    /**
     * Undoes the last modification made to the task list.
     *
     * This method restores the previous state of the most recent change.
     * It prioritizes restoring the task completion status (for mark/unmark actions).
     * If no task completion change is available, it reverts the most recent list modification
     * (such as adding or deleting a task).
     *
     * @return A message indicating whether the undo operation was successful.
     */
    public String undo() {
        if (!taskStateHistory.isEmpty()) {
            TaskState lastState = taskStateHistory.pop();
            Task task = tasks.get(lastState.getIndex());
            task.setDone(lastState.getPreviousState()); // Restore task state
            saveTasks();
            return "Undo successful! The status of this task is reverted:\n  " + task;
        }
        if (!history.isEmpty()) {
            tasks = history.pop();
            saveTasks();
            return "Undo successful! Your last command has been reverted.";
        }
        return "There is nothing to undo!";
    }
}
