import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConsoleMenu {
    private FileHandler fileHandler;
    private Scanner input;

    public ConsoleMenu() {
        fileHandler = new FileHandler();

        input = new Scanner(System.in);
    }

    public void run() throws FileNotFoundException, ScriptException {
        boolean exit = false;

        while (!exit) {
            System.out.print("Enter command (help for list of commands): ");
            String command = input.nextLine();
            String[] parts = command.split(" ");

            switch (parts[0]) {
                case "open":
                    fileHandler.openFile(parts[1]);
                    break;
                case "close":
                    fileHandler.closeFile();
                    break;
                case "print":
                    fileHandler.printTable();
                    break;
                case "edit":
                    fileHandler.editCell(parts[1], parts[2]);
                    break;
                case "save":
                    fileHandler.saveFile();
                    break;
                case "saveas":
                    fileHandler.saveFileAs(parts[1]);
                    break;
                case "help":
                    System.out.println("Commands: ");
                    System.out.println("open <file> - opens <file>");
                    System.out.println("close - closes currently opened file");
                    System.out.println("print - prints table");
                    System.out.println("edit <row> <col> <value> - edit cell at <row> <col> with <value>");
                    System.out.println("save - saves the currently open file");
                    System.out.println("saveas <file> - saves the currently open file in <file>");
                    System.out.println("help - prints this information");
                    System.out.println("exit - exits the program");
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid command. Type 'help' for list of commands.");
                    break;
            }
        }
    }
}