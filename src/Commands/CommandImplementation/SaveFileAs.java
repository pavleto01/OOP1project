package Commands.CommandImplementation;

import Commands.Contracts.Command;
import Handlers.FileHandler;
import Handlers.TableHandler;

public class SaveFileAs implements Command {
    private TableHandler tableHandler;
    private FileHandler fileHandler;

    public SaveFileAs(TableHandler tableHandler, FileHandler fileHandler) {
        this.tableHandler = tableHandler;
        this.fileHandler = fileHandler;
    }

    @Override
    public void execute(String[] args) {
        if(validateArgs(args)) {
            String filename = args[0];
            String[][] table = tableHandler.getTable();
            String delimiter = tableHandler.getDelimiter();
            fileHandler.saveFileAs(filename, table, delimiter);
        } else {
            System.out.println("Invalid arguments for the command 'saveas'. Correct format: 'saveas <file>'");
        }
    }

    @Override
    public boolean validateArgs(String[] args) {
        // 'saveas' command requires 1 argument
        return args.length == 1;
    }
}
