import java.util.Scanner;

public class Baymax {

    // Store list of tasks
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        // Greeting message
        System.out.println("==========================================");
        System.out.println(" Hey there! I'm Baymax");
        System.out.println(" How may I assist you?");
        System.out.println("==========================================");

        while (true) {
            String input = scanner.nextLine();

            // Command 'bye' to end the conversation
            if (input.equalsIgnoreCase("bye")) {
                System.out.println("==========================================");
                System.out.println(" Byeee! Hope to see you again soon!");
                System.out.println("==========================================");
                break;
            } else if (input.equalsIgnoreCase("list")) {
                // Command "list" to return list of tasks
                listTasks();
            } else if (input.startsWith("mark")) {
                markTask(input);
            } else if (input.startsWith("unmark")) {
                unmarkTask(input);
            } else {
                addTask(input);
            }
        }
        scanner.close();
    }

    // Add task as long as list is not full (100)
    private static void addTask(String description) {
        if (taskCount < 100) {
            tasks[taskCount] = new Task(description);
            taskCount++;
            System.out.println("==========================================");
            System.out.println(" added: " + description);
            System.out.println("==========================================");
        } else {
            System.out.println("==========================================");
            System.out.println(" Task limit reached! Unable to add more tasks.");
            System.out.println("==========================================");
        }
    }

    // List tasks if not empty
    private static void listTasks() {
        System.out.println("==========================================");
        if (taskCount == 0) {
            System.out.println(" No tasks added yet!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println(" " + (i + 1) + ". " + tasks[i]);
            }
        }
        System.out.println("==========================================");
    }

    // Mark tasks as done using 'X'
    private static void markTask(String input) {
        try {
            int taskNumber = Integer.parseInt(input.split(" ")[1]);
            if (taskNumber > 0 && taskNumber <= taskCount) {
                tasks[taskNumber - 1].markAsDone();
                System.out.println("----------------------------------------------------");
                System.out.println(" Good job on completing this task:");
                System.out.println("   " + tasks[taskNumber - 1]);
                System.out.println("----------------------------------------------------");
            } else {
                System.out.println(" Invalid task number!");
            }
        } catch (Exception e) {
            System.out.println(" Invalid input! Use: mark [task_number]");
        }
    }

    // Unmark tasks and leave the checkbox empty
    private static void unmarkTask(String input) {
        try {
            int taskNumber = Integer.parseInt(input.split(" ")[1]);
            if (taskNumber > 0 && taskNumber <= taskCount) {
                tasks[taskNumber - 1].markAsNotDone();
                System.out.println("----------------------------------------------------");
                System.out.println(" You have not finished this task:");
                System.out.println("   " + tasks[taskNumber - 1]);
                System.out.println("----------------------------------------------------");
            } else {
                System.out.println(" Invalid task number!");
            }
        } catch (Exception e) {
            System.out.println(" Invalid input! Use: unmark [task_number]");
        }
    }
}
