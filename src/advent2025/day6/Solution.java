package src.advent2025.day6;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {
    private static final Pattern WHITESPACE_SPLIT_PATTERN = Pattern.compile("\\s+");

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("123 328  51 64 \n" +
                " 45 64  387 23 \n" +
                "  6 98  215 314\n" +
                "*   +   *   +  ");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(4277556L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(3263827L);
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        var worksheet = lines.map(line -> WHITESPACE_SPLIT_PATTERN.split(line.trim()))
                .toArray(String[][]::new);

        long grandTotal = 0;
        for (int column = 0; column < worksheet[0].length; column++) {
            var finalColumn = column;
            var problemNumbers = Arrays.stream(worksheet, 0, worksheet.length - 1)
                    .map(worksheetColumns -> worksheetColumns[finalColumn])
                    .mapToLong(Long::parseLong);
            var operation = worksheet[worksheet.length - 1][column];
            if ("*".equals(operation)) {
                grandTotal += problemNumbers.reduce(1, (a, b) -> a * b);
            } else if ("+".equals(operation)) {
                grandTotal += problemNumbers.sum();
            }
        }
        return grandTotal;
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        var worksheet = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        long grandTotal = 0;
        var problemNumbers = LongStream.builder();
        for (int column = worksheet[0].length - 1; column >= 0; --column) {
            var problemNumber = new StringBuilder();
            for (int row = 0; row < worksheet.length - 1; row++) {
                var digit = worksheet[row][column];
                if (Character.isDigit(digit)) {
                    problemNumber.append(digit);
                }
            }
            problemNumbers.accept(Long.parseLong(problemNumber.toString()));
            var operation = worksheet[worksheet.length - 1][column];
            if (!Character.isWhitespace(operation)) {
                if (operation == '*') {
                    grandTotal += problemNumbers.build().reduce(1, (a, b) -> a * b);
                } else if (operation == '+') {
                    grandTotal += problemNumbers.build().sum();
                }
                --column;
                problemNumbers = LongStream.builder();
            }

        }
        return grandTotal;
    }
}
