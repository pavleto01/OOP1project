import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {
    private String fileName;
    private String delimiter = ",";
    private int maxRows = 0;
    private int maxColumns = 0;
    private Scanner sc;
    private String[][] table;



    public void openFile(String fileName) {
        this.fileName = fileName;
        try {
            File file = new File(fileName);
            sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] cells = sc.nextLine().split(delimiter);
                maxRows++;
                maxColumns = Math.max(maxColumns, cells.length);
            }
            table = new String[maxRows][maxColumns];
            sc = new Scanner(file);
            int i =0;
            while (sc.hasNextLine()) {
                String[] cells = sc.nextLine().split(delimiter);
                for (int j = 0; j < cells.length; j++) {
                    table[i][j] = cells[j];
                    table[i][j] = table[i][j].replace("\\\"", "");
                }
                i++;
            }
            System.out.println("File opened successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    public void closeFile() {
        sc.close();
        fileName = null;
        maxColumns = 0;
        maxRows = 0;
        table = null;
        System.out.println("File closed successfully.");
    }

    private String performMathOperation(String cell) {
        String[] values = cell.split("[+\\-/*]");
        String operation = cell.replaceAll("[0-9R]+C[0-9]+|[0-9]+|[a-zA-Z0-9\"']+", "");
        double result = 0;

        result = getValue(values[0]);

        for (int i = 1; i < values.length; i++) {
            double val = getValue(values[i]);

            switch (operation.charAt(i - 1)) {
                case '+':
                    result += val;
                    break;
                case '-':
                    result -= val;
                    break;
                case '*':
                    result *= val;
                    break;
                case '/':
                    if (val == 0) {
                        return "Error: Divide by zero";
                    } else {
                        result /= val;
                    }
                    break;
            }
        }
        return String.valueOf(result);
    }

    private double getValue(String val) {
        if (val.matches("[0-9]+")) {
            return Double.parseDouble(val);
        } else if (val.matches("R[0-9]+C[0-9]+")) {
            String[] loc = val.split("R|C");
            int row = Integer.parseInt(loc[1]) - 1;
            int col = Integer.parseInt(loc[2]) - 1;
            if (table[row][col] == null || table[row][col].isEmpty()) {
                return 0;
            } else {
                return Double.parseDouble(table[row][col]);
            }
        } else if (val.matches("[a-zA-Z0-9\"']+")) {
            val = val.replace("\"", "");
            if (val.matches("[0-9]+")) {
                return Double.parseDouble(val);
            } else {
                return 0;
            }
        }
        return 0;
    }



    public void printTable() {
        int[] maxLengths = new int[maxColumns];
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {
                if(table[i][j]!=null) {
                    String cellValue = table[i][j];
                    if(cellValue.matches("([R][0-9]+C[0-9]+|[0-9]+|[a-zA-Z0-9\"']+)[+\\-/*]([R][0-9]+C[0-9]+|[0-9]+|[a-zA-Z0-9\"']+)") ) {
                        cellValue = performMathOperation(cellValue);
                        table[i][j] = cellValue;
                    }
                    maxLengths[j] = Math.max(maxLengths[j], cellValue.length());
                }
            }
        }

        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {
                if(table[i][j]!=null) {
                    System.out.print(table[i][j]);
                    for (int k = table[i][j].length(); k <= maxLengths[j]; k++) {
                        System.out.print(" ");
                    }
                } else {
                    System.out.print(" ");
                    for (int k = 1; k <= maxLengths[j]; k++) {
                        System.out.print(" ");
                    }
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }

    public void editCell(String location, String value) {
        if (fileName == null) {
            System.out.println("No file opened.");
            return;
        }
        String[] loc = location.split("R|C");
        int row = Integer.parseInt(loc[1]) - 1;
        int col = Integer.parseInt(loc[2]) - 1;
        table[row][col] = value;
        System.out.println("Cell edited successfully.");
    }

    public void saveFile() {
        if (fileName == null) {
            System.out.println("No file opened.");
            return;
        }
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < maxRows; i++) {
                for (int j = 0; j < maxColumns; j++) {
                    writer.write(table[i][j] + delimiter);
                }
                writer.write("\n");
            }
            writer.close();
            System.out.println("File saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void saveFileAs(String fileName) {
        if (table == null) {
            System.out.println("No file open.");
            return;
        }
        try {
            FileWriter writer =new FileWriter(fileName);
            for (int i = 0; i < maxRows; i++) {
                for (int j = 0; j < maxColumns; j++) {
                    writer.write(table[i][j] + delimiter);
                }
                writer.write("\n");
            }
            writer.close();
            System.out.println("File saved successfully as: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
