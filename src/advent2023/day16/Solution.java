package src.advent2023.day16;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of(".|...\\....\n" +
                "|.-.\\.....\n" +
                ".....|-...\n" +
                "........|.\n" +
                "..........\n" +
                ".........\\\n" +
                "..../.\\\\..\n" +
                ".-.-/..|..\n" +
                ".|....-|.\\\n" +
                "..//.|....");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(46L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of();
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
