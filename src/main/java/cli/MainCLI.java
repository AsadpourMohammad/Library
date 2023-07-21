package cli;

import org.apache.commons.lang3.math.NumberUtils;
import java.util.Scanner;
import static utils.ConsoleColors.*;

public class MainCLI {

    public static void startMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("""
                Welcome to our library.
                Please select your desired option from the Start Menu:
                
                1. Manager
                2. Member
                3. Exit
                
                """);
        String input;

        while (true) {
            System.out.print(">");

            input = scanner.nextLine();

            switch (input) {
                case "1":
                    newLine();
                    ManagersCLI.managersMenu();
                case "2":
                    newLine();
                    MembersCLI.MembersMenu();
                case "3":
                    newLine();
                    System.out.println("EXITING LIBRARY...");
                    System.exit(0);
                case "":
                    continue;
                default:
                    System.out.println("Command was not understood.");
            }
        }
    }

    public static void newLine() {
        System.out.println(CYAN_BOLD_BRIGHT + "------------------------------------------" + RESET);
    }

    public static String checkBeforeContinue(String input) {
        if (input.equals("0"))
            return "";
        else if (!NumberUtils.isCreatable(input))
            return "Please enter a valid number.";
        else
            return null;
    }
}
