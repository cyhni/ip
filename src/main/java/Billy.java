import constants.DesignConstants;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import java.io.File;
import java.io.IOException;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Billy {
    private static ArrayList<Task> tasksList = new ArrayList<>();  
    private static int counter = 0;

    private static final Pattern DATETIME_PATTERN = Pattern.compile("\\d{2}-\\d{2}\\-\\d{4} \\d{4}");
    public static void main(String[] args) {
        try {
            startUp();
        } catch (IOException e) {
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            System.out.println(e.getMessage());
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            Billy.printBye();
            System.exit(0);
        }
        Billy.printIntroduction();
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
            } catch (DateTimeException e) {
                System.out.println(e.getMessage());
                System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            }
        }
        scanner.close();
        Billy.printBye();
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
                tasksList.add(new Todo(splitLine[2]));
                break;
            case "D":
                tasksList.add(new Deadline(splitLine[2], dateParsing(splitLine[3])));
                break;
            case "E":
                tasksList.add(new Event(splitLine[2], dateParsing(splitLine[3]), dateParsing(splitLine[4])));
                break;
            default:
                break;
            }
            if (splitLine[1].equals("1")) {
                tasksList.get(counter).markAsDone();
            }
            counter++;
        }
        fileScanner.close();
    }

    private final static void printIntroduction() {
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
        System.out.println(DesignConstants.LOGO_STRING);
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
        System.out.println("\nWelcome to the world of Billy!\n" + "How can I help you?\n");
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
    }

    private final static void printBye() {
        System.out.println("\nBye bye.\n");
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
    }

    private static void parseCommand (String userCmd) throws BillyUnknownException, BillyFieldErrorException, BillyUnkownTaskNumException, DateTimeException {
        String[] splitCmd = userCmd.split(" ");

        switch (splitCmd[0]) {
        case "list":
            Billy.printList();
            break;

        case "mark":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("mark");
            } else if (Integer.parseInt(splitCmd[1]) > counter) {
                throw new BillyUnkownTaskNumException(splitCmd[1]);
            }
            tasksList.get(Integer.parseInt(splitCmd[1]) - 1).markAsDone();
            Billy.updateFile();

            System.out.println("\nMarked as done:\n" 
                + (Integer.parseInt(splitCmd[1])) + ". " + tasksList.get(Integer.parseInt(splitCmd[1]) - 1) + "\n");
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            break;

        case "unmark":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("mark");
            } else if (Integer.parseInt(splitCmd[1]) > counter) {
                throw new BillyUnkownTaskNumException(splitCmd[1]);
            }
            tasksList.get(Integer.parseInt(splitCmd[1]) - 1).markAsUndone();
            Billy.updateFile();

            System.out.println("\nMarked as undone:\n" 
                + (Integer.parseInt(splitCmd[1])) + ". " + tasksList.get(Integer.parseInt(splitCmd[1]) - 1) + "\n");
            System.out.println(DesignConstants.HORIZONTALLINE_STRING);
            break;

        case "todo":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("todo");
            }
            tasksList.add(new Todo(userCmd));
            counter++;
            Billy.updateFile(tasksList.get(counter - 1));
            printTaskAdded(tasksList.get(counter - 1));
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
    
            LocalDateTime deadlineParsedDate = dateParsing(deadlineDate);
            if (deadlineParsedDate == null) {
                throw new DateTimeException("Billy does not understand the date format...\nPlease use dd-MM-yyyy HHmm format...");
            }

            tasksList.add(new Deadline(deadlineDescription, deadlineParsedDate));
            counter++;
            Billy.updateFile(tasksList.get(counter - 1));
            printTaskAdded(tasksList.get(counter - 1));
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

            LocalDateTime eventParsedFrom = dateParsing(eventFrom);
            LocalDateTime eventParsedTo = dateParsing(eventTo);
            if (eventParsedFrom == null || eventParsedTo == null) {
                throw new DateTimeException("Billy does not understand the date format...\nPlease use dd-MM-yyyy HHmm format...");
            } else if (eventParsedFrom.isAfter(eventParsedTo)) {
                throw new DateTimeException("Please ensure that the start date is before the end date...");
            }

            tasksList.add(new Event(eventDescription, eventParsedFrom, eventParsedTo));
            counter++;

            Billy.updateFile(tasksList.get(counter - 1));
            printTaskAdded(tasksList.get(counter - 1));
            break;

        case "delete":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("delete");
            } else if (Integer.parseInt(splitCmd[1]) > counter) {
                throw new BillyUnkownTaskNumException(splitCmd[1]);
            }
            Task deletedTask = tasksList.get(Integer.parseInt(splitCmd[1]) - 1);
            tasksList.remove(Integer.parseInt(splitCmd[1]) - 1);
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

    private static void printList() {
        System.out.println("\nHere are the items in your list:");
        for (int i = 0; i < counter; i++) {
            System.out.println((i + 1) + ". " + tasksList.get(i));
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
            updateFile(tasksList.get(i));
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

    private static LocalDateTime dateParsing(String date) {
        LocalDateTime parsedDate = null;
        if (DATETIME_PATTERN.matcher(date).matches()) {
            parsedDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
        }
        return parsedDate;
    }

    private static void printTaskAdded(Task task) {
        System.out.println("\nAdded to the list:\n" + counter + ". " + task + "\n");
        System.out.println("There are currently " + counter + " task(s) in the list.\n");
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
    }
}
