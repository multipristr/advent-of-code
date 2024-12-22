package src.advent2024.day4;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    private static boolean containsMasInDirection(char[][] wordSearch, int rowIndex, int columnIndex, int rowDirection, int columnDirection) {
        char[] letters = {'M', 'A', 'S'};
        for (char letter : letters) {
            rowIndex += rowDirection;
            columnIndex += columnDirection;
            if (!isLetterAtField(letter, wordSearch, rowIndex, columnIndex)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isLetterAtField(char letter, char[][] wordSearch, int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= wordSearch.length || columnIndex < 0) {
            return false;
        }
        char[] row = wordSearch[rowIndex];
        if (columnIndex >= row.length) {
            return false;
        }
        return row[columnIndex] == letter;
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of(
                "MMMSXXMASM\n" +
                        "MSAMXMSMSA\n" +
                        "AMXSXMAAMM\n" +
                        "MSAMASMSMX\n" +
                        "XMASAMXAMM\n" +
                        "XXAMMXXAMA\n" +
                        "SMSMSASXSS\n" +
                        "SAXAMASAAA\n" +
                        "MAMMMXMMMM\n" +
                        "MXMXAXMASX",
                "XMASXMAS\n" +
                        "XMASXMAS\n" +
                        "XMASXMAS\n" +
                        "XMASXMAS\n" +
                        "XMASXMAS\n" +
                        "XMASXMAS\n" +
                        "XMASXMAS\n" +
                        "XMASXMAS",
                "XXXX\n" +
                        "XXXM\n" +
                        "XXXX\n" +
                        "AXXX\n" +
                        "XSXX",
                ".....\n" +
                        "X.SAM\n" +
                        ".....",
                ".X.\n" +
                        "...\n" +
                        ".S.\n" +
                        ".A.\n" +
                        ".M."
        );
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(18L, 36, 0, 0, 0);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of("MMMSXXMASM\n" +
                "MSAMXMSMSA\n" +
                "AMXSXMAAMM\n" +
                "MSAMASMSMX\n" +
                "XMASAMXAMM\n" +
                "XXAMMXXAMA\n" +
                "SMSMSASXSS\n" +
                "SAXAMASAAA\n" +
                "MAMMMXMMMM\n" +
                "MXMXAXMASX");
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(9L);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        char[][] wordSearch = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        long occurrences = 0;
        int[] directions = {-1, 0, 1};
        for (int rowIndex = 0; rowIndex < wordSearch.length; rowIndex++) {
            char[] row = wordSearch[rowIndex];
            for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                if (row[columnIndex] == 'X') {
                    for (var rowDirection : directions) {
                        for (var columnDirection : directions) {
                            if (rowDirection == 0 && columnDirection == 0) {
                                continue;
                            }
                            if (containsMasInDirection(wordSearch, rowIndex, columnIndex, rowDirection, columnDirection)) {
                                ++occurrences;
                            }
                        }
                    }
                }
            }
        }

        return occurrences;
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        char[][] wordSearch = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        long occurrences = 0;
        int[] directions = {-1, 1};
        for (int rowIndex = 0; rowIndex < wordSearch.length; rowIndex++) {
            char[] row = wordSearch[rowIndex];
            for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                if (row[columnIndex] == 'A') {
                    int masCount = 0;
                    for (var rowDirection : directions) {
                        for (var columnDirection : directions) {
                            if (isLetterAtField('M', wordSearch, rowIndex + rowDirection, columnIndex + columnDirection)
                                    && isLetterAtField('S', wordSearch, rowIndex - rowDirection, columnIndex - columnDirection)) {
                                ++masCount;
                            }
                        }
                    }
                    occurrences += masCount / 2;
                }
            }
        }

        return occurrences;
    }

}
