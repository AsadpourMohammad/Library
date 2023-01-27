package Library;

import File.Files;
import Interface.Operations;
import Person.Member;

import java.io.Serializable;
import java.util.Date;

public class Rent implements Operations, Serializable {
    static int staticID = 1;

    protected Member member;
    protected Book book;
    protected Date date;
    protected int ID;

    public Rent(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.book.borrowed = true;
        this.date = new Date();
        this.ID = staticID;
        staticID++;
    }
    public static void setStaticID(int staticID) {
        Rent.staticID = staticID;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public Date getDate() {
        return date;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return String.format("%s. %s rented %s at %s.", ID, member.getName(), book.getName(), date.toString());
    }

    @Override
    public String save() {
        return Files.save(this);
    }

    @Override
    public String remove() {
        staticID--;
        this.book.borrowed = false;
        return Files.remove(this);
    }
}
