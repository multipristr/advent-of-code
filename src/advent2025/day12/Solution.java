package src.advent2025.day12;

import src.PuzzleSolver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.LongFunction;
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

        var presentShapes = new Region[inputParts.length - 1];
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
            presentShapes[shapeIndex] = new Region(presentShape);
        }

        var shapeVariants = generateShapeVariants(presentShapes);
        Map<SearchState, Boolean> memory = new ConcurrentHashMap<>();

        return inputParts[inputParts.length - 1].lines().parallel()
                .map(REGION_PATTERN::matcher)
                .filter(regionMatcher -> {
                    regionMatcher.find();
                    var width = Integer.parseInt(regionMatcher.group("width"));
                    var length = Integer.parseInt(regionMatcher.group("length"));
                    var region = new Region(new boolean[length][width]);

                    var shapeQuantitiesInput = regionMatcher.group("shapeQuantities").split("\\s");
                    var shapeQuantities = Arrays.stream(shapeQuantitiesInput)
                            .mapToLong(Long::parseLong)
                            .toArray();

                    boolean b = canAllPresentsFitInRegion(shapeVariants, region, shapeQuantities, memory);
                    if (b) {
                        System.out.println("✅ " + width + "x" + length + ": " + String.join(" ", shapeQuantitiesInput));
                    } else {
                        System.out.println("❌ " + width + "x" + length + ": " + String.join(" ", shapeQuantitiesInput));
                    }
                    return b;
                })
                .count();
    }

    private boolean canAllPresentsFitInRegion(Collection<Region>[] shapeVariants, Region region, long[] presentQuantities, Map<SearchState, Boolean> memory) {
        for (int present = 0; present < presentQuantities.length; present++) {
            if (presentQuantities[present] > 0) {
                var finalPresent = present;
                return shapeVariants[present].parallelStream()
                        .anyMatch(shape -> {
                            var columns = region.shape.length - shape.shape.length;
                            var rows = region.shape[0].length - shape.shape[0].length;
                            for (int fromColumn = 0; fromColumn <= columns; fromColumn++) {
                                for (int fromRow = 0; fromRow <= rows; fromRow++) {
                                    if (region.canFit(shape, fromColumn, fromRow)) {
                                        var nextRegion = region.fit(shape, fromColumn, fromRow);
                                        var nextPresentQuantities = Arrays.copyOf(presentQuantities, presentQuantities.length);
                                        --nextPresentQuantities[finalPresent];

                                        var memoryKey = new SearchState(nextPresentQuantities, nextRegion);
                                        var saved = memory.get(memoryKey);
                                        if (saved == null) {
                                            saved = canAllPresentsFitInRegion(shapeVariants, nextRegion, nextPresentQuantities, memory);
                                            memory.put(memoryKey, saved);
                                        }
                                        if (saved) {
                                            return true;
                                        }
                                    }
                                }
                            }
                            return false;
                        });
            }
        }
        return true;
    }

    private Collection<Region>[] generateShapeVariants(Region[] presents) {
        Collection<Region>[] shapeVariants = new Set[presents.length];
        for (int present = 0; present < presents.length; present++) {
            var shape = presents[present];
            Collection<Region> variants = new HashSet<>(12);
            addAllRotated(variants, shape);
            addAllRotated(variants, shape.flipHorizontally());
            addAllRotated(variants, shape.flipVertically());
            shapeVariants[present] = variants;
        }
        return shapeVariants;
    }

    private void addAllRotated(Collection<Region> variants, Region shape) {
        var rotated = shape;
        variants.add(rotated);
        for (int rotation = 0; rotation < 3; rotation++) {
            rotated = rotated.rotate();
            variants.add(rotated);
        }
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static final class Region {
        private final boolean[][] shape;

        Region(boolean[][] shape) {
            this.shape = shape;
        }

        private boolean canFit(Region that, int fromColumn, int fromRow) {
            for (int c = 0; c < that.shape.length; c++) {
                var shapeColumn = that.shape[c];
                var regionColumn = shape[c + fromColumn];
                for (int r = 0; r < shapeColumn.length; r++) {
                    if (shapeColumn[r] && regionColumn[r + fromRow]) {
                        return false;
                    }
                }
            }
            return true;
        }

        private Region fit(Region that, int fromColumn, int fromRow) {
            var rows = shape.length;
            var fitted = new boolean[rows][];
            for (int r = 0; r < rows; r++) {
                var columns = shape[r].length;
                fitted[r] = new boolean[columns];
                System.arraycopy(shape[r], 0, fitted[r], 0, columns);
            }
            for (int c = 0; c < that.shape.length; c++) {
                var shapeColumn = that.shape[c];
                var fittedColumn = fitted[c + fromColumn];
                for (int r = 0; r < shapeColumn.length; r++) {
                    fittedColumn[r + fromRow] |= shapeColumn[r];
                }
            }
            return new Region(fitted);
        }

        private Region flipHorizontally() {
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
            return new Region(flipped);
        }

        private Region flipVertically() {
            var rows = shape.length;
            var flipped = new boolean[rows][];
            for (int r = 0; r < rows; r++) {
                var columns = shape[r].length;
                flipped[r] = new boolean[columns];
                System.arraycopy(shape[rows - 1 - r], 0, flipped[r], 0, columns);
            }
            return new Region(flipped);
        }

        private Region rotate() {
            var rows = shape.length;
            var columns = shape[0].length;
            var rotated = new boolean[columns][rows];
            for (int r = 0; r < rows; r++) {
                var shapeRow = shape[r];
                for (int c = 0; c < columns; c++) {
                    rotated[c][rows - 1 - r] = shapeRow[c];
                }
            }
            return new Region(rotated);
        }

        @Override
        public boolean equals(Object that) {
            if (that == null || getClass() != that.getClass()) return false;
            Region region = (Region) that;
            return Arrays.deepEquals(shape, region.shape);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(shape);
        }

        @Override
        public String toString() {
            var text = new StringJoiner("\n");
            for (var row : shape) {
                var rowText = new StringBuilder(row.length);
                for (var place : row) {
                    rowText.append(place ? "#" : ".");
                }
                text.add(rowText.toString());
            }
            return text.toString();
        }
    }

    private static final class SearchState {
        private final long[] presentQuantities;
        private final Region region;

        private SearchState(long[] presentQuantities, Region region) {
            this.presentQuantities = presentQuantities;
            this.region = region;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            SearchState that = (SearchState) o;
            return Objects.deepEquals(presentQuantities, that.presentQuantities) && Objects.equals(region, that.region);
        }

        @Override
        public int hashCode() {
            return Objects.hash(Arrays.hashCode(presentQuantities), region);
        }

        @Override
        public String toString() {
            return region.shape[0].length + "x" + region.shape.length + ": " + Arrays.stream(presentQuantities).mapToObj(Long::toString).collect(Collectors.joining(" "))
                    + System.lineSeparator() + region;
        }
    }
}
