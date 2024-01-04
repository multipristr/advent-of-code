package src.advent2015.day1;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of(")())())");
    }

    @Override
    public List<String> getExampleOutput1() {
        return List.of("-3");
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of("()())");
    }

    @Override
    public List<String> getExampleOutput2() {
        return List.of("5");
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        return lines.mapToInt(line -> {
            int sum = 0;
            for (char symbol : line.toCharArray()) {
                if (symbol == '(') {
                    ++sum;
                } else if (symbol == ')') {
                    --sum;
                }
            }
            return sum;
        }).sum() + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return lines.mapToInt(line -> {
            int sum = 0;
            char[] chars = line.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char symbol = chars[i];
                if (symbol == '(') {
                    ++sum;
                } else if (symbol == ')') {
                    --sum;
                }
                if (sum < 0) {
                    return i + 1;
                }
            }
            return 0;
        }).sum() + "";
    }
}
