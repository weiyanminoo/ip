package baymax.ui;

import baymax.task.Task;
import baymax.tasklist.TaskList;

import java.util.ArrayList;
import java.util.Scanner;

public class UI {

    private Scanner scanner;

    public UI() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void welcomeMessage() {
        System.out.println("==========================================");
        System.out.println(" Wassup! I'm Baymax");
        System.out.println(" How can I help you?");
        System.out.println("==========================================");
    }

    public void exitMessage() {
        System.out.println("==========================================");
        System.out.println(" Byeee! Take care human!");
        System.out.println("==========================================");
    }

    public void errorMessage(String message) {
        System.out.println("==========================================");
        System.out.println(" Error: " + message);
        System.out.println("==========================================");
    }

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

    public void addTaskMessage(Task task, int taskCount) {
        System.out.println("==========================================");
        System.out.println(" Okiee, adding this task:");
        System.out.println("   " + task);
        System.out.println(" Now you got " + taskCount + " total tasks.");
        System.out.println("==========================================");
    }

    public void deleteTaskMessage(Task task, int taskCount) {
        System.out.println("==========================================");
        System.out.println(" Okiee, removing this task:");
        System.out.println("   " + task);
        System.out.println(" Now you got " + taskCount + " total tasks.");
        System.out.println("==========================================");
    }

    public void markTaskMessage(Task task) {
        System.out.println("==========================================");
        System.out.println(" Congrats on completing this:");
        System.out.println("   " + task);
        System.out.println("==========================================");
    }

    public void unmarkTaskMessage(Task task) {
        System.out.println("==========================================");
        System.out.println(" Oh no, you haven't complete this:");
        System.out.println("   " + task);
        System.out.println("==========================================");
    }

    public void matchingTaskMessage(ArrayList<Task> matchingTasks) {
        System.out.println("==========================================");
        if (matchingTasks.isEmpty()) {
            System.out.println(" You don't have this task in your list!");
        } else {
            System.out.println(" Here are the task(s):");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + matchingTasks.get(i));
            }
        }
        System.out.println("==========================================");
    }
}
