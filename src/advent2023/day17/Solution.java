package src.advent2023.day17;

import src.PuzzleSolver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    private static void minimiseHeatLoss(int[][] map, int row, int column, int rowDirection, int columnDirection, int remainingInDirection, Map<Integer, Map<Integer, Map<Integer, Map<Integer, NavigableMap<Integer, Long>>>>> visited, long path) {
        if (row < 0 || row >= map.length || column < 0 || column >= map[row].length) {
            return;
        }

        long nextPath = path + map[row][column];
        NavigableMap<Integer, Long> pathPerRemaining = visited.computeIfAbsent(row, r -> new ConcurrentHashMap<>(map.length))
                .computeIfAbsent(column, c -> new ConcurrentHashMap<>(map[0].length))
                .computeIfAbsent(rowDirection, rd -> new ConcurrentHashMap<>(3))
                .computeIfAbsent(columnDirection, cd -> new TreeMap<>());
        if (pathPerRemaining.tailMap(remainingInDirection, true)
                .values()
                .stream()
                .anyMatch(previousPath -> previousPath <= nextPath)) {
            return;
        }
        pathPerRemaining.put(remainingInDirection, nextPath);
        if (row == map.length - 1 && column == map[row].length - 1) {
            return;
        }

        if (remainingInDirection <= 0) {
            if (rowDirection == 0) {
                minimiseHeatLoss(map, row - 1, column, -1, 0, 2, visited, nextPath);
                minimiseHeatLoss(map, row + 1, column, 1, 0, 2, visited, nextPath);
            } else {
                minimiseHeatLoss(map, row, column - 1, 0, -1, 2, visited, nextPath);
                minimiseHeatLoss(map, row, column + 1, 0, 1, 2, visited, nextPath);
            }
        } else {
            if (rowDirection == 0) {
                minimiseHeatLoss(map, row - 1, column, -1, 0, 2, visited, nextPath);
                minimiseHeatLoss(map, row + 1, column, 1, 0, 2, visited, nextPath);
                minimiseHeatLoss(map, row, column - 1, 0, -1, remainingInDirection - 1, visited, nextPath);
                minimiseHeatLoss(map, row, column + 1, 0, 1, remainingInDirection - 1, visited, nextPath);
            } else {
                minimiseHeatLoss(map, row, column - 1, 0, -1, 2, visited, nextPath);
                minimiseHeatLoss(map, row, column + 1, 0, 1, 2, visited, nextPath);
                minimiseHeatLoss(map, row - 1, column, -1, 0, remainingInDirection - 1, visited, nextPath);
                minimiseHeatLoss(map, row + 1, column, 1, 0, remainingInDirection - 1, visited, nextPath);
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
    public String solvePartOne(Stream<String> lines) throws InterruptedException {
        int[][] map = lines.map(line -> line.chars().map(Character::getNumericValue).toArray()).toArray(int[][]::new);
        Map<Integer, Map<Integer, Map<Integer, Map<Integer, NavigableMap<Integer, Long>>>>> searchStates = new HashMap<>();
        searchStates.computeIfAbsent(0, r -> new ConcurrentHashMap<>(map.length))
                .computeIfAbsent(0, c -> new ConcurrentHashMap<>(map[0].length))
                .computeIfAbsent(1, rd -> new ConcurrentHashMap<>(3))
                .computeIfAbsent(0, cd -> new TreeMap<>())
                .put(2, 0L);
        searchStates.computeIfAbsent(0, r -> new ConcurrentHashMap<>(map.length))
                .computeIfAbsent(0, c -> new ConcurrentHashMap<>(map[0].length))
                .computeIfAbsent(0, rd -> new ConcurrentHashMap<>(3))
                .computeIfAbsent(1, cd -> new TreeMap<>())
                .put(2, 0L);
        searchStates.computeIfAbsent(0, r -> new ConcurrentHashMap<>(map.length))
                .computeIfAbsent(0, c -> new ConcurrentHashMap<>(map[0].length))
                .computeIfAbsent(-1, rd -> new ConcurrentHashMap<>(3))
                .computeIfAbsent(0, cd -> new TreeMap<>())
                .put(2, 0L);
        searchStates.computeIfAbsent(0, r -> new ConcurrentHashMap<>(map.length))
                .computeIfAbsent(0, c -> new ConcurrentHashMap<>(map[0].length))
                .computeIfAbsent(0, rd -> new ConcurrentHashMap<>(3))
                .computeIfAbsent(-1, cd -> new TreeMap<>())
                .put(2, 0L);
        ExecutorService threadPool = new ForkJoinPool();
        threadPool.execute(() -> minimiseHeatLoss(map, 1, 0, 1, 0, 2, searchStates, 0));
        minimiseHeatLoss(map, 0, 1, 0, 1, 2, searchStates, 0);
        threadPool.awaitTermination(10, TimeUnit.DAYS);
        return "" + searchStates.get(map.length - 1).get(map[0].length - 1).values().stream()
                .flatMap(m -> m.values().stream())
                .flatMap(m -> m.values().stream())
                .mapToLong(v -> v)
                .min().orElseThrow();
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return "";
    }
}
