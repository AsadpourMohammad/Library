package files;

import library.Book;
import library.Rent;
import person.Manager;
import person.Member;

import java.io.File;
import java.util.ArrayList;

public abstract class Files {
    protected static final String path = System.getProperty("user.dir") + "\\LibraryFiles";

    protected static ArrayList<Manager> managers = new ArrayList<>();
    protected static ArrayList<Member> members = new ArrayList<>();
    protected static ArrayList<Book> books = new ArrayList<>();
    protected static ArrayList<Rent> rents = new ArrayList<>();

    public static void readFiles() {
        new File(path).mkdir();

        MyManagerFiles.readManagers();
        MyMemberFiles.readMembers();
        MyBookFiles.readBooks();
        MyRentFiles.readRents();
    }

    public static String save(Manager manager) {
        managers.add(manager);
        return MyManagerFiles.writeManagers(true);
    }

    public static String remove(Manager manager) {
        managers.remove(manager);
        return MyManagerFiles.writeManagers(false);
    }

    public static String save(Member member) {
        members.add(member);
        return MyMemberFiles.writeMembers(true);
    }

    public static String remove(Member member) {
        ArrayList<Rent> toBeRemoved = new ArrayList<>();
        for (Rent rent : rents)
            if (rent.getMember().equals(member))
                toBeRemoved.add(rent);

        for (Rent rent : toBeRemoved)
            rent.remove();

        members.remove(member);
        return MyMemberFiles.writeMembers(false);
    }

    public static String save(Book book) {
        books.add(book);
        return MyBookFiles.writeBooks(true);
    }

    public static String remove(Book book) {
        ArrayList<Rent> toBeRemoved = new ArrayList<>();
        for (Rent rent : rents)
            if (rent.getBook().equals(book))
                toBeRemoved.add(rent);

        for (Rent rent : toBeRemoved)
            rent.remove();

        books.remove(book);
        return MyBookFiles.writeBooks(false);
    }

    public static String save(Rent rent) {
        rents.add(rent);
        return MyRentFiles.writeRents(true);
    }

    public static String remove(Rent rent) {
        rents.remove(rent);
        return MyRentFiles.writeRents(false);
    }
}
