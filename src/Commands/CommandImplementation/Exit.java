package Commands.CommandImplementation;

import Commands.Contracts.Command;

public class Exit implements Command {
    @Override
    public void execute(String[] args) {
        if(validateArgs(args)) {
            System.out.println("Exiting program...");
            System.exit(0);
        } else {
            System.out.println("Invalid arguments for the command 'exit'");
        }
    }

    @Override
    public boolean validateArgs(String[] args) {
        // 'exit' command doesn't require any arguments
        return args.length == 0;
    }
}
