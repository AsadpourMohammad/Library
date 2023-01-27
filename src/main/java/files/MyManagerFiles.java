package files;

import person.Manager;
import utils.ArraysHelper;

import java.io.*;
import java.util.Arrays;

public class MyManagerFiles extends Files implements Serializable {
    protected static final String managersPath = path + "\\managers.txt";

    public static void readManagers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(managersPath));) {
            while (true) {
                Manager manager = (Manager) in.readObject();
                managers.add(manager);
            }
        } catch (FileNotFoundException | EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace()));
        } finally {
            if (managers.size() != 0)
                Manager.setStaticID(managers.get(managers.size() - 1).getID() + 1);
        }
    }

    public static String writeManagers(boolean saveOrRemove) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(managersPath))) {
            for (Manager manager : managers) {
                out.writeObject(manager);
            }
            if (saveOrRemove)
                return "Manager added to system.";
            else
                return "Manager was removed from the system.";
        } catch (IOException e) {
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
    }

    public static Manager getManager(int managerID) {
        Manager chosenManager = null;
        for (Manager manger : managers) {
            if (manger.getID() == managerID) {
                chosenManager = manger;
                break;
            }
        }
        return chosenManager;
    }

    public static void showManagers() {
        if (managers.size() == 0)
            System.out.println("No manager was found.");
        else
            for (Manager manager : managers)
                System.out.println(manager);
    }

    public static boolean checkForNationalCode(long nationalCode) {
        for (Manager manager : managers)
            if (manager.getNationalCode() == nationalCode)
                return true;
        return false;
    }
}
