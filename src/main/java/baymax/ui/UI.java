package baymax.ui;

import baymax.gui.MainWindow;
import baymax.task.Task;
import baymax.tasklist.TaskList;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles user interactions in both CLI and GUI modes.
 * Responsible for displaying messages according to user input.
 */
public class UI {

    private Scanner scanner;
    private MainWindow mainWindow;

    /**
     * Constructs a UI instance with a Scanner to read user input.
     */
    public UI() {
        scanner = new Scanner(System.in);
    }

    /**
     * Sets the JavaFX MainWindow reference.
     *
     * @param mainWindow The JavaFX UI controller.
     */
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Sends a message to either CLI (console) or GUI (JavaFX).
     *
     * @param message The message to be displayed.
     */
    public String sendMessage(String message) {
        if (mainWindow != null) {
            mainWindow.displayMessage(message);  // Display in JavaFX
        } else {
            System.out.println(message);  // Print in CLI
        }
        return message;
    }

    public void welcomeMessage() {
        sendMessage("Wassup! I'm Baymax. How can I help you?");
    }

    public void exitMessage() {
        sendMessage("Byeee! Take care human!");
    }

    public void taskListMessage(TaskList tasks) {
        if (tasks.isEmpty()) {
            sendMessage("You got no tasks!");
        } else {
            sendMessage("Here's your task list:");
            tasks.printTasks();
        }
    }

    public void addTaskMessage(Task task, int taskCount) {
        sendMessage("Okiee, adding this task:\n   " + task + "\nNow you got " + taskCount + " total tasks.");
    }

    public void deleteTaskMessage(Task task, int taskCount) {
        sendMessage("Okiee, removing this task:\n   " + task + "\nNow you got " + taskCount + " total tasks.");
    }

    public void markTaskMessage(Task task) {
        sendMessage("Congrats on completing this:\n   " + task);
    }

    public void unmarkTaskMessage(Task task) {
        sendMessage("Oh no, you haven't completed this:\n   " + task);
    }

    public void matchingTaskMessage(ArrayList<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            sendMessage("You don't have this task in your list!");
        } else {
            StringBuilder response = new StringBuilder("Here are the task(s):\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                response.append(" ").append(i + 1).append(". ").append(matchingTasks.get(i)).append("\n");
            }
            sendMessage(response.toString());
        }
    }
}
