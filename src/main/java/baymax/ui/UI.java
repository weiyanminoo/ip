package baymax.ui;

import baymax.task.Task;
import baymax.tasklist.TaskList;

import java.util.Scanner;

/**
 * Handles user interactions in the Baymax application.
 * Responsible for displaying messages according to user input.
 */
public class UI {

    private Scanner scanner;

    /**
     * Constructs a UI instance with a Scanner to read user input.
     */
    public UI() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads a command inputted by the user.
     *
     * @return The user input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the welcome message.
     */
    public void welcomeMessage() {
        System.out.println("==========================================");
        System.out.println(" Wassup! I'm Baymax");
        System.out.println(" How can I help you?");
        System.out.println("==========================================");
    }

    /**
     * Displays the exit message.
     */
    public void exitMessage() {
        System.out.println("==========================================");
        System.out.println(" Byeee! Take care human!");
        System.out.println("==========================================");
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to be displayed.
     */
    public void errorMessage(String message) {
        System.out.println("==========================================");
        System.out.println(" Error: " + message);
        System.out.println("==========================================");
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks The TaskList containing tasks to be displayed.
     */
    public void taskListMessage(TaskList tasks) {
        System.out.println("==========================================");
        if (tasks.isEmpty()) {
            System.out.println(" You got no tasks!");
        } else {
            System.out.println(" Here's your task list:");
            tasks.printTasks();
        }
        System.out.println("==========================================");
    }

    /**
     * Displays a message when a task is added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks after adding the new task.
     */
    public void addTaskMessage(Task task, int taskCount) {
        System.out.println("==========================================");
        System.out.println(" Okiee, adding this task:");
        System.out.println("   " + task);
        System.out.println(" Now you got " + taskCount + " total tasks.");
        System.out.println("==========================================");
    }

    /**
     * Displays a message when a task is deleted.
     *
     * @param task The task that was deleted.
     * @param taskCount The total number of tasks after deletion.
     */
    public void deleteTaskMessage(Task task, int taskCount) {
        System.out.println("==========================================");
        System.out.println(" Okiee, removing this task:");
        System.out.println("   " + task);
        System.out.println(" Now you got " + taskCount + " total tasks.");
        System.out.println("==========================================");
    }

    /**
     * Displays a message when a task is marked as completed.
     *
     * @param task The task that was marked as completed.
     */
    public void markTaskMessage(Task task) {
        System.out.println("==========================================");
        System.out.println(" Congrats on completing this:");
        System.out.println("   " + task);
        System.out.println("==========================================");
    }

    /**
     * Displays a message when a task is unmarked as completed.
     *
     * @param task The task that was unmarked.
     */
    public void unmarkTaskMessage(Task task) {
        System.out.println("==========================================");
        System.out.println(" Oh no, you haven't complete this:");
        System.out.println("   " + task);
        System.out.println("==========================================");
    }
}
