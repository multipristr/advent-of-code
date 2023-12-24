package src;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Stream;

public abstract class PuzzleSolver {
    public void run() {
        ExecutorService thread = Executors.newSingleThreadExecutor();
        try {
            try {
                var exampleInput1 = getExampleInput1();
                var exampleOutput1 = getExampleOutput1();
                for (int i = 0; i < exampleInput1.size(); ++i) {
                    var input = exampleInput1.get(i);
                    var output = exampleOutput1.get(i);
                    String example1Solution = thread.submit(() -> solvePartOne(input.lines())).get(1, TimeUnit.MINUTES);
                    if (!Objects.equals(example1Solution, output)) {
                        throw new IllegalStateException("Part One: Expected '" + output + "' got '" + example1Solution + "'");
                    }
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new IllegalStateException("Part One", e);
            }
            Instant start = Instant.now();
            String solution1 = solvePartOne(getInput1());
            Instant end = Instant.now();
            System.out.println("'" + solution1 + "' - took " + Duration.between(start, end).toMillis() + " milliseconds");
            var exampleOutput2 = getExampleOutput2();
            if (!exampleOutput2.isEmpty()) {
                var exampleInput2 = getExampleInput2();
                try {
                    for (int i = 0; i < exampleInput2.size(); ++i) {
                        var input = exampleInput2.get(i);
                        var output = exampleOutput2.get(i);
                        String example2Solution = thread.submit(() -> solvePartTwo(input.lines())).get(1, TimeUnit.MINUTES);
                        if (!Objects.equals(example2Solution, output)) {
                            throw new IllegalStateException("Part Two: Expected '" + output + "' got '" + example2Solution + "'");
                        }
                    }
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    throw new IllegalStateException("Part Two", e);
                }
                start = Instant.now();
                String solution2 = solvePartTwo(getInput2());
                end = Instant.now();
                System.out.println("'" + solution2 + "' - took " + Duration.between(start, end).toMillis() + " milliseconds");
            }
        } finally {
            thread.shutdownNow();
        }
    }

    public abstract List<String> getExampleInput1();

    public abstract List<String> getExampleOutput1();

    public List<String> getExampleInput2() {
        return getExampleInput1();
    }

    public abstract List<String> getExampleOutput2();

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
