import cli.MainCLI;
import files.Files;

public class App {
    public static void main(String[] args) {
        Files.readFiles();

        MainCLI.startMenu();
    }
}
