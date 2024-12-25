package src.advent2024.day24;

import src.PuzzleSolver;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    private static final String GATE = "(?<input>(?<input1>\\p{Alnum}+) (?<operation>\\p{Alnum}+) (?<input2>\\p{Alnum}+))";
    private static final Pattern GATE_PATTERN = Pattern.compile(GATE);

    private static final Pattern WIRE_VALUE_PATTERN = Pattern.compile(
            "(?<wire>\\p{Alnum}+): (?<initialValue>[01])|" + GATE + " -> (?<output>\\p{Alnum}+)"
    );

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of(
                "x00: 1\n" +
                        "x01: 1\n" +
                        "x02: 1\n" +
                        "y00: 0\n" +
                        "y01: 1\n" +
                        "y02: 0\n" +
                        "\n" +
                        "x00 AND y00 -> z00\n" +
                        "x01 XOR y01 -> z01\n" +
                        "x02 OR y02 -> z02",
                "x00: 1\n" +
                        "x01: 0\n" +
                        "x02: 1\n" +
                        "x03: 1\n" +
                        "x04: 0\n" +
                        "y00: 1\n" +
                        "y01: 1\n" +
                        "y02: 1\n" +
                        "y03: 1\n" +
                        "y04: 1\n" +
                        "\n" +
                        "ntg XOR fgs -> mjb\n" +
                        "y02 OR x01 -> tnw\n" +
                        "kwq OR kpj -> z05\n" +
                        "x00 OR x03 -> fst\n" +
                        "tgd XOR rvg -> z01\n" +
                        "vdt OR tnw -> bfw\n" +
                        "bfw AND frj -> z10\n" +
                        "ffh OR nrd -> bqk\n" +
                        "y00 AND y03 -> djm\n" +
                        "y03 OR y00 -> psh\n" +
                        "bqk OR frj -> z08\n" +
                        "tnw OR fst -> frj\n" +
                        "gnj AND tgd -> z11\n" +
                        "bfw XOR mjb -> z00\n" +
                        "x03 OR x00 -> vdt\n" +
                        "gnj AND wpb -> z02\n" +
                        "x04 AND y00 -> kjc\n" +
                        "djm OR pbm -> qhw\n" +
                        "nrd AND vdt -> hwm\n" +
                        "kjc AND fst -> rvg\n" +
                        "y04 OR y02 -> fgs\n" +
                        "y01 AND x02 -> pbm\n" +
                        "ntg OR kjc -> kwq\n" +
                        "psh XOR fgs -> tgd\n" +
                        "qhw XOR tgd -> z09\n" +
                        "pbm OR djm -> kpj\n" +
                        "x03 XOR y03 -> ffh\n" +
                        "x00 XOR y04 -> ntg\n" +
                        "bfw OR bqk -> z06\n" +
                        "nrd XOR fgs -> wpb\n" +
                        "frj XOR qhw -> z04\n" +
                        "bqk OR frj -> z07\n" +
                        "y03 OR x01 -> nrd\n" +
                        "hwm AND bqk -> z03\n" +
                        "tgd XOR rvg -> z12\n" +
                        "tnw OR pbm -> gnj"
        );
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(4, 2024);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of("x00: 0\n" +
                "x01: 1\n" +
                "x02: 0\n" +
                "x03: 1\n" +
                "x04: 0\n" +
                "x05: 1\n" +
                "y00: 0\n" +
                "y01: 0\n" +
                "y02: 1\n" +
                "y03: 1\n" +
                "y04: 0\n" +
                "y05: 1\n" +
                "\n" +
                "x00 AND y00 -> z05\n" +
                "x01 AND y01 -> z02\n" +
                "x02 AND y02 -> z01\n" +
                "x03 AND y03 -> z03\n" +
                "x04 AND y04 -> z04\n" +
                "x05 AND y05 -> z00");
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of("z00,z01,z02,z05");
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        var wireValues = parseWireValues(lines);

        return wireValues.keySet().stream()
                .filter(wire -> wire.charAt(0) == 'z')
                .mapToLong(wire -> {
                    var bit = Byte.parseByte(wire.substring(1));
                    return calculateWireValue(wireValues, wire) << bit;
                })
                .reduce((bit1, bit2) -> bit1 | bit2)
                .orElseThrow();
    }

    private long calculateWireValue(Map<String, String> wireValues, String wire) {
        var gate = wireValues.get(wire);
        var matcher = GATE_PATTERN.matcher(gate);
        if (matcher.find()) {
            var wire1Value = calculateWireValue(wireValues, matcher.group("input1"));
            var wire2Value = calculateWireValue(wireValues, matcher.group("input2"));
            long outputValue;
            switch (matcher.group("operation")) {
                case "AND":
                    outputValue = wire1Value & wire2Value;
                    break;
                case "OR":
                    outputValue = wire1Value | wire2Value;
                    break;
                case "XOR":
                    outputValue = wire1Value ^ wire2Value;
                    break;
                default:
                    throw new IllegalArgumentException(gate);
            }
            wireValues.put(wire, outputValue + "");
            return outputValue;
        } else {
            return Long.parseUnsignedLong(gate);
        }
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        var wireValues = parseWireValues(lines);

        Collection<String> swappedOutputs = new TreeSet<>();
        for (var wireValue : wireValues.entrySet()) {
            var matcher = GATE_PATTERN.matcher(wireValue.getValue());
            if (matcher.find()) {
                var input1 = matcher.group("input1");
                var input2 = matcher.group("input2");
                switch (matcher.group("operation")) {
                    case "AND":
                        if (wireValue.getKey().charAt(0) == 'z') {
                            swappedOutputs.add(wireValue.getKey());
                            break;
                        }
                        boolean initialOutput = "x00".equals(input1) && "y00".equals(input2);
                        var targets = wireValues.values().stream()
                                .filter(gate -> gate.contains(wireValue.getKey()))
                                .toArray(String[]::new);
                        if (!initialOutput && targets.length != 1) {
                            swappedOutputs.add(wireValue.getKey());
                            break;
                        }
                        var andTargetMatcher = GATE_PATTERN.matcher(targets[0]);
                        if (!andTargetMatcher.find() || !initialOutput && !"OR".equals(andTargetMatcher.group("operation"))) {
                            swappedOutputs.add(wireValue.getKey());
                        }
                        break;
                    case "OR":
                        if (wireValue.getKey().charAt(0) == 'z' && !"z45".equals(wireValue.getKey())) {
                            swappedOutputs.add(wireValue.getKey());
                        }
                        var leftGateMatcher = GATE_PATTERN.matcher(wireValues.get(input1));
                        if (!leftGateMatcher.find() || !"AND".equals(leftGateMatcher.group("operation"))) {
                            swappedOutputs.add(input1);
                        }
                        var rightGateMatcher = GATE_PATTERN.matcher(wireValues.get(input2));
                        if (!rightGateMatcher.find() || !"AND".equals(rightGateMatcher.group("operation"))) {
                            swappedOutputs.add(input2);
                        }
                        break;
                    case "XOR":
                        boolean zOutput = wireValue.getKey().charAt(0) == 'z';
                        boolean xyInput1 = input1.charAt(0) == 'x' || input1.charAt(0) == 'y';
                        boolean xyInput2 = input2.charAt(0) == 'x' || input2.charAt(0) == 'y';
                        if (!zOutput && (!xyInput1 || !xyInput2)) {
                            swappedOutputs.add(wireValue.getKey());
                        }
                        break;
                    default:
                        throw new IllegalArgumentException(wireValue.getValue());
                }
            }
        }

        return String.join(",", swappedOutputs);
    }

    private Map<String, String> parseWireValues(Stream<String> lines) {
        Map<String, String> wireValues = new HashMap<>();
        var matcher = WIRE_VALUE_PATTERN.matcher(lines.collect(Collectors.joining(System.lineSeparator())));
        while (matcher.find()) {
            var initialValue = matcher.group("initialValue");
            if (initialValue != null) {
                wireValues.put(matcher.group("wire"), initialValue);
            } else {
                wireValues.put(matcher.group("output"), matcher.group("input"));
            }
        }
        return wireValues;
    }

}
