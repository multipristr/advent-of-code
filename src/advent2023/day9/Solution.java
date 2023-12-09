package src.advent2023.day9;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public String getExampleInput1() {
        return "0 3 6 9 12 15\n" +
                "1 3 6 10 15 21\n" +
                "10 13 16 21 30 45";
    }

    @Override
    public String getExampleOutput1() {
        return "114";
    }

    @Override
    public String getExampleOutput2() {
        return "2";
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        return lines.parallel()
                .map(line -> line.split("\\s"))
                .map(parts -> Arrays.stream(parts).mapToInt(Integer::parseInt).toArray())
                .mapToInt(this::calculatePrediction)
                .sum() + "";
    }

    private int calculatePrediction(int[] history) {
        int[] newSequence = new int[history.length - 1];
        boolean anyNonZero = false;
        for (int i = 1; i < history.length; i++) {
            int difference = history[i] - history[i - 1];
            anyNonZero |= difference != 0;
            newSequence[i - 1] = difference;
        }
        if (!anyNonZero) {
            return history[history.length - 1];
        }
        return history[history.length - 1] + calculatePrediction(newSequence);
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return lines.parallel()
                .map(line -> line.split("\\s"))
                .map(parts -> Arrays.stream(parts).mapToInt(Integer::parseInt).toArray())
                .mapToInt(this::calculatePredictionBackwards)
                .sum() + "";
    }

    private int calculatePredictionBackwards(int[] history) {
        int[] newSequence = new int[history.length - 1];
        boolean anyNonZero = false;
        for (int i = 1; i < history.length; i++) {
            int difference = history[i] - history[i - 1];
            anyNonZero |= difference != 0;
            newSequence[i - 1] = difference;
        }
        if (!anyNonZero) {
            return history[0];
        }
        return history[0] - calculatePredictionBackwards(newSequence);
    }
}
