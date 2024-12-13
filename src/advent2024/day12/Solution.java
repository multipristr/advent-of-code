package src.advent2024.day12;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
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
        int[] directions = {-1, 0, 0, -1, 0, 1, 1, 0};

        int[][] regionMap = new int[map.length][map[0].length];
        int regionIndex = 1;
        Map<Integer, Region> regions = new HashMap<>();
        for (int x = 0; x < map.length; x++) {
            char[] row = map[x];
            int[] regionRow = regionMap[x];
            for (int y = 0; y < row.length; y++) {
                if (regionRow[y] != 0) {
                    continue;
                }
                char plantType = row[y];

                long area = 0;
                Map<Edge, Integer> regionEdges = new HashMap<>();
                regionRow[y] = regionIndex;
                Deque<Map.Entry<Integer, Integer>> open = new ArrayDeque<>();
                open.addLast(new AbstractMap.SimpleImmutableEntry<>(x, y));
                while (!open.isEmpty()) {
                    var current = open.pollFirst();
                    ++area;

                    regionEdges.merge(new Edge(current.getKey(), current.getKey() + 1, current.getValue(), current.getValue()), 1, Integer::sum);
                    regionEdges.merge(new Edge(current.getKey(), current.getKey() + 1, current.getValue() + 1, current.getValue() + 1), 1, Integer::sum);
                    regionEdges.merge(new Edge(current.getKey(), current.getKey(), current.getValue(), current.getValue() + 1), 1, Integer::sum);
                    regionEdges.merge(new Edge(current.getKey() + 1, current.getKey() + 1, current.getValue(), current.getValue() + 1), 1, Integer::sum);

                    for (int i = 1; i < directions.length; i += 2) {
                        int nextX = current.getKey() + directions[i - 1];
                        int nextY = current.getValue() + directions[i];
                        if (isPlantTypeAt(map, plantType, nextX, nextY) && regionMap[nextX][nextY] == 0) {
                            regionMap[nextX][nextY] = regionIndex;
                            open.addLast(new AbstractMap.SimpleImmutableEntry<>(nextX, nextY));
                        }
                    }
                }

                regions.put(regionIndex - 1, new Region(regionEdges, area));
                ++regionIndex;
            }
        }

        for (Region region : regions.values()) {
            region.removeInnerEdges();
            Map<Integer, List<Edge>> yEdges = region.getEdges().keySet().stream()
                    .sorted(Comparator.comparingInt(Edge::getX1).thenComparing(Edge::getX2))
                    .collect(Collectors.groupingBy(Edge::getY1));
        }

        return regions.values()
                .stream()
                .mapToLong(region -> region.getArea() * region.getSides())
                .sum();
    }

    private boolean isPlantTypeAt(char[][] map, char plantType, int x, int y) {
        return x >= 0 && x < map.length && y >= 0 && y < map[x].length && map[x][y] == plantType;
    }

    private static final class Region {
        private final Map<Edge, Integer> edges;
        private final long area;
        private long sides;

        private Region(Map<Edge, Integer> edges, long area) {
            this.edges = edges;
            this.area = area;
        }

        public void removeInnerEdges() {
            edges.values().removeIf(occurrence -> occurrence > 1);
        }

        public Map<Edge, Integer> getEdges() {
            return edges;
        }

        public long getArea() {
            return area;
        }

        public long getSides() {
            return sides;
        }

        public Region setSides(long sides) {
            this.sides = sides;
            return this;
        }
    }

    private static final class Edge {
        private final int x1;
        private final int x2;
        private final int y1;
        private final int y2;

        private Edge(int x1, int x2, int y1, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }

        public int getX1() {
            return x1;
        }

        public int getX2() {
            return x2;
        }

        public int getY1() {
            return y1;
        }

        public int getY2() {
            return y2;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return x1 == edge.x1 && x2 == edge.x2 && y1 == edge.y1 && y2 == edge.y2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x1, x2, y1, y2);
        }
    }

}
