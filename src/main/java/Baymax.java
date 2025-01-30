public class Baymax {

    private UI ui;
    private TaskList taskList;
    private Parser parser;
    private Storage storage;

    public Baymax(String filepath) {
        this.ui = new UI();
        this.taskList = new TaskList();
        this.parser = new Parser(taskList, ui);
        this.storage = new Storage(filepath);
    }

    public void run() {
        ui.welcomeMessage();
        while (true) {
            try {
                String input = ui.readCommand();
                parser.processCommand(input);
            } catch (BaymaxException e) {
                ui.errorMessage(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Baymax("tasks.txt").run();
    }
}
