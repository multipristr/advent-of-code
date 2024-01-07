package src.advent2023.day25;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
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
    public List<String> getExampleOutput1() {
        return List.of("54");
    }

    @Override
    public List<String> getExampleOutput2() {
        return List.of();
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        Map<String, List<String>> components = new HashMap<>((int) (1539 / .75f + 1));
        List<Map.Entry<String, String>> wires = new ArrayList<>(3450);
        lines.map(line -> line.split(": "))
                .forEach(parts -> {
                    var targetComponents = Arrays.asList(parts[1].split(" "));
                    components.computeIfAbsent(parts[0], k -> new ArrayList<>(9)).addAll(targetComponents);
                    for (var targetComponent : targetComponents) {
                        components.computeIfAbsent(targetComponent, k -> new ArrayList<>(9)).add(parts[0]);
                        wires.add(new AbstractMap.SimpleImmutableEntry<>(parts[0], targetComponent));
                    }
                });
        for (int i = 0; i < wires.size(); i++) {
            Map.Entry<String, String> wire1 = wires.remove(i);
            components.get(wire1.getKey()).remove(wire1.getValue());
            components.get(wire1.getValue()).remove(wire1.getKey());
            for (int j = i; j < wires.size(); j++) {
                Map.Entry<String, String> wire2 = wires.remove(j);
                components.get(wire2.getKey()).remove(wire2.getValue());
                components.get(wire2.getValue()).remove(wire2.getKey());
                for (int k = j; k < wires.size(); k++) {
                    Map.Entry<String, String> wire3 = wires.remove(k);
                    components.get(wire3.getKey()).remove(wire3.getValue());
                    components.get(wire3.getValue()).remove(wire3.getKey());

                    long componentsInGroup = -1;
                    for (String component : components.keySet()) {
                        long connectedComponents = countConnectedEdges(component, components);
                        if (componentsInGroup != connectedComponents) {
                            if (componentsInGroup != -1) {
                                return "" + componentsInGroup * connectedComponents;
                            }
                            componentsInGroup = connectedComponents;
                        }
                    }

                    wires.add(k, wire3);
                    components.get(wire3.getKey()).add(wire3.getValue());
                    components.get(wire3.getValue()).add(wire3.getKey());
                }
                wires.add(j, wire2);
                components.get(wire2.getKey()).add(wire2.getValue());
                components.get(wire2.getValue()).add(wire2.getKey());
            }
            wires.add(i, wire1);
            components.get(wire1.getKey()).add(wire1.getValue());
            components.get(wire1.getValue()).add(wire1.getKey());
        }
        return "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return "";
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
