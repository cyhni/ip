package billy.parser;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import billy.command.Command;
import billy.command.DeadlineCommand;
import billy.command.DeleteCommand;
import billy.command.EventCommand;
import billy.command.ListCommand;
import billy.command.MarkCommand;
import billy.command.TodoCommand;
import billy.command.UnmarkCommand;
import billy.exceptions.BillyException;
import billy.exceptions.BillyFieldErrorException;
import billy.exceptions.BillyUnknownException;
import billy.exceptions.BillyUnkownTaskNumException;
import billy.tasks.Deadline;
import billy.tasks.Event;
import billy.tasks.TasksList;
import billy.tasks.Todo;
import billy.ui.Ui;

public class Parser {
    private static final Pattern DATETIME_PATTERN = Pattern.compile("\\d{2}-\\d{2}\\-\\d{4} \\d{4}");

    public static Command parseCommand(String userCmd, TasksList tasksList, Ui ui)
            throws BillyException,
            DateTimeException,
            IOException {
        Command command;
        String[] splitCmd = userCmd.split(" ");
        switch (splitCmd[0]) {
        case "list":
            command = new ListCommand();
            break;

        case "mark":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("mark");
            } else if (Integer.parseInt(splitCmd[1]) > tasksList.getSize()) {
                throw new BillyUnkownTaskNumException(splitCmd[1]);
            }

            command = new MarkCommand(Integer.parseInt(splitCmd[1]));
            break;

        case "unmark":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("mark");
            } else if (Integer.parseInt(splitCmd[1]) > tasksList.getSize()) {
                throw new BillyUnkownTaskNumException(splitCmd[1]);
            }

            command = new UnmarkCommand(Integer.parseInt(splitCmd[1]));
            break;

        case "todo":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("todo");
            }

            command = new TodoCommand(new Todo(userCmd.substring(splitCmd[0].length() + 1)));
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
                throw new DateTimeException("Billy does not understand the date format..."
                        + "\nPlease use dd-MM-yyyy HHmm format...");
            }

            command = new DeadlineCommand(new Deadline(deadlineDescription, deadlineParsedDate));
            break;

        case "event":
            String[] eventSplit = userCmd.split(" /from ");
            String[] eventSplit2 = userCmd.split(" /to ");
            String[] eventSplitCheck = eventSplit[1].split(" /to ");
            if (eventSplit.length <= 1
                    || eventSplit2.length <= 1
                    || eventSplit[0].length() == splitCmd[0].length()
                    || eventSplitCheck.length == 1) {
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
                throw new DateTimeException("Billy does not understand the date format..."
                        + "\nPlease use dd-MM-yyyy HHmm format...");
            } else if (eventParsedFrom.isAfter(eventParsedTo)) {
                throw new DateTimeException("Please ensure that the start date is before the end date...");
            }

            command = new EventCommand(new Event(eventDescription, eventParsedFrom, eventParsedTo));
            break;

        case "delete":
            if (splitCmd.length == 1) {
                throw new BillyFieldErrorException("delete");
            } else if (Integer.parseInt(splitCmd[1]) > tasksList.getSize()) {
                throw new BillyUnkownTaskNumException(splitCmd[1]);
            }

            command = new DeleteCommand(Integer.parseInt(splitCmd[1]) - 1,
                    tasksList.getTask(Integer.parseInt(splitCmd[1]) - 1));
            break;

        default:
            throw new BillyUnknownException();
        }
        return command;
    }

    public static LocalDateTime dateParsing(String date) throws DateTimeException {
        LocalDateTime parsedDate = null;
        if (DATETIME_PATTERN.matcher(date).matches()) {
            parsedDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
        }
        return parsedDate;
    }
}
