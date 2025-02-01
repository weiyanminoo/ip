package baymax.parser;

import baymax.exception.BaymaxException;
import baymax.tasklist.TaskList;
import baymax.ui.UI;

/**
 * Parses user input and executes corresponding commands.
 */
public class Parser {

    private TaskList taskList;
    private UI ui;

    /**
     * Constructs a Parser instance.
     *
     * @param taskList The task list to manipulate based on user commands.
     * @param ui The UI instance to interact with the user.
     */
    public Parser(TaskList taskList, UI ui) {
        this.taskList = taskList;
        this.ui = ui;
    }

    /**
     * Processes a user command and executes the corresponding action.
     *
     * @param input The command given by the user.
     * @throws BaymaxException If the command is invalid.
     */
    public void processCommand(String input) throws BaymaxException {
        String[] words = input.split(" ", 2);
        String command = words[0];

        switch (command) {
            case "bye":
                ui.exitMessage();
                System.exit(0);
                break;
            case "list":
                taskList.listTasks(ui);
                break;
            case "mark":
                taskList.markTask(parseIndex(words), true, ui);
                break;
            case "unmark":
                taskList.markTask(parseIndex(words), false, ui);
                break;
            case "delete":
                taskList.deleteTask(parseIndex(words), ui);
                break;
            case "todo":
                taskList.addTodo(input, ui);
                break;
            case "deadline":
                taskList.addDeadline(input, ui);
                break;
            case "event":
                taskList.addEvent(input, ui);
                break;
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
