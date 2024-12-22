package src.advent2024.day22;

import src.PuzzleSolver;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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
    public List<String> getExampleInput2() {
        return List.of("1\n" +
                "2\n" +
                "3\n" +
                "2024");
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(23);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        return lines.parallel()
                .mapToLong(Long::parseLong)
                .map(initialSecretNumber -> {
                    for (short i = 0; i < 2000; i++) {
                        initialSecretNumber = generateNextSecretNumber(initialSecretNumber);
                    }
                    return initialSecretNumber;
                })
                .sum();
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        Map<String, Long> sequences = new ConcurrentHashMap<>();
        lines.parallel()
                .mapToLong(Long::parseLong)
                .forEach(initialSecretNumber -> calculateSequenceBananas(sequences, initialSecretNumber));

        return sequences.values().stream().max(Comparator.naturalOrder()).orElseThrow();
    }

    private void calculateSequenceBananas(Map<String, Long> sequences, long initialSecretNumber) {
        Queue<String> priceChanges = new LinkedList<>();
        Set<String> seenSequences = new HashSet<>();

        var previousLastDigit = initialSecretNumber % 10;
        for (short i = 0; i < 2000; i++) {
            var secretNumber = generateNextSecretNumber(initialSecretNumber);
            var lastDigit = secretNumber % 10;
            var priceChange = lastDigit - previousLastDigit;

            priceChanges.add("" + priceChange);
            if (priceChanges.size() >= 4) {
                var sequence = String.join(",", priceChanges);
                if (seenSequences.add(sequence)) {
                    sequences.merge(sequence, lastDigit, Long::sum);
                }
                priceChanges.poll();
            }

            initialSecretNumber = secretNumber;
            previousLastDigit = lastDigit;
        }
    }

    private long generateNextSecretNumber(long secretNumber) {
        secretNumber ^= secretNumber << 6;
        secretNumber %= 16777216;

        secretNumber ^= secretNumber >> 5;
        secretNumber %= 16777216;

        secretNumber ^= secretNumber << 11;
        secretNumber %= 16777216;

        return secretNumber;
    }
}
