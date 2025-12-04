package src.advent2025.day3;

import src.PuzzleSolver;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("987654321111111\n" +
                "811111111111119\n" +
                "234234234234278\n" +
                "818181911112111");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(357L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(3121910778619L);
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        return findTotalOutputJoltage(lines, 2);
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        return findTotalOutputJoltage(lines, 12);
    }

    private long findTotalOutputJoltage(Stream<String> lines, int batteries) {
        return lines.map(bank -> findLargestJoltage(bank, batteries, 0).orElseThrow())
                .mapToLong(Long::parseLong)
                .sum();
    }

    private Optional<String> findLargestJoltage(String bank, int battery, int fromIndex) {
        for (byte batteryValue = 9; batteryValue >= 0; --batteryValue) {
            var batterStringValue = Byte.toString(batteryValue);
            var batteryIndex = bank.indexOf(batterStringValue, fromIndex);
            if (batteryIndex > -1) {
                if (battery <= 1) {
                    return Optional.of(batterStringValue);
                }
                var largestJoltage = findLargestJoltage(bank, battery - 1, batteryIndex + 1);
                if (largestJoltage.isPresent()) {
                    return largestJoltage.map(joltage -> batterStringValue + joltage);
                }
            }
        }
        return Optional.empty();
    }
}
