package src.advent2024.day13;

import src.PuzzleSolver;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    private static final Pattern XY_PATTERN = Pattern.compile(
            "Button A: X\\+(?<aX>\\d+), Y\\+(?<aY>\\d+)" +
                    "Button B: X\\+(?<bX>\\d+), Y\\+(?<bY>\\d+)" +
                    "Prize: X=(?<x>\\d+), Y=(?<y>\\d+)"
    );

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
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(480L);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(875318608908L);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        return calculateTokens(lines, 0d, 0d);
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        return calculateTokens(lines, 10000000000000d, 10000000000000d);
    }

    private long calculateTokens(Stream<String> lines, double xAddition, double yAddition) {
        String input = lines.collect(Collectors.joining());
        Matcher matcher = XY_PATTERN.matcher(input);

        long token = 0L;
        while (matcher.find()) {
            double aX = Double.parseDouble(matcher.group("aX"));
            double aY = Double.parseDouble(matcher.group("aY"));
            double bX = Double.parseDouble(matcher.group("bX"));
            double bY = Double.parseDouble(matcher.group("bY"));
            double x = Double.parseDouble(matcher.group("x")) + xAddition;
            double y = Double.parseDouble(matcher.group("y")) + yAddition;

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
            if (Math.rint(a) == a && Math.rint(b) == b) {
                token += (long) (a * 3.0 + b);
            }
        }
        return token;
    }

}
