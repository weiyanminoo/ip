import java.util.Scanner;

public class Baymax {

    public static void main(String[] args) {
        // Greeting message
        System.out.println("==========================================");
        System.out.println(" Hey there! I'm Baymax");
        System.out.println(" How may I assist you?");
        System.out.println("==========================================");

        // Get input from user
        Scanner scanner = new Scanner(System.in);
        String input;

        // echos user's input
        while (true) {

            input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("==========================================");
                System.out.println(" Byeee! Hope to see you again soon!");
                System.out.println("==========================================");
                break;
            }

            System.out.println("==========================================");
            System.out.println(" " + input);
            System.out.println("==========================================");
        }

        scanner.close();
    }
}
