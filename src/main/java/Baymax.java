import java.util.Scanner;

public class Baymax {

    // Array to store list of tasks
    private String[] tasks = new String[100];
    private int taskCount = 0;

    public void start() {
        // Greeting message
        System.out.println("==========================================");
        System.out.println(" Hey there! I'm Baymax");
        System.out.println(" How may I assist you?");
        System.out.println("==========================================");

        // Get input from user
        Scanner scanner = new Scanner(System.in);
        String input;

        // Echos user's input
        while (true) {
            input = scanner.nextLine();

            // Command 'bye' to end the conversation
            if (input.equalsIgnoreCase("bye")) {
                System.out.println("==========================================");
                System.out.println(" Byeee! Hope to see you again soon!");
                System.out.println("==========================================");
                break;
            }

            // Command 'list' to show all the tasks added
            if (input.equalsIgnoreCase("list")) {
                System.out.println("==========================================");
                if (taskCount == 0) {
                    System.out.println(" No tasks added yet!");
                } else {
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(" " + (i + 1) + ". " + tasks[i]);
                    }
                }
                System.out.println("==========================================");

            } else {

                if (taskCount < 100) {
                    tasks[taskCount] = input;
                    taskCount++;
                    System.out.println("==========================================");
                    System.out.println(" added: " + input);
                    System.out.println("==========================================");
                } else {
                    System.out.println("==========================================");
                    System.out.println(" Task limit reached! Unable to add more tasks.");
                    System.out.println("==========================================");
                }
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        // Create an instance of Baymax
        Baymax baymax = new Baymax();
        baymax.start();
    }
}
