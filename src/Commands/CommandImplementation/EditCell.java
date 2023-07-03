package Commands.CommandImplementation;

import Commands.Contracts.Command;
import Handlers.TableHandler;

public class EditCell implements Command {
    private TableHandler tableHandler;

    public EditCell(TableHandler tableHandler) {
        this.tableHandler = tableHandler;
    }

    @Override
    public void execute(String[] args) {
        try {
            if (validateArgs(args)) {
                String location = args[0];
                String newValue = args[1];
                tableHandler.editCell(location, newValue);
                System.out.println("Cel edited successfully!");
            } else {
                System.out.println("Invalid arguments for the command 'edit'");
            }
        } catch (Exception e) {
            System.out.println("Failed to execute 'edit' command: " + e.getMessage());
        }
    }

    @Override
    public boolean validateArgs(String[] args) {
        return args.length == 2 && args[0].matches("R[0-9]+C[0-9]+");
    }
}

