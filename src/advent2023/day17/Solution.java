package src.advent2023.day17;

import src.PuzzleSolver;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    private static int minimaseHeatLoss(Map<Integer, Integer> memory, char[][] map, int row, int column, int rowDirection, int columnDirection, int remainingInDirection) {
        if (row < 0 || row >= map.length || column < 0 || column >= map[row].length) {
            return Integer.MAX_VALUE;
        }

        var hash = Objects.hash(row, column, rowDirection, columnDirection);
        var found = memory.get(hash);
        if (found != null) {
            return found;
        }

        var value = 0;
        if (row == map.length - 1 && column == map[row].length - 1) {
            memory.put(hash, value);
            return value;
        }

        var path = Integer.MAX_VALUE;

        if (rowDirection == 0) {
            for (int i = 1; i <= 3; ++i) {
                path = Math.min(path, minimaseHeatLoss(memory, map, row - i, column, -1, 0, 3 - i));
            }
        } else {

        }
        return value + path;
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("2413432311323\n" +
                "3215453535623\n" +
                "3255245654254\n" +
                "3446585845452\n" +
                "4546657867536\n" +
                "1438598798454\n" +
                "4457876987766\n" +
                "3637877979653\n" +
                "4654967986887\n" +
                "4564679986453\n" +
                "1224686865563\n" +
                "2546548887735\n" +
                "4322674655533");
    }

    @Override
    public List<String> getExampleOutput1() {
        return List.of("102");
    }

    @Override
    public List<String> getExampleOutput2() {
        return List.of();
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        char[][] map = lines.map(line -> line.toCharArray())
                .toArray(char[][]::new);
        return "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return "";
    }

}
