package aoc.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Day05Test {

    @Test
    void testPart1_sample() {
        String input = aoc.Input.read(5, true);
        Day05 day = new Day05();
        assertEquals("3", day.part1(input));
    }

    @Test
    void testPart2_sample() {
        String input = aoc.Input.read(5, true);
        Day05 day = new Day05();
        assertEquals("14", day.part2(input));
    }
}
