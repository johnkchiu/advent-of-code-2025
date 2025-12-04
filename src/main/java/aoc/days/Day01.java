package aoc.days;

import aoc.core.Day;

import java.util.Arrays;

public class Day01 implements Day {
    @Override
    public String part1(String input) {
        // Example: sum of integers, one per line
        long sum = Arrays.stream(input.trim().split("\n"))
                .filter(s -> !s.isBlank())
                .mapToLong(Long::parseLong)
                .sum();
        return Long.toString(sum);
    }

    @Override
    public String part2(String input) {
        // Example: product of first two integers
        long[] nums = Arrays.stream(input.trim().split("\n"))
                .filter(s -> !s.isBlank())
                .mapToLong(Long::parseLong)
                .toArray();
        if (nums.length < 2) return "0";
        return Long.toString(nums[0] * nums[1]);
    }
}

