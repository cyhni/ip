import java.io.IOException;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern DATETIME_PATTERN = Pattern.compile("\\d{2}-\\d{2}\\-\\d{4} \\d{4}");

    public static void parseCommand(String userCmd, ArrayList<Task> tasksList, Ui ui, FileManager fileManager) 
            throws BillyUnknownException, BillyFieldErrorException, BillyUnkownTaskNumException, DateTimeException, IOException {
        String[] splitCmd = userCmd.split(" ");

        switch (splitCmd[0]) {
        case "list":
            ui.printList(tasksList);
            break;

        case "mark":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("mark");
            } else if (Integer.parseInt(splitCmd[1]) > tasksList.size()) {
                throw new BillyUnkownTaskNumException(splitCmd[1]);
            }
            tasksList.get(Integer.parseInt(splitCmd[1]) - 1).markAsDone();
            fileManager.updateFile(tasksList);

            ui.printToUser("\nMarked as done:\n" 
                + (Integer.parseInt(splitCmd[1])) + ". " + tasksList.get(Integer.parseInt(splitCmd[1]) - 1) + "\n");
            ui.printLine();
            break;

        case "unmark":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("mark");
            } else if (Integer.parseInt(splitCmd[1]) > tasksList.size()) {
                throw new BillyUnkownTaskNumException(splitCmd[1]);
            }
            tasksList.get(Integer.parseInt(splitCmd[1]) - 1).markAsUndone();
            fileManager.updateFile(tasksList);

            ui.printToUser("\nMarked as undone:\n" 
                + (Integer.parseInt(splitCmd[1])) + ". " + tasksList.get(Integer.parseInt(splitCmd[1]) - 1) + "\n");
            ui.printLine();
            break;

        case "todo":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("todo");
            }
            tasksList.add(new Todo(userCmd));
            fileManager.updateFile(tasksList.get(tasksList.size() - 1));
            ui.printTaskAdded(tasksList.get(tasksList.size() - 1), tasksList.size());
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
                throw new DateTimeException("\nBilly does not understand the date format...\nPlease use dd-MM-yyyy HHmm format...\n");
            }

            tasksList.add(new Deadline(deadlineDescription, deadlineParsedDate));
            fileManager.updateFile(tasksList.get(tasksList.size() - 1));
            ui.printTaskAdded(tasksList.get(tasksList.size() - 1), tasksList.size());
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
            
            fileManager.updateFile(tasksList.get(tasksList.size() - 1));
            ui.printTaskAdded(tasksList.get(tasksList.size() - 1), tasksList.size());
            break;

        case "delete":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("delete");
            } else if (Integer.parseInt(splitCmd[1]) > tasksList.size()) {
                throw new BillyUnkownTaskNumException(splitCmd[1]);
            }
            Task deletedTask = tasksList.get(Integer.parseInt(splitCmd[1]) - 1);
            tasksList.remove(Integer.parseInt(splitCmd[1]) - 1);
            fileManager.updateFile(tasksList);
            ui.printToUser("\nRemoved from the list:\n" + splitCmd[1] + ". " + deletedTask + "\n",
                    "There are currently " + tasksList.size() + " task(s) in the list.\n");
            ui.printLine();
            break;
            
        default:
            throw new BillyUnknownException();
        }
    }

    public static LocalDateTime dateParsing(String date) {
        LocalDateTime parsedDate = null;
        if (DATETIME_PATTERN.matcher(date).matches()) {
            parsedDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
        }
        return parsedDate;
    }
}
