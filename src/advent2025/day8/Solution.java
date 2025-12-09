package src.advent2025.day8;

import src.PuzzleSolver;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Integer, Long> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("162,817,812\n" +
                "57,618,57\n" +
                "906,360,560\n" +
                "592,479,940\n" +
                "352,342,300\n" +
                "466,668,158\n" +
                "542,29,236\n" +
                "431,825,988\n" +
                "739,650,466\n" +
                "52,470,668\n" +
                "216,146,977\n" +
                "819,987,18\n" +
                "117,168,530\n" +
                "805,96,715\n" +
                "346,949,466\n" +
                "970,615,88\n" +
                "941,993,340\n" +
                "862,61,35\n" +
                "984,92,344\n" +
                "425,690,689");
    }

    @Override
    public List<Integer> getExampleOutput1() {
        return List.of(40);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(25272L);
    }

    @Override
    public Integer solvePartOne(Stream<String> lines) {
        var junctionBoxes = lines.map(JunctionBox::new).collect(Collectors.toList());

        NavigableMap<Double, Map.Entry<JunctionBox, JunctionBox>> junctionBoxPairDistances = new TreeMap<>();
        for (var junctionBox1 : junctionBoxes) {
            for (var junctionBox2 : junctionBoxes) {
                if (!junctionBox1.equals(junctionBox2)) {
                    var distance = junctionBox1.calculateStraightLineDistance(junctionBox2);
                    junctionBoxPairDistances.put(distance, Map.entry(junctionBox1, junctionBox2));
                }
            }
        }

        var pairs = junctionBoxPairDistances.size() >= 1_000 ? 1_000 : 10;
        var junctionBoxPairs = junctionBoxPairDistances.values().iterator();
        Map<Integer, Set<JunctionBox>> circuitJunctionBoxes = new HashMap<>();
        circuitJunctionBoxes.put(0, new HashSet<>(junctionBoxes));
        int circuit = 1;
        for (int i = 0; i < pairs; i++) {
            var junctionBoxPair = junctionBoxPairs.next();
            var junctionBox1 = junctionBoxPair.getKey();
            var junctionBox2 = junctionBoxPair.getValue();
            if (junctionBox1.circuit == 0 && junctionBox2.circuit == 0) {
                var circuitBoxes = Set.of(junctionBox1, junctionBox2);
                var circuit0JunctionBoxes = circuitJunctionBoxes.get(0);
                circuit0JunctionBoxes.removeAll(circuitBoxes);
                if (circuit0JunctionBoxes.isEmpty()) {
                    circuitJunctionBoxes.remove(0);
                }
                junctionBox1.circuit = circuit;
                junctionBox2.circuit = circuit;
                circuitJunctionBoxes.put(circuit, new HashSet<>(circuitBoxes));
                ++circuit;
            } else if (junctionBox1.circuit == 0 || junctionBox2.circuit == 0) {
                var circuit0JunctionBox = junctionBox1.circuit == 0 ? junctionBox1 : junctionBox2;
                var circuitNonZeroJunctionBox = junctionBox1.circuit != 0 ? junctionBox1 : junctionBox2;
                var circuit0JunctionBoxes = circuitJunctionBoxes.get(0);
                circuit0JunctionBoxes.remove(circuit0JunctionBox);
                if (circuit0JunctionBoxes.isEmpty()) {
                    circuitJunctionBoxes.remove(0);
                }
                circuit0JunctionBox.circuit = circuitNonZeroJunctionBox.circuit;
                circuitJunctionBoxes.get(circuitNonZeroJunctionBox.circuit).add(circuit0JunctionBox);
            } else if (junctionBox1.circuit != junctionBox2.circuit) {
                var smallerCircuit = Math.min(junctionBox1.circuit, junctionBox2.circuit);
                var greaterCircuit = Math.max(junctionBox1.circuit, junctionBox2.circuit);
                var junctionBoxGreaterCircuit = circuitJunctionBoxes.get(greaterCircuit);
                circuitJunctionBoxes.remove(greaterCircuit);
                junctionBoxGreaterCircuit.forEach(junctionBox -> junctionBox.circuit = smallerCircuit);
                circuitJunctionBoxes.get(smallerCircuit).addAll(junctionBoxGreaterCircuit);
            }
        }

        circuitJunctionBoxes.remove(0);
        return circuitJunctionBoxes.values().stream()
                .map(Collection::size)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(1, (a, b) -> a * b);
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        var junctionBoxes = lines.map(JunctionBox::new).collect(Collectors.toList());

        NavigableMap<Double, Map.Entry<JunctionBox, JunctionBox>> junctionBoxPairDistances = new TreeMap<>();
        for (var junctionBox1 : junctionBoxes) {
            for (var junctionBox2 : junctionBoxes) {
                if (!junctionBox1.equals(junctionBox2)) {
                    var distance = junctionBox1.calculateStraightLineDistance(junctionBox2);
                    junctionBoxPairDistances.put(distance, Map.entry(junctionBox1, junctionBox2));
                }
            }
        }

        var junctionBoxPairs = junctionBoxPairDistances.values().iterator();
        Map<Integer, Set<JunctionBox>> circuitJunctionBoxes = new HashMap<>();
        circuitJunctionBoxes.put(0, new HashSet<>(junctionBoxes));
        int circuit = 1;
        while (junctionBoxPairs.hasNext()) {
            var junctionBoxPair = junctionBoxPairs.next();
            var junctionBox1 = junctionBoxPair.getKey();
            var junctionBox2 = junctionBoxPair.getValue();
            if (junctionBox1.circuit == 0 && junctionBox2.circuit == 0) {
                var circuitBoxes = Set.of(junctionBox1, junctionBox2);
                var circuit0JunctionBoxes = circuitJunctionBoxes.get(0);
                circuit0JunctionBoxes.removeAll(circuitBoxes);
                if (circuit0JunctionBoxes.isEmpty()) {
                    circuitJunctionBoxes.remove(0);
                }
                junctionBox1.circuit = circuit;
                junctionBox2.circuit = circuit;
                circuitJunctionBoxes.put(circuit, new HashSet<>(circuitBoxes));
                ++circuit;
            } else if (junctionBox1.circuit == 0 || junctionBox2.circuit == 0) {
                var circuit0JunctionBox = junctionBox1.circuit == 0 ? junctionBox1 : junctionBox2;
                var circuitNonZeroJunctionBox = junctionBox1.circuit != 0 ? junctionBox1 : junctionBox2;
                var circuit0JunctionBoxes = circuitJunctionBoxes.get(0);
                circuit0JunctionBoxes.remove(circuit0JunctionBox);
                if (circuit0JunctionBoxes.isEmpty()) {
                    circuitJunctionBoxes.remove(0);
                }
                circuit0JunctionBox.circuit = circuitNonZeroJunctionBox.circuit;
                circuitJunctionBoxes.get(circuitNonZeroJunctionBox.circuit).add(circuit0JunctionBox);
            } else if (junctionBox1.circuit != junctionBox2.circuit) {
                var smallerCircuit = Math.min(junctionBox1.circuit, junctionBox2.circuit);
                var greaterCircuit = Math.max(junctionBox1.circuit, junctionBox2.circuit);
                var junctionBoxGreaterCircuit = circuitJunctionBoxes.get(greaterCircuit);
                circuitJunctionBoxes.remove(greaterCircuit);
                junctionBoxGreaterCircuit.forEach(junctionBox -> junctionBox.circuit = smallerCircuit);
                circuitJunctionBoxes.get(smallerCircuit).addAll(junctionBoxGreaterCircuit);
            }
            if (circuitJunctionBoxes.size() == 1) {
                return junctionBox1.x * junctionBox2.x;
            }
        }

        throw new IllegalStateException("Junction boxes never form a single circuit");
    }

    private static final class JunctionBox {
        private final long x;
        private final long y;
        private final long z;
        private int circuit;

        JunctionBox(String line) {
            var coordinates = line.split(",");
            x = Long.parseLong(coordinates[0]);
            y = Long.parseLong(coordinates[1]);
            z = Long.parseLong(coordinates[2]);
        }

        private double calculateStraightLineDistance(JunctionBox to) {
            return Math.sqrt(Math.pow(x - to.x, 2) + Math.pow(y - to.y, 2) + Math.pow(z - to.z, 2));
        }
    }
}
