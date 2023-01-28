import javax.script.ScriptException;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, ScriptException {
        ConsoleMenu menu = new ConsoleMenu();
        menu.run();
    }
}