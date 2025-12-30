package src.advent2025.day12;

import src.PuzzleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {
    private static final Pattern REGION_PATTERN = Pattern.compile("(?<width>\\d+)x(?<length>\\d+): (?<shapeQuantities>[\\d\\s]+)");

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

        var presentVolumes = new long[inputParts.length - 1];
        var presentShapes = new boolean[inputParts.length - 1][][];
        for (int shapeIndex = 0; shapeIndex < inputParts.length - 1; shapeIndex++) {
            var shapeLines = inputParts[shapeIndex].split("\\R");
            var presentShape = new boolean[shapeLines.length - 1][];
            for (int shapeLine = 1; shapeLine < shapeLines.length; shapeLine++) {
                var line = shapeLines[shapeLine];
                var presentShapeLine = new boolean[line.length()];
                for (int column = 0; column < line.length(); column++) {
                    if (line.charAt(column) == '#') {
                        ++presentVolumes[shapeIndex];
                        presentShapeLine[column] = true;
                    }
                }
                presentShape[shapeLine - 1] = presentShapeLine;
            }
            presentShapes[shapeIndex] = presentShape;
        }

        var shapeVariants = generateShapeVariants(presentShapes);

        return inputParts[inputParts.length - 1].lines().parallel()
                .filter(line -> {
                    var regionMatcher = REGION_PATTERN.matcher(line);
                    regionMatcher.find();

                    var width = Integer.parseInt(regionMatcher.group("width"));
                    var length = Integer.parseInt(regionMatcher.group("length"));

                    var shapeQuantitiesInput = regionMatcher.group("shapeQuantities").split("\\s");
                    var shapeQuantities = Arrays.stream(shapeQuantitiesInput)
                            .mapToLong(Long::parseLong)
                            .toArray();

                    var regionVolume = width * length;
                    var requiredVolume = IntStream.range(0, shapeQuantities.length)
                            .mapToLong(i -> shapeQuantities[i] * presentVolumes[i])
                            .sum();
                    if (regionVolume < requiredVolume) {
                        return false;
                    }
                    return canAllPresentsFitInRegion(shapeVariants, new boolean[length][width], shapeQuantities);
                })
                .count();
    }

    private boolean canAllPresentsFitInRegion(List<boolean[][]>[] shapeVariants, boolean[][] region, long[] presentQuantities) {
        for (int present = 0; present < presentQuantities.length; present++) {
            if (presentQuantities[present] > 0) {
                for (var shape : shapeVariants[present]) {
                    if (canPresentFitInRegion(shapeVariants, region, presentQuantities, shape, present)) {
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }

    private boolean canPresentFitInRegion(List<boolean[][]>[] shapeVariants, boolean[][] region, long[] presentQuantities, boolean[][] shape, int present) {
        var columns = region.length - shape.length;
        var rows = region[0].length - shape[0].length;

        for (int fromColumn = 0; fromColumn <= columns; fromColumn++) {
            for (int fromRow = 0; fromRow <= rows; fromRow++) {
                if (fitInRegion(region, shape, fromColumn, fromRow)) {
                    --presentQuantities[present];
                    if (canAllPresentsFitInRegion(shapeVariants, region, presentQuantities)) {
                        return true;
                    }
                    ++presentQuantities[present];
                    removeFromRegion(region, shape, fromColumn, fromRow);
                }
            }
        }

        return false;
    }

    private boolean fitInRegion(boolean[][] region, boolean[][] shape, int fromColumn, int fromRow) {
        for (int c = 0; c < shape.length; c++) {
            var shapeColumn = shape[c];
            var regionColumn = region[c + fromColumn];
            for (int r = 0; r < shapeColumn.length; r++) {
                if (shapeColumn[r] && regionColumn[r + fromRow]) {
                    return false;
                }
            }
        }
        for (int c = 0; c < shape.length; c++) {
            var shapeColumn = shape[c];
            var regionColumn = region[c + fromColumn];
            for (int r = 0; r < shapeColumn.length; r++) {
                regionColumn[r + fromRow] |= shapeColumn[r];
            }
        }
        return true;
    }

    private void removeFromRegion(boolean[][] region, boolean[][] shape, int fromColumn, int fromRow) {
        for (int c = 0; c < shape.length; c++) {
            var shapeColumn = shape[c];
            var regionColumn = region[c + fromColumn];
            for (int r = 0; r < shapeColumn.length; r++) {
                regionColumn[r + fromRow] ^= shapeColumn[r];
            }
        }
    }

    private List<boolean[][]>[] generateShapeVariants(boolean[][][] presents) {
        List<boolean[][]>[] shapeVariants = new List[presents.length];
        for (int present = 0; present < presents.length; present++) {
            var shape = presents[present];
            List<boolean[][]> variants = new ArrayList<>(12);
            addAllRotated(variants, shape);
            addAllRotated(variants, flipHorizontally(shape));
            addAllRotated(variants, flipVertically(shape));
            shapeVariants[present] = variants;
        }
        return shapeVariants;
    }

    private void addAllRotated(List<boolean[][]> variants, boolean[][] shape) {
        var rotated = shape;
        var finalRotated = rotated;
        if (variants.stream().noneMatch(variant -> Arrays.deepEquals(finalRotated, variant))) {
            variants.add(finalRotated);
        }
        for (int rotation = 0; rotation < 3; rotation++) {
            rotated = rotate(rotated);
            var finalRotated2 = rotated;
            if (variants.stream().noneMatch(variant -> Arrays.deepEquals(finalRotated2, variant))) {
                variants.add(finalRotated2);
            }
        }
    }

    private boolean[][] flipHorizontally(boolean[][] shape) {
        var rows = shape.length;
        var flipped = new boolean[rows][];
        for (int r = 0; r < rows; r++) {
            var columns = shape[r].length;
            var flipRow = new boolean[columns];
            var shapeRow = shape[r];
            for (int c = 0; c < columns; c++) {
                flipRow[c] = shapeRow[columns - 1 - c];
            }
            flipped[r] = flipRow;
        }
        return flipped;
    }

    private boolean[][] flipVertically(boolean[][] shape) {
        var rows = shape.length;
        var flipped = new boolean[rows][];
        for (int r = 0; r < rows; r++) {
            var columns = shape[r].length;
            flipped[r] = new boolean[columns];
            System.arraycopy(shape[rows - 1 - r], 0, flipped[r], 0, columns);
        }
        return flipped;
    }

    private boolean[][] rotate(boolean[][] shape) {
        var rows = shape.length;
        var columns = shape[0].length;
        var rotated = new boolean[columns][rows];
        for (int r = 0; r < rows; r++) {
            var shapeRow = shape[r];
            for (int c = 0; c < columns; c++) {
                rotated[c][rows - 1 - r] = shapeRow[c];
            }
        }
        return rotated;
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
