package aoc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Input {
    public static String read(int day, boolean sample) {
        String filename = String.format("day%02d%s.txt", day, sample ? "_sample" : "");
        try (InputStream in = Input.class.getClassLoader().getResourceAsStream(filename)) {
            if (in == null) {
                throw new RuntimeException("Input file not found: " + filename);
            }
            byte[] bytes = in.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8).replace("\r\n", "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

