package aoc.days;

import aoc.core.Day;

public class Day03 implements Day {
    public int indexOfLargestDigit(String s) {
        int maxDigit = -1;
        int maxIndex = -1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int digit = Character.getNumericValue(c);
                if (digit > maxDigit) {
                    maxDigit = digit;
                    maxIndex = i;
                }
            }
        }
        return maxIndex;
    }

    public int indexOfLargestDigit(String s, int left, int right) {
        int maxDigit = -1;
        int maxIndex = -1;
        for (int i = left; i < right && i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int digit = Character.getNumericValue(c);
                if (digit > maxDigit) {
                    maxDigit = digit;
                    maxIndex = i;
                }
            }
        }
        return maxIndex;
    }

    @Override
    public String part1(String input) {
        int sum = 0;

        for (String line : input.trim().split("\n")) {
            // find the largest digit (except the last #)
            int maxIndex = indexOfLargestDigit(line.substring(0, line.length() - 1));
            // find 2nd largest digit and add to sum
            String line2 = line.substring(maxIndex + 1);
            int maxIndex2 = indexOfLargestDigit(line.substring(maxIndex + 1));
            System.out.println("Number: " + line.charAt(maxIndex) + line2.charAt(maxIndex2));
            sum += Integer.parseInt("" + line.charAt(maxIndex) + line2.charAt(maxIndex2));
        }

        return String.valueOf(sum);
    }

    @Override
    public String part2(String input) {
        int minLength = 12;
        long sum = 0;

        for (String line : input.trim().split("\n")) {
            // iterate thru the min length of string
            StringBuilder sb = new StringBuilder();
            int maxIndex = 0;
            int left = 0;
            int right = line.length() - (minLength - 1);

            for (int i = 0; i < minLength; i++) {
                maxIndex = indexOfLargestDigit(line, left, right);
                sb.append(line.charAt(maxIndex));
                left = maxIndex + 1;
                right = line.length() - (minLength - 1) + i + 1;
            }
            sum += Long.parseLong(sb.toString());
        }

        return String.valueOf(sum);
    }
}
