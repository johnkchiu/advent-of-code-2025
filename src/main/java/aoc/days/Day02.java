package aoc.days;

import aoc.core.Day;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day02 implements Day {

    public boolean isInvalid(long num) {
        String s = Long.toString(num);
        //  if num length is odd, return true
        if (s.length() % 2 != 0) {
            return false;
        }

        // if num is repeated twice, return false
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() / 2 + i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String part1(String input) {
        List<Long> invalids = new LinkedList<>();

        Arrays.stream(input.split(","))
                .forEach(s -> {
                    String[] range = s.split("-");
                    // loop thru range and print out the number
                    for (long i = Long.parseLong(range[0]); i <= Long.parseLong(range[1]); i++) {
                        if (isInvalid(i)) {
                            System.out.println("Invalid: " + i);
                            invalids.add(i);
                        }
                    }
                });

        System.out.println(invalids);
        // sum invalids list of Long and return a string
        return invalids.stream().mapToLong(Long::longValue).sum() + "";
    }

    public boolean isInvalidPart2(long num) {
        // convert to string
        String s = Long.toString(num);

        // loop thru possible substrings (from 1 to half of string)
        for (int i = 1; i <= s.length() / 2; i++) {
            // only true if substring length is divisible by string length
            if (s.length() % i != 0) {
                continue;
            }

            String sub = s.substring(0, i);
            StringBuilder sb = new StringBuilder();
            // build string by repeating substring
            for (int j = 0; j < s.length() / i; j++) {
                sb.append(sub);
            }
            if (sb.toString().equals(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String part2(String input) {
        List<Long> invalids = new LinkedList<>();

        Arrays.stream(input.split(","))
                .forEach(s -> {
                    String[] range = s.split("-");
                    // loop thru range and print out the number
                    for (long i = Long.parseLong(range[0]); i <= Long.parseLong(range[1]); i++) {
                        if (isInvalidPart2(i)) {
                            System.out.println("Invalid: " + i);
                            invalids.add(i);
                        }
                    }
                });

        System.out.println(invalids);
        // sum invalids list of Long and return a string
        return invalids.stream().mapToLong(Long::longValue).sum() + "";
    }
}
