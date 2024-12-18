package src.advent2024.day3;

import src.PuzzleSolver;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))");
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(161L);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))");
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(48L);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        Pattern instructions = Pattern.compile("mul\\((?<number1>\\d{1,3}),(?<number2>\\d{1,3})\\)");
        String input = lines.collect(Collectors.joining());

        long result = 0;
        Matcher matcher = instructions.matcher(input);
        while (matcher.find()) {
            long number1 = Long.parseLong(matcher.group("number1"));
            long number2 = Long.parseLong(matcher.group("number2"));
            result += number1 * number2;
        }

        return result;
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        Pattern instructions = Pattern.compile("mul\\((?<number1>\\d{1,3}),(?<number2>\\d{1,3})\\)|do\\(\\)|don't\\(\\)");
        String input = lines.collect(Collectors.joining());

        long result = 0;
        boolean mulEnabled = true;
        Matcher matcher = instructions.matcher(input);
        while (matcher.find()) {
            if (matcher.group(1) == null) {
                mulEnabled = "do()".equals(matcher.group(0));
            } else if (mulEnabled) {
                long number1 = Long.parseLong(matcher.group("number1"));
                long number2 = Long.parseLong(matcher.group("number2"));
                result += number1 * number2;
            }
        }

        return result;
    }

}
