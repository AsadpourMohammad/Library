package File;

import Library.Rent;
import java.io.*;
import java.util.Arrays;

public class MyRentFiles extends Files {
    protected static final String rentsPath = path + "\\rents.txt";

    public static void readRents() {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(rentsPath));)
        {
            while (true) {
                Rent rent = (Rent) in.readObject();
                rents.add(rent);
            }
        }
        catch (EOFException ignored) {}
        catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        finally {
            if(rents.size() != 0) {
                Rent.setStaticID(rents.get(rents.size() - 1).getID() + 1);
            }
        }
    }

    public static String writeRents(boolean saveOrRemove) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rentsPath))){
            for(Rent rent : rents){
                out.writeObject(rent);
            }

            if (saveOrRemove)
                return "Rent added to system.";
            else
                return "Rent was removed from the system.";
        } catch (IOException ex) {
            return Arrays.toString(ex.getStackTrace());
        }
    }

    public static Rent getRent(int rentID) {
        Rent chosenRent = null;
        for (Rent rent : rents){
            if(rent.getID() == rentID){
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
            for(Rent rent : rents) {
                if (ID == 0)
                    System.out.println(rent);
                else {
                    if (rent.getMember().getID() == ID)
                        System.out.println(rent);
                }
            }
    }

//    public static boolean isThisBookRented(int BookID) {
//        for (Rent rent : rents)
//            if(rent.getBook().getID() == BookID)
//                return true;
//
//        return false;
//    }
}
