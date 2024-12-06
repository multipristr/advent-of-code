package src.advent2024.day6;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
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

    private static boolean isCreatingCycle(char[][] input, int x, int y, int xDirection, int yDirection, int obstructionX, int obstructionY) {
        if (obstructionX == x && obstructionY == y) {
            return false;
        }

        Set<Position> distinctPositions = new HashSet<>();
        while (x >= 0 && x < input.length && y >= 0 && y < input[x].length) {
            if (!distinctPositions.add(new Position(x, y, xDirection, yDirection))) {
                StringJoiner joiner = new StringJoiner(System.lineSeparator());
                joiner.add("-------------------");
                for (int i = 0; i < input.length; i++) {
                    String s = new String(input[i]);
                    if (i == obstructionX) {
                        s = s.substring(0, obstructionY) + 'O' + s.substring(obstructionY + 1);
                    }
                    joiner.add(s);
                }
                joiner.add("-------------------");
//                System.out.println(joiner);
                return true;
            }
            int newX = x + xDirection;
            int newY = y + yDirection;
            if (newX < 0 || newX >= input.length || newY < 0 || newY >= input[newX].length) {
                return false;
            }
            if (input[newX][newY] == '#' || (newX == obstructionX && newY == obstructionY)) {
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
            }
            x = x + xDirection;
            y = y + yDirection;
        }

        return false;
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        char[][] input = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        int x;
        int y = 0;
        int xDirection = 0;
        int yDirection = 0;
        outer:
        for (x = 0; x < input.length; x++) {
            char[] row = input[x];
            for (y = 0; y < row.length; y++) {
                char position = row[y];
                switch (position) {
                    case '^':
                        xDirection = -1;
                        break outer;
                    case '>':
                        yDirection = 1;
                        break outer;
                    case 'v':
                        xDirection = 1;
                        break outer;
                    case '<':
                        yDirection = -1;
                        break outer;
                }
            }
        }

        Set<Map.Entry<Integer, Integer>> distinctPositions = new HashSet<>();
        while (x >= 0 && x < input.length && y >= 0 && y < input[x].length) {
            distinctPositions.add(new AbstractMap.SimpleImmutableEntry<>(x, y));
            int newX = x + xDirection;
            int newY = y + yDirection;
            if (newX < 0 || newX >= input.length || newY < 0 || newY >= input[newX].length) {
                break;
            }
            if (input[newX][newY] == '#') {
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
            }
            x = x + xDirection;
            y = y + yDirection;
        }

        return distinctPositions.size();
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        char[][] input = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        int x;
        int y = 0;
        int xDirection = 0;
        int yDirection = 0;
        outer:
        for (x = 0; x < input.length; x++) {
            char[] row = input[x];
            for (y = 0; y < row.length; y++) {
                char position = row[y];
                switch (position) {
                    case '^':
                        xDirection = -1;
                        break outer;
                    case '>':
                        yDirection = 1;
                        break outer;
                    case 'v':
                        xDirection = 1;
                        break outer;
                    case '<':
                        yDirection = -1;
                        break outer;
                }
            }
        }

        int finalXDirection = xDirection;
        int finalYDirection = yDirection;
        int finalY = y;
        int finalX = x;
        return IntStream.range(0, input.length).parallel()
                .mapToLong(obstructionX -> IntStream.range(0, input[obstructionX].length).parallel()
                        .filter(obstructionY -> isCreatingCycle(input, finalX, finalY, finalXDirection, finalYDirection, obstructionX, obstructionY))
                        .count()
                )
                .sum();
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
