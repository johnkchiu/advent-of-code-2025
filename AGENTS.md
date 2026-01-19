# Advent of Code 2025 Java Template - Codebase Analysis

## Overview
This is a Java template project for solving Advent of Code 2025 programming puzzles. It provides a structured framework for implementing daily challenges with clean separation of concerns and automated input handling.

## Architecture

### Core Components

**Day Interface** (`src/main/java/aoc/core/Day.java`)
- Defines the contract for each day's solution
- Requires two methods: `part1(String input)` and `part2(String input)`
- Returns solutions as strings

**Runner Class** (`src/main/java/aoc/Runner.java`)
- Main entry point for executing solutions
- Command line interface: `Runner <day-number> [--sample]`
- Measures execution time for both parts
- Currently supports days 1-5 (registered in `Runner.DAYS`)

**Input Class** (`src/main/java/aoc/Input.java`)
- Handles input file loading from resources
- Supports real input (`dayXX.txt`) and sample input (`dayXX_sample.txt`)
- Normalizes line endings (handles Windows/Unix differences)

### Project Structure
- **Build System**: Gradle with Java 21 toolchain
- **Testing**: JUnit 5 framework
- **Input Management**: Files in `inputs/` directory copied to resources during build
- **Package Structure**: `aoc.core` (interfaces), `aoc.days` (implementations)

## Implemented Problems

**Day 1**: Dial Rotation Problem
- Simulates a circular dial (0-99) with left/right rotations
- Input format: `L<num>` or `R<num>` (e.g., "L68", "R48")
- Part 1: Count passes through 0 when moved in full steps
- Part 2: Count passes through 0 when moved one step at a time

**Day 2**: Invalid Number Detection
- Processes comma-separated ranges (e.g., "11-22,95-115")
- Part 1: Finds palindromic numbers (even length, mirrored digits)
- Part 2: Finds numbers composed of repeating substrings

**Day 3**: Implemented but not analyzed in detail

**Day 4**: Implemented but not analyzed in detail

**Day 5**: Implemented but not analyzed in detail

**Day 6**: Trash Compactor - Math Worksheet Solver ✅
- Parses vertical math problems from a worksheet grid
- Each problem consists of numbers stacked vertically with + or * operator at bottom
- Input format: numbers separated by spaces, operators on bottom row
- Part 1: Solve all problems with mathematical operations and sum results (Answer: 3,261,038,365,331)
- Part 2: Cephalopod math - read right-to-left in columns, numbers formed vertically (Answer: 8,342,588,849,093)

## Usage Patterns

**Running Solutions**:
```bash
./gradlew run --args "1"           # Run day 1 with real input
./gradlew run --args "1 --sample"  # Run day 1 with sample input
```

**Adding New Days**:
1. Create `src/main/java/aoc/days/DayNN.java` implementing `Day`
2. Register in `Runner.DAYS` map
3. Add input files: `inputs/dayNN.txt` and optionally `inputs/dayNN_sample.txt`
4. (Optional) Add tests in `src/test/java/aoc/days/DayNNTest.java`

## Technical Details

**Build Configuration** (`build.gradle.kts`):
- Java 21 toolchain
- JUnit 5 for testing
- Application plugin with main class `aoc.Runner`
- Automatic resource copying from `inputs/` directory

**Input File Format**:
- Resources loaded as: `dayXX.txt` or `dayXX_sample.txt`
- UTF-8 encoding with normalized line endings

**Performance Monitoring**:
- Execution time measurement in milliseconds
- Output format includes timing for both parts

This codebase prioritizes algorithmic problem-solving over boilerplate, making it easy to focus on implementing solutions for Advent of Code challenges.