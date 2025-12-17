package src.advent2025.day12;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {
    private static final Pattern REGION_PATTERN = Pattern.compile("(?<width>\\d+)x(?<length>\\d+): (?<shapeQuantities>[\\d,\\s]+)");

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("0:\n" +
                "###\n" +
                "##.\n" +
                "##.\n" +
                "\n" +
                "1:\n" +
                "###\n" +
                "##.\n" +
                ".##\n" +
                "\n" +
                "2:\n" +
                ".##\n" +
                "###\n" +
                "##.\n" +
                "\n" +
                "3:\n" +
                "##.\n" +
                "###\n" +
                "##.\n" +
                "\n" +
                "4:\n" +
                "###\n" +
                "#..\n" +
                "###\n" +
                "\n" +
                "5:\n" +
                "###\n" +
                ".#.\n" +
                "###\n" +
                "\n" +
                "4x4: 0 0 0 0 2 0\n" +
                "12x5: 1 0 1 0 2 2\n" +
                "12x5: 1 0 1 0 3 2");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(2L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of();
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        var inputParts = lines.collect(Collectors.joining("\n")).split("\\R{2,}");

        var presentShapes = new boolean[inputParts.length - 1][][];
        for (int shapeIndex = 0; shapeIndex < inputParts.length - 1; shapeIndex++) {
            var shapeLines = inputParts[shapeIndex].split("\\R");
            var presentShape = new boolean[shapeLines.length - 1][];
            for (int shapeLine = 1; shapeLine < shapeLines.length; shapeLine++) {
                var line = shapeLines[shapeLine];
                var presentShapeLine = new boolean[line.length()];
                for (int column = 0; column < line.length(); column++) {
                    if (line.charAt(column) == '#') {
                        presentShapeLine[column] = true;
                    }
                }
                presentShape[shapeLine - 1] = presentShapeLine;
            }
            presentShapes[shapeIndex] = presentShape;
        }

        return inputParts[inputParts.length - 1].lines()
                .map(REGION_PATTERN::matcher)
                .filter(regionMatcher -> {
                    regionMatcher.find();
                    var width = Integer.parseInt(regionMatcher.group("width"));
                    var length = Integer.parseInt(regionMatcher.group("length"));
                    var region = new boolean[length][width];

                    var shapeQuantitiesInput = regionMatcher.group("shapeQuantities").split("\\s");
                    var shapeQuantities = Arrays.stream(shapeQuantitiesInput)
                            .mapToLong(Long::parseLong)
                            .toArray();

                    return canAllPresentsFitInRegion(presentShapes, region, shapeQuantities);
                })
                .count();
    }

    private boolean canAllPresentsFitInRegion(boolean[][][] presents, boolean[][] region, long[] presentQuantities) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
