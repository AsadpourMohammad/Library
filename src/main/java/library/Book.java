package library;

import files.Files;
import interfaces.Operations;

import java.io.Serializable;

public class Book implements Operations, Serializable {
    private static int staticID = 1;

    private final String name;
    private final String author;
    private final int ID;
    private boolean borrowed = false;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
        this.ID = staticID;
        staticID++;
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

    public static void setStaticID(int staticID) {
        Book.staticID = staticID;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    @Override
    public String save() {
        return Files.save(this);
    }

    @Override
    public String remove() {
        if (this.ID == staticID - 1)
            staticID--;
        return Files.remove(this);
    }

    @Override
    public String toString() {
        return String.format("%s. %s, by %s", ID, name, author);
    }
}
