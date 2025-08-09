package src.advent2023.day10;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Integer, Integer> {

    public static void main(String[] args) {
        new Solution().run();
    }

    private static int traverse(char[][] sketch, int[][] distances, int previousRow, int previousColumn, int row, int column) {
        if (row < 0 || row >= sketch.length || column < 0 || column >= sketch[row].length) {
            return Integer.MIN_VALUE;
        }
        int distance = 1;
        char field = sketch[row][column];
        while (field != 'S' && field != '.') {
            if (distances[row][column] != -1 &&
                    distances[row][column] < distance) {
                return Integer.MIN_VALUE;
            } else if (distances[row][column] == distance) {
                return distance;
            }
            distances[row][column] = distance;
            int tmpRow = row;
            int tmpColumn = column;
            switch (field) {
                case '|':
                    row += row - previousRow;
                    break;
                case '-':
                    column += column - previousColumn;
                    break;
                case 'L':
                    if (row == previousRow) {
                        --row;
                    } else {
                        ++column;
                    }
                    break;
                case 'J':
                    if (row == previousRow) {
                        --row;
                    } else {
                        --column;
                    }
                    break;
                case '7':
                    if (row == previousRow) {
                        ++row;
                    } else {
                        --column;
                    }
                    break;
                case 'F':
                    if (row == previousRow) {
                        ++row;
                    } else {
                        ++column;
                    }
                    break;
            }
            previousRow = tmpRow;
            previousColumn = tmpColumn;
            ++distance;
            field = sketch[row][column];
        }
        return Integer.MIN_VALUE;
    }

    private static boolean isEnclosed(char[][] sketch, int[][] distances, int row, int column) {
        return isEnclosedHorizontally(sketch, distances, row, column, -1)
                &&
                isEnclosedHorizontally(sketch, distances, row, column, 1)
                &&
                isEnclosedVertically(sketch, distances, row, column, -1)
                &&
                isEnclosedVertically(sketch, distances, row, column, 1);
    }

    private static boolean isEnclosedHorizontally(char[][] sketch, int[][] distances, int row, int column, int direction) {
        if (row - 1 < 0 || row + 1 >= sketch.length) {
            return false;
        }
        int intersectionsUp = 0;
        int intersectionsDown = 0;
        for (column += direction; column < sketch[row].length && column >= 0; column += direction) {
            if (distances[row][column] >= 0) {
                char up = sketch[row - 1][column];
                char pipe = sketch[row][column];
                char down = sketch[row + 1][column];
                if (pipe == '|' || pipe == 'S') {
                    if (distances[row - 1][column] >= 0 && (up == 'F' || up == '7' || up == '|' || up == 'S')) {
                        ++intersectionsUp;
                    }
                    if (distances[row + 1][column] >= 0 && (down == 'J' || down == 'L' || down == '|' || down == 'S')) {
                        ++intersectionsDown;
                    }
                } else if (pipe == 'J' || pipe == 'L') {
                    if (distances[row - 1][column] >= 0 && (up == 'F' || up == '7' || up == '|' || up == 'S')) {
                        ++intersectionsUp;
                    }
                } else if (pipe == 'F' || pipe == '7') {
                    if (distances[row + 1][column] >= 0 && (down == 'J' || down == 'L' || down == '|' || down == 'S')) {
                        ++intersectionsDown;
                    }
                }
            }
        }
        return (intersectionsUp & 1) != 0 && (intersectionsDown & 1) != 0;
    }

    private static boolean isEnclosedVertically(char[][] sketch, int[][] distances, int row, int column, int direction) {
        if (column - 1 < 0 || column + 1 >= sketch[row].length) {
            return false;
        }
        int intersectionsLeft = 0;
        int intersectionsRight = 0;
        for (row += direction; row < sketch.length && row >= 0; row += direction) {
            if (distances[row][column] >= 0) {
                char left = sketch[row][column - 1];
                char pipe = sketch[row][column];
                char right = sketch[row][column + 1];
                if (pipe == '-' || pipe == 'S') {
                    if (distances[row][column - 1] >= 0 && (left == 'F' || left == 'L' || left == '-' || left == 'S')) {
                        ++intersectionsLeft;
                    }
                    if (distances[row][column + 1] >= 0 && (right == 'J' || right == '7' || right == '-' || right == 'S')) {
                        ++intersectionsRight;
                    }
                } else if (pipe == '7' || pipe == 'J') {
                    if (distances[row][column - 1] >= 0 && (left == 'L' || left == 'F' || left == '-' || left == 'S')) {
                        ++intersectionsLeft;
                    }
                } else if (pipe == 'F' || pipe == 'L') {
                    if (distances[row][column + 1] >= 0 && (right == '7' || right == 'J' || right == '-' || right == 'S')) {
                        ++intersectionsRight;
                    }
                }
            }
        }
        return (intersectionsLeft & 1) != 0 && (intersectionsRight & 1) != 0;
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of(".....\n" +
                        ".S-7.\n" +
                        ".|.|.\n" +
                        ".L-J.\n" +
                        ".....",
                "..F7.\n" +
                        ".FJ|.\n" +
                        "SJ.L7\n" +
                        "|F--J\n" +
                        "LJ...");
    }

    @Override
    public List<Integer> getExampleOutput1() {
        return List.of(4, 8);
    }

    @Override
    public List<Integer> getExampleOutput2() {
        return List.of(4, 8, 10);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of(
                "...........\n" +
                        ".S-------7.\n" +
                        ".|F-----7|.\n" +
                        ".||.....||.\n" +
                        ".||.....||.\n" +
                        ".|L-7.F-J|.\n" +
                        ".|..|.|..|.\n" +
                        ".L--J.L--J.\n" +
                        "...........",

                ".F----7F7F7F7F-7....\n" +
                        ".|F--7||||||||FJ....\n" +
                        ".||.FJ||||||||L7....\n" +
                        "FJL7L7LJLJ||LJ.L-7..\n" +
                        "L--J.L7...LJS7F-7L7.\n" +
                        "....F-J..F7FJ|L7L7L7\n" +
                        "....L7.F7||L7|.L7L7|\n" +
                        ".....|FJLJ|FJ|F7|.LJ\n" +
                        "....FJL-7.||.||||...\n" +
                        "....L---J.LJ.LJLJ...",

                "FF7FSF7F7F7F7F7F---7\n" +
                        "L|LJ||||||||||||F--J\n" +
                        "FL-7LJLJ||||||LJL-77\n" +
                        "F--JF--7||LJLJ7F7FJ-\n" +
                        "L---JF-JLJ.||-FJLJJ7\n" +
                        "|F|F-JF---7F7-L7L|7|\n" +
                        "|FFJF7L7F-JF7|JL---7\n" +
                        "7-L-JL7||F7|L7F-7F7|\n" +
                        "L.L7LFJ|||||FJL7||LJ\n" +
                        "L7JLJL-JLJLJL--JLJ.L");
    }

    @Override
    public Integer solvePartOne(Stream<String> lines) {
        char[][] sketch = lines
                .map(line -> line.toCharArray())
                .toArray(char[][]::new);

        int startRow = -1;
        int startColumn = -1;
        int[][] distances = new int[sketch.length][];
        for (int row = 0; row < sketch.length; ++row) {
            var sketchRow = sketch[row];
            distances[row] = new int[sketchRow.length];
            for (int column = 0; column < sketchRow.length; ++column) {
                if (sketchRow[column] == 'S') {
                    startRow = row;
                    startColumn = column;
                    distances[row][column] = 0;
                } else {
                    distances[row][column] = -1;
                }
            }
        }

        int distance = traverse(sketch, distances, startRow, startColumn, startRow, startColumn - 1);
        distance = Math.max(distance, traverse(sketch, distances, startRow, startColumn, startRow, startColumn + 1));
        distance = Math.max(distance, traverse(sketch, distances, startRow, startColumn, startRow - 1, startColumn));
        distance = Math.max(distance, traverse(sketch, distances, startRow, startColumn, startRow + 1, startColumn));

        return distance;
    }

    @Override
    public Integer solvePartTwo(Stream<String> lines) {
        char[][] sketch = lines
                .map(line -> line.toCharArray())
                .toArray(char[][]::new);

        int startRow = -1;
        int startColumn = -1;
        int[][] distances = new int[sketch.length][];
        for (int row = 0; row < sketch.length; ++row) {
            var sketchRow = sketch[row];
            distances[row] = new int[sketchRow.length];
            for (int column = 0; column < sketchRow.length; ++column) {
                if (sketchRow[column] == 'S') {
                    startRow = row;
                    startColumn = column;
                    distances[row][column] = 0;
                } else {
                    distances[row][column] = -1;
                }
            }
        }

        traverse(sketch, distances, startRow, startColumn, startRow, startColumn - 1);
        traverse(sketch, distances, startRow, startColumn, startRow, startColumn + 1);
        traverse(sketch, distances, startRow, startColumn, startRow - 1, startColumn);
        traverse(sketch, distances, startRow, startColumn, startRow + 1, startColumn);

        int tiles = 0;
        for (int row = 0; row < sketch.length; ++row) {
            var sketchRow = sketch[row];
            var distancesRow = distances[row];
            for (int column = 0; column < sketchRow.length; ++column) {
                if (distancesRow[column] < 0 && isEnclosed(sketch, distances, row, column)) {
                    ++tiles;
                }
            }
        }

        return tiles;
    }
}
