import java.util.ArrayList;

import java.io.IOException;

import java.time.DateTimeException;

import billy.exceptions.BillyException;

import billy.filemanager.FileManager;

import billy.parser.Parser;

import billy.tasks.Task;

import billy.ui.Ui;

public class Billy {
    private ArrayList<Task> tasksList; 
    private Ui ui;
    private FileManager fileManager;


    public Billy() {
        this.ui = new Ui();
        this.tasksList = new ArrayList<>();
        this.fileManager = new FileManager();
        try {
            fileManager.startUp(this.tasksList);
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
                Parser.parseCommand(userCmd, tasksList, ui, fileManager);
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
