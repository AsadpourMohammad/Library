package cli;

import files.MyBookFiles;
import files.MyMemberFiles;
import files.MyRentFiles;
import library.Rent;
import person.Member;
import utils.ArraysHelper;

import java.util.Scanner;

public class MembersCLI extends MainCLI {

    private static final Scanner scanner = new Scanner(System.in);

    private static Member member;

    public static void MembersMenu() {

        System.out.print("""
                "Members Menu"
                Please select your desired menu:
                
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
                    System.out.print("Enter your Member ID ('0' to return): ");
                    String ID = scanner.next();

                    String check = checkBeforeContinue(ID);
                    if (check != null) {
                        if (check.equals(""))
                            System.out.print(check);
                        else
                            System.out.println(check);
                        newLine();
                        MembersMenu();
                    } else {
                        member = MyMemberFiles.getMember(Integer.parseInt(ID));
                        newLine();
                    }

                    if (member != null)
                        MemberMenu(member);
                    else
                        System.out.println("No such member was found.");
                    newLine();

                    MembersMenu();
                    break outer;
                case "2":
                    String answer = addMember();
                    if (answer != null && !answer.equals(""))
                        System.out.println(answer);
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
                Please select your desired menu:
                
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
                    String answer = removeMember();
                    if (answer != null && !answer.equals(""))
                        System.out.println(answer);
                    else {
                        newLine();
                        MemberMenu(member);
                    }
                    newLine();
                    MembersMenu();
                    break;
                case "3":
                    newLine();
                    MembersCLI.MembersMenu();
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
                Please select your desired menu:
                
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
                    String answer = addRent(member);
                    if (answer != null && !answer.equals(""))
                        System.out.println(answer);
                    newLine();
                    booksMenu(member);
                    break outer;
                case "3":
                    newLine();
                    answer = removeRent();
                    if (answer != null && !answer.equals(""))
                        System.out.println(answer);
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
        try {
            System.out.print("Enter your name ('0' to return): ");
            String name = scanner.nextLine();

            if (name.equals("0"))
                return "";

            System.out.print("Enter your nationalCode ('0' to return): ");
            String nationalCode = scanner.next();

            String check = checkBeforeContinue(nationalCode);
            if (check != null)
                return check;

            if (MyMemberFiles.checkForNationalCode(Long.parseLong(nationalCode)))
                return "A member already exists for this national code.";

            Member Member = new Member(name, Long.parseLong(nationalCode));

            newLine();
            System.out.printf("Your Member ID is '%s'. Please memorise it.%n",Member.getID());
            return Member.save();
        }
        catch (Exception e){
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
    }

    public static String removeMember() {
        try {
            System.out.print("Enter your Member ID ('0' to return): ");
            String ID = scanner.next();

            String check = checkBeforeContinue(ID);
            if (check != null)
                return check;

            newLine();

            Member member = MyMemberFiles.getMember(Integer.parseInt(ID));
            if (member != null) {
                System.out.printf("The member '%s', with the national code of '%s' and ID of '%s', " +
                        "was removed from the system. Its rented books were removed as well.%n", member.getName(), member.getNationalCode(), member.getID());
                return member.remove();
            } else
                return "No such member was found.";
        }
        catch(Exception e) {
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
    }

    public static String addRent(Member member) {
        try {
            System.out.print("Enter the book's ID ('0' to return): ");
            String ID = scanner.nextLine();

            String check = checkBeforeContinue(ID);
            if (check != null)
                return check;

            if (MyBookFiles.getBook(Integer.parseInt(ID)).isBorrowed())
                System.out.println("This Book is already rented from the library.");
            else {
                Rent rent = new Rent(member, MyBookFiles.getBook(Integer.parseInt(ID)));
                rent.getBook().setBorrowed(true);
                newLine();
                System.out.printf("Your book, '%s', written by '%s', was rented from the library in '%s'.%n",
                        rent.getBook().getName(), rent.getBook().getAuthor(), rent.getDate());
                return rent.save();
            }
        }
        catch(Exception e) {
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
        return null;
    }

    public static String removeRent() {
        try {
            System.out.print("Enter the rented book's ID ('0' to return): ");
            String ID = scanner.nextLine();

            String check = checkBeforeContinue(ID);
            if (check != null)
                return check;

            Rent rent = MyRentFiles.getRent(Integer.parseInt(ID));

            newLine();
            System.out.printf("The book '%s' written by '%s', was returned to the library.%n",
                    rent.getBook().getName(), rent.getBook().getAuthor());
            return rent.remove();
        }
        catch(Exception e) {
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
    }

}
