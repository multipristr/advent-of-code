package src.advent2023.day14;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Integer, Integer> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("O....#....\n" +
                "O.OO#....#\n" +
                ".....##...\n" +
                "OO.#O....O\n" +
                ".O.....O#.\n" +
                "O.#..O.#.#\n" +
                "..O..#O..O\n" +
                ".......O..\n" +
                "#....###..\n" +
                "#OO..#....");
    }

    @Override
    public List<Integer> getExampleOutput1() {
        return List.of(136);
    }

    @Override
    public List<Integer> getExampleOutput2() {
        return List.of(64);
    }

    @Override
    public Integer solvePartOne(Stream<String> lines) {
        char[][] platform = lines.map(line -> line.toCharArray())
                .toArray(char[][]::new);
        int totalLoad = 0;
        char[][] slidRocks = new char[platform.length][];

        for (int row = 0; row < platform.length; ++row) {
            var platformRow = platform[row];
            slidRocks[row] = Arrays.copyOf(platformRow, platformRow.length);
            for (int column = 0; column < platformRow.length; ++column) {
                char space = slidRocks[row][column];
                if (space == 'O') {
                    int newRow = row;
                    while (newRow > 0 && slidRocks[newRow - 1][column] == '.') {
                        --newRow;
                    }
                    slidRocks[row][column] = '.';
                    slidRocks[newRow][column] = 'O';
                    totalLoad += platform.length - newRow;
                }
            }
        }

        return totalLoad;
    }

    @Override
    public Integer solvePartTwo(Stream<String> lines) {
        char[][] platform = lines.map(line -> line.toCharArray())
                .toArray(char[][]::new);

        int totalLoad = 0;
        Map<Integer, Map.Entry<Long, Integer>> cycles = new HashMap<>();
        for (long cycle = 0; cycle < 1_000L; ++cycle) {
            // north
            for (int row = 0; row < platform.length; ++row) {
                var platformRow = platform[row];
                for (int column = 0; column < platformRow.length; ++column) {
                    char space = platformRow[column];
                    if (space == 'O') {
                        int newRow = row;
                        while (newRow > 0 && platform[newRow - 1][column] == '.') {
                            --newRow;
                        }
                        platformRow[column] = '.';
                        platform[newRow][column] = 'O';
                    }
                }
            }

            // west
            for (int row = 0; row < platform.length; ++row) {
                var platformRow = platform[row];
                for (int column = 0; column < platformRow.length; ++column) {
                    char space = platformRow[column];
                    if (space == 'O') {
                        int newColumn = column;
                        while (newColumn > 0 && platformRow[newColumn - 1] == '.') {
                            --newColumn;
                        }
                        platformRow[column] = '.';
                        platformRow[newColumn] = 'O';
                    }
                }
            }

            // south
            for (int row = platform.length - 1; row >= 0; --row) {
                var platformRow = platform[row];
                for (int column = 0; column < platformRow.length; ++column) {
                    char space = platformRow[column];
                    if (space == 'O') {
                        int newRow = row;
                        while (newRow < platform.length - 1 && platform[newRow + 1][column] == '.') {
                            ++newRow;
                        }
                        platformRow[column] = '.';
                        platform[newRow][column] = 'O';
                    }
                }
            }

            totalLoad = 0;
            // east
            for (int row = 0; row < platform.length; ++row) {
                var platformRow = platform[row];
                for (int column = platformRow.length - 1; column >= 0; --column) {
                    char space = platformRow[column];
                    if (space == 'O') {
                        int newColumn = column;
                        while (newColumn < platformRow.length - 1 && platformRow[newColumn + 1] == '.') {
                            ++newColumn;
                        }
                        platformRow[column] = '.';
                        platformRow[newColumn] = 'O';
                        totalLoad += platform.length - row;
                    }
                }
            }

            var hash = Arrays.deepHashCode(platform);
            var found = cycles.get(hash);
            if (found == null) {
                cycles.put(hash, new AbstractMap.SimpleImmutableEntry<>(cycle, totalLoad));
            }
        }

        return totalLoad;
    }
}
