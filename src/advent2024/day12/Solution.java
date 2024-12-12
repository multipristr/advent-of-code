package src.advent2024.day12;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of(
                "AAAA\n" +
                        "BBCD\n" +
                        "BBCC\n" +
                        "EEEC",
                "OOOOO\n" +
                        "OXOXO\n" +
                        "OOOOO\n" +
                        "OXOXO\n" +
                        "OOOOO",
                "RRRRIICCFF\n" +
                        "RRRRIICCCF\n" +
                        "VVRRRCCFFF\n" +
                        "VVRCCCJFFF\n" +
                        "VVVVCJJCFE\n" +
                        "VVIVCCJJEE\n" +
                        "VVIIICJJEE\n" +
                        "MIIIIIJJEE\n" +
                        "MIIISIJEEE\n" +
                        "MMMISSJEEE"
        );
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(140L, 772L, 1930L);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of(
                "AAAA\n" +
                        "BBCD\n" +
                        "BBCC\n" +
                        "EEEC"/*,
                "OOOOO\n" +
                        "OXOXO\n" +
                        "OOOOO\n" +
                        "OXOXO\n" +
                        "OOOOO",
                "EEEEE\n" +
                        "EXXXX\n" +
                        "EEEEE\n" +
                        "EXXXX\n" +
                        "EEEEE",
                "AAAAAA\n" +
                        "AAABBA\n" +
                        "AAABBA\n" +
                        "ABBAAA\n" +
                        "ABBAAA\n" +
                        "AAAAAA",
                "RRRRIICCFF\n" +
                        "RRRRIICCCF\n" +
                        "VVRRRCCFFF\n" +
                        "VVRCCCJFFF\n" +
                        "VVVVCJJCFE\n" +
                        "VVIVCCJJEE\n" +
                        "VVIIICJJEE\n" +
                        "MIIIIIJJEE\n" +
                        "MIIISIJEEE\n" +
                        "MMMISSJEEE"*/
        );
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(80L/*, 436L, 236L, 368L, 1206L*/);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        char[][] map = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        int[] directions = {-1, 0, 0, -1, 0, 1, 1, 0};
        boolean[][] closed = new boolean[map.length][map.length];
        long price = 0;

        for (int x = 0; x < map.length; x++) {
            char[] row = map[x];
            boolean[] closedRow = closed[x];
            for (int y = 0; y < row.length; y++) {
                if (closedRow[y]) {
                    continue;
                }
                char plantType = row[y];

                long area = 0;
                long perimeter = 0;
                closedRow[y] = true;
                Deque<Map.Entry<Integer, Integer>> open = new ArrayDeque<>();
                open.addLast(new AbstractMap.SimpleImmutableEntry<>(x, y));
                while (!open.isEmpty()) {
                    var current = open.pollFirst();
                    ++area;
                    for (int i = 1; i < directions.length; i += 2) {
                        int nextX = current.getKey() + directions[i - 1];
                        if (nextX < 0 || nextX >= map.length) {
                            ++perimeter;
                            continue;
                        }
                        int nextY = current.getValue() + directions[i];
                        if (nextY < 0 || nextY >= map[nextX].length || map[nextX][nextY] != plantType) {
                            ++perimeter;
                            continue;
                        }
                        if (!closed[nextX][nextY]) {
                            closed[nextX][nextY] = true;
                            open.addLast(new AbstractMap.SimpleImmutableEntry<>(nextX, nextY));
                        }
                    }
                }
                price += area * perimeter;
            }
        }

        return price;
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        char[][] map = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        int[] directions = {-1, 0, 0, -1, 0, 1, 1, 0};
        boolean[][] closed = new boolean[map.length][map.length];
        long price = 0;

        for (int x = 0; x < map.length; x++) {
            char[] row = map[x];
            boolean[] closedRow = closed[x];
            for (int y = 0; y < row.length; y++) {
                if (closedRow[y]) {
                    continue;
                }
                char plantType = row[y];

                long area = 0;
                closedRow[y] = true;
                Deque<Map.Entry<Integer, Integer>> open = new ArrayDeque<>();
                open.addLast(new AbstractMap.SimpleImmutableEntry<>(x, y));
                while (!open.isEmpty()) {
                    var current = open.pollFirst();
                    ++area;
                    for (int i = 1; i < directions.length; i += 2) {
                        int nextX = current.getKey() + directions[i - 1];
                        if (nextX < 0 || nextX >= map.length) {
                            continue;
                        }
                        int nextY = current.getValue() + directions[i];
                        if (nextY < 0 || nextY >= map[nextX].length || map[nextX][nextY] != plantType) {
                            continue;
                        }
                        if (!closed[nextX][nextY]) {
                            closed[nextX][nextY] = true;
                            open.addLast(new AbstractMap.SimpleImmutableEntry<>(nextX, nextY));
                        }
                    }
                }

                long sides = 0;

                System.out.println(plantType + " " + area + " x " + sides);
                price += area * sides;
            }
        }

        return price;
    }

}
