import java.util.ArrayList;
import java.util.Scanner;

public class Baymax {

    // Store list of tasks using ArrayList
    private static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        // Greeting message
        System.out.println("==========================================");
        System.out.println(" Hey there! I'm Baymax");
        System.out.println(" How may I assist you?");
        System.out.println("==========================================");

        while (true) {
            try {
                String input = scanner.nextLine();
                processCommand(input);
            } catch (BaymaxException e) {
                System.out.println("==========================================");
                System.out.println(" " + e.getMessage());
                System.out.println("==========================================");
            }
        }
    }

    private static void processCommand(String input) throws BaymaxException {

        // Command 'bye' to end the conversation
        if (input.equalsIgnoreCase("bye")) {
            System.out.println("==========================================");
            System.out.println(" Byeee! Hope to see you again soon!");
            System.out.println("==========================================");
            System.exit(0);
        } else if (input.equalsIgnoreCase("list")) {
            listTasks();
        } else if (input.startsWith("mark")) {
            markTask(input);
        } else if (input.startsWith("unmark")) {
            unmarkTask(input);
        } else if (input.startsWith("delete")) {
            deleteTask(input);
        } else if (input.startsWith("todo")) {
            addTodo(input);
        } else if (input.startsWith("deadline")) {
            addDeadline(input);
        } else if (input.startsWith("event")) {
            addEvent(input);
        } else {
            throw new BaymaxException(" Invalid command!");
        }
    }

    // List tasks if not empty
    private static void listTasks() throws BaymaxException {
        System.out.println("==========================================");
        if (tasks.isEmpty()) {
            throw new BaymaxException(" No tasks added yet!");
        }
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
        System.out.println("==========================================");
    }

    // Mark tasks as done using 'X'
    private static void markTask(String input) throws BaymaxException {
        try {
            int taskNumber = Integer.parseInt(input.split(" ")[1]);
            if (taskNumber <= 0 || taskNumber > tasks.size()) {
                throw new BaymaxException("Task number out of range! Enter a valid task number.");
            }
            tasks.get(taskNumber - 1).markAsDone();
            System.out.println("==========================================");
            System.out.println(" Good job on completing this task:");
            System.out.println("   " + tasks.get(taskNumber - 1));
            System.out.println("==========================================");
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new BaymaxException("Invalid input! Use: mark [task_number]");
        }
    }

    // Unmark tasks and leave the checkbox empty
    private static void unmarkTask(String input) throws BaymaxException {
        try {
            int taskNumber = Integer.parseInt(input.split(" ")[1]);
            if (taskNumber <= 0 || taskNumber > tasks.size()) {
                throw new BaymaxException("Task number out of range! Enter a valid task number.");
            }
            tasks.get(taskNumber - 1).markAsNotDone();
            System.out.println("==========================================");
            System.out.println(" You have not finished this task:");
            System.out.println("   " + tasks.get(taskNumber - 1));
            System.out.println("==========================================");
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new BaymaxException("Invalid input! Use: unmark [task_number]");
        }
    }

    private static void deleteTask(String input) throws BaymaxException {
        try {
            int taskNumber = Integer.parseInt(input.split(" ")[1]);
            if (taskNumber <= 0 || taskNumber > tasks.size()) {
                throw new BaymaxException("Task number out of range! Enter a valid task number.");
            }
            Task removedTask = tasks.remove(taskNumber - 1);
            System.out.println("==========================================");
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + removedTask);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("==========================================");
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new BaymaxException("Invalid input! Use: delete [task_number]");
        }
    }

    private static void addTodo(String input) throws BaymaxException {
        try {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new BaymaxException("The description of a todo cannot be empty!");
            }
            tasks.add(new Todo(description));
            System.out.println("==========================================");
            System.out.println(" Alright, adding this Todo task:");
            System.out.println("   " + tasks.get(tasks.size() - 1));
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("==========================================");
        } catch (StringIndexOutOfBoundsException e) {
            throw new BaymaxException("Invalid input! Use: todo [description]");
        }
    }

    private static void addDeadline(String input) throws BaymaxException {
        try {
            String description = input.substring(9).trim();
            String[] parts = description.split(" /by ");
            if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                throw new BaymaxException("Invalid deadline format. Use: deadline [description] /by [date]");
            }
            tasks.add(new Deadline(parts[0], parts[1]));
            System.out.println("==========================================");
            System.out.println(" Alright, adding this Deadline:");
            System.out.println("   " + tasks.get(tasks.size() - 1));
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("==========================================");
        } catch (StringIndexOutOfBoundsException e) {
            throw new BaymaxException("Invalid input! Use: deadline [description] /by [date]");
        }
    }

    private static void addEvent(String input) throws BaymaxException {
        try {
            String description = input.substring(6).trim();
            String[] parts = description.split(" /on | /from | /to ");
            if (parts.length < 4) {
                throw new BaymaxException("Invalid event format. Use: event [description] /on [day] /from [time] /to [time]");
            }
            tasks.add(new Event(parts[0], parts[1], parts[2], parts[3]));
            System.out.println("==========================================");
            System.out.println(" Alright, adding this Event:");
            System.out.println("   " + tasks.get(tasks.size() - 1));
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("==========================================");
        } catch (StringIndexOutOfBoundsException e) {
            throw new BaymaxException("Invalid input! Use: event [description] /on [day] /from [time] /to [time]");
        }
    }
}
