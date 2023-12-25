package src.advent2023.day25;

import src.PuzzleSolver;

import java.util.List;
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
        return "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return "";
    }

}
