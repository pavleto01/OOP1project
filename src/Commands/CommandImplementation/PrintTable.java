package Commands.CommandImplementation;

import Commands.Contracts.Command;
import Handlers.TableHandler;

public class PrintTable implements Command {
    private TableHandler tableHandler;

    public PrintTable(TableHandler tableHandler) {
        this.tableHandler = tableHandler;
    }

    @Override
    public void execute(String[] args) {
        if(validateArgs(args)) {
            tableHandler.printTable();
        } else {
            System.out.println("Invalid arguments for the command 'print'");
        }
    }

    @Override
    public boolean validateArgs(String[] args) {
        // 'print' command doesn't require any arguments
        return args.length == 0;
    }
}
