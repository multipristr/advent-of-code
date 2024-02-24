package src.advent2023.day25;

import src.PuzzleSolver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("jqt: rhn xhk nvd\n" +
                "rsh: frs pzl lsr\n" +
                "xhk: hfx\n" +
                "cmg: qnr nvd lhk bvb\n" +
                "rhn: xhk bvb hfx\n" +
                "bvb: xhk hfx\n" +
                "pzl: lsr hfx nvd\n" +
                "qnr: nvd\n" +
                "ntq: jqt hfx bvb xhk\n" +
                "nvd: lhk\n" +
                "lsr: lhk\n" +
                "rzs: qnr cmg lsr rsh\n" +
                "frs: qnr lhk lsr");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(54L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of();
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        Map<String, List<String>> components = new HashMap<>((int) (1539 / .75f + 1));
        lines.map(line -> line.split(": "))
                .forEach(parts -> {
                    var targetComponents = Arrays.asList(parts[1].split(" "));
                    components.computeIfAbsent(parts[0], k -> new ArrayList<>(9)).addAll(targetComponents);
                    for (var targetComponent : targetComponents) {
                        components.computeIfAbsent(targetComponent, k -> new ArrayList<>(9)).add(parts[0]);
                    }
                });

        var vtvWires = components.getOrDefault("vtv", List.of());
        var cmjWires = components.getOrDefault("cmj", List.of());
        var jllWires = components.getOrDefault("jll", List.of());
        for (int i = 0; i < vtvWires.size(); i++) {
            var vtvWire = vtvWires.remove(i);
            components.get(vtvWire).remove("vtv");
            for (int j = 0; j < cmjWires.size(); j++) {
                var cmjWire = cmjWires.remove(j);
                components.get(cmjWire).remove("cmj");
                for (int k = 0; k < jllWires.size(); k++) {
                    var jllWire = jllWires.remove(k);
                    components.get(jllWire).remove("jll");

                    LongSummaryStatistics occurrence = components.keySet().parallelStream()
                            .mapToLong(component -> countConnectedEdges(component, components))
                            .distinct()
                            .summaryStatistics();
                    if (occurrence.getCount() == 2) {
                        return occurrence.getMin() * occurrence.getMax();
                    }

                    jllWires.add(k, jllWire);
                    components.get(jllWire).add("jll");
                }
                cmjWires.add(j, cmjWire);
                components.get(cmjWire).add("cmj");
            }
            vtvWires.add(i, vtvWire);
            components.get(vtvWire).add("vtv");
        }

        throw new IllegalStateException("No result");
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private long countConnectedEdges(String node, Map<String, List<String>> edges) {
        long connected = 0;
        Deque<String> open = new ArrayDeque<>();
        Set<String> closed = new HashSet<>();
        open.add(node);
        closed.add(node);
        while (!open.isEmpty()) {
            String curNode = open.pollFirst();
            ++connected;
            for (String targetNode : edges.getOrDefault(curNode, List.of())) {
                if (closed.add(targetNode)) {
                    open.add(targetNode);
                }
            }
        }
        return connected;
    }
}
