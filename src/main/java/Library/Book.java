package Library;

import File.Files;
import Interface.Operations;
import java.io.Serializable;

public class Book implements Operations, Serializable {

    static int staticID = 1;

    protected String name;
    protected String author;
    protected int ID;
    protected boolean borrowed;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
        this.ID = staticID;
        staticID++;
    }

    public static void setStaticID(int staticID) {
        Book.staticID = staticID;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getID() {
        return ID;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    @Override
    public String toString() {
        return String.format("%s. %s, by %s", ID, name, author);
    }

    @Override
    public String save() {
        return Files.save(this);
    }

    @Override
    public String remove() {
        staticID--;
        return Files.remove(this);
    }
}
