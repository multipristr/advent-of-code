package src.advent2024.day17;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
    public List<Long> getExampleOutput2() {
        return List.of();
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        String input = lines.collect(Collectors.joining());
        Matcher matcher = INSTRUCTIONS_PATTERN.matcher(input);
        if (!matcher.find()) {
            throw new IllegalArgumentException(input + " not in format " + INSTRUCTIONS_PATTERN);
        }
        int registerA = Integer.parseInt(matcher.group("registerA"));
        int registerB = Integer.parseInt(matcher.group("registerB"));
        int registerC = Integer.parseInt(matcher.group("registerC"));
        int[] opcodes = Arrays.stream(matcher.group("opcodes").split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        StringJoiner output = new StringJoiner(",");
        for (int instructionPointer = 0; instructionPointer < opcodes.length; instructionPointer += 2) {
            int instructionOpcode = opcodes[instructionPointer];
            int operand = opcodes[instructionPointer + 1];
            switch (instructionOpcode) {
                case 0: // adv
                    int adv = (int) (registerA / Math.pow(2, getComboOperandValue(registerA, registerB, registerC, operand)));
                    registerA = adv;
                    break;
                case 1: //bxl
                    int bxl = registerB ^ operand;
                    registerB = bxl;
                    break;
                case 2: // bst
                    int bst = getComboOperandValue(registerA, registerB, registerC, operand) % 8;
                    registerB = bst;
                    break;
                case 3: // jnz
                    if (registerA != 0) {
                        instructionPointer = operand - 2;
                    }
                    break;
                case 4: //bxc
                    int bxc = registerB ^ registerC;
                    registerB = bxc;
                    break;
                case 5: // out
                    int out = getComboOperandValue(registerA, registerB, registerC, operand) % 8;
                    output.add(out + "");
                    break;
                case 6: // bdv
                    int bdv = (int) (registerA / Math.pow(2, getComboOperandValue(registerA, registerB, registerC, operand)));
                    registerB = bdv;
                    break;
                case 7: // cdv
                    int cdv = (int) (registerA / Math.pow(2, getComboOperandValue(registerA, registerB, registerC, operand)));
                    registerC = cdv;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid instruction opcode " + instructionOpcode);
            }
        }

        System.out.println(output);
        return Long.parseLong(output.toString().replaceAll(",", ""));
    }

    private int getComboOperandValue(int registerA, int registerB, int registerC, int operand) {
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

    @Override
    public long solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
