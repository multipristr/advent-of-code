package src.advent2024.day1;

import src.PuzzleSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("3   4\n" +
                "4   3\n" +
                "2   5\n" +
                "1   3\n" +
                "3   9\n" +
                "3   3");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(11L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(31L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        List<Long> leftList = new ArrayList<>();
        List<Long> rightList = new ArrayList<>();
        lines.map(line -> line.split("\\s+"))
                .forEach(listPair -> {
                    leftList.add(Long.parseLong(listPair[0]));
                    rightList.add(Long.parseLong(listPair[1]));
                });

        leftList.sort(null);
        rightList.sort(null);
        long distance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            distance += Math.abs(leftList.get(i) - rightList.get(i));
        }

        return distance;
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        List<Long> leftList = new ArrayList<>();
        Map<Long, Long> rightList = new HashMap<>();
        lines.map(line -> line.split("\\s+"))
                .forEach(listPair -> {
                    leftList.add(Long.parseLong(listPair[0]));
                    rightList.merge(Long.parseLong(listPair[1]), 1L, Long::sum);
                });

        return leftList.stream()
                .mapToLong(l -> l * rightList.getOrDefault(l, 0L))
                .sum();
    }
}
