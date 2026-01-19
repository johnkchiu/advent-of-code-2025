package aoc.days;

import aoc.core.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * See https://adventofcode.com/2025/day/9
 * Day 9: Movie Theater
 */
public class Day09 implements Day {

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private List<Point> parseInput(String input) {
        List<Point> points = new ArrayList<>();
        // Match numbers separated by comma, flexible whitespace around pairs
        Pattern p = Pattern.compile("(\\d+),(\\d+)");
        Matcher m = p.matcher(input);
        while (m.find()) {
            int x = Integer.parseInt(m.group(1));
            int y = Integer.parseInt(m.group(2));
            points.add(new Point(x, y));
        }
        return points;
    }

    @Override
    public String part1(String input) {
        List<Point> points = parseInput(input);
        long maxArea = 0;
        int n = points.size();

        for (int i = 0; i < n; i++) {
            Point p1 = points.get(i);
            for (int j = i + 1; j < n; j++) {
                Point p2 = points.get(j);
                long width = Math.abs((long) p1.x - p2.x) + 1;
                long height = Math.abs((long) p1.y - p2.y) + 1;
                long area = width * height;
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }

        return String.valueOf(maxArea);
    }

    @Override
    public String part2(String input) {
        List<Point> points = parseInput(input);
        int n = points.size();
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % n);
            edges.add(new Edge(p1, p2));
        }

        long maxArea = 0;

        for (int i = 0; i < n; i++) {
            Point p1 = points.get(i);
            for (int j = i + 1; j < n; j++) {
                Point p2 = points.get(j);

                long width = Math.abs((long) p1.x - p2.x) + 1;
                long height = Math.abs((long) p1.y - p2.y) + 1;
                long area = width * height;

                if (area <= maxArea)
                    continue;

                if (isValidRectangle(p1, p2, edges)) {
                    maxArea = area;
                }
            }
        }

        return String.valueOf(maxArea);
    }

    static class Edge {
        int x1, y1, x2, y2;
        boolean isVertical;

        Edge(Point p1, Point p2) {
            this.x1 = p1.x;
            this.y1 = p1.y;
            this.x2 = p2.x;
            this.y2 = p2.y;
            this.isVertical = (x1 == x2);
        }
    }

    private boolean isValidRectangle(Point p1, Point p2, List<Edge> edges) {
        long minX = Math.min(p1.x, p2.x);
        long maxX = Math.max(p1.x, p2.x);
        long minY = Math.min(p1.y, p2.y);
        long maxY = Math.max(p1.y, p2.y);

        // Step 1: strict interior check
        // The rectangle interior is (minX < x < maxX) AND (minY < y < maxY)
        // No polygon edge can have any point in this region.

        for (Edge e : edges) {
            if (e.isVertical) {
                // Vertical edge at x = e.x1
                // Intersects interior if minX < e.x1 < maxX
                // AND the y-intervals overlap strictly.
                if (e.x1 > minX && e.x1 < maxX) {
                    long eyMin = Math.min(e.y1, e.y2);
                    long eyMax = Math.max(e.y1, e.y2);
                    // Check overlap with (minY, maxY)
                    // Overlap exists if max(minY, eyMin) < min(maxY, eyMax)
                    if (Math.max(minY, eyMin) < Math.min(maxY, eyMax)) {
                        return false;
                    }
                }
            } else {
                // Horizontal edge at y = e.y1
                // Intersects interior if minY < e.y1 < maxY
                // AND x-intervals overlap strictly
                if (e.y1 > minY && e.y1 < maxY) {
                    long exMin = Math.min(e.x1, e.x2);
                    long exMax = Math.max(e.x1, e.x2);
                    if (Math.max(minX, exMin) < Math.min(maxX, exMax)) {
                        return false;
                    }
                }
            }
        }

        // Step 2: Midpoint check
        double midX = (minX + maxX) / 2.0;
        double midY = (minY + maxY) / 2.0;

        // Check if midpoint is on boundary
        boolean onBoundary = false;
        for (Edge e : edges) {
            if (e.isVertical) {
                // x distance is |x - midX|, y range check
                if (Math.abs(e.x1 - midX) < 1e-9) {
                    double eyMin = Math.min(e.y1, e.y2);
                    double eyMax = Math.max(e.y1, e.y2);
                    if (midY >= eyMin && midY <= eyMax) {
                        onBoundary = true;
                        break;
                    }
                }
            } else {
                if (Math.abs(e.y1 - midY) < 1e-9) {
                    double exMin = Math.min(e.x1, e.x2);
                    double exMax = Math.max(e.x1, e.x2);
                    if (midX >= exMin && midX <= exMax) {
                        onBoundary = true;
                        break;
                    }
                }
            }
        }

        if (onBoundary)
            return true;

        // Step 3: Winding number / Ray casting
        // Ray to the right (positive X)
        int winding = 0;
        for (Edge e : edges) {
            if (!e.isVertical)
                continue; // Horizontal edges don't intersect horizontal ray

            // Check if edge crosses the Y level of the ray
            double y1 = e.y1;
            double y2 = e.y2;

            // Standard crossing test: (y1 > midY) != (y2 > midY)
            if ((y1 > midY) != (y2 > midY)) {
                // Compute intersection X
                // x is constant e.x1
                if (e.x1 > midX) {
                    winding++;
                }
            }
        }

        return (winding % 2) != 0;
    }
}
