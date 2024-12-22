package src.advent2024.day22;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("1\n" +
                "10\n" +
                "100\n" +
                "2024");
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(37327623);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of();
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        return lines.parallel()
                .mapToLong(Long::parseLong)
                .map(this::generateSecretNumber)
                .sum();
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private long generateSecretNumber(long initialSecretNumber) {
        for (short i = 0; i < 2_000; i++) {
            initialSecretNumber ^= initialSecretNumber << 6;
            initialSecretNumber %= 16777216;

            initialSecretNumber ^= initialSecretNumber >>> 5;
            initialSecretNumber %= 16777216;

            initialSecretNumber ^= initialSecretNumber << 11;
            initialSecretNumber %= 16777216;
        }
        return initialSecretNumber;
    }
}
