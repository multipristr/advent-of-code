package src.advent2023.day25;

import src.PuzzleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        lines.map(line -> line.split(": "))
                .forEach(parts -> {
                    var targetComponents = Arrays.asList(parts[1].split(" "));
                    components.computeIfAbsent(parts[0], k -> new ArrayList<>(9)).addAll(targetComponents);
                    for (var targetComponent : targetComponents) {
                        components.computeIfAbsent(targetComponent, k -> new ArrayList<>(9)).add(parts[0]);
                    }
                });
        return "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return "";
    }

}
