import constants.DesignConstants;
import java.util.Scanner;

public class Billy {
    private static String[] list = new String[100];  
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

            Billy.parseCommand(userCmd);
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

    private static void parseCommand(String userCmd) {

        switch (userCmd) {
            case "list":
                Billy.list();
                break;
            default:
                list[counter] = userCmd;
                counter++;
                System.out.println("\nAdded to the list: " + userCmd + "\n");
                System.out.println(DesignConstants.HORIZONTALLINE_STRING);
        }
    }

    private static void list() {
        System.out.println("\nHere are the items in your list:");
        for (int i = 0; i < counter; i++) {
            System.out.println((i + 1) + ". " + list[i]);
        }
        System.out.println();
        System.out.println(DesignConstants.HORIZONTALLINE_STRING);
    }
}
