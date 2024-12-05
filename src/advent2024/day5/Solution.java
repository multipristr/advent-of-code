package src.advent2024.day5;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("47|53\n" +
                "97|13\n" +
                "97|61\n" +
                "97|47\n" +
                "75|29\n" +
                "61|13\n" +
                "75|53\n" +
                "29|13\n" +
                "97|29\n" +
                "53|29\n" +
                "61|53\n" +
                "97|53\n" +
                "61|29\n" +
                "47|13\n" +
                "75|47\n" +
                "97|75\n" +
                "47|61\n" +
                "75|61\n" +
                "47|29\n" +
                "75|13\n" +
                "53|13\n" +
                "\n" +
                "75,47,61,53,29\n" +
                "97,61,53,29,13\n" +
                "75,29,13\n" +
                "75,97,47,61,53\n" +
                "61,13,29\n" +
                "97,13,75,29,47");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(143L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(123L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        Map<Long, Set<Long>> pageOrderingRules = new HashMap<>();
        return lines.mapToLong(line -> {
                    if (line.contains("|")) {
                        String[] pageNumbers = line.split("\\|");
                        pageOrderingRules.computeIfAbsent(Long.parseLong(pageNumbers[0]), k -> new HashSet<>()).add(Long.parseLong(pageNumbers[1]));
                    } else if (line.contains(",")) {
                        long[] update = Arrays.stream(line.split(","))
                                .mapToLong(Long::parseLong)
                                .toArray();
                        for (int i = 0; i < update.length; i++) {
                            long updatePageNumber = update[i];
                            for (int j = 0; j < i; j++) {
                                if (!pageOrderingRules.get(update[j]).contains(updatePageNumber)) {
                                    return 0;
                                }
                            }
                            Set<Long> pageNumberRules = pageOrderingRules.getOrDefault(updatePageNumber, Set.of());
                            for (int j = i + 1; j < update.length; j++) {
                                if (!pageNumberRules.contains(update[j])) {
                                    return 0;
                                }
                            }
                        }
                        return update[update.length / 2];
                    }
                    return 0;
                })
                .sum();
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
