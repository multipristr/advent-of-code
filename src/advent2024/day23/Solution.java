package src.advent2024.day23;

import src.PuzzleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("kh-tc\n" +
                "qp-kh\n" +
                "de-cg\n" +
                "ka-co\n" +
                "yn-aq\n" +
                "qp-ub\n" +
                "cg-tb\n" +
                "vc-aq\n" +
                "tb-ka\n" +
                "wh-tc\n" +
                "yn-cg\n" +
                "kh-ub\n" +
                "ta-co\n" +
                "de-co\n" +
                "tc-td\n" +
                "tb-wq\n" +
                "wh-td\n" +
                "ta-ka\n" +
                "td-qp\n" +
                "aq-cg\n" +
                "wq-ub\n" +
                "ub-vc\n" +
                "de-ta\n" +
                "wq-aq\n" +
                "wq-vc\n" +
                "wh-yn\n" +
                "ka-de\n" +
                "kh-ta\n" +
                "co-tc\n" +
                "wh-qp\n" +
                "tb-vc\n" +
                "td-yn");
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(7);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of(
                "kh-tc\n" +
                        "qp-kh\n" +
                        "de-cg\n" +
                        "ka-co\n" +
                        "yn-aq\n" +
                        "qp-ub\n" +
                        "cg-tb\n" +
                        "vc-aq\n" +
                        "tb-ka\n" +
                        "wh-tc\n" +
                        "yn-cg\n" +
                        "kh-ub\n" +
                        "ta-co\n" +
                        "de-co\n" +
                        "tc-td\n" +
                        "tb-wq\n" +
                        "wh-td\n" +
                        "ta-ka\n" +
                        "td-qp\n" +
                        "aq-cg\n" +
                        "wq-ub\n" +
                        "ub-vc\n" +
                        "de-ta\n" +
                        "wq-aq\n" +
                        "wq-vc\n" +
                        "wh-yn\n" +
                        "ka-de\n" +
                        "kh-ta\n" +
                        "co-tc\n" +
                        "wh-qp\n" +
                        "tb-vc\n" +
                        "td-yn",
                "ka-co\n" +
                        "ta-co\n" +
                        "de-co\n" +
                        "ta-ka\n" +
                        "de-ta\n" +
                        "ka-de"
        );
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of("co,de,ka,ta", "co,de,ka,ta");
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        Map<String, Set<String>> computerConnections = new HashMap<>();
        lines.map(line -> line.split("-"))
                .forEach(computers -> {
                    var computer1 = computers[0];
                    var computer2 = computers[1];
                    computerConnections.computeIfAbsent(computer1, k -> new HashSet<>()).add(computer2);
                    computerConnections.computeIfAbsent(computer2, k -> new HashSet<>()).add(computer1);
                });

        Set<String> threeInterConnectedStartingWithT = new HashSet<>();
        computerConnections.forEach((computer, connections) -> {
            boolean startsWithT = computer.charAt(0) == 't';
            for (var connection : connections) {
                var commonConnections = new ArrayList<>(computerConnections.get(connection));
                commonConnections.retainAll(connections);
                boolean connectionStartsWithT = startsWithT || connection.charAt(0) == 't';
                for (var commonConnection : commonConnections) {
                    if (connectionStartsWithT || commonConnection.charAt(0) == 't') {
                        String[] threeInterConnected = {computer, connection, commonConnection};
                        Arrays.sort(threeInterConnected);
                        threeInterConnectedStartingWithT.add(String.join(",", threeInterConnected));
                    }
                }
            }
        });
        return threeInterConnectedStartingWithT.size();
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
