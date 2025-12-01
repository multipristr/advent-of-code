package src.advent2025.day1;

import src.PuzzleSolver;

import java.util.List;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("L68\n" +
                "L30\n" +
                "R48\n" +
                "L5\n" +
                "R60\n" +
                "L55\n" +
                "L1\n" +
                "L99\n" +
                "R14\n" +
                "L82");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(3L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(6L);
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        long dial = 50;
        long pointingAtZeroTime = 0;
        var linesIterator = lines.iterator();
        while (linesIterator.hasNext()) {
            var rotation = linesIterator.next();
            var distance = Long.parseLong(rotation.substring(1));
            if (rotation.startsWith("L")) {
                dial -= distance;
            } else {
                dial += distance;
            }
            dial %= 100;
            if (dial == 0) {
                ++pointingAtZeroTime;
            }
        }
        return pointingAtZeroTime;
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        long dial = 50;
        long pointingAtZeroTime = 0;
        var linesIterator = lines.iterator();
        while (linesIterator.hasNext()) {
            var rotation = linesIterator.next();
            var distance = Long.parseLong(rotation.substring(1));

            if (rotation.startsWith("L")) {
                boolean startedAtZero = dial == 0;
                dial -= distance;
                while (dial < 0) {
                    dial += 100;
                    if (!startedAtZero) {
                        ++pointingAtZeroTime;
                        startedAtZero = false;
                    }
                }
                if (dial == 0) {
                    ++pointingAtZeroTime;
                }
            } else {
                dial += distance;
                while (dial >= 100) {
                    dial -= 100;
                    ++pointingAtZeroTime;
                }
            }

        }
        return pointingAtZeroTime;
    }
}
