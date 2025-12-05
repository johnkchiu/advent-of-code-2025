package aoc.days;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Day01Test {
    @Test
    void samplePart1() {
        Day01 d = new Day01();
        String input = "1\n2\n3\n";
        assertEquals("3", d.part1(input));
    }

    @Test
    void samplePart2() {
        Day01 d = new Day01();
        String input = "4\n5\n";
        assertEquals("20", d.part2(input));
    }
}

