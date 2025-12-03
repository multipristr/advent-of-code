package src.advent2025.day2;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(1227775554L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(4174379265L);
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        return solve(lines, this::findIdsWithDigitsRepeatedTwiceInRange);
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        return solve(lines, this::findIdsWithDigitsRepeatedAtLeastTwiceInRange);
    }

    private long solve(Stream<String> lines, BiFunction<Long, Long, LongStream> findInvalidIdsInRange) {
        return lines.parallel()
                .flatMap(line -> Arrays.stream(line.split(",")))
                .flatMapToLong(range -> {
                    var dashIndex = range.indexOf("-");
                    var firstId = range.substring(0, dashIndex);
                    var lastId = range.substring(dashIndex + 1);
                    return findInvalidIdsInRange.apply(Long.parseLong(firstId), Long.parseLong(lastId));
                })
                .sum();
    }

    private LongStream findIdsWithDigitsRepeatedTwiceInRange(long firstId, long lastId) {
        var streamBuilder = LongStream.builder();
        for (long id = firstId; id <= lastId; id++) {
            var idString = Long.toString(id);
            var middleIndex = idString.length() >> 1;
            var firstHalf = idString.substring(0, middleIndex);
            var secondHalf = idString.substring(middleIndex);
            if (firstHalf.equals(secondHalf)) {
                streamBuilder.add(id);
            }
        }
        return streamBuilder.build();
    }

    private LongStream findIdsWithDigitsRepeatedAtLeastTwiceInRange(long firstId, long lastId) {
        var streamBuilder = LongStream.builder();
        outerLoop:
        for (long id = firstId; id <= lastId; id++) {
            var idString = Long.toString(id);
            var middleIndex = idString.length() >> 1;
            for (int digits = 1; digits <= middleIndex; digits++) {
                var subRange = idString.substring(0, digits);
                if (idString.matches('(' + subRange + ")+")) {
                    streamBuilder.add(id);
                    continue outerLoop;
                }
            }
        }
        return streamBuilder.build();
    }
}
