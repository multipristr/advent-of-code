package src.advent2025.day12;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("0:\n" +
                "###\n" +
                "##.\n" +
                "##.\n" +
                "\n" +
                "1:\n" +
                "###\n" +
                "##.\n" +
                ".##\n" +
                "\n" +
                "2:\n" +
                ".##\n" +
                "###\n" +
                "##.\n" +
                "\n" +
                "3:\n" +
                "##.\n" +
                "###\n" +
                "##.\n" +
                "\n" +
                "4:\n" +
                "###\n" +
                "#..\n" +
                "###\n" +
                "\n" +
                "5:\n" +
                "###\n" +
                ".#.\n" +
                "###\n" +
                "\n" +
                "4x4: 0 0 0 0 2 0\n" +
                "12x5: 1 0 1 0 2 2\n" +
                "12x5: 1 0 1 0 3 2");
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
    public Long solvePartOne(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
