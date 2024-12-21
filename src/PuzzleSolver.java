package src;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class PuzzleSolver {

    public void run() {
        var tasks = new ArrayList<Task>();

        var exampleInput1 = getExampleInput1();
        var exampleOutput1 = getExampleOutput1();
        if (exampleInput1.size() != exampleOutput1.size()) {
            throw new IllegalArgumentException(exampleInput1.size() + " example part one inputs ≠ " + exampleOutput1.size() + " example part one outputs");
        }
        for (int i = 0; i < exampleInput1.size(); i++) {
            tasks.add(new PartOneTask(exampleInput1.get(i), exampleOutput1.get(i)));
        }
        tasks.add(new PartOneTask(getInput1()));

        var exampleInput2 = getExampleInput2();
        var exampleOutput2 = getExampleOutput2();
        if (!exampleOutput2.isEmpty()) {
            if (exampleInput2.size() != exampleOutput2.size()) {
                throw new IllegalArgumentException(exampleInput2.size() + " example part two inputs ≠ " + exampleOutput2.size() + " example part two outputs");
            }
            for (int i = 0; i < exampleInput2.size(); i++) {
                tasks.add(new PartTwoTask(exampleInput2.get(i), exampleOutput2.get(i)));
            }
            tasks.add(new PartTwoTask(getInput2()));
        }

        var thread = Executors.newFixedThreadPool(tasks.size());
        try {
            var futures = tasks.stream()
                    .map(thread::submit)
                    .collect(Collectors.toList());
            for (var future : futures) {
                System.out.println(future.get());
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            thread.shutdownNow();
        }
    }

    public abstract Comparable<?> solvePartOne(Stream<String> lines) throws Exception;

    public abstract List<String> getExampleInput1();

    public abstract List<Comparable<?>> getExampleOutput1();

    public List<String> getExampleInput2() {
        return getExampleInput1();
    }

    public abstract List<Comparable<?>> getExampleOutput2();

    public Stream<String> getInput1() {
        try {
            return Files.lines(Paths.get(".").toAbsolutePath().resolve(getClass().getPackageName().replaceAll("\\.", "/")).resolve("input.txt"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Stream<String> getInput2() {
        return getInput1();
    }

    public abstract Comparable<?> solvePartTwo(Stream<String> lines) throws Exception;

    private static abstract class Task implements Callable<String> {

        private final Stream<String> input;
        private final Comparable<?> output;

        private Task(Stream<String> input) {
            this.input = input;
            this.output = null;
        }

        private Task(String input, Comparable<?> output) {
            this.input = input.lines();
            this.output = output;
        }

        Stream<String> getInput() {
            return input;
        }

        abstract String getText();

        abstract Comparable<?> solve() throws Exception;

        private static String red(Object text) {
            return "\033[0;31m" + text + "\033[0m";
        }

        private static String green(Object text) {
            return "\033[0;32m" + text + "\033[0m";
        }

        private static String bold(Object text) {
            return "\033[0;1m" + text + "\033[0m";
        }

        private static String boldUnderline(Object text) {
            return "\033[4;1m" + text + "\033[0m";
        }

        @Override
        public String call() {
            try {
                var start = Instant.now();
                var solution = solve();
                var duration = Duration.between(start, Instant.now());
                if (output == null) {
                    return boldUnderline(getText()) + " '" + bold(solution) + "' " + duration.toMillis() + " ms";
                } else {
                    boolean isCorrect = solution instanceof Number
                            ? ((Number) output).longValue() == ((Number) solution).longValue()
                            : output.equals(solution);
                    if (isCorrect) {
                        return green(getText()) + " '" + green(output) + "' ✅ " + duration.toMillis() + " ms";
                    } else {
                        return red(getText()) + " '" + red(output) + "' ❌ " + duration.toMillis() + " ms | Got '" + solution + "'";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return output != null ? red(getText()) + " '" + red(output) + "' ❌ " : red(getText()) + " ❌ " + e;
            }
        }

    }

    private class PartOneTask extends Task {

        private PartOneTask(Stream<String> input) {
            super(input);
        }

        private PartOneTask(String input, Comparable<?> output) {
            super(input, output);
        }

        @Override
        String getText() {
            return "Part One";
        }

        @Override
        Comparable<?> solve() throws Exception {
            return solvePartOne(getInput());
        }

    }

    private class PartTwoTask extends Task {

        private PartTwoTask(Stream<String> input) {
            super(input);
        }

        private PartTwoTask(String input, Comparable<?> output) {
            super(input, output);
        }

        @Override
        String getText() {
            return "Part Two";
        }

        @Override
        Comparable<?> solve() throws Exception {
            return solvePartTwo(getInput());
        }

    }

}
