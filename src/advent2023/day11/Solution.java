package src.advent2023.day11;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of(".......#..\n" +
                "#.........\n" +
                "..........\n" +
                "......#...\n" +
                ".#........\n" +
                ".........#\n" +
                "..........\n" +
                ".......#..\n" +
                "#...#.....");
    }

    @Override
    public List<String> getExampleOutput1() {
        return List.of("374");
    }

    @Override
    public List<String> getExampleOutput2() {
        return List.of("8410");
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        return "9370588";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return "746207878188";
    }
}
