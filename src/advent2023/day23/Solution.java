package src.advent2023.day23;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Integer, Integer> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("#.#####################\n" +
                "#.......#########...###\n" +
                "#######.#########.#.###\n" +
                "###.....#.>.>.###.#.###\n" +
                "###v#####.#v#.###.#.###\n" +
                "###.>...#.#.#.....#...#\n" +
                "###v###.#.#.#########.#\n" +
                "###...#.#.#.......#...#\n" +
                "#####.#.#.#######.#.###\n" +
                "#.....#.#.#.......#...#\n" +
                "#.#####.#.#.#########v#\n" +
                "#.#...#...#...###...>.#\n" +
                "#.#.#v#######v###.###v#\n" +
                "#...#.>.#...>.>.#.###.#\n" +
                "#####v#.#.###v#.#.###.#\n" +
                "#.....#...#...#.#.#...#\n" +
                "#.#########.###.#.#.###\n" +
                "#...###...#...#...#.###\n" +
                "###.###.#.###v#####v###\n" +
                "#...#...#.#.>.>.#.>.###\n" +
                "#.###.###.#.###.#.#v###\n" +
                "#.....###...###...#...#\n" +
                "#####################.#");
    }

    @Override
    public List<Integer> getExampleOutput1() {
        return List.of(94);
    }

    @Override
    public List<Integer> getExampleOutput2() {
        return List.of();
    }

    @Override
    public Integer solvePartOne(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Integer solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
