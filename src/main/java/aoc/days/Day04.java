package aoc.days;

import aoc.core.Day;

public class Day04 implements Day {

    /**
     * Counts the number of '@' cells in the grid that have fewer than 4
     * adjacent '@' neighbors. An '@' cell is considered "accessible" if it has
     * less than 4 '@' neighbors in the 8 directions.
     *
     * @param input Multiline string representing the grid.
     * @return Number of accessible '@' cells as a string.
     */
    @Override
    public String part1(String input) {
        String[] lines = input.split("\n");
        int rows = lines.length;
        int cols = lines[0].length();
        char[][] grid = new char[rows][cols];
        // Convert input lines to a 2D char grid
        for (int i = 0; i < rows; i++) {
            grid[i] = lines[i].toCharArray();
        }
        // Directions for 8 neighbors (N, NE, E, SE, S, SW, W, NW)
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
        int accessibleCount = 0;
        // Iterate over each cell in the grid
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '@') {
                    int adj = 0;
                    // Count adjacent '@' neighbors
                    for (int d = 0; d < 8; d++) {
                        int nr = r + dr[d];
                        int nc = c + dc[d];
                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == '@') {
                            adj++;
                        }
                    }
                    // If fewer than 4 adjacent '@', count as accessible
                    if (adj < 4) {
                        accessibleCount++;
                    }
                }
            }
        }
        return String.valueOf(accessibleCount);
    }

    /**
     * Iteratively removes '@' cells that have fewer than 4 adjacent '@'
     * neighbors, until no more cells can be removed. Counts the number of
     * removed cells.
     *
     * @param input Multiline string representing the grid.
     * @return Number of removed '@' cells as a string.
     */
    @Override
    public String part2(String input) {
        String[] lines = input.split("\n");
        int rows = lines.length;
        int cols = lines[0].length();
        char[][] grid = new char[rows][cols];
        // Convert input lines to a 2D char grid
        for (int i = 0; i < rows; i++) {
            grid[i] = lines[i].toCharArray();
        }
        // Directions for 8 neighbors (N, NE, E, SE, S, SW, W, NW)
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
        boolean changed;
        int removed = 0;
        // Repeat until no more cells can be removed
        do {
            changed = false;
            boolean[][] toRemove = new boolean[rows][cols];
            // Mark '@' cells with fewer than 4 adjacent '@' for removal
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (grid[r][c] == '@') {
                        int adj = 0;
                        // Count adjacent '@' neighbors
                        for (int d = 0; d < 8; d++) {
                            int nr = r + dr[d];
                            int nc = c + dc[d];
                            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == '@') {
                                adj++;
                            }
                        }
                        // Mark for removal if fewer than 4 adjacent '@'
                        if (adj < 4) {
                            toRemove[r][c] = true;
                        }
                    }
                }
            }
            // Remove marked cells and update counters
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (toRemove[r][c]) {
                        grid[r][c] = '.';
                        removed++;
                        changed = true;
                    }
                }
            }
        } while (changed); // Continue if any cell was removed in this iteration
        return String.valueOf(removed);
    }
}
