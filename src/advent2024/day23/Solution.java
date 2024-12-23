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
    public List<Comparable<?>> getExampleOutput2() {
        return List.of("co,de,ka,ta");
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        var networkMap = createNetworkMap(lines);

        Set<String> threeInterConnectedComputersStartingWithT = new HashSet<>();
        networkMap.forEach((computer, connections) -> {
            boolean startsWithT = computer.charAt(0) == 't';
            for (var connection : connections) {
                var interConnections = new ArrayList<>(networkMap.get(connection));
                interConnections.retainAll(connections);
                boolean connectionStartsWithT = startsWithT || connection.charAt(0) == 't';
                for (var interConnection : interConnections) {
                    if (connectionStartsWithT || interConnection.charAt(0) == 't') {
                        String[] threeInterConnected = {computer, connection, interConnection};
                        Arrays.sort(threeInterConnected);
                        threeInterConnectedComputersStartingWithT.add(String.join(",", threeInterConnected));
                    }
                }
            }
        });
        return threeInterConnectedComputersStartingWithT.size();
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        var networkMap = createNetworkMap(lines);

        List<String> largestConnectedComputersSet = List.of();
        for (var computerConnections : networkMap.entrySet()) {
            List<String> connectedComputersSet = new ArrayList<>();
            connectedComputersSet.add(computerConnections.getKey());
            for (var connection : computerConnections.getValue()) {
                if (networkMap.get(connection).containsAll(connectedComputersSet)) {
                    connectedComputersSet.add(connection);
                }
            }
            if (connectedComputersSet.size() > largestConnectedComputersSet.size()) {
                largestConnectedComputersSet = connectedComputersSet;
            }
        }
        largestConnectedComputersSet.sort(null);
        return String.join(",", largestConnectedComputersSet);
    }

    private Map<String, Set<String>> createNetworkMap(final Stream<String> lines) {
        Map<String, Set<String>> networkMap = new HashMap<>();
        lines.map(line -> line.split("-"))
                .forEach(computers -> {
                    var computer1 = computers[0];
                    var computer2 = computers[1];
                    networkMap.computeIfAbsent(computer1, k -> new HashSet<>()).add(computer2);
                    networkMap.computeIfAbsent(computer2, k -> new HashSet<>()).add(computer1);
                });
        return networkMap;
    }

}
