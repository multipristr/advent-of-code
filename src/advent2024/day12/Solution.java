package src.advent2024.day12;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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

        int[][] regions = new int[map.length][map[0].length];
        int region = 1;
        Map<Integer, Long> areas = new HashMap<>();
        for (int x = 0; x < map.length; x++) {
            char[] row = map[x];
            int[] regionRow = regions[x];
            for (int y = 0; y < row.length; y++) {
                if (regionRow[y] != 0) {
                    continue;
                }
                char plantType = row[y];

                long area = 0;
                regionRow[y] = region;
                Deque<Map.Entry<Integer, Integer>> open = new ArrayDeque<>();
                open.addLast(new AbstractMap.SimpleImmutableEntry<>(x, y));
                while (!open.isEmpty()) {
                    var current = open.pollFirst();
                    ++area;
                    for (int i = 1; i < directions.length; i += 2) {
                        int nextX = current.getKey() + directions[i - 1];
                        int nextY = current.getValue() + directions[i];
                        if (isPlantTypeAt(map, plantType, nextX, nextY) && regions[nextX][nextY] == 0) {
                            regions[nextX][nextY] = region;
                            open.addLast(new AbstractMap.SimpleImmutableEntry<>(nextX, nextY));
                        }
                    }
                }

                areas.put(region, area);
                ++region;
            }
        }

        Map<Integer, Long> sides = new HashMap<>(areas.size());
        boolean[] closedRegions = new boolean[areas.size()];
        for (int x = 0; x < map.length; x++) {
            char[] row = map[x];
            int[] regionRow = regions[x];
            for (int y = 0; y < row.length; y++) {
                region = regionRow[y];
                if (closedRegions[region - 1]) {
                    continue;
                }
                closedRegions[region - 1] = true;
                char plantType = row[y];

                long outerSides = 1;
                int currentX = x;
                int currentY = y;
                Direction currentDirection = Direction.RIGHT;
                Direction firstSideTouchDirection = Direction.UP;
                Integer outsideRegion = -1;
                while (currentX != x || currentY != y || currentDirection != firstSideTouchDirection) {
                    Direction leftDirection = currentDirection.getLeft();
                    int fenceX = currentX + leftDirection.x;
                    int fenceY = currentY + leftDirection.y;
                    if (isPlantTypeAt(map, plantType, fenceX, fenceY)) {
                        ++outerSides;
                        currentX = fenceX;
                        currentY = fenceY;
                        currentDirection = leftDirection;
                    } else {
                        int nextX = currentX + currentDirection.x;
                        int nextY = currentY + currentDirection.y;
                        if (isPlantTypeAt(map, plantType, nextX, nextY)) {
                            currentX = nextX;
                            currentY = nextY;
                        } else {
                            if (nextX >= 0 && nextX < map.length && nextY >= 0 && nextY < map[nextX].length) {
                                if (outsideRegion == -1) {
                                    outsideRegion = regions[nextX][nextY];
                                } else if (outsideRegion != regions[nextX][nextY]) {
                                    outsideRegion = -2;
                                }
                            } else {
                                outsideRegion = -2;
                            }
                            ++outerSides;
                            currentDirection = currentDirection.getRight();
                        }
                    }
                }

                if (outsideRegion > 0) {
                    sides.merge(outsideRegion, outerSides, Long::sum);
                }
                sides.merge(region, outerSides, Long::sum);
            }
        }

        return areas.entrySet().stream()
                .mapToLong(entry -> entry.getValue() * sides.get(entry.getKey()))
                .sum();
    }

    private boolean isPlantTypeAt(char[][] map, char plantType, int x, int y) {
        return x >= 0 && x < map.length && y >= 0 && y < map[x].length && map[x][y] == plantType;
    }

    private enum Direction {
        UP(-1, 0),
        LEFT(0, -1),
        DOWN(1, 0),
        RIGHT(0, 1);

        private final int x;
        private final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Direction getLeft() {
            switch (this) {
                case UP:
                    return LEFT;
                case LEFT:
                    return DOWN;
                case DOWN:
                    return RIGHT;
                case RIGHT:
                    return UP;
                default:
                    throw new IllegalStateException(toString());
            }
        }

        private Direction getRight() {
            switch (this) {
                case UP:
                    return RIGHT;
                case LEFT:
                    return UP;
                case DOWN:
                    return LEFT;
                case RIGHT:
                    return DOWN;
                default:
                    throw new IllegalStateException(toString());
            }
        }
    }

}
