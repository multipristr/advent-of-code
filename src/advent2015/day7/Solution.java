package src.advent2015.day7;

import src.PuzzleSolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("123 -> x\n" +
                "456 -> y\n" +
                "x AND y -> d\n" +
                "x OR y -> e\n" +
                "x LSHIFT 2 -> f\n" +
                "y RSHIFT 2 -> g\n" +
                "NOT x -> a\n" +
                "NOT y -> i");
    }

    @Override
    public List<String> getExampleOutput1() {
        return List.of("65412");
    }


    @Override
    public List<String> getExampleOutput2() {
        return List.of("65412");
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        Map<String, String> wireSignals = new HashMap<>();

        lines.forEach(line -> {
            try {
                String[] parts = line.split(" ");
                if ("NOT".equals(parts[0])) {
                    wireSignals.put(parts[3], line);
                } else if ("->".equals(parts[1])) {
                    wireSignals.put(parts[2], parts[0]);
                } else {
                    wireSignals.put(parts[4], line);
                }
            } catch (Exception e) {
                throw new IllegalStateException(line, e);
            }
        });

        return Integer.toString(calculateWireValue(wireSignals, "a"));
    }

    private char calculateWireValue(Map<String, String> wireSignals, String wire) {
        Optional<Character> maybeNumber = tryParseNumber(wire);
        if (maybeNumber.isPresent()) {
            return maybeNumber.get();
        }
        String line = wireSignals.get(wire);
        if (line == null) {
            System.out.println();
        }
        String[] parts = line.split(" ");
        char wireSignal = 0;
        if ("NOT".equals(parts[0])) {
            wireSignal = (char) ~calculateWireValue(wireSignals, parts[1]);
        } else if (parts.length == 1) {
            Optional<Character> signal = tryParseNumber(line);
            if (signal.isPresent()) {
                return signal.get();
            } else {
                wireSignal = calculateWireValue(wireSignals, line);
            }
        } else {
            if ("AND".equals(parts[1])) {
                wireSignal = (char) (calculateWireValue(wireSignals, parts[0]) & calculateWireValue(wireSignals, parts[2]));
            } else if ("OR".equals(parts[1])) {
                wireSignal = (char) (calculateWireValue(wireSignals, parts[0]) | calculateWireValue(wireSignals, parts[2]));
            } else if ("LSHIFT".equals(parts[1])) {
                wireSignal = (char) (calculateWireValue(wireSignals, parts[0]) << Short.parseShort(parts[2]));
            } else if ("RSHIFT".equals(parts[1])) {
                wireSignal = (char) (calculateWireValue(wireSignals, parts[0]) >>> Short.parseShort(parts[2]));
            } else {
                throw new IllegalArgumentException(line);
            }
        }
        wireSignals.put(wire, Integer.toString(wireSignal));
        return wireSignal;
    }

    private Optional<Character> tryParseNumber(String input) {
        try {
            return Optional.of((char) Integer.parseUnsignedInt(input));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        Map<String, String> wireSignals = new HashMap<>();

        lines.forEach(line -> {
            try {
                String[] parts = line.split(" ");
                if ("NOT".equals(parts[0])) {
                    wireSignals.put(parts[3], line);
                } else if ("->".equals(parts[1])) {
                    wireSignals.put(parts[2], parts[0]);
                } else {
                    wireSignals.put(parts[4], line);
                }
            } catch (Exception e) {
                throw new IllegalStateException(line, e);
            }
        });

        wireSignals.put("b", "46065");

        return Integer.toString(calculateWireValue(wireSignals, "a"));
    }
}
