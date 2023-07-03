package Commands.CommandImplementation;

import Commands.Contracts.Command;

public class Help implements Command {
    @Override
    public void execute(String[] args) {
        if(validateArgs(args)) {
        System.out.println("Commands: ");
        System.out.println("open <file> - opens <file>");
        System.out.println("close - closes currently opened file");
        System.out.println("print - prints table");
        System.out.println("edit <row> <col> <value> - edit cell at <row> <col> with <value>");
        System.out.println("save - saves the currently open file");
        System.out.println("saveas <file> - saves the currently open file in <file>");
        System.out.println("help - prints this information");
        System.out.println("exit - exits the program");
        } else {
            System.out.println("Invalid arguments for the command 'help'");
        }
    }

    @Override
    public boolean validateArgs(String[] args) {
        // 'help' command doesn't require any arguments
        return args.length == 0;
    }
}
