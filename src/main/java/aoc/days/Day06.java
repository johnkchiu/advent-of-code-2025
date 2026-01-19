package aoc.days;

import aoc.core.Day;

import java.util.ArrayList;
import java.util.List;

/**
 * See https://adventofcode.com/2025/day/6
 */
public class Day06 implements Day {

    @Override
    public String part1(String input) {
        String[] lines = input.trim().split("\n");
        int numRows = lines.length;
        int numCols = Math.max(lines[0].length(), lines[numRows - 1].length());

        long grandTotal = 0;

        // Find all columns with operators (+ or *)
        for (int col = 0; col < numCols; col++) {
            if (col >= lines[numRows - 1].length())
                continue;
            char bottomChar = lines[numRows - 1].charAt(col);
            if (bottomChar == '+' || bottomChar == '*') {
                // Found an operator, collect all numbers that start at or after this column
                List<Long> numbers = new ArrayList<>();

                // Go from top to bottom (but skip the operator row)
                for (int row = 0; row < numRows - 1; row++) {
                    String line = lines[row];

                    // Find the first digit at or after this column position
                    for (int c = col; c < line.length(); c++) {
                        if (Character.isDigit(line.charAt(c))) {
                            // Found start of a number, extract it
                            StringBuilder numStr = new StringBuilder();
                            while (c < line.length() && Character.isDigit(line.charAt(c))) {
                                numStr.append(line.charAt(c));
                                c++;
                            }
                            if (numStr.length() > 0) {
                                numbers.add(Long.parseLong(numStr.toString()));
                            }
                            break; // Only take the first number in this row for this problem
                        }
                    }
                }

                // Calculate result for this problem
                if (!numbers.isEmpty()) {
                    long result = numbers.get(0);
                    for (int i = 1; i < numbers.size(); i++) {
                        if (bottomChar == '+') {
                            result += numbers.get(i);
                        } else if (bottomChar == '*') {
                            result *= numbers.get(i);
                        }
                    }
                    System.out.println("Problem at column " + (col + 1) + ": " +
                            numbers + " " + bottomChar + " = " + result);
                    grandTotal += result;
                }
            }
        }

        return String.valueOf(grandTotal);
    }

    @Override
    public String part2(String input) {
        String[] lines = input.trim().split("\n");
        int numRows = lines.length;
        int numCols = 0;
        for (String line : lines) {
            numCols = Math.max(numCols, line.length());
        }


        long grandTotal = 0;

        // Find all operator columns (+ or *)
        List<Integer> operatorColumns = new ArrayList<>();
        for (int col = 0; col < numCols; col++) {
            if (col < lines[numRows - 1].length()) {
                char bottomChar = lines[numRows - 1].charAt(col);
                if (bottomChar == '+' || bottomChar == '*') {
                    operatorColumns.add(col);
                }
            }
        }

        // Process problems right-to-left
        for (int i = operatorColumns.size() - 1; i >= 0; i--) {
            int opCol = operatorColumns.get(i);
            char operator = lines[numRows - 1].charAt(opCol);

            // Find the range of this problem (from this operator column onwards until no more digits)
            int startCol = opCol;
            int endCol = opCol;

            // Find all columns that have digits
            for (int c = opCol; c < numCols; c++) {
                boolean hasDigit = false;
                for (int r = 0; r < numRows - 1; r++) { // Skip operator row
                    if (r < lines[r].length() && c < lines[r].length() && Character.isDigit(lines[r].charAt(c))) {
                        hasDigit = true;
                        break;
                    }
                }
                if (hasDigit) {
                    endCol = c;
                } else {
                    break; // Stop when we find a column with no digits
                }
            }

            // Extract numbers for this problem
            List<Long> numbers = new ArrayList<>();

            // For each column in this problem (right to left within the problem)
            for (int col = endCol; col >= startCol; col--) {
                StringBuilder numStr = new StringBuilder();

                // Read digits from top to bottom (most significant to least significant)
                for (int row = 0; row < numRows - 1; row++) { // Skip operator row
                    if (row < lines[row].length() && col < lines[row].length()) {
                        char ch = lines[row].charAt(col);
                        if (Character.isDigit(ch)) {
                            numStr.append(ch);
                        }
                    }
                }

                if (numStr.length() > 0) {
                    long number = Long.parseLong(numStr.toString());
                    numbers.add(number);
                }
            }

            // Calculate result for this problem
            if (!numbers.isEmpty()) {
                long result = numbers.get(0);
                for (int j = 1; j < numbers.size(); j++) {
                    if (operator == '+') {
                        result += numbers.get(j);
                    } else if (operator == '*') {
                        result *= numbers.get(j);
                    }
                }
                System.out.println("Problem at column " + opCol + ": " + numbers + " " + operator + " = " + result);
                grandTotal += result;
            }
        }

        return String.valueOf(grandTotal);
    }
}
