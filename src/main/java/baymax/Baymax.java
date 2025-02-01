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
     * Runs the main program loop, continuously reading and processing user input.
     */
    public void run() {
        ui.welcomeMessage();
        while (true) {
            try {
                String input = ui.readCommand();
                parser.processCommand(input);
            } catch (BaymaxException e) {
                ui.errorMessage(e.getMessage());
            }
        }
    }

    /**
     * The main method to start the Baymax application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Baymax("./data/tasks.txt").run();
    }
}
