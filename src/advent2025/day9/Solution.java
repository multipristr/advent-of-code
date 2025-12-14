package src.advent2025.day9;

import src.PuzzleSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        List<Map.Entry<Long, Long>> redTiles = new ArrayList<>();
        Map<Long, Map.Entry<Long, Long>> columnRanges = new HashMap<>();
        Map<Long, Map.Entry<Long, Long>> rowRanges = new HashMap<>();
        Map<Long, Set<Long>> connections = new HashMap<>();
        Long lastRow = null;
        Long lastColumn = null;

        var linesIterator = lines.map(line -> line.split(",")).iterator();
        while (linesIterator.hasNext()) {
            var coordinates = linesIterator.next();
            var column = Long.parseLong(coordinates[0]);
            var row = Long.parseLong(coordinates[1]);
            redTiles.add(Map.entry(column, row));

            if (lastColumn != null) {
                for (long c = Math.min(lastColumn, column); c <= Math.max(lastColumn, column); c++) {
                    for (long r = Math.min(lastRow, row); r <= Math.max(lastRow, row); r++) {
                        connections.computeIfAbsent(c, k -> new HashSet<>()).add(r);
                        columnRanges.merge(r, Map.entry(c, c), (a, b) -> Map.entry(Math.min(a.getKey(), b.getKey()), Math.max(a.getValue(), b.getValue())));
                        rowRanges.merge(c, Map.entry(r, r), (a, b) -> Map.entry(Math.min(a.getKey(), b.getKey()), Math.max(a.getValue(), b.getValue())));
                    }
                }
            }
            lastColumn = column;
            lastRow = row;
        }
        var firstRedCorner = redTiles.get(0);
        for (long c = Math.min(lastColumn, firstRedCorner.getKey()); c <= Math.max(lastColumn, firstRedCorner.getKey()); c++) {
            for (long r = Math.min(lastRow, firstRedCorner.getValue()); r <= Math.max(lastRow, firstRedCorner.getValue()); r++) {
                connections.computeIfAbsent(c, k -> new HashSet<>()).add(r);
                columnRanges.merge(r, Map.entry(c, c), (a, b) -> Map.entry(Math.min(a.getKey(), b.getKey()), Math.max(a.getValue(), b.getValue())));
                rowRanges.merge(c, Map.entry(r, r), (a, b) -> Map.entry(Math.min(a.getKey(), b.getKey()), Math.max(a.getValue(), b.getValue())));
            }
        }

        long largestArea = Long.MIN_VALUE;
        for (int i = 0; i < redTiles.size(); i++) {
            var redTile1 = redTiles.get(i);
            for (int j = i + 1; j < redTiles.size(); j++) {
                var redTile2 = redTiles.get(j);
                var rectangleArea = calculateRectangleArea(redTile1, redTile2);
                if (rectangleArea > largestArea && isUsingOnlyRedGreenTiles(connections, rowRanges, columnRanges, redTile1, redTile2)) {
                    largestArea = rectangleArea;
                }
            }
        }
        return largestArea; // should be less than 3358922490
    }

    private boolean isUsingOnlyRedGreenTiles(Map<Long, Set<Long>> connections,
                                             Map<Long, Map.Entry<Long, Long>> rowRanges, Map<Long, Map.Entry<Long, Long>> columnRanges,
                                             Map.Entry<Long, Long> corner1, Map.Entry<Long, Long> corner2) {
        var topLeftCorner = Map.entry(Math.min(corner1.getKey(), corner2.getKey()), Math.min(corner1.getValue(), corner2.getValue()));
        var topRightCorner = Map.entry(Math.min(corner1.getKey(), corner2.getKey()), Math.max(corner1.getValue(), corner2.getValue()));
        var bottomLeftCorner = Map.entry(Math.max(corner1.getKey(), corner2.getKey()), Math.min(corner1.getValue(), corner2.getValue()));
        var bottomRightCorner = Map.entry(Math.max(corner1.getKey(), corner2.getKey()), Math.max(corner1.getValue(), corner2.getValue()));

        return isCornerInside(connections, rowRanges, columnRanges, topLeftCorner)
                && isCornerInside(connections, rowRanges, columnRanges, topRightCorner)
                && isCornerInside(connections, rowRanges, columnRanges, bottomLeftCorner)
                && isCornerInside(connections, rowRanges, columnRanges, bottomRightCorner);
    }

    private boolean isCornerInside(Map<Long, Set<Long>> connections,
                                   Map<Long, Map.Entry<Long, Long>> rowRanges, Map<Long, Map.Entry<Long, Long>> columnRanges,
                                   Map.Entry<Long, Long> corner) {
        if (connections.getOrDefault(corner.getKey(), Set.of()).contains(corner.getValue())) {
            return true;
        }
        return isCornerInsideColumn(connections, columnRanges, corner, -1)
                && isCornerInsideColumn(connections, columnRanges, corner, 1)
                && isCornerInsideRow(connections, rowRanges, corner, -1)
                && isCornerInsideRow(connections, rowRanges, corner, 1);
    }

    private boolean isCornerInsideColumn(Map<Long, Set<Long>> connections, Map<Long, Map.Entry<Long, Long>> columnRanges,
                                         Map.Entry<Long, Long> corner, int columnDirection) {
        long intersections = 0;
        var intersectionStart = false;
        var intersectionEnd = false;
        var row = corner.getValue();
        var columnRange = columnRanges.get(row);
        for (var column = corner.getKey(); columnRange.getKey() <= column && column <= columnRange.getValue(); column += columnDirection) {
            if (connections.getOrDefault(column, Set.of()).contains(row)) {
                intersectionEnd = true;
            } else {
                if (intersectionStart && intersectionEnd) {
                    intersections++;
                }
                intersectionStart = true;
                intersectionEnd = false;
            }
        }
        if (intersectionStart && intersectionEnd) {
            intersections++;
        }
        return (intersections & 1L) != 0L;
    }

    private boolean isCornerInsideRow(Map<Long, Set<Long>> connections, Map<Long, Map.Entry<Long, Long>> rowRanges,
                                      Map.Entry<Long, Long> corner, int rowDirection) {
        long intersections = 0;
        var intersectionStart = false;
        var intersectionEnd = false;
        var column = corner.getKey();
        var rowRange = rowRanges.get(column);
        var columnConnections = connections.getOrDefault(column, Set.of());
        for (var row = corner.getValue(); rowRange.getKey() <= row && row <= rowRange.getValue(); row += rowDirection) {
            if (columnConnections.contains(row)) {
                intersectionEnd = true;
            } else {
                if (intersectionStart && intersectionEnd) {
                    intersections++;
                }
                intersectionStart = true;
                intersectionEnd = false;
            }
        }
        if (intersectionStart && intersectionEnd) {
            intersections++;
        }
        return (intersections & 1L) != 0L;
    }

    private long calculateRectangleArea(Map.Entry<Long, Long> corner1, Map.Entry<Long, Long> corner2) {
        return (Math.abs(corner2.getKey() - corner1.getKey()) + 1) * (Math.abs(corner2.getValue() - corner1.getValue()) + 1);
    }
}
