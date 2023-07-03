package Commands.Contracts;

public interface Command {
    void execute(String[] args);
    boolean validateArgs(String[] args);
}

