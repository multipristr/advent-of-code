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
        return List.of(4L);
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
        return lines.map(line -> line.split("\\s+"))
                .filter(line -> {
                    outer:
                    for (int skipLevel = 0; skipLevel <= line.length; skipLevel++) {
                        boolean increasing = false;
                        boolean decreasing = false;
                        for (int i = 1; i < line.length; i++) {
                            if (i == skipLevel || i == 1 && skipLevel == 0) {
                                continue;
                            }
                            int index1 = i - 1 == skipLevel ? i - 2 : i - 1;
                            long first = Long.parseLong(line[index1]);
                            long second = Long.parseLong(line[i]);
                            long difference = Math.abs(first - second);
                            if (difference > 3 || difference == 0) {
                                continue outer;
                            }
                            if (second > first) {
                                if (decreasing) {
                                    continue outer;
                                }
                                increasing = true;
                            } else if (second < first) {
                                if (increasing) {
                                    continue outer;
                                }
                                decreasing = true;
                            }
                        }
                        return true;
                    }
                    return false;
                })
                .count();
    }

}
