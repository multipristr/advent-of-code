package src.advent2024.day11;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
        return List.of(0L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        return calculateStonesAfterBlinking(lines, 25);
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        return calculateStonesAfterBlinking(lines, 75);
    }

    private int calculateStonesAfterBlinking(Stream<String> lines, int blinks) {
        List<Long> stones = lines.map(line -> line.split("\\s+"))
                .flatMap(Arrays::stream)
                .map(Long::parseLong)
                .collect(Collectors.toList());
        for (int blink = 0; blink < blinks; blink++) {
            for (int stoneIndex = 0; stoneIndex < stones.size(); stoneIndex++) {
                Long stone = stones.get(stoneIndex);
                if (stone == 0) {
                    stones.set(stoneIndex, 1L);
                } else if ((stone.toString().length() & 1L) == 0L) {
                    String stoneNumber = stone.toString();
                    int numberMiddle = stoneNumber.length() >>> 1;
                    stones.set(stoneIndex, Long.valueOf(stoneNumber.substring(0, numberMiddle)));
                    ++stoneIndex;
                    stones.add(stoneIndex, Long.valueOf(stoneNumber.substring(numberMiddle)));
                } else {
                    stones.set(stoneIndex, stone * 2024);
                }
            }
        }
        return stones.size();
    }
}
