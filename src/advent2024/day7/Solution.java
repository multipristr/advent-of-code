package src.advent2024.day7;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.List;
import java.util.function.LongBinaryOperator;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("190: 10 19\n" +
                "3267: 81 40 27\n" +
                "83: 17 5\n" +
                "156: 15 6\n" +
                "7290: 6 8 6 15\n" +
                "161011: 16 10 13\n" +
                "192: 17 8 14\n" +
                "21037: 9 7 18 13\n" +
                "292: 11 6 16 20");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(3749L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(11387L);
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        LongBinaryOperator[] operations = {
                Long::sum,
                (a, b) -> a * b
        };
        return determineTotalCalibrationResult(lines, operations);
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        LongBinaryOperator[] operations = {
                Long::sum,
                (a, b) -> a * b,
                (a, b) -> Long.parseLong(a + "" + b)
        };
        return determineTotalCalibrationResult(lines, operations);
    }

    private long determineTotalCalibrationResult(Stream<String> lines, LongBinaryOperator[] operations) {
        return lines.mapToLong(line -> {
                    String[] parts = line.split(":?\\s");
                    long testValue = Long.parseLong(parts[0]);
                    long[] numbers = Arrays.stream(parts, 1, parts.length)
                            .mapToLong(Long::parseLong)
                            .toArray();
                    return isTrueByOperations(testValue, numbers, operations, 1, numbers[0]) ? testValue : 0;
                })
                .sum();
    }

    private boolean isTrueByOperations(long testValue, long[] numbers, LongBinaryOperator[] operations, int index, long carry) {
        if (carry > testValue) {
            return false;
        }
        if (index >= numbers.length) {
            return testValue == carry;
        }
        return Arrays.stream(operations)
                .anyMatch(operation ->
                        isTrueByOperations(testValue, numbers, operations, index + 1, operation.applyAsLong(carry, numbers[index])));
    }
}
