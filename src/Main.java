import Handlers.CommandHandler;
import Handlers.FileHandler;
import Handlers.TableHandler;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TableHandler tableHandler = new TableHandler();
        FileHandler fileHandler = new FileHandler();
        CommandHandler commandHandler = new CommandHandler(tableHandler, fileHandler);
        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.print("> ");
            command = scanner.nextLine();
        } while (!commandHandler.handleCommand(command) && !command.equalsIgnoreCase("exit"));
    }
}

