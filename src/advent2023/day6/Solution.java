package src.advent2023.day6;

import src.PuzzleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public String getExampleInput1() {
        return "Time:      7  15   30\n" +
                "Distance:  9  40  200";
    }

    @Override
    public String getExampleOutput1() {
        return "288";
    }

    @Override
    public String getExampleOutput2() {
        return "71503";
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
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
            waysOfBeatingRecord[i] = (int) IntStream.range(1, time).parallel()
                    .filter(millisecondsHoldingButton -> millisecondsHoldingButton * (time - millisecondsHoldingButton) > record)
                    .count();
        }

        return Arrays.stream(waysOfBeatingRecord).reduce(1, (a, b) -> a * b) + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        String[] linesArray = lines.toArray(String[]::new);
        int time = Integer.parseInt(linesArray[0].replaceFirst("Time:", "").replaceAll("\\s", ""));
        long distance = Long.parseLong(linesArray[1].replaceFirst("Distance:", "").replaceAll("\\s", ""));

        return LongStream.range(1, time).parallel()
                .filter(millisecondsHoldingButton -> millisecondsHoldingButton * (time - millisecondsHoldingButton) > distance)
                .count() + "";
    }
}
