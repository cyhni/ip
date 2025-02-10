package billy.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

import billy.command.Command;
import billy.command.DeleteCommand;
import billy.command.EventCommand;
import billy.command.ListCommand;
import billy.command.MarkCommand;
import billy.command.TodoCommand;
import billy.exceptions.BillyException;
import billy.tasks.TasksList;
import billy.ui.Ui;

public class ParserTest {
    @Test
    public void parseCommand_listInput_returnsListCommand()
            throws BillyException,
            DateTimeException,
            IOException {
        String input = "list";
        Command c = Parser.parseCommand(input, new TasksList(), new Ui());
        assertTrue(c instanceof ListCommand);
    }

    @Test
    public void parseCommand_markInputWithoutTask_exceptionThrown()
            throws BillyException,
            DateTimeException,
            IOException {
        String input = "mark 1";
        try {
            Parser.parseCommand(input, new TasksList(), new Ui());
        } catch (BillyException e) {
            assertEquals("Billy does not have task number 1...", e.getMessage());
        }
    }

    @Test
    public void parseCommand_markInvalidInput_exceptionThrown()
            throws BillyException,
            DateTimeException,
            IOException {
        String input = "mark";
        try {
            Parser.parseCommand(input, new TasksList(), new Ui());
        } catch (BillyException e) {
            assertEquals("Incorrect fields for " + input + " function...\n\nBilly is confused!!!",
                    e.getMessage());
        }
    }

    @Test
    public void parseCommand_markInputWithTask_returnsMarkCommand()
            throws BillyException,
            DateTimeException,
            IOException {
        TasksList tasksList = new TasksList();
        Ui ui = new Ui();
        String input = "todo read book";
        Command c = Parser.parseCommand(input, tasksList, ui);
        c.execute(tasksList, ui);
        input = "mark 1";
        c = Parser.parseCommand(input, tasksList, ui);
        assertTrue(c instanceof MarkCommand);
    }

    @Test
    public void parseCommand_todoInput_returnsTodoCommand()
            throws BillyException,
            DateTimeException,
            IOException {
        String input = "todo read book";
        Command c = Parser.parseCommand(input, new TasksList(), new Ui());
        assertTrue(c instanceof TodoCommand);
    }

    @Test
    public void parseCommand_eventInput_returnsEventCommand()
            throws BillyException,
            DateTimeException,
            IOException {
        String input = "event read book /from 01-01-2021 0000 /to 01-01-2021 0100";
        Command c = Parser.parseCommand(input, new TasksList(), new Ui());
        assertTrue(c instanceof EventCommand);
    }

    @Test
    public void parseCommand_eventInvalidDateTimeFormat_throwsException()
            throws BillyException,
            DateTimeException,
            IOException {
        String input = "event read book /from 01-01-2021 0000 /to 01-01-2021 010";
        try {
            Parser.parseCommand(input, new TasksList(), new Ui());
        } catch (DateTimeException e) {
            assertEquals("Billy does not understand the date format..."
                    + "\nPlease use dd-MM-yyyy HHmm format...",
                    e.getMessage());
        }
    }

    @Test
    public void parseCommand_eventInvalidStartAndEnd_throwsException()
            throws BillyException,
            DateTimeException,
            IOException {
        String input = "event read book /from 01-01-2021 0000 /to 01-01-2020 0000";
        try {
            Parser.parseCommand(input, new TasksList(), new Ui());
        } catch (DateTimeException e) {
            assertEquals("Please ensure that the start date is before the end date...",
                    e.getMessage());
        }
    }

    @Test
    public void parseCommand_deleteInputWithTask_returnsDeleteCommand()
            throws BillyException,
            DateTimeException,
            IOException {
        TasksList tasksList = new TasksList();
        Ui ui = new Ui();
        String input = "todo read book";
        Command c = Parser.parseCommand(input, tasksList, ui);
        c.execute(tasksList, ui);
        input = "delete 1";
        c = Parser.parseCommand(input, tasksList, ui);
        assertTrue(c instanceof DeleteCommand);
    }
}
