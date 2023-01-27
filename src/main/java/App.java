import CLI.MainCLI;
import File.MyBookFiles;
import File.MyManagerFiles;
import File.MyMemberFiles;
import File.MyRentFiles;

public class App {
    public static void main(String[] args) {
        MyManagerFiles.readManagers();
        MyMemberFiles.readMembers();
        MyBookFiles.readBooks();
        MyRentFiles.readRents();

        MainCLI.startMenu();
    }
}
