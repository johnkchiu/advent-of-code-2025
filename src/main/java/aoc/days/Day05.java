package aoc.days;

import aoc.core.Day;

public class Day05 implements Day {
    @Override
    public String part1(String input) {
        String[] sections = input.split("\\n\\s*\\n");
        if (sections.length < 2) return "0";
        String[] rangeLines = sections[0].split("\\n");
        String[] idLines = sections[1].split("\\n");

        // Parse ranges
        java.util.List<long[]> ranges = new java.util.ArrayList<>();
        for (String line : rangeLines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);
            ranges.add(new long[]{start, end});
        }

        int freshCount = 0;
        for (String idLine : idLines) {
            idLine = idLine.trim();
            if (idLine.isEmpty()) continue;
            long id = Long.parseLong(idLine);
            boolean fresh = false;
            for (long[] range : ranges) {
                if (id >= range[0] && id <= range[1]) {
                    fresh = true;
                    break;
                }
            }
            if (fresh) freshCount++;
        }
        return Integer.toString(freshCount);
    }

    @Override
    public String part2(String input) {
        String[] sections = input.split("\n\s*\n");
        if (sections.length < 1) return "0";
        String[] rangeLines = sections[0].split("\n");

        java.util.List<long[]> ranges = new java.util.ArrayList<>();
        for (String line : rangeLines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);
            ranges.add(new long[]{start, end});
        }

        // Merge overlapping/adjacent ranges
        ranges.sort(java.util.Comparator.comparingLong(a -> a[0]));
        java.util.List<long[]> merged = new java.util.ArrayList<>();
        for (long[] range : ranges) {
            if (merged.isEmpty()) {
                merged.add(range);
            } else {
                long[] last = merged.get(merged.size() - 1);
                if (range[0] <= last[1] + 1) {
                    last[1] = Math.max(last[1], range[1]);
                } else {
                    merged.add(range);
                }
            }
        }

        long total = 0;
        for (long[] m : merged) {
            total += (m[1] - m[0] + 1);
        }
        return Long.toString(total);
    }
}
