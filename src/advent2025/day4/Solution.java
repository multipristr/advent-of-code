package src.advent2025.day4;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Integer, Long> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("..@@.@@@@.\n" +
                "@@@.@.@.@@\n" +
                "@@@@@.@.@@\n" +
                "@.@@@@..@.\n" +
                "@@.@@@@.@@\n" +
                ".@@@@@@@.@\n" +
                ".@.@.@.@@@\n" +
                "@.@@@.@@@@\n" +
                ".@@@@@@@@.\n" +
                "@.@.@@@.@.");
    }

    @Override
    public List<Integer> getExampleOutput1() {
        return List.of(13);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(43L);
    }

    @Override
    public Integer solvePartOne(Stream<String> lines) {
        var diagram = lines.map(String::toCharArray)
                .toArray(char[][]::new);
        return findAccessiblePaperRolls(diagram).size();
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        var diagram = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        long removablePaperRolls = 0;
        var accessiblePaperRolls = findAccessiblePaperRolls(diagram);
        while (!accessiblePaperRolls.isEmpty()) {
            removablePaperRolls += accessiblePaperRolls.size();
            for (var accessiblePaperRoll : accessiblePaperRolls) {
                diagram[accessiblePaperRoll.getKey()][accessiblePaperRoll.getValue()] = 'x';
            }
            accessiblePaperRolls = findAccessiblePaperRolls(diagram);
        }

        return removablePaperRolls;
    }

    private static List<Map.Entry<Integer, Integer>> findAccessiblePaperRolls(char[][] diagram) {
        List<Map.Entry<Integer, Integer>> accessiblePaperRolls = new ArrayList<>();

        byte[] directions = {-1, 0, 1};
        for (int row = 0; row < diagram.length; row++) {
            var diagramRow = diagram[row];
            for (int column = 0; column < diagramRow.length; column++) {
                if (diagramRow[column] == '@') {

                    byte adjacentPaperRolls = 0;

                    outerLoop:
                    for (var rowDirection : directions) {
                        var adjacentX = row + rowDirection;
                        if (adjacentX < 0 || adjacentX >= diagram.length) {
                            continue;
                        }
                        var adjacentRow = diagram[adjacentX];
                        for (var columnDirection : directions) {
                            if (rowDirection == 0 && columnDirection == 0) {
                                continue;
                            }
                            var adjacentY = column + columnDirection;
                            if (adjacentY < 0 || adjacentY >= adjacentRow.length) {
                                continue;
                            }
                            if (adjacentRow[adjacentY] == '@') {
                                ++adjacentPaperRolls;
                                if (adjacentPaperRolls >= 4) {
                                    break outerLoop;
                                }
                            }
                        }
                    }

                    if (adjacentPaperRolls < 4) {
                        accessiblePaperRolls.add(new AbstractMap.SimpleImmutableEntry<>(row, column));
                    }

                }
            }
        }

        return accessiblePaperRolls;
    }
}
