import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private final String filePath;
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    // Load tasks from the file
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // If opening chatbot for the first time,
        // create the file and its directory
        if (!file.exists()) {
            file.getParentFile().mkdir();
            file.createNewFile();
            return tasks;
        }

        Scanner scanner = new Scanner(file);

        // Read file according to set format
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            // Checking the specific task types
            switch (type) {
                case "T":
                    tasks.add(new Todo(description, isDone));
                    break;
                case "D":
                    tasks.add(new Deadline(description, parts[3], isDone));
                    break;
                case "E":
                    tasks.add(new Event(description, parts[3], isDone));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown task type!")
            }
        }
        scanner.close();
        return tasks;
    }

    // Save tasks from the file
    public void save(ArrayList<Task> tasks) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        for (Task task : tasks) {
            writer.write(task.toFileFormat() + System.lineSeparator());
        }
        writer.close();;
    }
}
