package src.advent2025.day9;

import src.PuzzleSolver;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("7,1\n" +
                "11,1\n" +
                "11,7\n" +
                "9,7\n" +
                "9,5\n" +
                "2,5\n" +
                "2,3\n" +
                "7,3");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(50L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(24L);
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        var grid = lines.map(line -> line.split(","))
                .map(coordinates -> Map.entry(Long.parseLong(coordinates[0]), Long.parseLong(coordinates[1])))
                .collect(Collectors.toList());

        return grid.stream()
                .flatMapToLong(redTile1 -> grid.stream()
                        .filter(redTile2 -> !redTile1.equals(redTile2))
                        .mapToLong(redTile2 -> calculateRectangleArea(redTile1, redTile2))
                )
                .max().orElseThrow();
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        var grid = lines.map(line -> line.split(","))
                .map(coordinates -> Map.entry(Long.parseLong(coordinates[0]), Long.parseLong(coordinates[1])))
                .collect(Collectors.toList());

        return grid.stream()
                .flatMapToLong(redTile1 -> grid.stream()
                        .filter(redTile2 -> !redTile1.equals(redTile2))
                        .filter(redTile2 -> isUsingOnlyRedGreenTiles(grid, redTile1, redTile2))
                        .mapToLong(redTile2 -> calculateRectangleArea(redTile1, redTile2))
                )
                .max().orElseThrow();
    }

    private boolean isUsingOnlyRedGreenTiles(Collection<Map.Entry<Long, Long>> grid, Map.Entry<Long, Long> corner1, Map.Entry<Long, Long> corner2) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private long calculateRectangleArea(Map.Entry<Long, Long> corner1, Map.Entry<Long, Long> corner2) {
        return (Math.abs(corner2.getKey() - corner1.getKey()) + 1) * (Math.abs(corner2.getValue() - corner1.getValue()) + 1);
    }
}
