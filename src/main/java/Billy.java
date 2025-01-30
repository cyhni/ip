import constants.DesignConstants;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Billy {
    private static ArrayList<Task> tasklist = new ArrayList<>();  
    private static int counter = 0;
    public static void main(String[] args) {
        try {
            startUp();
        } catch (IOException e) {
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            System.out.println(e.getMessage());
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            Billy.bye();
            System.exit(0);
        }
        Billy.introduction();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter your command: ");
            String userCmd = scanner.nextLine();
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);

            if (userCmd.equals("bye")) {
                break;
            }
            try {
                Billy.parseCommand(userCmd);
            } catch (BillyUnkownTaskNumException e) {
                System.out.println(e.getMessage());
                System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            } catch (BillyFieldErrorException e) {
                System.out.println(e.getMessage());
                System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            } catch (BillyUnknownException e) {
                System.out.println(e.getMessage());
                System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            }
        }
        scanner.close();
        Billy.bye();
    }

    private static void startUp() throws IOException {
        File file = new File("./src/main/java/data/billy.txt");
        if (!file.getParentFile().exists()) {
            try {
                file.getParentFile().mkdir();
            } catch (Exception e) {
                throw new IOException("Billy failed to create directory...");
            }
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IOException("Billy failed to create file...");
            }
        }

        Scanner fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] splitLine = line.split(" \\| ");
            switch (splitLine[0]) {
            case "T":
                tasklist.add(new Todo(splitLine[2]));
                break;
            case "D":
                tasklist.add(new Deadline(splitLine[2], splitLine[3]));
                break;
            case "E":
                tasklist.add(new Event(splitLine[2], splitLine[3], splitLine[4]));
                break;
            default:
                break;
            }
            if (splitLine[1].equals("1")) {
                tasklist.get(counter).markAsDone();
            }
            counter++;
        }
    }

    private final static void introduction() {
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
        System.out.println(DesignConstants.LOGO_STRING);
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
        System.out.println("\nWelcome to the world of Billy!\n" + "How can I help you?\n");
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
    }

    private final static void bye() {
        System.out.println("\nBye bye.\n");
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
    }

    private static void parseCommand (String userCmd) throws BillyUnknownException, BillyFieldErrorException, BillyUnkownTaskNumException {
        String[] splitCmd = userCmd.split(" ");

        switch (splitCmd[0]) {
        case "list":
            Billy.list();
            break;

        case "mark":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("mark");
            } else if (Integer.parseInt(splitCmd[1]) > counter) {
                throw new BillyUnkownTaskNumException(splitCmd[1]);
            }
            tasklist.get(Integer.parseInt(splitCmd[1]) - 1).markAsDone();
            Billy.updateFile();

            System.out.println("\nMarked as done:\n" 
                + (Integer.parseInt(splitCmd[1])) + ". " + tasklist.get(Integer.parseInt(splitCmd[1]) - 1) + "\n");
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            break;

        case "unmark":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("mark");
            } else if (Integer.parseInt(splitCmd[1]) > counter) {
                throw new BillyUnkownTaskNumException(splitCmd[1]);
            }
            tasklist.get(Integer.parseInt(splitCmd[1]) - 1).markAsUndone();
            Billy.updateFile();

            System.out.println("\nMarked as undone:\n" 
                + (Integer.parseInt(splitCmd[1])) + ". " + tasklist.get(Integer.parseInt(splitCmd[1]) - 1) + "\n");
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            break;

        case "todo":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("todo");
            }
            tasklist.add(new Todo(userCmd));
            counter++;
            Billy.updateFile(tasklist.get(counter - 1));
            taskAdderPrinter(tasklist.get(counter - 1));
            break;

        case "deadline":
            String[] deadlineSplit = userCmd.split(" /by ");
            if (deadlineSplit.length <= 1 || deadlineSplit[0].length() == splitCmd[0].length()) {
                throw new BillyFieldErrorException("deadline");
            }
            String deadlineDescription = deadlineSplit[0].substring(splitCmd[0].length() + 1);
            String deadlineDate = deadlineSplit[1];

            if (deadlineDescription.equals("") || deadlineDate.equals("")) {
                throw new BillyFieldErrorException("deadline");
            }

            tasklist.add(new Deadline(deadlineDescription, deadlineDate));
            counter++;
            Billy.updateFile(tasklist.get(counter - 1));
            taskAdderPrinter(tasklist.get(counter - 1));
            break;

        case "event":
            String[] eventSplit = userCmd.split(" /from ");
            String[] eventSplit2 = userCmd.split(" /to ");
            String[] eventSplitCheck = eventSplit[1].split(" /to ");
            if (eventSplit.length <= 1 || eventSplit2.length <= 1 || eventSplit[0].length() == splitCmd[0].length() || eventSplitCheck.length == 1) {
                throw new BillyFieldErrorException("event");
            }

            String eventDescription = eventSplit[0].substring(splitCmd[0].length() + 1);
            String eventFrom = eventSplit[1].substring(0, eventSplit[1].length() - eventSplit2[1].length() - 5);
            String eventTo = eventSplit2[1];

            if (eventDescription.equals("") || eventFrom.equals("") || eventTo.equals("")) {
                throw new BillyFieldErrorException("event");
            }

            tasklist.add(new Event(eventDescription, eventFrom, eventTo));
            counter++;

            Billy.updateFile(tasklist.get(counter - 1));
            taskAdderPrinter(tasklist.get(counter - 1));
            break;

        case "delete":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("delete");
            } else if (Integer.parseInt(splitCmd[1]) > counter) {
                throw new BillyUnkownTaskNumException(splitCmd[1]);
            }
            Task deletedTask = tasklist.get(Integer.parseInt(splitCmd[1]) - 1);
            tasklist.remove(Integer.parseInt(splitCmd[1]) - 1);
            counter--;
            Billy.updateFile();
            System.out.println("\nRemoved from the list:\n" + splitCmd[1] + ". " + deletedTask + "\n");
            System.out.println("There are currently " + counter + " task(s) in the list.\n");
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            break;
            
        default:
            throw new BillyUnknownException();
        }
    }

    private static void list() {
        System.out.println("\nHere are the items in your list:");
        for (int i = 0; i < counter; i++) {
            System.out.println((i + 1) + ". " + tasklist.get(i));
        }
        System.out.println();
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
    }

    private static void updateFile(Task task) {
        File file = new File("./src/main/java/data/billy.txt");
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter(file, true);
            fileWriter.write(task.getFileDescriptor() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            System.out.println(e.getMessage());
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);
        }
    }

    private static void updateFile() {
        deleteFile();
        for (int i = 0; i < counter; i++) {
            updateFile(tasklist.get(i));
        }
    }

    private static void deleteFile() {
        File file = new File("./src/main/java/data/billy.txt");
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter(file, false);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            System.out.println(e.getMessage());
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);
        }
    }

    private static void taskAdderPrinter(Task task) {
        System.out.println("\nAdded to the list:\n" + counter + ". " + task + "\n");
        System.out.println("There are currently " + counter + " task(s) in the list.\n");
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
    }
}
