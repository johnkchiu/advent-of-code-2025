package aoc.days;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Day03Test {

    @Test
    void samplePart1() {
        Day03 d = new Day03();
        String input = aoc.Input.read(3, true);
        assertEquals("357", d.part1(input));
    }

    @Test
    void samplePart2() {
        Day03 d = new Day03();
        String input = aoc.Input.read(3, true);
        assertEquals("3121910778619", d.part2(input));
    }
}
