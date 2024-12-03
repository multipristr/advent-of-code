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
    public List<String> getExampleInput2() {
        return List.of("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))");
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(48L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
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
    public long solvePartTwo(Stream<String> lines) {
        Pattern mulInstructions = Pattern.compile("mul\\((?<number1>\\d{1,3}),(?<number2>\\d{1,3})\\)");
        String input = lines.collect(Collectors.joining());
        boolean mulEnabled = true;
        long result = 0;

        int fromIndex = 0;
        while (fromIndex < input.length()) {
            int doIndex = input.indexOf("do()", fromIndex);
            doIndex = doIndex < 0 ? Integer.MAX_VALUE : doIndex;
            int dontIndex = input.indexOf("don't()", fromIndex);
            dontIndex = dontIndex < 0 ? Integer.MAX_VALUE : dontIndex;
            int mulIndex;
            long mulOperand1;
            long mulOperand2;
            int mulLength;
            Matcher matcher = mulInstructions.matcher(input);
            if (matcher.find(fromIndex)) {
                mulIndex = matcher.start();
                mulLength = matcher.group(0).length();
                mulOperand1 = Long.parseLong(matcher.group("number1"));
                mulOperand2 = Long.parseLong(matcher.group("number2"));
            } else {
                break;
            }

            if (doIndex < dontIndex) {
                if (mulIndex < doIndex) { // mul first
                    if (mulEnabled) {
                        result += mulOperand1 * mulOperand2;
                    }
                    fromIndex = mulIndex + mulLength;
                } else { // do() first
                    mulEnabled = true;
                    fromIndex = doIndex + "do()".length();
                }
            } else {
                if (mulIndex < dontIndex) { // mul first
                    if (mulEnabled) {
                        result += mulOperand1 * mulOperand2;
                    }
                    fromIndex = mulIndex + mulLength;
                } else { // don() first
                    mulEnabled = false;
                    fromIndex = dontIndex + "don't()".length();
                }
            }
        }

        return result;
    }

}
