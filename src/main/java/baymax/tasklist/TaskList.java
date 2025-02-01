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
     * @param ui The UI instance for user interaction.
     * @throws BaymaxException If the input is invalid.
     */
    public void addTodo(String input, UI ui) throws BaymaxException {
        try {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new BaymaxException("The description of a todo cannot be empty!");
            }
            Task todo = new Todo(description);
            tasks.add(todo);
            saveTasks();
            ui.addTaskMessage(todo, tasks.size());
        } catch (StringIndexOutOfBoundsException e) {
            throw new BaymaxException("Invalid input! Use: todo [description]");
        }
    }

    /**
     * Adds a Deadline task to the list.
     *
     * @param input The full input command containing the description and deadline date.
     * @param ui The UI instance for user interaction.
     * @throws BaymaxException If the input format is invalid.
     */
    public void addDeadline(String input, UI ui) throws BaymaxException {
        try {
            String description = input.substring(9).trim();
            String[] parts = description.split(" /by ");
            if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                throw new BaymaxException("Invalid deadline format. Use: deadline [description] /by [yyyy-MM-dd HHmm]");
            }

            try {
                LocalDateTime deadline = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                Task deadlineTask = new Deadline(parts[0], parts[1]);
                tasks.add(deadlineTask);
                saveTasks();
                ui.addTaskMessage(deadlineTask, tasks.size());
            } catch (DateTimeParseException e) {
                throw new BaymaxException("Invalid format! Use: deadline [description] /by [yyyy-MM-dd HHmm]");
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new BaymaxException("Invalid input! Use: deadline [description] /by [date]");
        }
    }

    /**
     * Adds an Event task to the list.
     *
     * @param input The full input command containing the description, date, and time range.
     * @param ui    The UI instance for user interaction.
     * @throws BaymaxException If the input format is invalid.
     */
    public void addEvent(String input, UI ui) throws BaymaxException {
        try {
            String description = input.substring(6).trim();
            String[] parts = description.split(" /on | /from | /to ");
            if (parts.length < 4) {
                throw new BaymaxException("Invalid event format. " +
                        "Use: event [description] /on [yyyy-MM-dd] /from [HHmm] /to [HHmm]");
            }

            // Validate date format
            LocalDate date;
            try {
                date = LocalDate.parse(parts[1], DATE_FORMAT);
            } catch (DateTimeParseException e) {
                throw new BaymaxException("Invalid date format! Use: yyyy-MM-dd (e.g., 2025-01-30)");
            }

            // Validate start and end times
            LocalTime fromTime, toTime;
            try {
                fromTime = LocalTime.parse(parts[2], TIME_FORMAT);
                toTime = LocalTime.parse(parts[3], TIME_FORMAT);
            } catch (DateTimeParseException e) {
                throw new BaymaxException("Invalid time format! Use: /on [yyyy-MM-dd] /from [HHmm] /to [HHmm]");
            }

            // Ensure start time is before end time
            if (fromTime.isAfter(toTime)) {
                throw new BaymaxException("Invalid time range! Start time must be before end time.");
            }

            Task event = new Event(parts[0], date.toString(), fromTime.toString(), toTime.toString());
            tasks.add(event);
            saveTasks();
            ui.addTaskMessage(event, tasks.size());

        } catch (StringIndexOutOfBoundsException e) {
            throw new BaymaxException("Invalid input! Use: event [description] /on [yyyy-MM-dd] /from [HHmm] /to [HHmm]");
        }
    }

    /**
     * Deletes a task from the list.
     *
     * @param index The index of the task to delete.
     * @param ui The UI instance for user interaction.
     * @throws BaymaxException If the index is out of range.
     */
    public void deleteTask(int index, UI ui) throws BaymaxException {
        if (index < 0 || index >= tasks.size()) {
            throw new BaymaxException("Task number out of range!");
        }
        Task removedTask = tasks.remove(index);
        ui.deleteTaskMessage(removedTask, tasks.size());
    }

    /**
     * Marks a task as done or not done.
     *
     * @param index The index of the task.
     * @param isDone True if marking as done, false if unmarking.
     * @param ui The UI instance for user interaction.
     * @throws BaymaxException If the index is out of range.
     */
    public void markTask(int index, boolean isDone, UI ui) throws BaymaxException {
        if (index < 0 || index >= tasks.size()) {
            throw new BaymaxException("Task number out of range!");
        }
        Task task = tasks.get(index);
        if (isDone) {
            task.markAsDone();
            ui.markTaskMessage(task);
        } else {
            task.markAsNotDone();
            ui.unmarkTaskMessage(task);
        }
        saveTasks();
    }

    /**
     * Displays the list of tasks.
     *
     * @param ui The UI instance for user interaction.
     */
    public void listTasks(UI ui) {
        ui.taskListMessage(this);
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
}
