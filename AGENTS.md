# Advent of Code 2025 Java Template - Agent Development Guide

## Build & Development Commands

```bash
./gradlew build                    # Build project
./gradlew test                     # Run all tests
./gradlew check                    # Run all checks (build + test)
./gradlew run --args "1"           # Run day 1 with real input
./gradlew run --args "1 --sample"  # Run day 1 with sample input

# Single test execution
./gradlew test --tests "aoc.days.Day01Test"
./gradlew test --tests "aoc.days.Day01Test.samplePart1"
./gradlew test --tests "Day01Test"     # Short form works too
```

**Development Workflow**: `./gradlew test --tests "DayXXTest"` → `./gradlew run --args "XX"`

## Code Style Guidelines

### Naming Conventions
- **Classes**: `PascalCase` (e.g., `Day01`, `TrashCompactor`)
- **Methods**: `camelCase` (e.g., `part1`, `solveGrid`, `findOperators`)
- **Variables**: `camelCase` (e.g., `grandTotal`, `numRows`, `maxArea`)
- **Constants**: `UPPER_SNAKE_CASE` (e.g., `DAYS` map in Runner)
- **Packages**: lowercase (e.g., `aoc.core`, `aoc.days`)

### Import Organization
```java
// Standard Java imports first
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Project imports
import aoc.core.Day;
import aoc.Input;
```
- No wildcard imports except in rare cases
- Project imports: `aoc.core.Day`, `aoc.Input`

### Formatting Standards
- **Indentation**: 4 spaces (no tabs)
- **Braces**: Opening brace on same line, closing on new line
- **Line Length**: Keep under 100 characters
- **Spacing**: Single blank line between methods

### Class Structure
```java
package aoc.days;

import aoc.core.Day;

/**
 * See https://adventofcode.com/2025/day/XX
 */
public class DayXX implements Day {
    
    @Override
    public String part1(String input) {
        // Implementation
    }
    
    @Override  
    public String part2(String input) {
        // Implementation
    }
    
    private long calculateResult(List<Long> numbers, char operator) {
        // Helper logic
    }
}
```

## Architecture & Patterns

### Core Interface Contract
```java
public interface Day {
    String part1(String input);  // Raw input string → string result
    String part2(String input);  // Raw input string → string result
}
```
- Both methods accept raw input string, return string result
- No exception handling in interface
- Use `String.valueOf()` for numeric returns

### Input Handling Patterns
```java
// Standard input processing
String[] lines = input.trim().split("\n");
int numRows = lines.length;

// Stream processing (when appropriate)
Arrays.stream(input.trim().split("\n"))
    .filter(s -> !s.isBlank())
    .forEach(line -> {
        // Process each line
    });
```

### Error Handling
- Minimal try-catch blocks
- Let most errors propagate as `RuntimeException`
- Basic validation: null/empty checks when needed
- Input class throws `RuntimeException` for file I/O errors

## Testing Guidelines

### Test Structure
```java
public class DayXXTest {
    @Test
    void samplePart1() {
        DayXX d = new DayXX();
        String input = aoc.Input.read(1, true);  // File-based
        // OR embedded string literal
        assertEquals("expected", d.part1(input));
    }
    
    @Test  
    void samplePart2() {
        // Same pattern for part 2
    }
}
```

### Testing Patterns
- **File-based**: Use `aoc.Input.read(1, true)` for sample files
- **Embedded**: Use string literals for simple test cases  
- **Assertions**: `assertEquals(expected, actual)` pattern
- **No mocking**: Pure unit tests with real implementations

## Implementation Patterns

### Common Approaches
1. **Helper Methods**: Extract complex logic into private methods
2. **StringBuilder**: Use for string construction in loops
3. **Primitive Types**: Prefer `int`, `long` over boxed types
4. **Stream API**: Use when it improves readability, not always

### Algorithm Design
- Focus on clarity over micro-optimization
- Use appropriate data structures (ArrayList, HashMap, etc.)
- Consider time complexity but prioritize correctness
- Leverage Java 21 features when helpful

### Debug Output
```java
// Acceptable for complex problems
System.out.println("Problem at column " + col + ": " + 
    numbers + " " + operator + " = " + result);
```
- Use sparingly, mainly for complex multi-step problems
- Focus on key intermediate results, not tracing

## Adding New Days

### Step-by-Step Process
1. **Create Implementation**: `src/main/java/aoc/days/DayXX.java`
2. **Register in Runner**: Add to `Runner.DAYS` map
3. **Add Input Files**: `inputs/dayXX.txt` and `inputs/dayXX_sample.txt`
4. **Create Tests**: `src/test/java/aoc/days/DayXXTest.java`
5. **Verify**: Run sample tests, then real input

### Registration Pattern
```java
// In Runner.java, add to DAYS map:
DAYS.put(1, new Day01());
DAYS.put(2, new Day02());
DAYS.put(XX, new DayXX());  // Replace XX with your day number
```

## Performance Considerations

### Timing
- Runner automatically measures execution time
- Focus on algorithmic efficiency, not micro-optimizations
- Use appropriate data structures for problem scale

### Memory
- Generally memory-efficient solutions expected
- Primitive types preferred over boxed types
- Avoid unnecessary object creation in hot loops

## Code Quality Standards

### What to Avoid
- Wildcard imports (except rare cases)
- Debug print statements in final production code
- Complex nested expressions without helper methods
- Inconsistent error handling patterns

### What to Emphasize  
- Clean, readable algorithmic implementations
- Consistent naming and formatting
- Proper separation of concerns
- Comprehensive sample testing

This codebase prioritizes algorithmic problem-solving with minimal boilerplate, enabling focus on implementing clean Advent of Code solutions.

## Tools & Environment

### Java Version
- **Target**: Java 21
- **Features**: Leverage Java 21 features when helpful
- **Compatibility**: Project uses Java 21 toolchain

### Build System
- **Gradle**: Kotlin DSL (`build.gradle.kts`)
- **Testing**: JUnit 5.10.2
- **Application**: Main class `aoc.Runner`
- **Resources**: Input files copied to resources on build

### IDE Integration
- No IDE configuration required
- Standard Gradle project structure