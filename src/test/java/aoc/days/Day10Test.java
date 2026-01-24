package aoc.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Day10Test {

    @Test
    void samplePart1() {
        Day10 d = new Day10();
        String input = aoc.Input.read(10, true);
        assertEquals("7", d.part1(input));
    }

    @Test
    void samplePart2() {
        Day10 d = new Day10();
        String input = aoc.Input.read(10, true);
        assertEquals("0", d.part2(input));
    }
}