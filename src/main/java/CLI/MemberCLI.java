package CLI;

import File.MyBookFiles;
import File.MyMemberFiles;
import File.MyRentFiles;
import Library.Rent;
import Person.Member;
import org.apache.commons.lang3.math.NumberUtils;
import zOther.ArraysHelper;

import java.util.Scanner;

public class MemberCLI extends MainCLI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void MembersMenu() {

        System.out.print("""
                "Members Menu"
                Please select your desired option:
                
                1. Old Member
                2. New Member
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
                    System.out.print("Enter your Member ID: ");
                    int ID = scanner.nextInt();
                    Member member = MyMemberFiles.getMember(ID);
                    newLine();

                    if (member != null)
                        MemberMenu(member);
                    else
                        System.out.println("No such member was found.");
                    newLine();

                    MembersMenu();
                    break outer;
                case "2":
                    System.out.println(addMember());
                    newLine();
                    MembersMenu();
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

    public static void MemberMenu(Member member) {
        System.out.printf("""
                "Member Menu for %s"
                Please select your desired option:
                
                1. Books & Renting
                2. Remove this member
                3. return to Members Menu
                
                """, member.getName());
        String input;

        outer:
        while (true) {
            System.out.print(">");

            input = scanner.next();

            switch (input) {
                case "1":
                    newLine();
                    booksMenu(member);
                    break;
                case "2":
                    newLine();
                    System.out.println(removeMember());
                    newLine();
                    MembersMenu();
                    break;
                case "3":
                    newLine();
                    MemberCLI.MembersMenu();
                    break outer;
                case "":
                    continue;
                default:
                    System.out.println("Command was not understood.");
            }
        }
    }

    public static void booksMenu(Member member) {
        System.out.print("""
                "Books Menu"
                Please select your desired option:
                
                1. View all Books
                2. Rent a Book
                3. Return a Book
                4. View Rented Books
                5. return to Member Menu
                
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
                    booksMenu(member);
                    break;
                case "2":
                    newLine();
                    System.out.println(addRent(member));
                    newLine();
                    booksMenu(member);
                    break outer;
                case "3":
                    newLine();
                    System.out.println(removeRent());
                    newLine();
                    booksMenu(member);
                    break outer;
                case "4":
                    newLine();
                    MyRentFiles.showRents(member.getID());
                    newLine();
                    booksMenu(member);
                    break;
                case "5":
                    newLine();
                    MemberMenu(member);
                    break outer;
                case "":
                    continue;
                default:
                    System.out.println("Command was not understood.");
            }
        }
    }

    public static String addMember() {
        Scanner inputName = new Scanner(System.in);
        Scanner inputNum = new Scanner(System.in);

        try {
            System.out.print("Enter your name: ");
            String name = inputName.nextLine();
            System.out.print("Enter your nationalCode: ");
            int nationalCode = inputNum.nextInt();

            Member Member = new Member(name, nationalCode);

            newLine();
            System.out.printf("Your Member ID is '%s'. Please memorise it.%n",Member.getID());
            return Member.save();
        }
        catch (Exception e){
            return ArraysHelper.toString(e.getStackTrace());
        }
    }

    public static String removeMember() {
        try {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter your Member ID: ");
            String ID = input.next();
            newLine();

            if (NumberUtils.isCreatable(ID)) {
                Member member = MyMemberFiles.getMember(Integer.parseInt(ID));
                if (member != null) {
                    System.out.printf("The member '%s', with the national code of '%s' and ID of '%s', " +
                            "was removed from the system. It's rented books were removed as well.%n", member.getName(), member.getNationalCode(), member.getID());
                    return member.remove();
                } else
                    return "No such member was found.";
            } else
                return "Please enter a valid ID.";
        }
        catch(Exception e) {
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
    }

    public static String addRent(Member member) {
        try {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter the book's ID ('0' to return): ");
            String ID = input.nextLine();

            if (ID.equals("0"))
                return "";

            if (MyBookFiles.getBook(Integer.parseInt(ID)).isBorrowed())
                System.out.println("This Book is already rented from the library.");
            else {
                Rent rent = new Rent(member, MyBookFiles.getBook(Integer.parseInt(ID)));

                newLine();
                System.out.printf("Your book, '%s', written by '%s', was rented from the library in '%s'.%n" +
                                "Your rent ID is '%s'. Please memorise it.%n",
                        rent.getBook().getName(), rent.getBook().getAuthor(), rent.getDate(), rent.getID());
                return rent.save();
            }
        }
        catch(Exception e) {
            return ArraysHelper.toString(e.getStackTrace());
        }
        return null;
    }

    public static String removeRent() {
        try {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter your rent's ID: ");
            String ID = input.nextLine();

            Rent rent = MyRentFiles.getRent(Integer.parseInt(ID));

            newLine();
            System.out.printf("Your book, '%s', written by '%s', was returned to the library.%n",
                    rent.getBook().getName(), rent.getBook().getAuthor());
            return rent.remove();
        }
        catch(Exception e) {
            return ArraysHelper.toString(e.getStackTrace());
        }
    }

}
