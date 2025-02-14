package baymax;

import baymax.exception.BaymaxException;
import baymax.parser.Parser;
import baymax.storage.Storage;
import baymax.tasklist.TaskList;
import baymax.ui.UI;

/**
 * The main entry point for the Baymax application.
 * This class initializes the necessary components and runs the program.
 */
public class Baymax {

    private UI ui;
    private Storage storage;
    private TaskList taskList;
    private Parser parser;

    /**
     * Constructs a Baymax instance and initializes its components.
     *
     * @param filepath The file path to load and save tasks.
     */
    public Baymax(String filepath) {
        this.ui = new UI();
        this.storage = new Storage(filepath);
        this.taskList = new TaskList(storage);
        this.parser = new Parser(taskList, ui);
    }

    /**
     * Handles user input and returns the chatbot's response.
     *
     * @param input The user command.
     * @return Baymax's response.
     */
    public String getResponse(String input) {
        try {
            return parser.processCommand(input);
        } catch (BaymaxException e) {
            return "Error: " + e.getMessage();
        }
    }

}
