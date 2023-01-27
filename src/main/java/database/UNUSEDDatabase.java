package database;

import library.Book;
import library.Rent;
import person.Manager;
import person.Member;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

//  Unfortunately, I was unable to successfully implement the database.

public class UNUSEDDatabase {
    public static final String url = "jdbc:postgresql://localhost/Library";
    public static final String user = "postgres";
    public static final String password = "--------";

    protected static ArrayList<Manager> managers = new ArrayList<>();
    protected static ArrayList<Member> members = new ArrayList<>();
    protected static ArrayList<Book> books = new ArrayList<>();
    protected static ArrayList<Rent> rents = new ArrayList<>();

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the server.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void loadDatabase() {
        managerLoad();
        memberLoad();
        bookLoad();
        rentLoad();
    }

    public static void save(Manager manager) {
        managers.add(manager);

        String add = "INSERT INTO manager(ID,name,nationalCode)"
                + "VALUES(?,?,?)";

        try (Connection cnt = connect(); PreparedStatement pst = cnt.prepareStatement(add)) {
            pst.setInt(1,manager.getID());
            pst.setString(2,manager.getName());
            pst.setLong(3,manager.getNationalCode());
            pst.executeUpdate();
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void managerLoad() {
        Manager manager;
        int managerID;
        String managerName;
        long managerNationalCode;

        String select = "SELECT * FROM manager";

        try (Connection cnt = connect(); PreparedStatement pst = cnt.prepareStatement(select)) {
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                managerID = resultSet.getInt(1);
                managerName = resultSet.getString(2);
                managerNationalCode = resultSet.getLong(3);
                Manager.setStaticID(managerID);

                manager = new Manager(managerName,managerNationalCode);
                managers.add(manager);
            }
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void remove(Manager manager) {
        String delete = "DELETE FROM manager WHERE ID = ?";

        try (Connection cnt = connect(); PreparedStatement pst = cnt.prepareStatement(delete)) {
            pst.setInt(1, manager.getID());
            pst.executeUpdate();
            managers.remove(manager);
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void save(Member member) {
        members.add(member);

        String add = "INSERT INTO member(ID,name,nationalCode)"
                + "VALUES(?,?,?)";

        try (Connection cnt = connect(); PreparedStatement pst = cnt.prepareStatement(add)) {
            pst.setInt(1,member.getID());
            pst.setString(2,member.getName());
            pst.setLong(3,member.getNationalCode());
            pst.executeUpdate();
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void memberLoad() {
        Member member;
        int memberID;
        String memberName;
        long memberNationalCode;

        String select = "SELECT * FROM member";

        try (Connection cnt = connect(); PreparedStatement pst = cnt.prepareStatement(select)) {
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                memberID = resultSet.getInt(1);
                memberName = resultSet.getString(2);
                memberNationalCode = resultSet.getLong(3);
                Member.setStaticID(memberID);

                member = new Member(memberName, memberNationalCode);
                members.add(member);
            }
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void remove(Member member) {
        ArrayList<Rent> toBeRemoved = new ArrayList<>();
        for (Rent rent : rents)
            if (rent.getMember().equals(member))
                toBeRemoved.add(rent);

        for (Rent rent : toBeRemoved)
            rent.remove();

        String delete = "DELETE FROM member WHERE ID = ?";

        try (Connection cnt = connect(); PreparedStatement pst = cnt.prepareStatement(delete)) {
            pst.setInt(1, member.getID());
            pst.executeUpdate();
            members.remove(member);
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void save(Book book) {
        books.add(book);

        String add = "INSERT INTO book(ID,name,author,borrowed)"
                + "VALUES(?,?,?,?)";

        try (Connection cnt = connect(); PreparedStatement pst = cnt.prepareStatement(add)) {
            pst.setInt(1, book.getID());
            pst.setString(2, book.getName());
            pst.setString(3, book.getAuthor());
            pst.setBoolean(4, book.isBorrowed());
            pst.executeUpdate();
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void bookLoad() {
        Book book;
        int bookID;
        String bookName;
        String bookAuthor;
        boolean bookBorrowed;

        String select = "SELECT * FROM book";

        try (Connection cnt = connect(); PreparedStatement pst = cnt.prepareStatement(select)) {
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                bookID = resultSet.getInt(1);
                bookName = resultSet.getString(2);
                bookAuthor = resultSet.getString(3);
                bookBorrowed = resultSet.getBoolean(4);
                Book.setStaticID(bookID);

                book = new Book(bookName, bookAuthor);
                book.setBorrowed(bookBorrowed);

                books.add(book);
            }
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void remove(Book book) {
        ArrayList<Rent> toBeRemoved = new ArrayList<>();
        for (Rent rent : rents)
            if (rent.getBook().equals(book))
                toBeRemoved.add(rent);

        for (Rent rent : toBeRemoved)
            rent.remove();

        String delete = "DELETE FROM book WHERE ID = ?";

        try (Connection cnt = connect(); PreparedStatement pst = cnt.prepareStatement(delete)) {
            pst.setInt(1, book.getID());
            pst.executeUpdate();
            books.remove(book);
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void save(Rent rent) {
        rents.add(rent);

        String add = "INSERT INTO rent(book,member,date)"
                + "VALUES(?,?,?)";

        try (Connection cnt = connect(); PreparedStatement pst = cnt.prepareStatement(add)) {
            pst.setObject(1, rent.getBook());
            pst.setObject(2, rent.getMember());
            pst.setObject(3, rent.getDate());
            pst.executeUpdate();
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void rentLoad() {
        Rent rent;
        Book rentedBook;
        Member rentingMember;
        Date rentDate;

        String select = "SELECT * FROM rent";

        try (Connection cnt = connect(); PreparedStatement pst = cnt.prepareStatement(select)) {
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                rentedBook = (Book) resultSet.getObject(1);
                rentingMember = (Member) resultSet.getObject(2);
                rentDate = resultSet.getDate(3);

                rent = new Rent(rentingMember, rentedBook);
                rent.setDate(rentDate);

                rents.add(rent);
            }
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void remove(Rent rent) {
        String delete = "DELETE FROM rent WHERE book = ?";

        try (Connection cnt = connect(); PreparedStatement pst = cnt.prepareStatement(delete)) {
            pst.setObject(1, rent.getBook());
            pst.executeUpdate();
            rents.remove(rent);
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(PreparedStatement.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
