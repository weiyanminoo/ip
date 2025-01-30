import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task, UI ui) {
        tasks.add(task);
        ui.addTaskMessage(task, tasks.size());
    }

    public void deleteTask(int index, UI ui) throws BaymaxException {
        if (index < 0 || index >= tasks.size()) {
            throw new BaymaxException("Task number out of range!");
        }
        Task removedTask = tasks.remove(index);
        ui.deleteTaskMessage(removedTask, tasks.size());
    }

    public void markTask(int index, boolean isDone, UI ui) throws BaymaxException {
        if (index < 0 || index >= tasks.size()) {
            throw new BaymaxException("Task number out of range!");
        }
        Task task = tasks.get(index);
        if (isDone) {
            task.markAsDone();
            ui.markTaskMessage(task);
        } else {
            task.markAsNotDone();
            ui.unmarkTaskMessage(task);
        }
    }

    public void listTasks(UI ui) {
        ui.taskListMessage(this);
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void printTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
    }
}
