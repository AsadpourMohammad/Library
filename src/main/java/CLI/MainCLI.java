package CLI;

import java.util.Scanner;

public class MainCLI {

    public static void newLine() {
        System.out.println("------------------------------------------");
    }

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
                    ManagerCLI.managersMenu();
                case "2":
                    newLine();
                    MemberCLI.MembersMenu();
                case "3":
                    System.out.println("EXITING LIBRARY...");
                    System.exit(0);
                case "":
                    continue;
                default:
                    System.out.println("Command was not understood.");
            }
        }
    }
}
