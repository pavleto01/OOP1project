package Commands.CommandImplementation;

import Commands.Contracts.Command;
import Handlers.FileHandler;

public class CloseFile implements Command {
    private FileHandler fileHandler;

    public CloseFile(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void execute(String[] args) {
        if (validateArgs(args)) {
            try {
                fileHandler.closeFile();
            } catch (Exception e) {
                System.out.println("Error closing file: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid arguments for the command 'close'");
        }
    }

    @Override
    public boolean validateArgs(String[] args) {
        // 'close' command doesn't require any arguments
        return args.length == 0;
    }
}
