package src.advent2024.day10;

import src.PuzzleSolver;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of(
                "0123\n" +
                        "1234\n" +
                        "8765\n" +
                        "9876"
                ,
                "89010123\n" +
                        "78121874\n" +
                        "87430965\n" +
                        "96549874\n" +
                        "45678903\n" +
                        "32019012\n" +
                        "01329801\n" +
                        "10456732"
        );
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(1L, 36L);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of("89010123\n" +
                "78121874\n" +
                "87430965\n" +
                "96549874\n" +
                "45678903\n" +
                "32019012\n" +
                "01329801\n" +
                "10456732");
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(81L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        int[][] topographicMap = lines.map(line -> line.chars().map(Character::getNumericValue).toArray())
                .toArray(int[][]::new);

        return IntStream.range(0, topographicMap.length).parallel()
                .mapToLong(x -> IntStream.range(0, topographicMap[x].length)
                        .filter(y -> topographicMap[x][y] == 0)
                        .mapToLong(y -> findReachableNines(topographicMap, new Position(0, x, y)))
                        .sum()
                )
                .sum();
    }

    private long findReachableNines(int[][] topographicMap, Position trailheadStart) {
        int[] directions = {-1, 0, 0, -1, 0, 1, 1, 0};
        Set<Position> closed = new HashSet<>();
        Deque<Position> open = new ArrayDeque<>();
        long reachedNines = 0;

        open.add(trailheadStart);
        while (!open.isEmpty()) {
            Position current = open.pollFirst();
            for (int i = 1; i < directions.length; i += 2) {
                int nextX = current.x + directions[i - 1];
                if (nextX < 0 || nextX >= topographicMap.length) {
                    continue;
                }
                int nextY = current.y + directions[i];
                if (nextY < 0 || nextY >= topographicMap[nextX].length) {
                    continue;
                }
                int nextHeight = topographicMap[nextX][nextY];
                if (nextHeight == current.height + 1) {
                    Position next = new Position(nextHeight, nextX, nextY);
                    if (closed.add(next)) {
                        if (nextHeight == 9) {
                            ++reachedNines;
                        } else {
                            open.addLast(next);
                        }
                    }
                }
            }
        }

        return reachedNines;
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        int[][] topographicMap = lines.map(line -> line.chars().map(Character::getNumericValue).toArray())
                .toArray(int[][]::new);

        return IntStream.range(0, topographicMap.length).parallel()
                .mapToLong(x -> IntStream.range(0, topographicMap[x].length)
                        .filter(y -> topographicMap[x][y] == 0)
                        .mapToLong(y -> calculateTrailheadRating(topographicMap, new Position(0, x, y)))
                        .sum()
                )
                .sum();
    }

    private long calculateTrailheadRating(int[][] topographicMap, Position trailheadStart) {
        int[] directions = {-1, 0, 0, -1, 0, 1, 1, 0};
        Deque<Position> open = new ArrayDeque<>();
        long distinctHikingTrails = 0;

        open.add(trailheadStart);
        while (!open.isEmpty()) {
            Position current = open.pollFirst();
            for (int i = 1; i < directions.length; i += 2) {
                int nextX = current.x + directions[i - 1];
                if (nextX < 0 || nextX >= topographicMap.length) {
                    continue;
                }
                int nextY = current.y + directions[i];
                if (nextY < 0 || nextY >= topographicMap[nextX].length) {
                    continue;
                }
                int nextHeight = topographicMap[nextX][nextY];
                if (nextHeight == current.height + 1) {
                    Position next = new Position(nextHeight, nextX, nextY);
                    if (nextHeight == 9) {
                        ++distinctHikingTrails;
                    } else {
                        open.addLast(next);
                    }
                }
            }
        }

        return distinctHikingTrails;
    }

    private static final class Position {

        private final int height;
        private final int x;
        private final int y;

        Position(int height, int x, int y) {
            this.height = height;
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

    }

}
