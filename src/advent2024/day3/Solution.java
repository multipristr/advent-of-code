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
    public List<Long> getExampleOutput1() {
        return List.of(161L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of();
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        Pattern instructions = Pattern.compile(".*mul\\((?<number1>\\d{1,3}),(?<number2>\\d{1,3})\\).*");
        String input = lines.collect(Collectors.joining());

        Matcher matcher = instructions.matcher(input);
        if (!matcher.find()) {
            throw new IllegalStateException(instructions + " not found in " + input);
        }

        return 0L;
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
