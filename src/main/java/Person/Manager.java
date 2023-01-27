package Person;

import File.Files;

public class Manager extends Person {
    static int staticID = 1;

    public Manager(String name, int nationalCode) {
        super(name, nationalCode);
        this.ID = staticID;
        staticID++;
    }

    public static void setStaticID(int staticID) {
        Manager.staticID = staticID;
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
