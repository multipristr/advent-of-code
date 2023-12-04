package src.advent2015.day2;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public String getExampleInput1() {
        return "1x1x10";
    }

    @Override
    public String getExampleOutput1() {
        return "43";
    }

    @Override
    public String getExampleInput2() {
        return "2x3x4";
    }

    @Override
    public String getExampleOutput2() {
        return "34";
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        return lines.mapToInt(line -> {
            String[] dimensions = line.split("x");
            int lw = Integer.parseInt(dimensions[0]) * Integer.parseInt(dimensions[1]);
            int wh = Integer.parseInt(dimensions[1]) * Integer.parseInt(dimensions[2]);
            int hl = Integer.parseInt(dimensions[2]) * Integer.parseInt(dimensions[0]);

            return 2 * lw + 2 * wh + 2 * hl + Math.min(lw, Math.min(wh, hl));
        }).sum() + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return lines.mapToInt(line -> {
            String[] dimensions = line.split("x");
            List<Integer> sides = Arrays.asList(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]), Integer.parseInt(dimensions[2]));
            sides.sort(null);
            return 2 * sides.get(0) + 2 * sides.get(1) + sides.stream().reduce(1, (a, b) -> a * b);
        }).sum() + "";
    }
}
