package baymax.storage;

import baymax.task.Deadline;
import baymax.task.Task;
import baymax.task.Todo;
import baymax.task.Event;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to a file.
 */
public class Storage {

    private String filepath;
    private static final String DIRECTORY_PATH = "./data";
    private static final String FILE_PATH = DIRECTORY_PATH + "/tasks.txt";

    /**
     * Constructs a Storage instance.
     *
     * @param filepath The file path to load and save tasks.
     */
    public Storage(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Loads tasks from the file.
     *
     * @return A list of tasks retrieved from the file.
     * @throws IOException If an error occurs while reading the file.
     */
    public List<Task> loadTasks() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return tasks;  // Return empty list if no saved tasks
        }

        Scanner scanner = new Scanner(file);

        // Read file according to set format
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" \\| ");

            // Catch corrupted files by detecting invalid content format
            try {
                if (parts.length < 3) {
                    throw new IllegalArgumentException("Invalid task format: " + line);
                }

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                assert type != null : "Task type should not be null";
                assert description != null && !description.isEmpty() : "Task description should not be empty";

                switch (type) {
                case "T":
                    tasks.add(new Todo(description, isDone));
                    break;
                case "D":
                    if (parts.length < 4) throw new IllegalArgumentException("Invalid Deadline format: " + line);
                    tasks.add(new Deadline(description, parts[3], isDone));
                    break;
                case "E":
                    if (parts.length < 6) throw new IllegalArgumentException("Invalid Event format: " + line);
                    tasks.add(new Event(description, parts[3], parts[4], parts[5], isDone));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown task type: " + type);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Skipped corrupted line: " + e.getMessage());
            }
        }
        scanner.close();
        return tasks;
    }

    /**
     * Saves tasks to the file.
     *
     * @param tasks The list of tasks to be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void saveTasks(List<Task> tasks) throws IOException {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs(); // Ensure directory exists before writing
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        for (Task task : tasks) {
            writer.write(task.toFileFormat());
            writer.newLine();
        }
        writer.close();
    }
}
