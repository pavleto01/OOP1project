package Commands.CommandImplementation;

import Commands.Contracts.Command;
import Handlers.FileHandler;
import Handlers.TableHandler;

public class SaveFile implements Command {
    private TableHandler tableHandler;
    private FileHandler fileHandler;

    public SaveFile(TableHandler tableHandler, FileHandler fileHandler) {
        this.tableHandler = tableHandler;
        this.fileHandler = fileHandler;
    }

    @Override
    public void execute(String[] args) {
        if(validateArgs(args)) {
            String[][] table = tableHandler.getTable();
            String delimiter = tableHandler.getDelimiter();
            fileHandler.saveFile(table, delimiter);
        } else {
            System.out.println("Invalid arguments for the command 'save'");
        }
    }

    @Override
    public boolean validateArgs(String[] args) {
        // 'save' command doesn't require any arguments
        return args.length == 0;
    }
}
