package src.advent2024.day25;

import src.PuzzleSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("#####\n" +
                ".####\n" +
                ".####\n" +
                ".####\n" +
                ".#.#.\n" +
                ".#...\n" +
                ".....\n" +
                "\n" +
                "#####\n" +
                "##.##\n" +
                ".#.##\n" +
                "...##\n" +
                "...#.\n" +
                "...#.\n" +
                ".....\n" +
                "\n" +
                ".....\n" +
                "#....\n" +
                "#....\n" +
                "#...#\n" +
                "#.#.#\n" +
                "#.###\n" +
                "#####\n" +
                "\n" +
                ".....\n" +
                ".....\n" +
                "#.#..\n" +
                "###..\n" +
                "###.#\n" +
                "###.#\n" +
                "#####\n" +
                "\n" +
                ".....\n" +
                ".....\n" +
                ".....\n" +
                "#....\n" +
                "#.#..\n" +
                "#.#.#\n" +
                "#####");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(3L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of();
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        var schematics = lines.collect(Collectors.joining(System.lineSeparator())).split("\\R{2}");

        List<int[]> lockHeights = new ArrayList<>();
        List<int[]> keyHeights = new ArrayList<>();
        int schematicHeight = 0;
        for (var schematic : schematics) {
            var schematicRows = schematic.split("\\R");
            schematicHeight = schematicRows.length;
            var topRow = schematicRows[0];
            boolean lock = topRow.codePoints().allMatch(c -> (char) c == '#');
            var heights = new int[topRow.length()];
            for (var schematicRow : schematicRows) {
                for (int column = 0; column < schematicRow.length(); column++) {
                    if (schematicRow.charAt(column) == '#') {
                        ++heights[column];
                    }
                }
            }
            if (lock) {
                lockHeights.add(heights);
            } else {
                keyHeights.add(heights);
            }
        }

        int finalSchematicHeight = schematicHeight;
        return lockHeights.parallelStream()
                .flatMap(lockHeight -> keyHeights.stream()
                        .filter(keyHeight -> IntStream.range(0, keyHeight.length)
                                .noneMatch(column -> lockHeight[column] + keyHeight[column] > finalSchematicHeight)
                        )
                )
                .count();
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
