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
            return "Byeee! Take care human!";
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
                throw new BaymaxException("Please provide a keyword to search.");
            }
            return taskList.findTask(words[1]);
        case "undo":
            return taskList.undo();
        default:
            throw new BaymaxException("Invalid command!");
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
            throw new BaymaxException("Invalid input! Command requires an index.");
        }
        try {
            return Integer.parseInt(words[1]) - 1;
        } catch (NumberFormatException e) {
            throw new BaymaxException("Invalid task number!");
        }
    }
}
