package Handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {
    private File currentFile;
    private Scanner scanner;

    public void openFile(String fileName) {
        try {
            currentFile = new File(fileName);
            scanner = new Scanner(currentFile);
            System.out.println("File opened successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    public void closeFile() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
            currentFile = null;
            System.out.println("File closed successfully.");
        } else {
            System.out.println("No file opened.");
        }
    }

    public void saveFile(String[][] table, String delimiter) {
        if (currentFile != null) {
            saveToFile(currentFile, table, delimiter);
        } else {
            System.out.println("No file opened.");
        }
    }

    public void saveFileAs(String fileName, String[][] table, String delimiter) {
        File file = new File(fileName);
        saveToFile(file, table, delimiter);
    }

    private void saveToFile(File file, String[][] table, String delimiter) {
        try {
            FileWriter writer = new FileWriter(file);
            for (String[] row : table) {
                for (String cell : row) {
                    writer.write(cell + delimiter);
                }
                writer.write(System.lineSeparator());
            }
            writer.close();
            System.out.println("File saved successfully as: " + file.getName());
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public Scanner getScanner() {
        return scanner;
    }
}
