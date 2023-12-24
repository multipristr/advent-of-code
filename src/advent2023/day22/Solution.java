package src.advent2023.day22;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("1,0,1~1,2,1\n" +
                "0,0,2~2,0,2\n" +
                "0,2,3~2,2,3\n" +
                "0,0,4~0,2,4\n" +
                "2,0,5~2,2,5\n" +
                "0,1,6~2,1,6\n" +
                "1,1,8~1,1,9");
    }

    @Override
    public List<String> getExampleOutput1() {
        return List.of("5");
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
