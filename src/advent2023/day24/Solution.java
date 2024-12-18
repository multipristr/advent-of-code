package src.advent2023.day24;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("19, 13, 30 @ -2,  1, -2\n" +
                "18, 19, 22 @ -1, -1, -2\n" +
                "20, 25, 34 @ -2, -2, -4\n" +
                "12, 31, 28 @ -1, -2, -1\n" +
                "20, 19, 15 @  1, -5, -3");
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(2L);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of();
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
