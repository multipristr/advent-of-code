package src.advent2025.day5;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {
    private static final Pattern FRESH_INGREDIENT_ID_RANGE_PATTERN = Pattern.compile("(?<fromId>\\d+)-(?<toId>\\d+)");

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("3-5\n" +
                "10-14\n" +
                "16-20\n" +
                "12-18\n" +
                "\n" +
                "1\n" +
                "5\n" +
                "8\n" +
                "11\n" +
                "17\n" +
                "32");
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
        List<Map.Entry<Long, Long>> freshIngredientIdRanges = new ArrayList<>();

        var linesIterator = lines.iterator();
        while (linesIterator.hasNext()) {
            var line = linesIterator.next();
            var matcher = FRESH_INGREDIENT_ID_RANGE_PATTERN.matcher(line);
            if (!matcher.find()) {
                break;
            }
            var fromId = Long.parseLong(matcher.group("fromId"));
            var toId = Long.parseLong(matcher.group("toId"));
            freshIngredientIdRanges.add(new AbstractMap.SimpleImmutableEntry<>(fromId, toId));
        }

        long availableFreshIngredient = 0;
        while (linesIterator.hasNext()) {
            var availableIngredientId = Long.parseLong(linesIterator.next());
            for (var freshIngredientIdRange : freshIngredientIdRanges) {
                if (freshIngredientIdRange.getKey() <= availableIngredientId && freshIngredientIdRange.getValue() >= availableIngredientId) {
                    ++availableFreshIngredient;
                    break;
                }
            }
        }
        return availableFreshIngredient;
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
