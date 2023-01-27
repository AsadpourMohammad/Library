package person;

import files.Files;

public class Manager extends Person {
    private static int staticID = 1;

    public Manager(String name, long nationalCode) {
        super(name, nationalCode);
        this.ID = staticID;
        staticID++;
    }

    public static void setStaticID(int staticID) {
        Manager.staticID = staticID;
    }

    public static int getStaticID() {
        return staticID;
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
