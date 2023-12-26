package src.advent2023.day17;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    private static void minimaseHeatLoss(int[][] map, int row, int column, int rowDirection, int remainingInDirection, long[][] visited, long path) {
        if (row < 0 || row >= map.length || column < 0 || column >= map[row].length) {
            return;
        }

        long value = map[row][column];
        path += value;
        if (path > visited[row][column]) {
            return;
        }
        visited[row][column] = path;
        if (row == map.length - 1 && column == map[row].length - 1) {
            return;
        }

        if (remainingInDirection <= 0) {
            if (rowDirection == 0) {
                minimaseHeatLoss(map, row - 1, column, -1, 2, visited, path);
                minimaseHeatLoss(map, row + 1, column, 1, 2, visited, path);
            } else {
                minimaseHeatLoss(map, row, column - 1, 0, 2, visited, path);
                minimaseHeatLoss(map, row, column + 1, 0, 2, visited, path);
            }
        } else {
            if (rowDirection == 0) {
                minimaseHeatLoss(map, row - 1, column, -1, 2, visited, path);
                minimaseHeatLoss(map, row + 1, column, 1, 2, visited, path);
                minimaseHeatLoss(map, row, column - 1, 0, remainingInDirection - 1, visited, path);
                minimaseHeatLoss(map, row, column + 1, 0, remainingInDirection - 1, visited, path);
            } else {
                minimaseHeatLoss(map, row, column - 1, 0, 2, visited, path);
                minimaseHeatLoss(map, row, column + 1, 0, 2, visited, path);
                minimaseHeatLoss(map, row - 1, column, -1, remainingInDirection - 1, visited, path);
                minimaseHeatLoss(map, row + 1, column, 1, remainingInDirection - 1, visited, path);
            }
        }
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
        int[][] map = lines.map(line -> line.chars().map(Character::getNumericValue).toArray())
                .toArray(int[][]::new);
        long[][] visited = LongStream.range(0, map.length)
                .mapToObj(i -> LongStream.range(0, map[0].length)
                        .map(j -> Long.MAX_VALUE)
                        .toArray()
                )
                .toArray(long[][]::new);
        visited[0][0] = 0;
        minimaseHeatLoss(map, 1, 0, 1, 2, visited, 0);
        minimaseHeatLoss(map, 0, 1, 0, 2, visited, 0);
        return visited[visited.length - 1][visited.length - 1] + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return "";
    }

}
