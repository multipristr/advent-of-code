package src.advent2023.day21;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("...........\n" +
                ".....###.#.\n" +
                ".###.##..#.\n" +
                "..#.#...#..\n" +
                "....#.#....\n" +
                ".##..S####.\n" +
                ".##..#...#.\n" +
                ".......##..\n" +
                ".##.#.####.\n" +
                ".##..##.##.\n" +
                "...........");
    }

    @Override
    public List<String> getExampleOutput1() {
        return List.of("16");
    }

    @Override
    public List<String> getExampleOutput2() {
        return List.of();
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        return "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return "";
    }

}
