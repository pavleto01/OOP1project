package Handlers;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableHandler {
    private String delimiter = ",";
    private int maxRows = 0;
    private int maxColumns = 0;
    private String[][] table;
    private int maxLength = 0;

    public TableHandler() {}

    public void loadTable(Scanner sc) {
        List<String[]> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine().split(delimiter));
        }

        maxRows = lines.size();
        maxColumns = lines.stream().mapToInt(cells -> cells.length).max().orElse(0);

        table = new String[maxRows][maxColumns];
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < lines.get(i).length; j++) {
                table[i][j] = lines.get(i)[j];
                maxLength = Math.max(maxLength, table[i][j].length());
            }
        }

        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {
                if (table[i][j] != null && table[i][j].matches("([R][0-9]+C[0-9]+|[0-9]+)[-+/*]([R][0-9]+C[0-9]+|[0-9]+)")) {
                    table[i][j] = performMathOperation(table[i][j]);
                }
            }
        }
    }

    private String performMathOperation(String cell) {
        List<String> operators = new ArrayList<>();
        List<String> operands = new ArrayList<>();

        Pattern pattern = Pattern.compile("[-+/*]");
        Matcher matcher = pattern.matcher(cell);
        while (matcher.find()) {
            operators.add(matcher.group());
        }

        pattern = Pattern.compile("([R][0-9]+C[0-9]+|[0-9]+)");
        matcher = pattern.matcher(cell);
        while (matcher.find()) {
            operands.add(matcher.group());
        }

        double result = getValue(operands.get(0));

        for (int i = 1; i < operands.size(); i++) {
            double operandValue = getValue(operands.get(i));
            String operator = operators.get(i - 1);

            switch (operator) {
                case "+":
                    result += operandValue;
                    break;
                case "-":
                    result -= operandValue;
                    break;
                case "*":
                    result *= operandValue;
                    break;
                case "/":
                    if (operandValue == 0) {
                        throw new IllegalArgumentException("Division by zero.");
                    }
                    result /= operandValue;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown operator: " + operator);
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
            if (table[row][col] == null || table[row][col].isEmpty() || table[row][col].equals("null")) {
                return 0;
            } else if (table[row][col].matches("([R][0-9]+C[0-9]+|[0-9]+)[-+/*]([R][0-9]+C[0-9]+|[0-9]+)")) {
                return getValue(performMathOperation(table[row][col]));  // recursively evaluate the cell reference
            } else if (table[row][col].matches("R[0-9]+C[0-9]+")) {
                return getValue(table[row][col]);  // recursively evaluate the cell reference
            } else {
                return Double.parseDouble(table[row][col]);
            }
        } else if (val.matches("([R][0-9]+C[0-9]+|[0-9]+)[-+/*]([R][0-9]+C[0-9]+|[0-9]+)")) {
            return Double.parseDouble(performMathOperation(val));  // recursively evaluate the formula
        }
        throw new IllegalArgumentException("Unknown value: " + val);
    }


    public void printTable() {
        String format = "%-" + maxLength + "s|";

        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {
                String cellValue;
                if (table[i][j] != null && !table[i][j].isEmpty()) {
                    if (table[i][j].matches("R[0-9]+C[0-9]+")) {
                        cellValue = String.valueOf(getValue(table[i][j]));
                    } else if (table[i][j].matches("([R][0-9]+C[0-9]+|[0-9]+)\\+([R][0-9]+C[0-9]+|[0-9]+)")) {
                        cellValue = performMathOperation(table[i][j]);
                    } else {
                        cellValue = table[i][j];
                    }
                    if (cellValue.equals("0") || cellValue.equalsIgnoreCase("null")) {
                        cellValue = " ";
                    }
                } else {
                    cellValue = " ";
                }

                System.out.printf(format, cellValue);
            }
            System.out.println();
        }
    }

    public void editCell(String location, String value) {
        String[] loc = location.split("[RC]", -1);
        int row = Integer.parseInt(loc[1]) - 1;
        int col = Integer.parseInt(loc[2]) - 1;
        table[row][col] = value;
    }

    public String[][] getTable() {
        return table;
    }

    public String getDelimiter() {
        return delimiter;
    }
}
