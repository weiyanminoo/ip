package baymax.tasklist;

import baymax.exception.BaymaxException;
import baymax.task.Event;
import baymax.task.Deadline;
import baymax.task.Task;
import baymax.task.Todo;
import baymax.storage.Storage;
import baymax.ui.UI;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Manages a list of tasks and provides methods to modify and retrieve tasks.
 */
public class TaskList {

    private ArrayList<Task> tasks;
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
    }

    /**
     * Constructs an empty TaskList (used for testing).
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
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
        try {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new BaymaxException("The description of a todo cannot be empty!");
            }
            Task todo = new Todo(description);
            tasks.add(todo);
            assert tasks.contains(todo) : "Task was not added successfully";
            saveTasks();
            return "Added task:\n  " + todo + "\nNow you have " + tasks.size() + " tasks in the list.";
        } catch (StringIndexOutOfBoundsException e) {
            throw new BaymaxException("Invalid input! Use: todo [description]");
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
        try {
            String description = input.substring(9).trim();
            String[] parts = description.split(" /by ");
            if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                throw new BaymaxException("Invalid deadline format. Use: deadline [description] /by [yyyy-MM-dd HHmm]");
            }

            LocalDateTime deadline = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            Task deadlineTask = new Deadline(parts[0], parts[1]);
            tasks.add(deadlineTask);
            assert tasks.contains(deadlineTask) : "Task was not added successfully";
            saveTasks();
            return "Added task:\n  " + deadlineTask + "\nNow you have " + tasks.size() + " tasks in the list.";
        } catch (DateTimeParseException e) {
            throw new BaymaxException("Invalid format! Use: deadline [description] /by [yyyy-MM-dd HHmm]");
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
        try {
            String description = input.substring(6).trim();
            String[] parts = description.split(" /on | /from | /to ");
            if (parts.length < 4) {
                throw new BaymaxException("Invalid event format. Use: event [description] /on [yyyy-MM-dd] /from [HHmm] /to [HHmm]");
            }

            LocalDate date = LocalDate.parse(parts[1], DATE_FORMAT);
            LocalTime fromTime = LocalTime.parse(parts[2], TIME_FORMAT);
            LocalTime toTime = LocalTime.parse(parts[3], TIME_FORMAT);

            if (fromTime.isAfter(toTime)) {
                throw new BaymaxException("Invalid time range! Start time must be before end time.");
            }

            Task event = new Event(parts[0], date.toString(), fromTime.toString(), toTime.toString());
            tasks.add(event);
            assert tasks.contains(event) : "Task was not added successfully";
            saveTasks();
            return "Added task:\n  " + event + "\nNow you have " + tasks.size() + " tasks in the list.";
        } catch (DateTimeParseException e) {
            throw new BaymaxException("Invalid date/time format! Use: event [description] /on [yyyy-MM-dd] /from [HHmm] /to [HHmm]");
        }
    }

    /**
     * Deletes a task from the list.
     *
     * @param index The index of the task to delete.
     * @throws BaymaxException If the index is out of range.
     */
    public String deleteTask(int index) throws BaymaxException {
        assert index >= 0 && index < tasks.size() : "Index out of bounds";
        Task removedTask = tasks.remove(index);
        saveTasks();
        return "Removed task:\n  " + removedTask + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Marks a task as done or not done.
     *
     * @param index The index of the task.
     * @param isDone True if marking as done, false if unmarking.
     * @throws BaymaxException If the index is out of range.
     */
    public String markTask(int index, boolean isDone) throws BaymaxException {
        assert index >= 0 && index < tasks.size() : "Index out of bounds";
        Task task = tasks.get(index);
        if (isDone) {
            task.markAsDone();
            saveTasks();
            return "Marked task as done:\n  " + task;
        } else {
            task.markAsNotDone();
            saveTasks();
            return "Marked task as not done:\n  " + task;
        }
    }

    /**
     * Displays the list of tasks.
     */
    public String listTasks() {
        if (tasks.isEmpty()) {
            return "You have no tasks in your list!";
        }
        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
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
            return "No matching tasks found!";
        }

        StringBuilder response = new StringBuilder("Here are the matching tasks:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            response.append((i + 1)).append(". ").append(matchingTasks.get(i)).append("\n");
        }
        return response.toString();
    }
}
