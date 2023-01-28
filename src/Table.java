public class Table {
    private String[][] table;
    private int maxRows;
    private int maxColumns;
    private FileHandler fileHandler;

    public Table(int rows, int columns) {
        this.maxRows = rows;
        this.maxColumns = columns;
        table = new String[rows][columns];
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public String performMathOperation(String cell) {
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

    public void editCell(String location, String value) {
        if (fileHandler != null && fileHandler.isFileOpen()) {
            System.out.println("Error: Cannot edit cell while file is open.");
            return;
        }
        String[] loc = location.split("R|C");
        int row = Integer.parseInt(loc[1]) - 1;
        int col = Integer.parseInt(loc[2]) - 1;
        if (row >= maxRows || col >= maxColumns) {
            System.out.println("Error: Cell location out of bounds.");
            return;
        }
        table[row][col] = value;
        System.out.println("Cell edited successfully.");
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
}