package CLI;

import File.MyBookFiles;
import File.MyManagerFiles;
import File.MyMemberFiles;
import File.MyRentFiles;
import Library.Book;
import Person.Manager;
import Person.Member;
import org.apache.commons.lang3.math.NumberUtils;
import zOther.ArraysHelper;

import java.util.Scanner;

public class ManagerCLI extends MainCLI {
    private static final Scanner scanner = new Scanner(System.in);

    private static Manager manager;

    public static void managersMenu() {

        System.out.print("""
                "Managers Menu"
                Please select your desired option:
                
                1. Old Manager
                2. New Manager
                3. return to Start Menu
                
                """);

        String input;

        outer:
        while (true) {
            System.out.print(">");

            input = scanner.next();

            switch (input) {
                case "1":
                    newLine();
                    System.out.print("Enter your Manager ID: ");
                    int ID = scanner.nextInt();
                    manager = MyManagerFiles.getManager(ID);
                    newLine();

                    if (manager != null)
                        managerMenu(manager);
                    else
                        System.out.println("No such manager was found.");
                    newLine();

                    managersMenu();
                    break outer;
                case "2":
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
                Please select your desired option:

                1. Books
                2. Rents
                3. View all Members
                4. View all Managers (Only accessible to the Main Manager)
                5. Remove a Manager (Only accessible to the Main Manager)
                6. return to Managers Menu
                
                """, manager.getName());
        String input;

        outer:
        while (true) {
            System.out.print(">");

            input = scanner.next();

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
                    newLine();
                    managersMenu();
                    break;
                case "6":
                    newLine();
                    ManagerCLI.managersMenu();
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
                Please select your desired option:
                
                1. View all Books
                2. Add a new Book
                3. Remove a book
                4. return to Manager Menu
                
                """);
        String input;

        outer:
        while (true) {
            System.out.print(">");

            input = scanner.next();

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
        Scanner inputName = new Scanner(System.in);
        Scanner inputNum = new Scanner(System.in);

        try {
            System.out.print("Enter your name: ");
            String name = inputName.nextLine();
            System.out.print("Enter your nationalCode: ");
            int nationalCode = inputNum.nextInt();

            Manager manager = new Manager(name, nationalCode);

            newLine();
            System.out.printf("Your manager ID is '%s'. Please memorise it.%n",manager.getID());
            return manager.save();
        }
        catch (Exception e){
            return ArraysHelper.toString(e.getStackTrace());
        }
    }

    public static String removeManager() {
        try {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter the manager's ID: ");
            String ID = input.next();
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
        }
        catch(Exception e) {
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
    }

    public static String addBook() {
        try {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter the book's name: ");
            String name = input.nextLine();
            System.out.print("Enter the book's author: ");
            String author = input.nextLine();

            Book book = new Book(name, author);

            newLine();
            System.out.printf("Your book, '%s', written by '%s', was added to library.%n", book.getName(), book.getAuthor());
            return book.save();
        }
        catch(Exception e) {
            return ArraysHelper.toString(e.getStackTrace());
        }
    }

    public static String removeBook() {
        try {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter the book's name: ");
            String name = input.nextLine();
            System.out.print("Enter the book's author: ");
            String author = input.nextLine();
            newLine();

            Book book = MyBookFiles.getBook(name, author);

            if (book != null) {
                System.out.printf("Your book, '%s', written by '%s', was removed from the library.%n", name, author);
                return book.remove();
            } else
                return "No such book was found.";
        }
        catch(Exception e) {
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
    }

}
