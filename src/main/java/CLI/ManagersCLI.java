package cli;

import files.MyBookFiles;
import files.MyManagerFiles;
import files.MyMemberFiles;
import files.MyRentFiles;
import library.Book;
import person.Manager;
import org.apache.commons.lang3.math.NumberUtils;
import utils.ArraysHelper;

import java.util.Objects;
import java.util.Scanner;

public class ManagersCLI extends MainCLI {
    private static final Scanner scanner = new Scanner(System.in);

    private static Manager manager = null;

    public static void managersMenu() {

        System.out.print("""
                "Managers Menu"
                Please select your desired menu:
                                
                1. Old manager
                2. New Manager
                3. return to Start Menu
                                
                """);

        String input;

        outer:
        while (true) {
            System.out.print(">");

            input = scanner.nextLine();

            switch (input) {
                case "1":
                    newLine();
                    System.out.print("Enter your Manager ID ('0' to return): ");
                    String ID = scanner.nextLine();

                    String check = checkBeforeContinue(ID);
                    if (check != null) {
                        if (check.equals(""))
                            System.out.print(check);
                        else
                            System.out.println(check);
                        newLine();
                        managersMenu();
                    } else {
                        manager = MyManagerFiles.getManager(Integer.parseInt(ID));
                        newLine();
                    }

                    if (manager != null)
                        managerMenu(manager);
                    else
                        System.out.println("No such manager was found.");
                    newLine();

                    managersMenu();
                    break outer;
                case "2":
                    newLine();
                    System.out.println(addManager());
                    newLine();
                    managersMenu();
                    break outer;
                case "3":
                    newLine();
                    MainCLI.startMenu();
                    break outer;
                case "":
                    continue;
                default:
                    System.out.println("Command was not understood.");
            }
        }
    }

    public static void managerMenu(Manager manager) {
        System.out.printf("""
                "Manager Menu for %s"
                Please select your desired menu:

                1. Books Menu
                2. View all rents
                3. View all Members
                4. View all Managers (Only accessible to the Main Manager)
                5. Remove a Manager (Only accessible to the Main Manager)
                6. return to Managers Menu
                                
                """, manager.getName());
        String input;

        outer:
        while (true) {
            System.out.print(">");

            input = scanner.nextLine();

            switch (input) {
                case "1":
                    newLine();
                    booksMenu();
                    break;
                case "2":
                    newLine();
                    MyRentFiles.showRents(0);
                    newLine();
                    managerMenu(manager);
                    break;
                case "3":
                    newLine();
                    MyMemberFiles.showMembers();
                    newLine();
                    managerMenu(manager);
                    break;
                case "4":
                    newLine();
                    if (manager.getID() == 1)
                        MyManagerFiles.showManagers();
                    else
                        System.out.println("This list is only accessible to the Main Manager.");
                    newLine();
                    managerMenu(manager);
                    break;
                case "5":
                    newLine();
                    if (manager.getID() == 1)
                        System.out.println(removeManager());
                    else
                        System.out.println("This list is only accessible to the Main Manager.");
                    //  The main manager is the manager with the ID of '1'
                    newLine();
                    managersMenu();
                    break;
                case "6":
                    newLine();
                    ManagersCLI.managersMenu();
                    break outer;
                case "":
                    continue;
                default:
                    System.out.println("Command was not understood.");
            }
        }
    }

    public static void booksMenu() {
        System.out.print("""
                "Books Menu"
                Please select your desired menu:
                                
                1. View all Books
                2. Add a new Book
                3. Remove a book
                4. return to Manager Menu
                                
                """);
        String input;

        outer:
        while (true) {
            System.out.print(">");

            input = scanner.nextLine();

            switch (input) {
                case "1":
                    newLine();
                    MyBookFiles.showBooks();
                    newLine();
                    booksMenu();
                    break;
                case "2":
                    newLine();
                    System.out.println(addBook());
                    newLine();
                    booksMenu();
                    break outer;
                case "3":
                    newLine();
                    System.out.println(removeBook());
                    newLine();
                    booksMenu();
                    break;
                case "4":
                    newLine();
                    managerMenu(manager);
                    break outer;
                case "":
                    continue;
                default:
                    System.out.println("Command was not understood.");
            }
        }
    }

    public static String addManager() {
        try {
            System.out.print("Enter your name ('0' to return): ");
            String name = scanner.nextLine();

            if (Objects.equals(name, "0"))
                return "";

            System.out.print("Enter your nationalCode ('0' to return): ");
            String nationalCode = scanner.nextLine();

            String check = checkBeforeContinue(nationalCode);
            if (check != null)
                return check;

            if (MyManagerFiles.checkForNationalCode(Long.parseLong(nationalCode)))
                return "A manager already exists for this national code.";

            Manager manager = new Manager(name, Long.parseLong(nationalCode));

            newLine();
            System.out.printf("Your manager ID is '%s'. Please memorise it.%n", manager.getID());
            return manager.save();
        } catch (Exception e) {
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
    }

    public static String removeManager() {
        try {
            System.out.print("Enter the manager's ID ('0' to return): ");
            String ID = scanner.nextLine();

            String check = checkBeforeContinue(ID);
            if (check != null)
                return check;

            newLine();

            if (NumberUtils.isCreatable(ID)) {
                Manager manager = MyManagerFiles.getManager(Integer.parseInt(ID));
                if (manager != null) {
                    System.out.printf("The manager '%s', with the national code of '%s' and ID of '%s', " +
                            "was removed from the system.%n", manager.getName(), manager.getNationalCode(), manager.getID());
                    return manager.remove();
                } else
                    return "No such manager was found.";
            } else
                return "Please enter a valid ID.";
        } catch (Exception e) {
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
    }

    public static String addBook() {
        try {
            System.out.print("Enter the book's name ('0' to return): ");
            String name = scanner.nextLine();

            if (Objects.equals(name, "0"))
                return "";

            System.out.print("Enter the book's author ('0' to return): ");
            String author = scanner.nextLine();

            if (Objects.equals(name, "0"))
                return "";

            newLine();

            if (MyBookFiles.checkForBook(name, author))
                return "This book already exists in the library.";

            Book book = new Book(name, author);

            System.out.printf("Your book, '%s', written by '%s', was added to library.%n", book.getName(), book.getAuthor());
            return book.save();
        } catch (Exception e) {
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
    }

    public static String removeBook() {
        try {
            System.out.print("Enter the book's name ('0' to return): ");
            String name = scanner.nextLine();

            if (Objects.equals(name, "0"))
                return "";

            System.out.print("Enter the book's author ('0' to return): ");
            String author = scanner.nextLine();

            if (Objects.equals(author, "0"))
                return "";

            newLine();

            Book book = MyBookFiles.getBook(name, author);

            if (book != null) {
                System.out.printf("Your book, '%s', written by '%s', was removed from the library.%n", name, author);
                return book.remove();
            } else
                return "No such book was found.";
        } catch (Exception e) {
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
    }

}
