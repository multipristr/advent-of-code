package src.advent2023.day9;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("0 3 6 9 12 15\n" +
                "1 3 6 10 15 21\n" +
                "10 13 16 21 30 45");
    }

    @Override
    public List<String> getExampleOutput1() {
        return List.of("114");
    }

    @Override
    public List<String> getExampleOutput2() {
        return List.of("2");
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        return "1798691765";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return "1104";
    }
}
