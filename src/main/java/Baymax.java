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
            } else if (input.startsWith("todo")) {
                addTodo(input);
            } else if (input.startsWith("deadline")) {
                addDeadline(input);
            } else if (input.startsWith("event")) {
                addEvent(input);
            } else {
                System.out.println(" Invalid command!");
            }
        }
        scanner.close();
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
                System.out.println("==========================================");
                System.out.println(" Good job on completing this task:");
                System.out.println("   " + tasks[taskNumber - 1]);
                System.out.println("==========================================");
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
                System.out.println("==========================================");
                System.out.println(" You have not finished this task:");
                System.out.println("   " + tasks[taskNumber - 1]);
                System.out.println("==========================================");
            } else {
                System.out.println(" Invalid task number!");
            }
        } catch (Exception e) {
            System.out.println(" Invalid input! Use: unmark [task_number]");
        }
    }

    private static void addTodo(String input) {
        String description = input.substring(5).trim();
        tasks[taskCount] = new Todo(description);
        taskCount++;
        System.out.println("==========================================");
        System.out.println(" Alright, adding this Todo task:");
        System.out.println("   " + tasks[taskCount - 1]);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println("==========================================");
    }

    private static void addDeadline(String input) {
        String description = input.substring(9).trim();
        String[] parts = description.split(" /by ");
        if (parts.length < 2) {
            System.out.println(" Invalid deadline format. Use: deadline [description] /by [date]");
            return;
        }
        tasks[taskCount] = new Deadline(parts[0], parts[1]);
        taskCount++;
        System.out.println("==========================================");
        System.out.println(" Alright, adding this Deadline:");
        System.out.println("   " + tasks[taskCount - 1]);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println("==========================================");
    }

    private static void addEvent(String input) {
        String description = input.substring(6).trim();
        String[] parts = description.split(" /on | /from | /to ");
        if (parts.length < 4) {
            System.out.println(" Invalid event format. Use: event [description] /on day /from [time] /to [time]");
            return;
        }
        tasks[taskCount] = new Event(parts[0], parts[1], parts[2], parts[3]);
        taskCount++;
        System.out.println("==========================================");
        System.out.println(" Alright, adding this Event:");
        System.out.println("   " + tasks[taskCount - 1]);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println("==========================================");
    }
}
