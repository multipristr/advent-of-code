package src.advent2024.day2;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("7 6 4 2 1\n" +
                "1 2 7 8 9\n" +
                "9 7 6 2 1\n" +
                "1 3 2 4 5\n" +
                "8 6 4 4 1\n" +
                "1 3 6 7 9");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(2L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of();
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        return lines.map(line -> line.split("\\s+"))
                .filter(line -> {
                    boolean increasing = false;
                    boolean decreasing = false;
                    for (int i = 1; i < line.length; i++) {
                        long first = Long.parseLong(line[i - 1]);
                        long second = Long.parseLong(line[i]);
                        long difference = Math.abs(first - second);
                        if (difference > 3 || difference == 0) {
                            return false;
                        }
                        if (second > first) {
                            if (decreasing) {
                                return false;
                            }
                            increasing = true;
                        } else if (second < first) {
                            if (increasing) {
                                return false;
                            }
                            decreasing = true;
                        }
                    }
                    return true;
                })
                .count();
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
