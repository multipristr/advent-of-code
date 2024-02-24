package src.advent2023.day6;

import src.PuzzleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("Time:      7  15   30\n" +
                "Distance:  9  40  200");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(288L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(71503L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        String[] linesArray = lines.toArray(String[]::new);

        StringTokenizer tokens = new StringTokenizer(linesArray[0], " ");
        tokens.nextToken();
        List<Integer> times = new ArrayList<>(4);
        while (tokens.hasMoreTokens()) {
            times.add(Integer.parseInt(tokens.nextToken()));
        }

        int[] waysOfBeatingRecord = new int[times.size()];
        tokens = new StringTokenizer(linesArray[1], " ");
        tokens.nextToken();
        for (int i = 0; i < times.size(); i++) {
            int time = times.get(i);
            int record = Integer.parseInt(tokens.nextToken());
            for (int millisecondsHoldingButton = 1; millisecondsHoldingButton < time; millisecondsHoldingButton++) {
                if (millisecondsHoldingButton * (time - millisecondsHoldingButton) > record) {
                    waysOfBeatingRecord[i] = 1 + time - 2 * millisecondsHoldingButton;
                    break;
                }
            }
        }

        return Arrays.stream(waysOfBeatingRecord).reduce(1, (a, b) -> a * b);
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        String[] linesArray = lines.toArray(String[]::new);
        int time = Integer.parseInt(linesArray[0].replaceFirst("Time:", "").replaceAll("\\s", ""));
        long distance = Long.parseLong(linesArray[1].replaceFirst("Distance:", "").replaceAll("\\s", ""));

        /*
        var sqrt = Math.sqrt(Math.pow(time, 2) - 4 * distance);
        var start = Math.floor((time - sqrt) / 2 + 1);
        var end = Math.ceil((time + sqrt) / 2 - 1);
        return (long) (end - start + 1) ;
         */

        for (long millisecondsHoldingButton = 1; millisecondsHoldingButton < time; millisecondsHoldingButton++) {
            if (millisecondsHoldingButton * (time - millisecondsHoldingButton) > distance) {
                return 1 + time - 2 * millisecondsHoldingButton;
            }
        }

        throw new IllegalStateException("No result");
    }
}
