package library;

import files.Files;
import interfaces.Operations;
import person.Member;

import java.io.Serializable;
import java.util.Date;

public class Rent implements Operations, Serializable {
    private final Member member;
    private final Book book;
    private Date date;

    public Rent(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.book.setBorrowed(true);
        this.date = new Date();
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

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String save() {
        return Files.save(this);
    }

    @Override
    public String remove() {
        this.getBook().setBorrowed(false);
        return Files.remove(this);
    }

    @Override
    public String toString() {
        return String.format("%s rented %s(%s) at %s.", member.getName(), book.getName(), book.getID(), date);
    }
}
