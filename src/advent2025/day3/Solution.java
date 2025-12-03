package src.advent2025.day3;

import src.PuzzleSolver;

import java.util.List;
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
        return findTotalOutputJoltage(lines, (byte) 2);
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        return findTotalOutputJoltage(lines, (byte) 12);
    }

    private long findTotalOutputJoltage(Stream<String> lines, byte batteries) {
        return lines.parallel()
                .mapToLong(bank -> findLargestJoltagePerBankPerBatteries(bank, batteries))
                .sum();
    }

    private long findLargestJoltagePerBankPerBatteries(String bank, byte batteries) {
        for (byte firstBattery = 9; firstBattery >= 0; --firstBattery) {
            var firstBatteryIndex = bank.indexOf(Byte.toString(firstBattery));
            if (firstBatteryIndex > -1) {
                for (byte secondBattery = 9; secondBattery >= 0; --secondBattery) {
                    var secondBatteryIndex = bank.indexOf(Byte.toString(secondBattery), firstBatteryIndex + 1);
                    if (secondBatteryIndex > -1) {
                        return firstBattery * 10 + secondBattery;
                    }
                }
            }
        }
        throw new IllegalStateException(bank);
    }
}
