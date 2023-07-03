package Handlers;

import Commands.CommandImplementation.*;
import Commands.Contracts.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private Map<String, Command> commandMap;
    private final TableHandler tableHandler;
    private FileHandler fileHandler;

    public CommandHandler(TableHandler tableHandler, FileHandler fileHandler) {
        this.tableHandler = tableHandler;
        this.fileHandler = fileHandler;
        this.commandMap = new HashMap<>();
        commandMap.put("open", new OpenFile(fileHandler, tableHandler));
        commandMap.put("close", new CloseFile(fileHandler));
        commandMap.put("save", new SaveFile(tableHandler, fileHandler));
        commandMap.put("saveas", new SaveFileAs(tableHandler, fileHandler));
        commandMap.put("print", new PrintTable(tableHandler));
        commandMap.put("edit", new EditCell(tableHandler));
        commandMap.put("help", new Help());
        commandMap.put("exit", new Exit());
    }

    public boolean handleCommand(String command) {
        try {
            String[] parts = command.split(" ");
            Command cmd = commandMap.get(parts[0]);

            if (cmd != null) {
                String[] args = new String[parts.length - 1];
                System.arraycopy(parts, 1, args, 0, parts.length - 1);
                cmd.execute(args);
                return cmd instanceof Exit;
            } else {
                handleInvalidCommand(parts[0]);
            }
        } catch (Exception e) {
            System.out.println("Failed to handle command: " + command);
            e.printStackTrace();
        }
        return false;
    }

    private void handleInvalidCommand(String invalidCommand) {
        System.out.println("'" + invalidCommand + "' is an invalid command. Type 'help' for a list of commands.");
    }
}
