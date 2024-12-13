package src.advent2024.day13;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    private static final Pattern XY_PATTERN = Pattern.compile("[+=](?<xy>\\d+)");

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("Button A: X+94, Y+34\n" +
                "Button B: X+22, Y+67\n" +
                "Prize: X=8400, Y=5400\n" +
                "\n" +
                "Button A: X+26, Y+66\n" +
                "Button B: X+67, Y+21\n" +
                "Prize: X=12748, Y=12176\n" +
                "\n" +
                "Button A: X+17, Y+86\n" +
                "Button B: X+84, Y+37\n" +
                "Prize: X=7870, Y=6450\n" +
                "\n" +
                "Button A: X+69, Y+23\n" +
                "Button B: X+27, Y+71\n" +
                "Prize: X=18641, Y=10279");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(480L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(875318608908L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        String[] input = lines.collect(Collectors.joining("\n")).split("\\R{2}");
        return Arrays.stream(input).parallel()
                .mapToLong(equation -> solveEquation(equation, 0d, 0d))
                .sum();
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        String[] input = lines.collect(Collectors.joining("\n")).split("\\R{2}");
        return Arrays.stream(input).parallel()
                .mapToLong(equation -> solveEquation(equation, 10000000000000d, 10000000000000d))
                .sum();
    }

    private long solveEquation(String equation, double xAddition, double yAddition) {
        Matcher matcher = XY_PATTERN.matcher(equation);
        matcher.find();
        double aX = Double.parseDouble(matcher.group("xy"));
        matcher.find();
        double aY = Double.parseDouble(matcher.group("xy"));
        matcher.find();
        double bX = Double.parseDouble(matcher.group("xy"));
        matcher.find();
        double bY = Double.parseDouble(matcher.group("xy"));
        matcher.find();
        double x = Double.parseDouble(matcher.group("xy")) + xAddition;
        matcher.find();
        double y = Double.parseDouble(matcher.group("xy")) + yAddition;

        // 94a + 22b = 8400
        // 34a + 67b = 5400
        // ---
        // aX * a + bX * b = x
        // aY * a + bY * b = y
        // ---
        // b = (x - aX * a) / bX
        // aY * a + bY * ((x - aX * a) / bX) = y
        // aY * a + (bY * x - bY * aX * a) / bX = y
        // aY * bX * a + bY * x - bY * aX * a = bX * y
        // aY * bX * a - bY * aX * a = bX * y - bY * x
        // a (aY * bX - bY * aX) = bX * y - bY * x
        // a = (bX * y - bY * x) / (aY * bX - bY * aX)
        double a = (bX * y - bY * x) / (aY * bX - bY * aX);
        double b = (x - aX * a) / bX;
        return Math.rint(a) == a && Math.rint(b) == b ? (long) (a * 3.0 + b) : 0L;
    }

}
