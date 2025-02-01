package billy;

import java.io.IOException;
import java.time.DateTimeException;

import billy.command.Command;
import billy.exceptions.BillyException;
import billy.filemanager.FileManager;
import billy.parser.Parser;
import billy.tasks.TasksList;
import billy.ui.Ui;

public class Billy {
    private TasksList tasksList;
    private Ui ui;

    public Billy() {
        this.ui = new Ui();
        this.tasksList = new TasksList();
        try {
            FileManager.startUp(this.tasksList.getTasksList());
        } catch (IOException | SecurityException e) {
            ui.printLine();
            ui.printError(e.getMessage());
        }
    }

    public void run() {
        ui.printIntroduction();
        while (true) {
            String userCmd = ui.readCommand();
            ui.printLine();

            if (userCmd.equals("bye")) {
                break;
            }
            try {
                Command c = Parser.parseCommand(userCmd, tasksList, ui);
                c.execute(tasksList, ui);
            } catch (BillyException | DateTimeException | IOException e) {
                ui.printError(e.getMessage());
            }
        }
        ui.printBye();
    }

    public static void main(String[] args) {
        new Billy().run();
    }
}
