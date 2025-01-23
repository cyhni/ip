import constants.DesignConstants;
import java.util.Scanner;

public class Billy {
    private static Task[] tasklist = new Task[100];  
    private static int counter = 0;
    public static void main(String[] args) {
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
                tasklist[Integer.parseInt(splitCmd[1]) - 1].markAsDone();

                System.out.println("\nMarked as done:\n" 
                    + (Integer.parseInt(splitCmd[1])) + ". " + tasklist[Integer.parseInt(splitCmd[1]) - 1] + "\n");
                System.out.println(DesignConstants.HORIZONTALLINE_STRING);
                break;
            case "unmark":
                if (splitCmd.length == 1) {
                    throw new BillyFieldErrorException("mark");
                } else if (Integer.parseInt(splitCmd[1]) > counter) {
                    throw new BillyUnkownTaskNumException(splitCmd[1]);
                }
                tasklist[Integer.parseInt(splitCmd[1]) - 1].markAsUndone();

                System.out.println("\nMarked as undone:\n" 
                    + (Integer.parseInt(splitCmd[1])) + ". " + tasklist[Integer.parseInt(splitCmd[1]) - 1] + "\n");
                System.out.println(DesignConstants.HORIZONTALLINE_STRING);
                break;
            case "todo":
                if (splitCmd.length == 1) {
                    throw new BillyFieldErrorException("todo");
                }
                tasklist[counter] = new Todo(userCmd);
                counter++;

                taskAdderPrinter(tasklist[counter - 1]);
                break;
            case "deadline":
                String[] deadlineSplit = userCmd.split(" /by ");
                if (deadlineSplit.length <= 1) {
                    throw new BillyFieldErrorException("deadline");
                }
                String deadlineDescription = deadlineSplit[0].substring(splitCmd[0].length() + 1);
                String deadlineDate = deadlineSplit[1];

                if (deadlineDescription.equals("") || deadlineDate.equals("")) {
                    throw new BillyFieldErrorException("deadline");
                }

                tasklist[counter] = new Deadline(deadlineDescription, deadlineDate);
                counter++;

                taskAdderPrinter(tasklist[counter - 1]);
                break;
            case "event":
                String[] eventSplit = userCmd.split(" /from ");
                String[] eventSplit2 = userCmd.split(" /to ");
                if (eventSplit.length <= 1 || eventSplit2.length <= 1) {
                    throw new BillyFieldErrorException("event");
                }

                String eventDescription = eventSplit[0].substring(splitCmd[0].length() + 1);
                String eventFrom = eventSplit[1].substring(0, eventSplit[1].length() - eventSplit2[1].length() - 5);
                String eventTo = eventSplit2[1];

                if (eventDescription.equals("") || eventFrom.equals("") || eventTo.equals("")) {
                    throw new BillyFieldErrorException("event");
                }

                tasklist[counter] = new Event(eventDescription, eventFrom, eventTo);
                counter++;

                taskAdderPrinter(tasklist[counter - 1]);
                break;
            default:
                throw new BillyUnknownException();
        }
    }

    private static void list() {
        System.out.println("\nHere are the items in your list:");
        for (int i = 0; i < counter; i++) {
            System.out.println((i + 1) + ". " + tasklist[i]);
        }
        System.out.println();
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
    }

    private static void taskAdderPrinter(Task task) {
        System.out.println("\nAdded to the list:\n" + counter + ". " + task + "\n");
        System.out.println("There are currently " + counter + " tasks in the list.\n");
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
    }
}
