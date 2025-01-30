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
}
