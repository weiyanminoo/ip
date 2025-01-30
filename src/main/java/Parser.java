public class Parser {

    private TaskList taskList;
    private UI ui;

    public Parser(TaskList taskList, UI ui) {
        this.taskList = taskList;
        this.ui = ui;
    }

    public void processCommand(String input) throws BaymaxException {
        String[] words = input.split(" ", 2);
        String command = words[0];

        switch (command) {
            case "bye":
                ui.exitMessage();
                System.exit(0);
                break;
            case "list":
                taskList.listTasks(ui);
                break;
            case "mark":
                taskList.markTask(parseIndex(words), true, ui);
                break;
            case "unmark":
                taskList.markTask(parseIndex(words), false, ui);
                break;
            case "delete":
                taskList.deleteTask(parseIndex(words), ui);
                break;
            case "todo":
                taskList.addTodo(input, ui);
                break;
            case "deadline":
                taskList.addDeadline(input, ui);
                break;
            case "event":
                taskList.addEvent(input, ui);
                break;
            default:
                throw new BaymaxException("Invalid command!");
        }
    }

    private int parseIndex(String[] words) throws BaymaxException {
        if (words.length < 2) {
            throw new BaymaxException("Invalid input! Command requires an index.");
        }
        try {
            return Integer.parseInt(words[1]) - 1;
        } catch (NumberFormatException e) {
            throw new BaymaxException("Invalid task number!");
        }
    }
}
