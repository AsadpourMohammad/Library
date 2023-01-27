package File;

import Library.Book;
import zOther.ArraysHelper;
import java.io.*;

public class MyBookFiles extends Files {
    protected static final String booksPath = path + "\\books.txt";

    public static void readBooks(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(booksPath));)
        {
            while (true) {
                Book book = (Book) in.readObject();
                books.add(book);
            }
        }
        catch (EOFException ignored) {}
        catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        finally {
            if(books.size() != 0) {
                Book.setStaticID(books.get(books.size() - 1).getID() + 1);
            }
        }
    }

    public static String writeBooks(boolean saveOrRemove) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(booksPath))){
            for(Book book : books){
                out.writeObject(book);
            }
            if (saveOrRemove)
                return "Book added to system.";
            else
                return "Book was removed from the system.";
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return ArraysHelper.toString(ex.getStackTrace());
        }
    }

    public static Book getBook(String name, String author) {
        Book chosenBook = null;
        for (Book book : books){
            if(book.getName().equals(name) && book.getAuthor().equals(author)){
                chosenBook = book;
                break;
            }
        }
        return chosenBook;
    }

    public static Book getBook(int ID) {
        Book chosenBook = null;
        for (Book book : books){
            if(book.getID() == ID){
                chosenBook = book;
                break;
            }
        }
        return chosenBook;
    }

    public static void showBooks() {
        if (books.size() == 0)
            System.out.println("No member was found.");
        else
            for(Book book : books)
                System.out.println(book);
    }
}
