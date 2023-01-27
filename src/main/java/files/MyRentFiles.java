package files;

import library.Rent;
import utils.ArraysHelper;

import java.io.*;
import java.util.Arrays;

public class MyRentFiles extends Files {
    protected static final String rentsPath = path + "\\rents.txt";

    public static void readRents() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(rentsPath))) {
            while (true) {
                Rent rent = (Rent) in.readObject();
                rents.add(rent);
            }
        } catch (FileNotFoundException | EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace()));
        }
    }

    public static String writeRents(boolean saveOrRemove) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rentsPath))) {
            for (Rent rent : rents)
                out.writeObject(rent);

            if (saveOrRemove)
                return "Rent added to system.";
            else
                return "Rent was removed from the system.";
        } catch (IOException e) {
            return e.getMessage() + "\n" + ArraysHelper.toString(e.getStackTrace());
        }
    }

    public static Rent getRent(int bookID) {
        Rent chosenRent = null;
        for (Rent rent : rents) {
            if (rent.getBook().getID() == bookID) {
                chosenRent = rent;
                break;
            }
        }

        return chosenRent;
    }

    public static void showRents(int ID) {
        if (rents.size() == 0)
            System.out.println("No rents was found.");
        else
            for (Rent rent : rents) {
                if (ID == 0)
                    System.out.println(rent);
                else if (rent.getMember().getID() == ID)
                    System.out.println(rent);
            }
    }
}
