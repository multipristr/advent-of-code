package src.advent2015.day6;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public String getExampleInput1() {
        return "turn on 0,0 through 999,999\n" +
                "toggle 0,0 through 999,0\n" +
                "turn off 499,499 through 500,500";
    }

    @Override
    public String getExampleOutput1() {
        return "998996";
    }

    @Override
    public String getExampleOutput2() {
        return "1001996";
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        boolean[][] lights = new boolean[1_000][1_000];

        lines.forEach(line -> {
            String[] parts = line.split(" ");
            switch (parts[0]) {
                case "turn":
                    boolean newValue = false;
                    switch (parts[1]) {
                        case "on":
                            newValue = true;
                            break;
                        case "off":
                            newValue = false;
                            break;
                    }
                    String[] startIndices = parts[2].split(",");
                    String[] endIndices = parts[4].split(",");
                    for (int row = Integer.parseInt(startIndices[0]); row <= Integer.parseInt(endIndices[0]); row++) {
                        for (int column = Integer.parseInt(startIndices[1]); column <= Integer.parseInt(endIndices[1]); column++) {
                            lights[row][column] = newValue;
                        }
                    }
                    break;
                case "toggle":
                    String[] fromIndices = parts[1].split(",");
                    String[] toIndices = parts[3].split(",");
                    for (int row = Integer.parseInt(fromIndices[0]); row <= Integer.parseInt(toIndices[0]); row++) {
                        for (int column = Integer.parseInt(fromIndices[1]); column <= Integer.parseInt(toIndices[1]); column++) {
                            lights[row][column] = !lights[row][column];
                        }
                    }
                    break;
            }
        });

        int lit = 0;
        for (boolean[] row : lights) {
            for (boolean isOn : row) {
                if (isOn) {
                    ++lit;
                }
            }
        }
        return lit + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        int[][] lights = new int[1_000][1_000];

        lines.forEach(line -> {
            String[] parts = line.split(" ");
            switch (parts[0]) {
                case "turn":
                    int valueAdjustment = 0;
                    switch (parts[1]) {
                        case "on":
                            valueAdjustment = 1;
                            break;
                        case "off":
                            valueAdjustment = -1;
                            break;
                    }
                    String[] startIndices = parts[2].split(",");
                    String[] endIndices = parts[4].split(",");
                    for (int row = Integer.parseInt(startIndices[0]); row <= Integer.parseInt(endIndices[0]); row++) {
                        for (int column = Integer.parseInt(startIndices[1]); column <= Integer.parseInt(endIndices[1]); column++) {
                            lights[row][column] = Math.max(0, lights[row][column] + valueAdjustment);
                        }
                    }
                    break;
                case "toggle":
                    String[] fromIndices = parts[1].split(",");
                    String[] toIndices = parts[3].split(",");
                    for (int row = Integer.parseInt(fromIndices[0]); row <= Integer.parseInt(toIndices[0]); row++) {
                        for (int column = Integer.parseInt(fromIndices[1]); column <= Integer.parseInt(toIndices[1]); column++) {
                            lights[row][column] += 2;
                        }
                    }
                    break;
            }
        });

        return Arrays.stream(lights)
                .flatMapToInt(Arrays::stream)
                .sum() + "";
    }
}
