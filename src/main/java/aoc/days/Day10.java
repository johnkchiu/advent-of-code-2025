package aoc.days;

import aoc.core.Day;
import java.util.*;
import java.util.stream.Collectors;

/**
 * See https://adventofcode.com/2025/day/10
 */
public class Day10 implements Day {

    @Override
    public String part1(String input) {
        String[] lines = input.trim().split("\n");
        int totalPresses = 0;
        
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            
            Machine machine = parseMachine(line);
            int minPresses = solveMachine(machine);
            totalPresses += minPresses;
            
            System.out.println("Machine: " + machine.targetPattern + " -> " + minPresses + " presses");
        }
        
        return String.valueOf(totalPresses);
    }

    @Override
    public String part2(String input) {
        // TODO: Implement part 2
        return "0";
    }

    private Machine parseMachine(String line) {
        // Parse indicator light diagram [.##.]
        int bracketStart = line.indexOf('[');
        int bracketEnd = line.indexOf(']');
        String pattern = line.substring(bracketStart + 1, bracketEnd);
        
        // Parse button schematics
        List<int[]> buttons = new ArrayList<>();
        int parenStart = line.indexOf('(');
        while (parenStart != -1) {
            int parenEnd = line.indexOf(')', parenStart);
            String buttonStr = line.substring(parenStart + 1, parenEnd);
            
            int[] positions = Arrays.stream(buttonStr.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
            
            buttons.add(positions);
            parenStart = line.indexOf('(', parenEnd + 1);
        }
        
        return new Machine(pattern, buttons);
    }

    private int solveMachine(Machine machine) {
        int nLights = machine.targetPattern.length();
        int nButtons = machine.buttons.size();
        
        // Target vector (what we want to achieve)
        int[] target = new int[nLights];
        for (int i = 0; i < nLights; i++) {
            target[i] = machine.targetPattern.charAt(i) == '#' ? 1 : 0;
        }
        
        // Build coefficient matrix (each button is a column)
        int[][] matrix = new int[nLights][nButtons];
        for (int j = 0; j < nButtons; j++) {
            int[] button = machine.buttons.get(j);
            for (int pos : button) {
                if (pos < nLights) {
                    matrix[pos][j] = 1;
                }
            }
        }
        
        // Solve using Gaussian elimination modulo 2
        return solveLinearSystem(matrix, target);
    }

    private int solveLinearSystem(int[][] matrix, int[] target) {
        int n = matrix.length;    // number of equations (lights)
        int m = matrix[0].length; // number of variables (buttons)
        
        // Create augmented matrix
        int[][] aug = new int[n][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                aug[i][j] = matrix[i][j];
            }
            aug[i][m] = target[i];
        }
        
        // Track which variables are basic variables
        int[] basicVars = new int[n];
        Arrays.fill(basicVars, -1);
        
        // Forward elimination
        int row = 0;
        for (int col = 0; col < m && row < n; col++) {
            // Find pivot
            int pivot = -1;
            for (int i = row; i < n; i++) {
                if (aug[i][col] == 1) {
                    pivot = i;
                    break;
                }
            }
            
            if (pivot == -1) continue; // No pivot in this column
            
            // Swap rows if needed
            if (pivot != row) {
                int[] temp = aug[pivot];
                aug[pivot] = aug[row];
                aug[row] = temp;
            }
            
            // Record basic variable
            basicVars[row] = col;
            
            // Eliminate this variable from other rows
            for (int i = 0; i < n; i++) {
                if (i != row && aug[i][col] == 1) {
                    // XOR row i with row (since we're working modulo 2)
                    for (int j = col; j <= m; j++) {
                        aug[i][j] ^= aug[row][j];
                    }
                }
            }
            
            row++;
        }
        
        // Check for consistency
        for (int i = row; i < n; i++) {
            boolean allZeros = true;
            for (int j = 0; j < m; j++) {
                if (aug[i][j] != 0) {
                    allZeros = false;
                    break;
                }
            }
            if (allZeros && aug[i][m] == 1) {
                return -1; // No solution
            }
        }
        
        // If system is underdetermined, we need to find the solution with minimum Hamming weight
        // This is NP-hard, but for small systems we can use backtracking
        return findMinWeightSolution(aug, basicVars, row, m);
    }

    private int findMinWeightSolution(int[][] aug, int[] basicVars, int rank, int nVars) {
        // Initialize solution array
        int[] solution = new int[nVars];
        
        // Set basic variables
        for (int i = 0; i < rank; i++) {
            int var = basicVars[i];
            if (var != -1) {
                solution[var] = aug[i][nVars]; // RHS
            }
        }
        
        // For free variables, try all combinations to find minimum weight solution
        int[] freeVars = new int[nVars - rank];
        int freeCount = 0;
        for (int j = 0; j < nVars; j++) {
            boolean isBasic = false;
            for (int i = 0; i < rank; i++) {
                if (basicVars[i] == j) {
                    isBasic = true;
                    break;
                }
            }
            if (!isBasic) {
                freeVars[freeCount++] = j;
            }
        }
        
        // If no free variables, return the solution we found
        if (freeCount == 0) {
            return countOnes(solution);
        }
        
        // Try all combinations of free variables (2^freeCount possibilities)
        int minWeight = Integer.MAX_VALUE;
        for (int mask = 0; mask < (1 << freeCount); mask++) {
            int[] testSolution = solution.clone();
            
            // Set free variables according to mask
            for (int i = 0; i < freeCount; i++) {
                testSolution[freeVars[i]] = ((mask >> i) & 1);
            }
            
            // Recalculate basic variables based on free variables
            for (int i = 0; i < rank; i++) {
                int var = basicVars[i];
                int sum = aug[i][nVars]; // Start with RHS
                
                // Subtract contribution of free variables
                for (int j = 0; j < nVars; j++) {
                    if (aug[i][j] == 1 && var != j) {
                        sum ^= testSolution[j];
                    }
                }
                testSolution[var] = sum;
            }
            
            // Verify solution
            if (isValidSolution(aug, testSolution)) {
                int weight = countOnes(testSolution);
                if (weight < minWeight) {
                    minWeight = weight;
                }
            }
        }
        
        return minWeight == Integer.MAX_VALUE ? -1 : minWeight;
    }

    private boolean isValidSolution(int[][] aug, int[] solution) {
        int n = aug.length;
        int m = solution.length;
        
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < m; j++) {
                sum ^= (aug[i][j] & solution[j]);
            }
            if (sum != aug[i][m]) {
                return false;
            }
        }
        return true;
    }

    private int countOnes(int[] arr) {
        int count = 0;
        for (int val : arr) {
            count += val;
        }
        return count;
    }

    private static class Machine {
        final String targetPattern;
        final List<int[]> buttons;
        
        Machine(String targetPattern, List<int[]> buttons) {
            this.targetPattern = targetPattern;
            this.buttons = buttons;
        }
    }
}