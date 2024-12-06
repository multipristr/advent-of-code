package src.advent2024.day6;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        while (x >= 0 && x < input.length && y >= 0 && y < input[y].length) {
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
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
