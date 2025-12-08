package aoc.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Test {

    @Test
    void samplePart2() {
        Day03 d = new Day03();
        String input = """
                234234234234278""";
        assertEquals("434234234278", d.part2(input));
    }
}
