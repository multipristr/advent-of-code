package src.advent2025.day7;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of(".......S.......\n" +
                "...............\n" +
                ".......^.......\n" +
                "...............\n" +
                "......^.^......\n" +
                "...............\n" +
                ".....^.^.^.....\n" +
                "...............\n" +
                "....^.^...^....\n" +
                "...............\n" +
                "...^.^...^.^...\n" +
                "...............\n" +
                "..^...^.....^..\n" +
                "...............\n" +
                ".^.^.^.^.^...^.\n" +
                "...............");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(21L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(40L);
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        var diagram = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        Set<Integer> beamColumns = new HashSet<>();
        beamColumns.add(Arrays.binarySearch(diagram[0], 'S'));
        long beamSplits = 0;
        for (int row = 1; row < diagram.length; row++) {
            var diagramRow = diagram[row];

            var nextBeamColumns = new HashSet<>(beamColumns);
            for (var column : beamColumns) {
                if (diagramRow[column] == '^') {
                    ++beamSplits;
                    nextBeamColumns.remove(column);
                    nextBeamColumns.addAll(List.of(column - 1, column + 1));
                }
            }
            beamColumns = nextBeamColumns;
        }
        return beamSplits;
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        var diagram = lines.map(String::toCharArray)
                .toArray(char[][]::new);
        var memory = new long[diagram.length][diagram[0].length];
        var particleColumn = Arrays.binarySearch(diagram[0], 'S');
        return countParticleTimelines(diagram, memory, 1, particleColumn);
    }

    private long countParticleTimelines(char[][] diagram, long[][] memory, int row, int column) {
        if (column < 0 || column >= diagram[row].length) {
            return 1;
        }
        for (var r = row; r < diagram.length; r++) {
            if (diagram[r][column] == '^') {
                var memorisedParticleTimelines = memory[r][column];
                if (memorisedParticleTimelines != 0) {
                    return memorisedParticleTimelines;
                }
                var particleTimelines = countParticleTimelines(diagram, memory, r, column - 1) + countParticleTimelines(diagram, memory, r, column + 1);
                memory[r][column] = particleTimelines;
                return particleTimelines;
            }
        }
        return 1;
    }
}
