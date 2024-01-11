package src.advent2023.day5;

import src.PuzzleSolver;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("seeds: 79 14 55 13\n" +
                "\n" +
                "seed-to-soil map:\n" +
                "50 98 2\n" +
                "52 50 48\n" +
                "\n" +
                "soil-to-fertilizer map:\n" +
                "0 15 37\n" +
                "37 52 2\n" +
                "39 0 15\n" +
                "\n" +
                "fertilizer-to-water map:\n" +
                "49 53 8\n" +
                "0 11 42\n" +
                "42 0 7\n" +
                "57 7 4\n" +
                "\n" +
                "water-to-light map:\n" +
                "88 18 7\n" +
                "18 25 70\n" +
                "\n" +
                "light-to-temperature map:\n" +
                "45 77 23\n" +
                "81 45 19\n" +
                "68 64 13\n" +
                "\n" +
                "temperature-to-humidity map:\n" +
                "0 69 1\n" +
                "1 0 69\n" +
                "\n" +
                "humidity-to-location map:\n" +
                "60 56 37\n" +
                "56 93 4");
    }

    @Override
    public List<String> getExampleOutput1() {
        return List.of("35");
    }

    @Override
    public List<String> getExampleOutput2() {
        return List.of("46");
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        String[] lineArray = lines.toArray(String[]::new);
        TreeMap<Long, Long> locations = new TreeMap<>();

        StringTokenizer tokens = new StringTokenizer(lineArray[0], " ");
        tokens.nextToken();
        while (tokens.hasMoreTokens()) {
            long initialSeed = Long.parseLong(tokens.nextToken());
            locations.put(initialSeed, initialSeed);
        }
        int nextMapStartLine = 3;
        for (int i = 0; i < 7; i++) {
            for (String line = lineArray[nextMapStartLine]; !line.isEmpty() && nextMapStartLine + 1 < lineArray.length; line = lineArray[++nextMapStartLine]) {
                tokens = new StringTokenizer(line, " ");
                long destinationRangeStart = Long.parseLong(tokens.nextToken());
                long sourceRangeStart = Long.parseLong(tokens.nextToken());
                long rangeLength = Long.parseLong(tokens.nextToken());
                locations.subMap(sourceRangeStart, sourceRangeStart + rangeLength)
                        .replaceAll((key, oldValue) -> (key - sourceRangeStart) + destinationRangeStart);
            }
            locations = locations.values().stream()
                    .collect(Collectors.toMap(Function.identity(), Function.identity(),
                            (v1, v2) -> {
                                throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));
                            },
                            TreeMap::new
                    ));
            nextMapStartLine += 2;
        }

        return locations.keySet().stream().min(Comparator.naturalOrder()).get() + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        String[] lineArray = lines.toArray(String[]::new);
        Map<Range, Range> locations = new HashMap<>();

        StringTokenizer tokens = new StringTokenizer(lineArray[0], " ");
        tokens.nextToken();
        while (tokens.hasMoreTokens()) {
            long seedRangeStart = Long.parseLong(tokens.nextToken());
            long seedRangeLength = Long.parseLong(tokens.nextToken());
            Range range = new Range(seedRangeStart, seedRangeStart + seedRangeLength - 1);
            locations.put(range, range);
        }
        int nextMapStartLine = 3;
        for (int i = 0; i < 7; i++) {
            for (String line = lineArray[nextMapStartLine]; !line.isEmpty() && nextMapStartLine + 1 < lineArray.length; line = lineArray[++nextMapStartLine]) {
                tokens = new StringTokenizer(line, " ");
                long destinationRangeStart = Long.parseLong(tokens.nextToken());
                long sourceRangeStart = Long.parseLong(tokens.nextToken());
                long rangeLength = Long.parseLong(tokens.nextToken());
                long sourceRangeEnd = sourceRangeStart + rangeLength - 1;
                var oldRanges = locations.keySet().stream()
                        .filter(range -> range.getStart() <= sourceRangeEnd && range.getEnd() >= sourceRangeStart)
                        .collect(Collectors.toList());
                for (var oldRange : oldRanges) {
                    locations.remove(oldRange);
                    if (sourceRangeStart > oldRange.getStart()) {
                        Range range = new Range(oldRange.getStart(), sourceRangeStart - 1);
                        locations.put(range, range);
                    }

                    long sourceMappingStart = Math.max(oldRange.getStart(), sourceRangeStart);
                    long sourceMappingEnd = Math.min(oldRange.getEnd(), sourceRangeEnd);
                    long shift = destinationRangeStart - sourceRangeStart;
                    locations.put(new Range(sourceMappingStart, sourceMappingEnd), new Range(sourceMappingStart + shift, sourceMappingEnd + shift));

                    if (sourceRangeEnd < oldRange.getEnd()) {
                        Range range = new Range(sourceRangeEnd + 1, oldRange.getEnd());
                        locations.put(range, range);
                    }
                }
            }
            locations = locations.values().stream().collect(Collectors.toMap(Function.identity(), Function.identity()));
            nextMapStartLine += 2;
        }

        return locations.keySet().stream().mapToLong(Range::getStart).min().orElseThrow() + "";
    }

    private static final class Range {
        private final long start;
        private final long end;

        Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public long getStart() {
            return start;
        }

        public long getEnd() {
            return end;
        }
    }
}
