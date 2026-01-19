package aoc;

import java.util.Map;

import aoc.core.Day;
import aoc.days.Day01;
import aoc.days.Day02;
import aoc.days.Day03;
import aoc.days.Day04;
import aoc.days.Day05;
import aoc.days.Day06;
import aoc.days.Day07;
import aoc.days.Day08;
import aoc.days.Day09;

public class Runner {

    private static final Map<Integer, Day> DAYS = Map.ofEntries(
            Map.entry(1, new Day01()),
            Map.entry(2, new Day02()),
            Map.entry(3, new Day03()),
            Map.entry(4, new Day04()),
            Map.entry(5, new Day05()),
            Map.entry(6, new Day06()),
            Map.entry(7, new Day07()),
            Map.entry(8, new Day08()),
            Map.entry(9, new Day09()));

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: Runner <day-number> [--sample]");
            System.exit(1);
        }
        int day = Integer.parseInt(args[0]);
        boolean sample = args.length > 1 && args[1].equals("--sample");
        Day solver = DAYS.get(day);
        if (solver == null) {
            System.err.println("Day " + day + " not implemented yet.");
            System.exit(2);
        }
        String input = Input.read(day, sample);
        long start = System.nanoTime();
        String part1 = solver.part1(input);
        String part2 = solver.part2(input);
        long end = System.nanoTime();
        System.out.println("Day " + day + ":");
        System.out.println("  Part 1: " + part1);
        System.out.println("  Part 2: " + part2);
        System.out.printf("  Time: %.2f ms\n", (end - start) / 1_000_000.0);
    }
}
