package aoc.days;

import aoc.core.Day;

import java.util.*;

/**
 * See https://adventofcode.com/2025/day/7
 */
public class Day07 implements Day {

    @Override
    public String part1(String input) {
        String[] lines = input.trim().split("\n");
        int rows = lines.length;
        int cols = 0;
        for (String line : lines) {
            cols = Math.max(cols, line.length());
        }
        
        // Find starting position
        int startRow = -1;
        int startCol = -1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < lines[r].length(); c++) {
                if (lines[r].charAt(c) == 'S') {
                    startRow = r;
                    startCol = c;
                    break;
                }
            }
            if (startRow != -1) break;
        }
        
        if (startRow == -1) {
            return "0"; // No starting position found
        }
        
        // Simulate beam propagation
        // Track active beam columns at each row
        // Map: row -> set of columns where beams are active
        Map<Integer, Set<Integer>> beamsByRow = new HashMap<>();
        beamsByRow.put(startRow, new HashSet<>());
        beamsByRow.get(startRow).add(startCol);
        
        int splitCount = 0;
        
        // Process each row from startRow to the end
        for (int r = startRow; r < rows - 1; r++) {
            Set<Integer> currentBeams = beamsByRow.getOrDefault(r, new HashSet<>());
            if (currentBeams.isEmpty()) {
                continue; // No beams at this row
            }
            
            Set<Integer> nextRowBeams = beamsByRow.getOrDefault(r + 1, new HashSet<>());
            
            // Process each beam at this row
            for (int col : currentBeams) {
                if (col < 0 || col >= cols) {
                    continue; // Out of bounds
                }
                
                // Beam moves down to row r+1, same column
                int nextRow = r + 1;
                if (nextRow >= rows) {
                    continue; // Exits grid
                }
                
                if (col >= lines[nextRow].length()) {
                    continue; // Out of bounds for this row
                }
                
                char cell = lines[nextRow].charAt(col);
                
                if (cell == '.') {
                    // Beam passes through, continues to next row
                    nextRowBeams.add(col);
                } else if (cell == '^') {
                    // Beam hits splitter - stops, creates two new beams at left and right
                    splitCount++;
                    int leftCol = col - 1;
                    int rightCol = col + 1;
                    
                    // New beams are created at the same row as the splitter (nextRow)
                    if (leftCol >= 0) {
                        nextRowBeams.add(leftCol);
                    }
                    if (rightCol < cols && rightCol < lines[nextRow].length()) {
                        nextRowBeams.add(rightCol);
                    }
                }
                // Other characters stop the beam
            }
            
            if (!nextRowBeams.isEmpty()) {
                beamsByRow.put(r + 1, nextRowBeams);
            }
        }
        
        return String.valueOf(splitCount);
    }

    @Override
    public String part2(String input) {
        String[] lines = input.trim().split("\n");
        int rows = lines.length;
        int cols = 0;
        for (String line : lines) {
            cols = Math.max(cols, line.length());
        }
        
        // Find starting position
        int startRow = -1;
        int startCol = -1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < lines[r].length(); c++) {
                if (lines[r].charAt(c) == 'S') {
                    startRow = r;
                    startCol = c;
                    break;
                }
            }
            if (startRow != -1) break;
        }
        
        if (startRow == -1) {
            return "0"; // No starting position found
        }
        
        // Dynamic programming: track number of paths reaching each column at each row
        // Use long to handle large numbers
        long[] pathCounts = new long[cols];
        pathCounts[startCol] = 1; // Start with 1 path at the starting column
        
        // Process each row from startRow to the end
        for (int r = startRow; r < rows - 1; r++) {
            long[] nextPathCounts = new long[cols];
            
            // Process each column that has paths
            for (int col = 0; col < cols; col++) {
                if (pathCounts[col] == 0) {
                    continue; // No paths at this column
                }
                
                long pathsHere = pathCounts[col];
                int nextRow = r + 1;
                
                if (nextRow >= rows) {
                    continue; // Exits grid
                }
                
                if (col >= lines[nextRow].length()) {
                    continue; // Out of bounds for this row
                }
                
                char cell = lines[nextRow].charAt(col);
                
                if (cell == '^') {
                    // Path splits into left and right
                    int leftCol = col - 1;
                    int rightCol = col + 1;
                    
                    if (leftCol >= 0) {
                        nextPathCounts[leftCol] += pathsHere;
                    }
                    if (rightCol < cols && rightCol < lines[nextRow].length()) {
                        nextPathCounts[rightCol] += pathsHere;
                    }
                } else {
                    // Path continues straight down (empty space or other characters)
                    nextPathCounts[col] += pathsHere;
                }
            }
            
            pathCounts = nextPathCounts;
        }
        
        // Sum all paths in the bottom row
        long totalPaths = 0;
        for (long count : pathCounts) {
            totalPaths += count;
        }
        
        return String.valueOf(totalPaths);
    }
}
