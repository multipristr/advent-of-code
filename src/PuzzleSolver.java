package src;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class PuzzleSolver {
    public void run() {
        String example1Solution = solvePartOne(getExampleInput1().lines());
        if (!Objects.equals(example1Solution, getExampleOutput1())) {
            throw new IllegalStateException("Part One: Expected '" + getExampleOutput1() + "' got '" + example1Solution + "'");
        }
        Instant start = Instant.now();
        String solution1 = solvePartOne(getInput1());
        Instant end = Instant.now();
        System.out.println("'" + solution1 + "' - took " + Duration.between(start, end).toMillis() + " milliseconds");
        if (getExampleOutput2() != null) {
            String example2Solution = solvePartTwo(getExampleInput2().lines());
            if (!Objects.equals(example2Solution, getExampleOutput2())) {
                throw new IllegalStateException("Part Two: Expected '" + getExampleOutput2() + "' got '" + example2Solution + "'");
            }
            start = Instant.now();
            String solution2 = solvePartTwo(getInput2());
            end = Instant.now();
            System.out.println("'" + solution2 + "' - took " + Duration.between(start, end).toMillis() + " milliseconds");
        }
    }

    public abstract String getExampleInput1();

    public abstract String getExampleOutput1();

    public String getExampleInput2() {
        return getExampleInput1();
    }

    public abstract String getExampleOutput2();

    public Stream<String> getInput1() {
        try {
            return Files.lines(Paths.get(".").toAbsolutePath().resolve(getClass().getPackageName().replaceAll("\\.", "/")).resolve("input.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Stream<String> getInput2() {
        return getInput1();
    }

    public abstract String solvePartOne(Stream<String> lines);

    public abstract String solvePartTwo(Stream<String> lines);
}
