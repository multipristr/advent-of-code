package src.advent2024.day20;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("###############\n" +
                "#...#...#.....#\n" +
                "#.#.#.#.#.###.#\n" +
                "#S#...#.#.#...#\n" +
                "#######.#.#.###\n" +
                "#######.#.#...#\n" +
                "#######.#.###.#\n" +
                "###..E#...#...#\n" +
                "###.#######.###\n" +
                "#...###...#...#\n" +
                "#.#####.#.###.#\n" +
                "#.#...#.#.#...#\n" +
                "#.#.#.#.#.#.###\n" +
                "#...#...#...###\n" +
                "###############");
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(0);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(0);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        return countCheatSavingAtLeastPicoseconds(lines, 2, 100);
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        return countCheatSavingAtLeastPicoseconds(lines, 20, 100);
    }

    private int countCheatSavingAtLeastPicoseconds(Stream<String> lines, int cheatPicoseconds, int saveAtLeastPicoseconds) {
        var racetrackMap = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;
        for (int y = 0; y < racetrackMap.length; y++) {
            var row = racetrackMap[y];
            for (int x = 0; x < row.length; x++) {
                var mark = row[x];
                if (mark == 'S') {
                    startX = x;
                    startY = y;
                } else if (mark == 'E') {
                    endX = x;
                    endY = y;
                }
            }
        }

        var distancesFromEnd = calculateDistancesFrom(racetrackMap, endX, endY);
        var distancesFromStart = calculateDistancesFrom(racetrackMap, startX, startY);

        var timeLimit = distancesFromStart[endY][endX] - saveAtLeastPicoseconds;
        int savingCheats = 0;
        for (int y1 = 0; y1 < racetrackMap.length; y1++) {
            var racetrackRow1 = racetrackMap[y1];
            for (int x1 = 0; x1 < racetrackRow1.length; x1++) {
                if (racetrackRow1[x1] == '#'
                        || calculateManhattanDistance(x1, y1, endX, endY) >= timeLimit
                        || calculateManhattanDistance(x1, y1, startX, startY) >= timeLimit) {
                    continue;
                }
                var distanceFromStart = distancesFromStart[y1][x1];
                if (distanceFromStart >= timeLimit) {
                    continue;
                }
                for (int y2 = 0; y2 < racetrackMap.length; y2++) {
                    var racetrackRow2 = racetrackMap[y2];
                    var distanceFromEndRow = distancesFromEnd[y2];
                    for (int x2 = 0; x2 < racetrackRow2.length; x2++) {
                        if (racetrackRow2[x2] == '#') {
                            continue;
                        }
                        int cheatDistance = calculateManhattanDistance(x1, y1, x2, y2);
                        if (cheatDistance <= cheatPicoseconds && distanceFromStart + cheatDistance + distanceFromEndRow[x2] <= timeLimit) {
                            ++savingCheats;
                        }
                    }
                }
            }
        }
        return savingCheats;
    }

    private int calculateManhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private int[][] calculateDistancesFrom(char[][] racetrackMap, int x, int y) {
        byte[] directions = {-1, 0, 0, -1, 0, 1, 1, 0};
        var distances = new int[racetrackMap.length][racetrackMap[0].length];
        var startMove = new AbstractMap.SimpleImmutableEntry<>(x, y);
        Queue<Map.Entry<Integer, Integer>> open = new ArrayDeque<>();
        open.add(startMove);
        while (!open.isEmpty()) {
            var current = open.poll();

            for (byte i = 1; i < directions.length; i += 2) {
                var nextY = current.getValue() + directions[i - 1];
                if (nextY < 0 || nextY >= racetrackMap.length) {
                    continue;
                }
                var nextX = current.getKey() + directions[i];
                if (nextX < 0) {
                    continue;
                }
                var racetrackRow = racetrackMap[nextY];
                if (nextX >= racetrackRow.length || racetrackRow[nextX] == '#') {
                    continue;
                }
                var distanceRow = distances[nextY];
                if (distanceRow[nextX] == 0) {
                    distanceRow[nextX] = distances[current.getValue()][current.getKey()] + 1;
                    open.add(new AbstractMap.SimpleImmutableEntry<>(nextX, nextY));
                }
            }
        }
        distances[y][x] = 0;

        return distances;
    }

}
