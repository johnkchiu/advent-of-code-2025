gits
# Advent of Code 2025 - Java Template

This is a minimal Java template using Gradle for solving Advent of Code.

Features:
- Java 21 toolchain
- Simple `Day` interface with `part1` and `part2`
- Runner to execute a specific day and load input from resources

## Layout
- `src/main/java/aoc/core/Day.java` – base interface
- `src/main/java/aoc/Runner.java` – main entry point `aoc.Runner`
- `src/main/java/aoc/Input.java` – reads input from resources
- `src/main/java/aoc/days/Day01.java` – sample day implementation
- `inputs/` – put your `dayXX.txt` and optional `dayXX_sample.txt` files here
- `src/test/java/aoc/days/Day01Test.java` – sample test for Day 01

## Usage

Place your input files under `inputs/` (they are copied to resources on build).

Run tests:

```fish
./gradlew test
```

Run a day:

```fish
./gradlew run --args "1"
./gradlew run --args "1 --sample"
```

Add new day:
- Create `src/main/java/aoc/days/DayNN.java` implementing `Day`
- Register it in `Runner.DAYS`
- (optional) Add tests in `src/test/java` 

