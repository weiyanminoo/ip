public class Baymax {

    private UI ui;
    private Storage storage;
    private TaskList taskList;
    private Parser parser;

    public Baymax(String filepath) {
        this.ui = new UI();
        this.storage = new Storage(filepath);
        this.taskList = new TaskList(storage);
        this.parser = new Parser(taskList, ui);
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
        new Baymax("./data/tasks.txt").run();
    }
}
