package src;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public abstract class PuzzleSolver {
    public void run() {
        List<Task> tasks = new ArrayList<>();

        var exampleInput1 = getExampleInput1();
        var exampleOutput1 = getExampleOutput1();
        if (exampleInput1.size() != exampleOutput1.size()) {
            throw new IllegalArgumentException(exampleInput1.size() + " example 1 inputs != " + exampleOutput1.size() + " example 1 outputs");
        }
        for (int i = 0; i < exampleInput1.size(); i++) {
            tasks.add(new PartOneTask(exampleInput1.get(i), exampleOutput1.get(i)));
        }
        tasks.add(new PartOneTask(getInput1()));

        var exampleInput2 = getExampleInput2();
        var exampleOutput2 = getExampleOutput2();
        if (!exampleOutput2.isEmpty()) {
            if (exampleInput2.size() != exampleOutput2.size()) {
                throw new IllegalArgumentException(exampleInput2.size() + " example 2 inputs != " + exampleOutput2.size() + " example 2 outputs");
            }
            for (int i = 0; i < exampleInput2.size(); i++) {
                tasks.add(new PartTwoTask(exampleInput2.get(i), exampleOutput2.get(i)));
            }
            tasks.add(new PartTwoTask(getInput2()));
        }

        ExecutorService thread = Executors.newFixedThreadPool(tasks.size());
        try {
            List<Future<String>> futures = thread.invokeAll(tasks);
            for (Future<String> future : futures) {
                System.out.println(future.get());
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            thread.shutdownNow();
        }
    }

    public abstract long solvePartOne(Stream<String> lines) throws Exception;

    public abstract List<String> getExampleInput1();

    public abstract List<Long> getExampleOutput1();

    public List<String> getExampleInput2() {
        return getExampleInput1();
    }

    public abstract List<Long> getExampleOutput2();

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

    public abstract long solvePartTwo(Stream<String> lines) throws Exception;

    private static abstract class Task implements Callable<String> {
        private final Stream<String> input;
        private final Long output;

        Task(Stream<String> input) {
            this.input = input;
            this.output = null;
        }

        Task(String input, long output) {
            this.input = input.lines();
            this.output = output;
        }

        Stream<String> getInput() {
            return input;
        }

        abstract String getText();

        abstract long solve() throws Exception;

        @Override
        public String call() {
            try {
                Instant start = Instant.now();
                var solution = solve();
                var duration = Duration.between(start, Instant.now());
                if (output == null) {
                    return getText() + " '" + solution + "' " + duration.toMillis() + " ms";
                } else {
                    if (output != solution) {
                        return getText() + " '" + output + "' ❌ " + duration.toMillis() + " ms | Got '" + solution + "'";
                    } else {
                        return getText() + " '" + output + "' ✔ " + duration.toMillis() + " ms";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return (output != null ? getText() + " '" + output + "' ❌ " : getText() + " ❌ ") + e;
            }
        }
    }

    private class PartOneTask extends Task {
        PartOneTask(Stream<String> input) {
            super(input);
        }

        PartOneTask(String input, long output) {
            super(input, output);
        }

        @Override
        String getText() {
            return "Part One";
        }

        @Override
        long solve() throws Exception {
            return solvePartOne(getInput());
        }
    }

    private class PartTwoTask extends Task {
        PartTwoTask(Stream<String> input) {
            super(input);
        }

        PartTwoTask(String input, long output) {
            super(input, output);
        }

        @Override
        String getText() {
            return "Part Two";
        }

        @Override
        long solve() throws Exception {
            return solvePartTwo(getInput());
        }
    }
}
