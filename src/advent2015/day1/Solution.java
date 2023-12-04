package src.advent2015.day1;

import src.PuzzleSolver;

import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public String getExampleInput1() {
        return ")())())";
    }

    @Override
    public String getExampleOutput1() {
        return "-3";
    }

    @Override
    public String getExampleInput2() {
        return "()())";
    }

    @Override
    public String getExampleOutput2() {
        return "5";
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
