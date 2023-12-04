package src.advent2023.day1;

import src.PuzzleSolver;

import java.util.stream.Stream;

public class Solution extends PuzzleSolver {
    private static final String[] SPELLED = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public String getExampleInput1() {
        return "1abc2\n" +
                "pqr3stu8vwx\n" +
                "a1b2c3d4e5f\n" +
                "treb7uchet";
    }

    @Override
    public String getExampleOutput1() {
        return "142";
    }

    @Override
    public String getExampleInput2() {
        return "two1nine\n" +
                "eightwothree\n" +
                "abcone2threexyz\n" +
                "xtwone3four\n" +
                "4nineeightseven2\n" +
                "zoneight234\n" +
                "7pqrstsixteen";
    }

    @Override
    public String getExampleOutput2() {
        return "281";
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        return lines.mapToInt(line -> {
                    String firstDigit = null;
                    String lastDigit = null;
                    int firstIndex = Integer.MAX_VALUE;
                    int lastIndex = Integer.MIN_VALUE;

                    for (int i = 1; i < 10; i++) {
                        int fI = line.indexOf("" + i);
                        if (fI != -1 && fI < firstIndex) {
                            firstIndex = fI;
                            firstDigit = "" + i;
                        }
                        int lI = line.lastIndexOf("" + i);
                        if (lI != -1 && lI > lastIndex) {
                            lastIndex = lI;
                            lastDigit = "" + i;
                        }
                    }

                    return Integer.parseInt(firstDigit + lastDigit);
                })
                .sum() + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return lines.mapToInt(line -> {
                    String firstDigit = null;
                    String lastDigit = null;
                    int firstIndex = Integer.MAX_VALUE;
                    int lastIndex = Integer.MIN_VALUE;

                    for (int i = 1; i < 10; i++) {
                        int fI = line.indexOf("" + i);
                        if (fI != -1 && fI < firstIndex) {
                            firstIndex = fI;
                            firstDigit = "" + i;
                        }
                        int lI = line.lastIndexOf("" + i);
                        if (lI != -1 && lI > lastIndex) {
                            lastIndex = lI;
                            lastDigit = "" + i;
                        }

                        String spelled = SPELLED[i - 1];
                        fI = line.indexOf(spelled);
                        if (fI != -1 && fI < firstIndex) {
                            firstIndex = fI;
                            firstDigit = "" + i;
                        }
                        lI = line.lastIndexOf(spelled);
                        if (lI != -1 && lI > lastIndex) {
                            lastIndex = lI;
                            lastDigit = "" + i;
                        }
                    }

                    return Integer.parseInt(firstDigit + lastDigit);
                })
                .sum() + "";
    }
}
