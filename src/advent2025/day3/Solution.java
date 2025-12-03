package src.advent2025.day3;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("987654321111111\n" +
                "811111111111119\n" +
                "234234234234278\n" +
                "818181911112111");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(357L);
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
