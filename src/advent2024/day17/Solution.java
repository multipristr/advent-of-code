package src.advent2024.day17;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    private static final Pattern INSTRUCTIONS_PATTERN = Pattern.compile(
            "Register A: (?<registerA>-?\\d+)Register B: (?<registerB>-?\\d+)Register C: (?<registerC>-?\\d+)Program: (?<opcodes>(-?\\d+,?)+)"
    );

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("Register A: 729\n" +
                "Register B: 0\n" +
                "Register C: 0\n" +
                "\n" +
                "Program: 0,1,5,4,3,0");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(4635635210L);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of("Register A: 2024\n" +
                "Register B: 0\n" +
                "Register C: 0\n" +
                "\n" +
                "Program: 0,3,5,4,3,0");
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(117440L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        String input = lines.collect(Collectors.joining());
        Matcher matcher = INSTRUCTIONS_PATTERN.matcher(input);
        if (!matcher.find()) {
            throw new IllegalArgumentException(input + " not in format " + INSTRUCTIONS_PATTERN);
        }
        long registerA = Long.parseLong(matcher.group("registerA"));
        long registerB = Long.parseLong(matcher.group("registerB"));
        long registerC = Long.parseLong(matcher.group("registerC"));
        int[] opcodes = Arrays.stream(matcher.group("opcodes").split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        String output = determineProgramOutput(opcodes, registerA, registerB, registerC);
        System.out.println(output);
        return Long.parseLong(output.replaceAll(",", ""));
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        String input = lines.collect(Collectors.joining());
        Matcher matcher = INSTRUCTIONS_PATTERN.matcher(input);
        if (!matcher.find()) {
            throw new IllegalArgumentException(input + " not in format " + INSTRUCTIONS_PATTERN);
        }
        long registerB = Long.parseLong(matcher.group("registerB"));
        long registerC = Long.parseLong(matcher.group("registerC"));
        String program = matcher.group("opcodes");
        int[] opcodes = Arrays.stream(program.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        return LongStream.iterate(9_999_999_999L,
                        registerA -> !determineProgramOutput(opcodes, registerA, registerB, registerC).equals(program),
                        i -> ++i)
                .unordered().parallel()
                .max().orElseThrow() + 1L;
    }

    private String determineProgramOutput(int[] opcodes, long registerA, long registerB, long registerC) {
        StringJoiner output = new StringJoiner(",");
        for (int instructionPointer = 0; instructionPointer < opcodes.length; instructionPointer += 2) {
            int instructionOpcode = opcodes[instructionPointer];
            int operand = opcodes[instructionPointer + 1];
            switch (instructionOpcode) {
                case 0: // adv
                    long adv = (long) (registerA / Math.pow(2, getComboOperandValue(registerA, registerB, registerC, operand)));
                    registerA = adv;
                    break;
                case 1: //bxl
                    long bxl = registerB ^ operand;
                    registerB = bxl;
                    break;
                case 2: // bst
                    long bst = getComboOperandValue(registerA, registerB, registerC, operand) % 8;
                    registerB = bst;
                    break;
                case 3: // jnz
                    if (registerA != 0) {
                        instructionPointer = operand - 2;
                    }
                    break;
                case 4: //bxc
                    long bxc = registerB ^ registerC;
                    registerB = bxc;
                    break;
                case 5: // out
                    long out = getComboOperandValue(registerA, registerB, registerC, operand) % 8;
                    output.add(out + "");
                    break;
                case 6: // bdv
                    long bdv = (long) (registerA / Math.pow(2, getComboOperandValue(registerA, registerB, registerC, operand)));
                    registerB = bdv;
                    break;
                case 7: // cdv
                    long cdv = (long) (registerA / Math.pow(2, getComboOperandValue(registerA, registerB, registerC, operand)));
                    registerC = cdv;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid instruction opcode " + instructionOpcode);
            }
        }
        return output.toString();
    }

    private long getComboOperandValue(long registerA, long registerB, long registerC, int operand) {
        switch (operand) {
            case 0:
            case 1:
            case 2:
            case 3:
                return operand;
            case 4:
                return registerA;
            case 5:
                return registerB;
            case 6:
                return registerC;
            default:
                throw new IllegalArgumentException("Invalid operand " + operand);
        }
    }

}
