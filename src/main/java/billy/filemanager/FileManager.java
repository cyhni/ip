package billy.filemanager;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

import billy.parser.Parser;

import billy.tasks.Deadline;
import billy.tasks.Event;
import billy.tasks.Task;
import billy.tasks.Todo;

public class FileManager {
    private static String PATH_NAME = "./src/main/java/data/billy.txt";

    public void startUp(ArrayList<Task> tasksList) throws IOException {
        File file = new File(PATH_NAME);
        if (!file.getParentFile().exists()) {
            try {
                file.getParentFile().mkdir();
            } catch (SecurityException e) {
                throw e;
            }
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw e;
            }
        }

        Scanner fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] splitLine = line.split(" \\| ");
            switch (splitLine[0]) {
            case "T":
                tasksList.add(new Todo(splitLine[2]));
                break;
            case "D":
                tasksList.add(new Deadline(splitLine[2], Parser.dateParsing(splitLine[3])));
                break;
            case "E":
                tasksList.add(new Event(splitLine[2], Parser.dateParsing(splitLine[3]), Parser.dateParsing(splitLine[4])));
                break;
            default:
                break;
            }
            if (splitLine[1].equals("1")) {
                tasksList.get(tasksList.size() - 1).markAsDone();
            }
        }
        fileScanner.close();
    }

    public void updateFile(Task task) throws IOException {
        File file = new File(PATH_NAME);
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter(file, true);
            fileWriter.write(task.getFileDescriptor() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            throw e;
        }
    }

    public void updateFile(ArrayList<Task> tasksList) throws IOException {
        deleteFile();
        for (int i = 0; i < tasksList.size(); i++) {
            updateFile(tasksList.get(i));
        }
    }

    private void deleteFile() throws IOException {
        File file = new File(PATH_NAME);
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter(file, false);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            throw e;
        }
    }
}
