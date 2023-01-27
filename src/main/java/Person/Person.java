package Person;

import Interface.Operations;

import java.io.Serializable;

public abstract class Person implements Operations, Serializable {

    protected final String name;
    protected final int nationalCode;
    protected int ID;

    public Person(String name, int nationalCode) {
        this.name = name;
        this.nationalCode = nationalCode;
    }

    public String getName() {
        return name;
    }

    public int getNationalCode() {
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
