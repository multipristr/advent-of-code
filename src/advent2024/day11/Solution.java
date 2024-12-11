package src.advent2024.day11;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("125 17");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(55312L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(65601038650482L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        return calculateStonesAfterBlinking(lines, 25);
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        return calculateStonesAfterBlinking(lines, 75);
    }

    private long calculateStonesAfterBlinking(Stream<String> lines, int blinks) {
        Map<Long, Long>[] stonesMemory = new Map[blinks];
        for (int i = 0; i < stonesMemory.length; i++) {
            stonesMemory[i] = new HashMap<>();
        }
        return lines.parallel().map(line -> line.split("\\s+"))
                .flatMap(Arrays::stream)
                .map(Long::parseLong)
                .mapToLong(stone -> calculateStonesAfterBlinking(stone, 0, blinks, stonesMemory))
                .sum();
    }

    private long calculateStonesAfterBlinking(Long stone, int blink, int blinkTimes, Map<Long, Long>[] stonesMemory) {
        if (blink == blinkTimes) {
            return 1L;
        } else if (stonesMemory[blink].containsKey(stone)) {
            return stonesMemory[blink].get(stone);
        } else if (stone == 0) {
            long stones = calculateStonesAfterBlinking(1L, blink + 1, blinkTimes, stonesMemory);
            stonesMemory[blink].put(stone, stones);
            return stones;
        } else if ((stone.toString().length() & 1L) == 0L) {
            String stoneNumber = stone.toString();
            int numberMiddle = stoneNumber.length() >>> 1;
            long stones = calculateStonesAfterBlinking(Long.parseLong(stoneNumber.substring(0, numberMiddle)), blink + 1, blinkTimes, stonesMemory)
                    + calculateStonesAfterBlinking(Long.parseLong(stoneNumber.substring(numberMiddle)), blink + 1, blinkTimes, stonesMemory);
            stonesMemory[blink].put(stone, stones);
            return stones;
        } else {
            long stones = calculateStonesAfterBlinking(stone * 2024, blink + 1, blinkTimes, stonesMemory);
            stonesMemory[blink].put(stone, stones);
            return stones;
        }
    }

}
