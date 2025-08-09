package src.advent2023.day18;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {

    public static void main(String[] args) {
        new Solution().run();
    }

    private static long parseSteps(String color) {
        return Long.parseLong(color.substring(2, color.length() - 2), 16);
    }

    // TODO Figure out why the area calculated from the shoelace formula is off by 2
    private static long shoelace_formula(List<Map.Entry<Long, Long>> coordinates) {
        long shoelaces = 0;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            var coordinate = coordinates.get(i);
            var nextCoordinate = coordinates.get(i + 1);
            shoelaces += (coordinate.getKey() * nextCoordinate.getValue() - nextCoordinate.getKey() * coordinate.getValue());
        }
        return Math.abs(shoelaces >> 1) + 2;
    }

    private static long picks_theorem_formula(long borderLength, long insideArea) {
        return insideArea + (borderLength >> 1) - 1;
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("R 6 (#70c710)\n" +
                "D 5 (#0dc571)\n" +
                "L 2 (#5713f0)\n" +
                "D 2 (#d2c081)\n" +
                "R 2 (#59c680)\n" +
                "D 2 (#411b91)\n" +
                "L 5 (#8ceee2)\n" +
                "U 2 (#caa173)\n" +
                "L 1 (#1b58a2)\n" +
                "U 2 (#caa171)\n" +
                "R 2 (#7807d2)\n" +
                "U 3 (#a77fa3)\n" +
                "L 2 (#015232)\n" +
                "U 2 (#7a21e3)");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(62L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(952408144115L);
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        long row = 0;
        long column = 0;
        long steps = 0;
        List<String[]> parsedLines = lines.map(line -> line.split(" ")).collect(Collectors.toList());
        List<Map.Entry<Long, Long>> coordinates = new ArrayList<>(parsedLines.size());
        for (var parts : parsedLines) {
            long amount = Long.parseLong(parts[1]);
            if ("R".equals(parts[0])) {
                column += amount;
            } else if ("L".equals(parts[0])) {
                column -= amount;
            } else if ("D".equals(parts[0])) {
                row += amount;
            } else if ("U".equals(parts[0])) {
                row -= amount;
            }
            steps += amount;
            coordinates.add(new AbstractMap.SimpleImmutableEntry<>(row, column));
        }

        return picks_theorem_formula(steps, shoelace_formula(coordinates));
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        long row = 0;
        long column = 0;
        long steps = 0;
        List<String[]> parsedLines = lines.map(line -> line.split(" ")).collect(Collectors.toList());
        List<Map.Entry<Long, Long>> coordinates = new ArrayList<>(parsedLines.size());
        for (var parts : parsedLines) {
            char direction = parts[2].charAt(parts[2].length() - 2);
            long amount = parseSteps(parts[2]);
            if (direction == '0') {
                column += amount;
            } else if (direction == '2') {
                column -= amount;
            } else if (direction == '1') {
                row += amount;
            } else if (direction == '3') {
                row -= amount;
            }
            steps += amount;
            coordinates.add(new AbstractMap.SimpleImmutableEntry<>(row, column));
        }

        return picks_theorem_formula(steps, shoelace_formula(coordinates));
    }
}
