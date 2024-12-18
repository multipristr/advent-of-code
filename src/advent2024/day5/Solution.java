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
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(143L);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(123L);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        Map<Long, Set<Long>> pagesOrderedAfter = new HashMap<>();
        return lines.mapToLong(line -> {
                    if (line.contains("|")) {
                        String[] pageNumbers = line.split("\\|");
                        pagesOrderedAfter.computeIfAbsent(Long.parseLong(pageNumbers[0]), k -> new HashSet<>()).add(Long.parseLong(pageNumbers[1]));
                    } else if (line.contains(",")) {
                        Long[] update = Arrays.stream(line.split(","))
                                .map(Long::parseLong)
                                .toArray(Long[]::new);
                        Long[] sorted = Arrays.copyOf(update, update.length);
                        Arrays.sort(sorted, (a, b) -> pagesOrderedAfter.getOrDefault(b, Set.of()).contains(a) ? 1 : -1);
                        return Arrays.equals(update, sorted) ? sorted[sorted.length / 2] : 0;
                    }
                    return 0;
                })
                .sum();
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        Map<Long, Set<Long>> pagesOrderedAfter = new HashMap<>();
        return lines.mapToLong(line -> {
                    if (line.contains("|")) {
                        String[] pageNumbers = line.split("\\|");
                        pagesOrderedAfter.computeIfAbsent(Long.parseLong(pageNumbers[0]), k -> new HashSet<>()).add(Long.parseLong(pageNumbers[1]));
                    } else if (line.contains(",")) {
                        Long[] update = Arrays.stream(line.split(","))
                                .map(Long::parseLong)
                                .toArray(Long[]::new);
                        Long[] sorted = Arrays.copyOf(update, update.length);
                        Arrays.sort(sorted, (a, b) -> pagesOrderedAfter.getOrDefault(b, Set.of()).contains(a) ? 1 : -1);
                        return Arrays.equals(update, sorted) ? 0 : sorted[sorted.length / 2];
                    }
                    return 0;
                })
                .sum();
    }
}
