package src.advent2024.day24;

import src.PuzzleSolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    private static final Pattern WIRE_VALUE_PATTERN = Pattern.compile(
            "(?<wire>\\p{Alnum}+): (?<initialValue>[01])|(?<input>\\p{Alnum}+ \\p{Alnum}+ \\p{Alnum}+) -> (?<output>\\p{Alnum}+)"
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

        return wireValues.keySet().stream()
                .filter(s -> s.charAt(0) == 'z')
                .mapToLong(s -> {
                    int bit = Integer.parseInt(s.substring(1));
                    return (long) calculateWireValue(wireValues, s) << bit;
                })
                .reduce((bit1, bit2) -> bit1 | bit2)
                .orElseThrow();
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private int calculateWireValue(Map<String, String> wireValues, String wire) {
        var gate = wireValues.get(wire);
        var maybeValue = tryParseNumber(gate);
        if (maybeValue.isPresent()) {
            return maybeValue.get();
        }
        var parts = gate.split(" ");
        int wireValue;
        switch (parts[1]) {
            case "AND":
                wireValue = calculateWireValue(wireValues, parts[0]) & calculateWireValue(wireValues, parts[2]);
                break;
            case "OR":
                wireValue = calculateWireValue(wireValues, parts[0]) | calculateWireValue(wireValues, parts[2]);
                break;
            case "XOR":
                wireValue = calculateWireValue(wireValues, parts[0]) ^ calculateWireValue(wireValues, parts[2]);
                break;
            default:
                throw new IllegalArgumentException(gate);
        }
        wireValues.put(wire, wireValue + "");
        return wireValue;
    }

    private Optional<Integer> tryParseNumber(String initialValue) {
        try {
            return Optional.of(Integer.parseInt(initialValue));
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

}
