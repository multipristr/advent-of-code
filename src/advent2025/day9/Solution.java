package src.advent2025.day9;

import src.PuzzleSolver;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
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

        long largestArea = Long.MIN_VALUE;
        for (int i = 0; i < grid.size(); i++) {
            var redTile1 = grid.get(i);
            for (int j = i + 1; j < grid.size(); j++) {
                largestArea = Math.max(largestArea, calculateRectangleArea(redTile1, grid.get(j)));
            }
        }
        return largestArea;
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        var redTiles = lines.map(line -> line.split(","))
                .map(coordinates -> Map.entry(Long.parseLong(coordinates[0]), Long.parseLong(coordinates[1])))
                .collect(Collectors.toList());

        long largestGreenRedTilesArea = Long.MIN_VALUE;
        for (int i = 0; i < redTiles.size(); i++) {
            var redTile1 = redTiles.get(i);
            for (int j = i + 1; j < redTiles.size(); j++) {
                var redTile2 = redTiles.get(j);
                var rectangleArea = calculateRectangleArea(redTile1, redTile2);
                if (rectangleArea > largestGreenRedTilesArea && isUsingOnlyRedGreenTiles(redTiles, redTile1, i, redTile2, j)) {
                    largestGreenRedTilesArea = rectangleArea;
                }
            }
        }
        return largestGreenRedTilesArea;
    }

    private boolean isUsingOnlyRedGreenTiles(List<Map.Entry<Long, Long>> redTiles, Map.Entry<Long, Long> corner1, int corner1Index, Map.Entry<Long, Long> corner2, int corner2Index) {
        Predicate<Map.Entry<Long, Long>> corner1Check = redTile -> redTile.getValue() <= Math.max(corner1.getValue(), corner2.getValue()) && redTile.getValue() > Math.min(corner1.getValue(), corner2.getValue());
        Predicate<Map.Entry<Long, Long>> corner2Check = redTile -> redTile.getValue() <= Math.max(corner1.getValue(), corner2.getValue()) && redTile.getValue() > Math.min(corner1.getValue(), corner2.getValue());
        if (corner1.getValue() <= corner2.getValue()) {
            if (corner1.getKey() >= corner2.getKey()) {
                corner1Check = corner1Check.and(redTile -> redTile.getKey() < corner1.getKey());
                corner2Check = corner2Check.and(redTile -> redTile.getKey() > corner2.getKey());
            } else {
                corner1Check = corner1Check.and(redTile -> redTile.getKey() < corner2.getKey());
                corner2Check = corner2Check.and(redTile -> redTile.getKey() > corner1.getKey());
            }
        } else {
            if (corner1.getKey() >= corner2.getKey()) {
                corner1Check = corner1Check.and(redTile -> redTile.getKey() > corner2.getKey());
                corner2Check = corner2Check.and(redTile -> redTile.getKey() < corner1.getKey());
            } else {
                corner1Check = corner1Check.and(redTile -> redTile.getKey() > corner1.getKey());
                corner2Check = corner2Check.and(redTile -> redTile.getKey() < corner2.getKey());
            }
        }

        for (int i = corner1Index + 1; i < corner2Index; i++) {
            var redTile = redTiles.get(i);
            if (corner1Check.test(redTile)) {
                return false;
            }
        }
        for (int i = corner2Index + 1 >= redTiles.size() ? 0 : corner2Index + 1; i > corner2Index || i < corner1Index; i = (i + 1) % redTiles.size()) {
            var redTile = redTiles.get(i);
            if (corner2Check.test(redTile)) {
                return false;
            }
        }
        return true;
    }

    private long calculateRectangleArea(Map.Entry<Long, Long> corner1, Map.Entry<Long, Long> corner2) {
        return (Math.abs(corner2.getKey() - corner1.getKey()) + 1) * (Math.abs(corner2.getValue() - corner1.getValue()) + 1);
    }
}
