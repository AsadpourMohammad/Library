package person;

import interfaces.Operations;

import java.io.Serializable;

public abstract class Person implements Operations, Serializable {
    protected final String name;
    protected final long nationalCode;
    protected int ID;

    public Person(String name, long nationalCode) {
        this.name = name;
        this.nationalCode = nationalCode;
    }

    public String getName() {
        return name;
    }

    public long getNationalCode() {
        return nationalCode;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return String.format("%s. %s, with national code of %s", ID, name, nationalCode);
    }
}
