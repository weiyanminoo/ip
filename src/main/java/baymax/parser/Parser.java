package baymax.parser;

import baymax.exception.BaymaxException;
import baymax.tasklist.TaskList;

/**
 * Parses user input and executes corresponding commands.
 */
public class Parser {

    private TaskList taskList;

    /**
     * Constructs a Parser instance.
     *
     * @param taskList The task list to manipulate based on user commands.
     */
    public Parser(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Processes a user command and executes the corresponding action.
     *
     * @param input The command given by the user.
     * @throws BaymaxException If the command is invalid.
     */
    public String processCommand(String input) throws BaymaxException {
        assert input != null : "Input command should not be null";
        assert !input.trim().isEmpty() : "Input command should not be empty";

        String[] words = input.split(" ", 2);
        String command = words[0];

        switch (command) {
        case "bye":
            scheduleExit();
            return "Byeee! Take care :D";
        case "list":
            return taskList.listTasks();
        case "mark":
            return taskList.markTask(parseIndex(words), true);
        case "unmark":
            return taskList.markTask(parseIndex(words), false);
        case "delete":
            return taskList.deleteTask(parseIndex(words));
        case "todo":
            return taskList.addTodo(input);
        case "deadline":
            return taskList.addDeadline(input);
        case "event":
            return taskList.addEvent(input);
        case "find":
            if (words.length < 2) {
                throw new BaymaxException("Please give me a keyword to search.");
            }
            return taskList.findTask(words[1]);
        case "undo":
            return taskList.undo();
        default:
            throw new BaymaxException("I don't understand what you're saying!");
        }
    }

    /**
     * Parses an index from user input.
     *
     * @param words The split user input containing the command and index.
     * @return The parsed index.
     * @throws BaymaxException If the index is missing or invalid.
     */
    private int parseIndex(String[] words) throws BaymaxException {
        assert words != null : "Words array should not be null";

        if (words.length < 2) {
            throw new BaymaxException("Command requires an index.");
        }
        try {
            return Integer.parseInt(words[1]) - 1;
        } catch (NumberFormatException e) {
            throw new BaymaxException("That task number doesn't exist!");
        }
    }

    /**
     * Schedules the application to exit after 1 second.
     */
    private void scheduleExit() {
        new Thread(() -> {
            try {
                Thread.sleep(1500); // Wait for 1 second before exiting
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.exit(0); // Exit the application
        }).start();
    }
}
