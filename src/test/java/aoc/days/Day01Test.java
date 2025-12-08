package aoc.days;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Day01Test {
    @Test
    void samplePart1() {
        Day01 d = new Day01();
        String input = """
                L68
                L30
                R48
                L5
                R60
                L55
                L1
                L99
                R14
                L82""";
        assertEquals("3", d.part1(input));
    }

    @Test
    void samplePart2() {
        Day01 d = new Day01();
        String input = """
                L68
                L30
                R48
                L5
                R60
                L55
                L1
                L99
                R14
                L82""";
        assertEquals("6", d.part2(input));
    }
}
