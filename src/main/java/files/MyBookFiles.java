package files;

import library.Book;
import utils.ArraysHelper;

import java.io.*;

public class MyBookFiles extends Files {
    protected static final String booksPath = path + "\\books.txt";

    public static void readBooks() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(booksPath))) {
            while (true) {
                Book book = (Book) in.readObject();
                books.add(book);
            }
        } catch (FileNotFoundException | EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace()));
        } finally {
            if (books.size() != 0)
                Book.setStaticID(books.get(books.size() - 1).getID() + 1);
        }
    }

    public static String writeBooks(boolean saveOrRemove) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(booksPath))) {
            for (Book book : books) {
                out.writeObject(book);
            }
            if (saveOrRemove)
                return "Book added to system.";
            else
                return "Book was removed from the system.";
        } catch (IOException e) {
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
    }

    public static Book getBook(String name, String author) {
        Book chosenBook = null;
        for (Book book : books) {
            if (book.getName().equals(name) && book.getAuthor().equals(author)) {
                chosenBook = book;
                break;
            }
        }
        return chosenBook;
    }

    public static Book getBook(int ID) {
        Book chosenBook = null;
        for (Book book : books) {
            if (book.getID() == ID) {
                chosenBook = book;
                break;
            }
        }
        return chosenBook;
    }

    public static void showBooks() {
        if (books.size() == 0)
            System.out.println("No books was found.");
        else
            for (Book book : books)
                System.out.println(book);
    }

    public static boolean checkForBook(String name, String author) {
        for (Book book : books)
            if (book.getName().equals(name) && book.getAuthor().equals(author))
                return true;
        return false;
    }
}
