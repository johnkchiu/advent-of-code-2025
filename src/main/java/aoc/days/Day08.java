package aoc.days;

import aoc.core.Day;

import java.util.*;

/**
 * See https://adventofcode.com/2025/day/8
 * Day 8: Playground - Junction Box Circuit Problem
 */
public class Day08 implements Day {

    static class Point3D {
        int x, y, z;

        Point3D(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        double distanceTo(Point3D other) {
            long dx = (long) this.x - other.x;
            long dy = (long) this.y - other.y;
            long dz = (long) this.z - other.z;
            return Math.sqrt(dx * dx + dy * dy + dz * dz);
        }
    }

    static class Connection implements Comparable<Connection> {
        int idx1, idx2;
        double distance;

        Connection(int idx1, int idx2, double distance) {
            this.idx1 = idx1;
            this.idx2 = idx2;
            this.distance = distance;
        }

        @Override
        public int compareTo(Connection other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    static class UnionFind {
        int[] parent;
        int[] size;

        UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // path compression
            }
            return parent[x];
        }

        boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) {
                return false; // already in same set
            }

            // union by size
            if (size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }
            return true;
        }

        List<Integer> getCircuitSizes() {
            Map<Integer, Integer> circuits = new HashMap<>();
            for (int i = 0; i < parent.length; i++) {
                int root = find(i);
                circuits.put(root, size[root]);
            }
            return new ArrayList<>(circuits.values());
        }
    }

    private List<Point3D> parseInput(String input) {
        List<Point3D> points = new ArrayList<>();
        for (String line : input.trim().split("\n")) {
            line = line.trim();
            if (line.isEmpty())
                continue;

            String[] parts = line.split(",");
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            int z = Integer.parseInt(parts[2].trim());
            points.add(new Point3D(x, y, z));
        }
        return points;
    }

    private long solve(String input, int numConnections) {
        List<Point3D> points = parseInput(input);
        int n = points.size();

        // Generate all possible connections and sort by distance
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dist = points.get(i).distanceTo(points.get(j));
                connections.add(new Connection(i, j, dist));
            }
        }

        Collections.sort(connections);

        // Apply the shortest connections using Union-Find
        UnionFind uf = new UnionFind(n);
        int connectionsApplied = 0;

        for (Connection conn : connections) {
            if (connectionsApplied >= numConnections) {
                break;
            }
            uf.union(conn.idx1, conn.idx2);
            connectionsApplied++;
        }

        // Get all circuit sizes and find the three largest
        List<Integer> circuitSizes = uf.getCircuitSizes();
        circuitSizes.sort(Collections.reverseOrder());

        // Multiply the three largest
        long result = 1;
        for (int i = 0; i < Math.min(3, circuitSizes.size()); i++) {
            result *= circuitSizes.get(i);
        }

        return result;
    }

    @Override
    public String part1(String input) {
        // Connect the 1000 closest pairs
        return String.valueOf(solve(input, 1000));
    }

    @Override
    public String part2(String input) {
        List<Point3D> points = parseInput(input);
        int n = points.size();

        // Generate all possible connections and sort by distance
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dist = points.get(i).distanceTo(points.get(j));
                connections.add(new Connection(i, j, dist));
            }
        }

        Collections.sort(connections);

        // Apply connections until all junction boxes are in one circuit
        UnionFind uf = new UnionFind(n);
        Connection lastConnection = null;

        for (Connection conn : connections) {
            // Check if these two boxes are already in the same circuit
            if (uf.find(conn.idx1) != uf.find(conn.idx2)) {
                uf.union(conn.idx1, conn.idx2);
                lastConnection = conn;

                // Check if all boxes are now in one circuit
                // This happens when we have exactly 1 circuit
                List<Integer> circuitSizes = uf.getCircuitSizes();
                if (circuitSizes.size() == 1) {
                    break;
                }
            }
        }

        // Multiply the X coordinates of the last two connected junction boxes
        if (lastConnection != null) {
            int x1 = points.get(lastConnection.idx1).x;
            int x2 = points.get(lastConnection.idx2).x;
            return String.valueOf((long) x1 * x2);
        }

        return "0";
    }
}
