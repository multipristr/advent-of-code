package src.advent2024.day6;

import src.PuzzleSolver;

import java.util.AbstractMap;
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

    private static boolean isCreatingCycle(char[][] input, int x, int y, int xDirection, int yDirection, int obstructionX, int obstructionY) {
        Set<Position> distinctPositions = new HashSet<>();
        while (isWithinBounds(input, x, y)) {
            if (!distinctPositions.add(new Position(x, y, xDirection, yDirection))) {
                return true;
            }
            while (true) {
                int newX = x + xDirection;
                int newY = y + yDirection;
                if (!isWithinBounds(input, newX, newY)) {
                    return false;
                } else if (input[newX][newY] == '#' || (newX == obstructionX && newY == obstructionY)) {
                    if (xDirection == -1) {
                        xDirection = 0;
                        yDirection = 1;
                    } else if (yDirection == 1) {
                        xDirection = 1;
                        yDirection = 0;
                    } else if (xDirection == 1) {
                        xDirection = 0;
                        yDirection = -1;
                    } else if (yDirection == -1) {
                        xDirection = -1;
                        yDirection = 0;
                    }
                } else {
                    break;
                }
            }
            x += xDirection;
            y += yDirection;
        }
        return false;
    }

    private static boolean isWithinBounds(char[][] input, int x, int y) {
        if (x < 0 || x >= input.length || y < 0) {
            return false;
        }
        char[] row = input[x];
        return y < row.length;
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("....#.....\n" +
                ".........#\n" +
                "..........\n" +
                "..#.......\n" +
                ".......#..\n" +
                "..........\n" +
                ".#..^.....\n" +
                "........#.\n" +
                "#.........\n" +
                "......#...");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(41L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(6L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        char[][] input = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        int x;
        int y = 0;
        int xDirection = -1;
        int yDirection = 0;
        outer:
        for (x = 0; x < input.length; x++) {
            char[] row = input[x];
            for (y = 0; y < row.length; y++) {
                if (row[y] == '^') {
                    break outer;
                }
            }
        }

        Set<Map.Entry<Integer, Integer>> distinctPositions = new HashSet<>();
        while (isWithinBounds(input, x, y)) {
            distinctPositions.add(new AbstractMap.SimpleImmutableEntry<>(x, y));
            while (true) {
                int newX = x + xDirection;
                int newY = y + yDirection;
                if (!isWithinBounds(input, newX, newY)) {
                    return distinctPositions.size();
                } else if (input[newX][newY] == '#') {
                    if (xDirection == -1) {
                        xDirection = 0;
                        yDirection = 1;
                    } else if (yDirection == 1) {
                        xDirection = 1;
                        yDirection = 0;
                    } else if (xDirection == 1) {
                        xDirection = 0;
                        yDirection = -1;
                    } else if (yDirection == -1) {
                        xDirection = -1;
                        yDirection = 0;
                    }
                } else {
                    break;
                }
            }
            x += xDirection;
            y += yDirection;
        }

        return distinctPositions.size();
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        char[][] input = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        int x;
        int y = 0;
        int xDirection = -1;
        int yDirection = 0;
        outer:
        for (x = 0; x < input.length; x++) {
            char[] row = input[x];
            for (y = 0; y < row.length; y++) {
                if (row[y] == '^') {
                    break outer;
                }
            }
        }

        int initialX = x;
        int initialY = y;

        Set<Map.Entry<Integer, Integer>> distinctPositions = new HashSet<>();
        outer:
        while (isWithinBounds(input, x, y)) {
            distinctPositions.add(new AbstractMap.SimpleImmutableEntry<>(x, y));
            while (true) {
                int newX = x + xDirection;
                int newY = y + yDirection;
                if (!isWithinBounds(input, newX, newY)) {
                    break outer;
                } else if (input[newX][newY] == '#') {
                    if (xDirection == -1) {
                        xDirection = 0;
                        yDirection = 1;
                    } else if (yDirection == 1) {
                        xDirection = 1;
                        yDirection = 0;
                    } else if (xDirection == 1) {
                        xDirection = 0;
                        yDirection = -1;
                    } else if (yDirection == -1) {
                        xDirection = -1;
                        yDirection = 0;
                    }
                } else {
                    break;
                }
            }
            x += xDirection;
            y += yDirection;
        }

        distinctPositions.remove(new AbstractMap.SimpleImmutableEntry<>(initialX, initialY));
        return distinctPositions.stream()
                .filter(position -> isCreatingCycle(input, initialX, initialY, -1, 0, position.getKey(), position.getValue()))
                .count();
    }

    private static final class Position {

        private final int x;
        private final int y;
        private final int xDirection;
        private final int yDirection;

        private Position(int x, int y, int xDirection, int yDirection) {
            this.x = x;
            this.y = y;
            this.xDirection = xDirection;
            this.yDirection = yDirection;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y && xDirection == position.xDirection && yDirection == position.yDirection;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, xDirection, yDirection);
        }

    }

}
