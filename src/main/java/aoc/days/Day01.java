package aoc.days;

import aoc.core.Day;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * See https://adventofcode.com/2025/day/1
 */
public class Day01 implements Day {
    @Override
    public String part1(String input) {
        AtomicInteger start = new AtomicInteger(50);
        AtomicInteger count = new AtomicInteger();

        System.out.println("  - The dial starts by pointing at " + start + ".");

        Arrays.stream(input.trim().split("\n"))
                .filter(s -> !s.isBlank())
                .forEach(s -> {
                    // if 1st char in s is "L", then subtract the integer value of the rest of the string from start
                    int num = Integer.parseInt(s.substring(1).trim());
                    if (s.charAt(0) == 'L') {
                        start.addAndGet(-num);
                        while (start.intValue() < 0) {
                            start.addAndGet(100);
                        }
                    } else if (s.charAt(0) == 'R') {
                        start.addAndGet(num);
                        while (start.intValue() > 99) {
                            start.addAndGet(-100);
                        }
                    }
                    if (start.get() == 0) {
                        count.getAndIncrement();
                    }
                    System.out.println("  - The dial is rotated " + s + " to point at " + start + ".");
                });
        return Integer.toString(count.get());
    }

    @Override
    public String part2(String input) {
        AtomicInteger start = new AtomicInteger(50);
        AtomicInteger count = new AtomicInteger();

        System.out.println("  - The dial starts by pointing at " + start + ".");

        Arrays.stream(input.trim().split("\n"))
                .filter(s -> !s.isBlank())
                .forEach(s -> {
                    // if 1st char in s is "L", then subtract the integer value of the rest of the string from start
                    int num = Integer.parseInt(s.substring(1).trim());
                    // move 1 step at a time !!
                    for (int i = 0; i < num; i++) {
                        if (s.charAt(0) == 'L') {
                            start.addAndGet(-1);
                            while (start.intValue() < 0) {
                                start.addAndGet(100);
                            }
                        } else if (s.charAt(0) == 'R') {
                            start.addAndGet(1);
                            while (start.intValue() > 99) {
                                start.addAndGet(-100);
                            }
                        }
                        if (start.get() == 0) {
                            count.getAndIncrement();
                        }
                    }
                    System.out.println("  - The dial is rotated " + s + " to point at " + start + ".");
                });
        return Integer.toString(count.get());    }
}

