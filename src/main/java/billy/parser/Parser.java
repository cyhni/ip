package billy.parser;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import billy.command.Command;
import billy.command.DeadlineCommand;
import billy.command.DeleteCommand;
import billy.command.EventCommand;
import billy.command.FindCommand;
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

/**
 * The Parser class is responsible for interpreting user commands and converting them into executable Command objects.
 * It supports various commands such as list, mark, unmark, todo, deadline, event, and delete.
 */
public class Parser {
    private static final Pattern DATETIME_PATTERN = Pattern.compile("\\d{2}-\\d{2}\\-\\d{4} \\d{4}");

    /**
     * Parses the user command and returns the corresponding Command object.
     * The user command must be in the format "command [description] [date]".
     * The date must be in the format "dd-MM-yyyy HHmm".
     *
     * @param userCmd The user command to be parsed.
     * @param tasksList The list of tasks.
     * @return The Command object corresponding to the user command.
     * @throws BillyException If an error occurs during parsing.
     * @throws DateTimeException If an error occurs during date parsing.
     */
    public static Command parseCommand(String userCmd, TasksList tasksList)
            throws BillyException,
            DateTimeException {
        Command command;
        String[] commandStrings = userCmd.split(" ");

        switch (commandStrings[0]) {
        case "list":
        case "l":
            command = parseList();
            break;
        case "mark":
        case "m":
            command = parseMark(commandStrings, tasksList);
            break;
        case "unmark":
        case "um":
            command = parseUnmark(commandStrings, tasksList);
            break;
        case "todo":
        case "t":
            command = parseToDo(userCmd, commandStrings);
            break;
        case "deadline":
        case "d":
            command = parseDeadline(userCmd, commandStrings);
            break;
        case "event":
        case "e":
            command = parseEvent(userCmd, commandStrings);
            break;
        case "delete":
        case "del":
            command = parseDelete(commandStrings, tasksList);
            break;
        case "find":
        case "f":
            command = parseFind(commandStrings);
            break;
        default:
            throw new BillyUnknownException();
        }
        assert command != null : "Command should not be null";
        return command;
    }

    private static Command parseList() {
        return new ListCommand();
    }

    private static Command parseMark(String[] commandStrings, TasksList tasksList) throws BillyException {
        if (commandStrings.length == 1) {
            throw new BillyFieldErrorException("mark");
        } else if (Integer.parseInt(commandStrings[1]) > tasksList.getSize() || Integer.parseInt(commandStrings[1]) < 1) {
            throw new BillyUnkownTaskNumException(commandStrings[1]);
        }

        return new MarkCommand(Integer.parseInt(commandStrings[1]));
    }

    private static Command parseUnmark(String[] commandStrings, TasksList tasksList) throws BillyException {
        if (commandStrings.length == 1) {
            throw new BillyFieldErrorException("unmark");
        } else if (Integer.parseInt(commandStrings[1]) > tasksList.getSize() || Integer.parseInt(commandStrings[1]) < 1) {
            throw new BillyUnkownTaskNumException(commandStrings[1]);
        }

        return new UnmarkCommand(Integer.parseInt(commandStrings[1]));
    }

    private static Command parseToDo(String userCmd, String[] commandStrings) throws BillyException {
        if (commandStrings.length == 1) {
            throw new BillyFieldErrorException("todo");
        }

        return new TodoCommand(new Todo(userCmd.substring(commandStrings[0].length() + 1)));
    }

    private static Command parseDeadline(String userCmd, String[] commandStrings)
            throws BillyException,
            DateTimeException {
        String[] deadlineSplit = userCmd.split(" /by ");
        if (deadlineSplit.length <= 1 || deadlineSplit[0].length() == commandStrings[0].length()) {
            throw new BillyFieldErrorException("deadline");
        }
        String deadlineDescription = deadlineSplit[0].substring(commandStrings[0].length() + 1);
        String deadlineDate = deadlineSplit[1];
        if (deadlineDescription.equals("") || deadlineDate.equals("")) {
            throw new BillyFieldErrorException("deadline");
        }
        LocalDateTime deadlineParsedDate = parseDate(deadlineDate);
        if (deadlineParsedDate == null) {
            throw new DateTimeException("Billy does not understand the date format..."
                    + "\nPlease use dd-MM-yyyy HHmm format...");
        }

        return new DeadlineCommand(new Deadline(deadlineDescription, deadlineParsedDate));
    }

    private static Command parseEvent(String userCmd, String[] commandStrings)
            throws BillyException,
            DateTimeException {
        String[] eventSplit = userCmd.split(" /from ");
        String[] eventSplit2 = userCmd.split(" /to ");
        String[] eventSplitCheck = eventSplit[1].split(" /to ");
        if (eventSplit.length <= 1
                || eventSplit2.length <= 1
                || eventSplit[0].length() == commandStrings[0].length()
                || eventSplitCheck.length == 1) {
            throw new BillyFieldErrorException("event");
        }
        String eventDescription = eventSplit[0].substring(commandStrings[0].length() + 1);
        String eventFrom = eventSplit[1].substring(0, eventSplit[1].length() - eventSplit2[1].length() - 5);
        String eventTo = eventSplit2[1];
        if (eventDescription.equals("") || eventFrom.equals("") || eventTo.equals("")) {
            throw new BillyFieldErrorException("event");
        }
        LocalDateTime eventParsedFrom = parseDate(eventFrom);
        LocalDateTime eventParsedTo = parseDate(eventTo);
        if (eventParsedFrom == null || eventParsedTo == null) {
            throw new DateTimeException("Billy does not understand the date format..."
                    + "\nPlease use dd-MM-yyyy HHmm format...");
        } else if (eventParsedFrom.isAfter(eventParsedTo)) {
            throw new DateTimeException("Please ensure that the start date is before the end date...");
        }

        return new EventCommand(new Event(eventDescription, eventParsedFrom, eventParsedTo));
    }

    private static Command parseDelete(String[] commandStrings, TasksList tasksList) throws BillyException {
        if (commandStrings.length == 1) {
            throw new BillyFieldErrorException("delete");
        } else if (Integer.parseInt(commandStrings[1]) > tasksList.getSize() || Integer.parseInt(commandStrings[1]) < 1) {
            throw new BillyUnkownTaskNumException(commandStrings[1]);
        }

        return new DeleteCommand(Integer.parseInt(commandStrings[1]) - 1,
                tasksList.getTask(Integer.parseInt(commandStrings[1]) - 1));
    }

    private static Command parseFind(String[] commandStrings) throws BillyException {
        if (commandStrings.length == 1) {
            throw new BillyFieldErrorException("find");
        }

        return new FindCommand(commandStrings[1]);
    }

    /**
     * Parses the date string and returns the corresponding LocalDateTime object.
     * The date string must be in the format "dd-MM-yyyy HHmm".
     *
     * @param date The date string to be parsed.
     * @return The LocalDateTime object corresponding to the date string.
     * @throws DateTimeException If an error occurs during date parsing.
     */
    public static LocalDateTime parseDate(String date) throws DateTimeException {
        LocalDateTime parsedDate = null;
        if (DATETIME_PATTERN.matcher(date).matches()) {
            parsedDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
        }
        return parsedDate;
    }
}
