package Commands.CommandImplementation;

import Commands.Contracts.Command;
import Handlers.FileHandler;
import Handlers.TableHandler;

import java.util.Scanner;

public class OpenFile implements Command {
    private final FileHandler fileHandler;
    private final TableHandler tableHandler;

    public OpenFile(FileHandler fileHandler, TableHandler tableHandler) {
        this.fileHandler = fileHandler;
        this.tableHandler = tableHandler;
    }

    @Override
    public void execute(String[] args) {
        if(validateArgs(args)) {
            String fileName = args[0];
            fileHandler.openFile(fileName);
            Scanner fileScanner = fileHandler.getScanner();
            if (fileScanner != null) {
                tableHandler.loadTable(fileScanner);
            }
        } else {
            System.out.println("Invalid arguments for the command 'open'. Correct format: 'open <file>'");
        }
    }

    @Override
    public boolean validateArgs(String[] args) {
        // 'open' command requires 1 argument
        return args.length == 1;
    }
}
