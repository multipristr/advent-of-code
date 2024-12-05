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
        Map<Long, Set<Long>> pagesOrderedAfter = new HashMap<>();
        Map<Long, Set<Long>> pagesOrderedBefore = new HashMap<>();
        return lines.mapToLong(line -> {
                    if (line.contains("|")) {
                        String[] pageNumbers = line.split("\\|");
                        long pageNumber1 = Long.parseLong(pageNumbers[0]);
                        long pageNumber2 = Long.parseLong(pageNumbers[1]);
                        pagesOrderedAfter.computeIfAbsent(pageNumber1, k -> new HashSet<>()).add(pageNumber2);
                        pagesOrderedBefore.computeIfAbsent(pageNumber2, k -> new HashSet<>()).add(pageNumber1);
                    } else if (line.contains(",")) {
                        long[] update = Arrays.stream(line.split(","))
                                .mapToLong(Long::parseLong)
                                .toArray();
                        for (int i = 0; i < update.length; i++) {
                            long updatePageNumber = update[i];
                            Set<Long> pageNumbersBefore = pagesOrderedBefore.get(updatePageNumber);
                            for (int j = 0; j < i; j++) {
                                if (!pageNumbersBefore.contains(update[j])) {
                                    return 0;
                                }
                            }
                            Set<Long> pageNumbersAfter = pagesOrderedAfter.getOrDefault(updatePageNumber, Set.of());
                            for (int j = i + 1; j < update.length; j++) {
                                if (!pageNumbersAfter.contains(update[j])) {
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
        Map<Long, Set<Long>> pagesOrderedAfter = new HashMap<>();
        Map<Long, Set<Long>> pagesOrderedBefore = new HashMap<>();
        return lines.mapToLong(line -> {
                    if (line.contains("|")) {
                        String[] pageNumbers = line.split("\\|");
                        long pageNumber1 = Long.parseLong(pageNumbers[0]);
                        long pageNumber2 = Long.parseLong(pageNumbers[1]);
                        pagesOrderedAfter.computeIfAbsent(pageNumber1, k -> new HashSet<>()).add(pageNumber2);
                        pagesOrderedBefore.computeIfAbsent(pageNumber2, k -> new HashSet<>()).add(pageNumber1);
                    } else if (line.contains(",")) {
                        long[] update = Arrays.stream(line.split(","))
                                .mapToLong(Long::parseLong)
                                .toArray();
                        boolean fixed = false;
                        outer:
                        for (int i = 0; i < update.length; i++) {
                            long updatePageNumber = update[i];
                            Set<Long> pageNumbersBefore = pagesOrderedBefore.get(updatePageNumber);
                            for (int j = 0; j < i; j++) {
                                long pageNumber = update[j];
                                if (!pageNumbersBefore.contains(pageNumber)) {
                                    update[j] = updatePageNumber;
                                    update[i] = pageNumber;
                                    i = j;
                                    fixed = true;
                                    continue outer;
                                }
                            }
                            Set<Long> pageNumbersAfter = pagesOrderedAfter.getOrDefault(updatePageNumber, Set.of());
                            for (int j = i + 1; j < update.length; j++) {
                                long pageNumber = update[j];
                                if (!pageNumbersAfter.contains(pageNumber)) {
                                    update[i] = pageNumber;
                                    update[j] = updatePageNumber;
                                    --i;
                                    fixed = true;
                                    continue outer;
                                }
                            }
                        }
                        return fixed ? update[update.length / 2] : 0;
                    }
                    return 0;
                })
                .sum();
    }
}
