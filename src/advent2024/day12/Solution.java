package src.advent2024.day12;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.Deque;
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
                        "MMMISSJEEE",
                ".....\n" +
                        ".AAA.\n" +
                        ".A.A.\n" +
                        ".AA..\n" +
                        ".A.A.\n" +
                        ".AAA.\n" +
                        "....."
        );
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(140L, 772L, 1930L, 1202L);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of(
                "OOOOO\n" +
                        "OXOXO\n" +
                        "OXXXO",
                "AAAA\n" +
                        "BBCD\n" +
                        "BBCC\n" +
                        "EEEC",
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
                        "MMMISSJEEE",
                ".....\n" +
                        ".AAA.\n" +
                        ".A.A.\n" +
                        ".AA..\n" +
                        ".A.A.\n" +
                        ".AAA.\n" +
                        "....."
        );
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(160L, 80L, 436L, 236L, 368L, 1206L, 452L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        char[][] map = lines.map(String::toCharArray)
                .toArray(char[][]::new);
        byte[] directions = {-1, 0, 0, -1, 0, 1, 1, 0};

        boolean[][] closed = new boolean[map.length][map[0].length];
        long price = 0;
        for (int x = 0; x < map.length; x++) {
            boolean[] closedRow = closed[x];
            for (int y = 0; y < closedRow.length; y++) {
                if (closedRow[y]) {
                    continue;
                }
                char plantType = map[x][y];

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
                        int nextY = current.getValue() + directions[i];
                        if (!isPlantTypeAt(map, plantType, nextX, nextY)) {
                            ++perimeter;
                        } else if (!closed[nextX][nextY]) {
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
        byte[] directions = {-1, 0, 0, -1, 0, 1, 1, 0};

        var regionMap = new long[map.length][map[0].length];
        long regionCount = 1;
        Map<Long, Region> regions = new HashMap<>();
        for (int x = 0; x < map.length; x++) {
            var regionRow = regionMap[x];
            for (int y = 0; y < regionRow.length; y++) {
                if (regionRow[y] != 0) {
                    continue;
                }
                char plantType = map[x][y];

                long area = 0;
                regionRow[y] = regionCount;
                Deque<Map.Entry<Integer, Integer>> open = new ArrayDeque<>();
                open.addLast(new AbstractMap.SimpleImmutableEntry<>(x, y));
                while (!open.isEmpty()) {
                    var current = open.pollFirst();
                    ++area;

                    for (int i = 1; i < directions.length; i += 2) {
                        int nextX = current.getKey() + directions[i - 1];
                        int nextY = current.getValue() + directions[i];
                        if (isPlantTypeAt(map, plantType, nextX, nextY) && regionMap[nextX][nextY] == 0) {
                            regionMap[nextX][nextY] = regionCount;
                            open.addLast(new AbstractMap.SimpleImmutableEntry<>(nextX, nextY));
                        }
                    }
                }

                regions.put(regionCount, new Region(area));
                ++regionCount;
            }
        }

        for (byte rotation = 0; rotation < 4; rotation++) {
            for (int y = 0; y < regionMap.length; y++) {
                var row = regionMap[y];
                for (int x = 0; x < row.length; x++) {
                    var region = row[x];
                    if (isLeftCrossing(regionMap, region, y, x)
                            && (y == 0 || regionMap[y - 1][x] != region || !isLeftCrossing(regionMap, region, y - 1, x))
                    ) {
                        regions.get(region).addSide();
                    }
                }
            }

            final int M = regionMap.length;
            final int N = regionMap[0].length;
            var ret = new long[N][M];
            for (int r = 0; r < M; r++) {
                for (int c = 0; c < N; c++) {
                    ret[c][M - 1 - r] = regionMap[r][c];
                }
            }
            regionMap = ret;
        }

        return regions.values()
                .stream()
                .mapToLong(region -> region.getArea() * region.getSides())
                .sum();
    }

    private boolean isPlantTypeAt(char[][] map, char plantType, int x, int y) {
        return x >= 0 && x < map.length && y >= 0 && y < map[x].length && map[x][y] == plantType;
    }

    private boolean isLeftCrossing(long[][] regions, long region, int y, int x) {
        return x <= 0 || regions[y][x - 1] != region;
    }

    private static final class Region {
        private final long area;
        private long sides;

        private Region(long area) {
            this.area = area;
        }

        public long getArea() {
            return area;
        }

        public long getSides() {
            return sides;
        }

        public Region addSide() {
            ++this.sides;
            return this;
        }
    }

}
