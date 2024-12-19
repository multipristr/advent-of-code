package src.advent2024.day19;

import src.PuzzleSolver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("r, wr, b, g, bwu, rb, gb, br\n" +
                "\n" +
                "brwrr\n" +
                "bggr\n" +
                "gbbr\n" +
                "rrbgbr\n" +
                "ubwu\n" +
                "bwurrg\n" +
                "brgr\n" +
                "bbrgwb");
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(6);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(16);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        var linesIterator = lines.iterator();
        var availableTowelPatterns = linesIterator.next().split(", ");
        linesIterator.next(); // empty line
        Map<String, Long> possibleDesignsMemory = new ConcurrentHashMap<>();
        long possibleDesigns = 0L;
        while (linesIterator.hasNext()) {
            String desiredPattern = linesIterator.next();
            if (countDifferentDesignWays(possibleDesignsMemory, availableTowelPatterns, desiredPattern) > 0L) {
                ++possibleDesigns;
            }
        }
        return possibleDesigns;
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        var linesIterator = lines.iterator();
        var availableTowelPatterns = linesIterator.next().split(", ");
        linesIterator.next(); // empty line
        Map<String, Long> possibleDesignsMemory = new ConcurrentHashMap<>();
        long differentDesignWays = 0L;
        while (linesIterator.hasNext()) {
            String desiredPattern = linesIterator.next();
            long differentDesignWay = countDifferentDesignWays(possibleDesignsMemory, availableTowelPatterns, desiredPattern);
            differentDesignWays += differentDesignWay;
        }
        return differentDesignWays;
    }

    private long countDifferentDesignWays(Map<String, Long> possibleDesignsMemory, String[] availableTowelPatterns, String desiredPattern) {
        if (desiredPattern.isEmpty()) {
            return 1L;
        }
        var memorizedPossibleDesigns = possibleDesignsMemory.get(desiredPattern);
        if (memorizedPossibleDesigns != null) {
            return memorizedPossibleDesigns;
        }
        long possibleDesigns = 0L;
        for (var availableTowelPattern : availableTowelPatterns) {
            if (desiredPattern.startsWith(availableTowelPattern)) {
                possibleDesigns += countDifferentDesignWays(possibleDesignsMemory, availableTowelPatterns, desiredPattern.substring(availableTowelPattern.length()));
            }
        }
        possibleDesignsMemory.put(desiredPattern, possibleDesigns);
        return possibleDesigns;
    }

}
