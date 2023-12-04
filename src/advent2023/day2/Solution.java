package src.advent2023.day2;

import src.PuzzleSolver;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public String getExampleInput1() {
        return "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green\n" +
                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue\n" +
                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red\n" +
                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red\n" +
                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green";
    }

    @Override
    public String getExampleOutput1() {
        return "8";
    }

    @Override
    public String getExampleOutput2() {
        return "2286";
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        Map<String, Integer> cubes = new HashMap<>(3);
        cubes.put("red", 12);
        cubes.put("green", 13);
        cubes.put("blue", 14);

        return lines.map(line -> line.replaceAll(":", ""))
                .map(line -> line.replaceAll(",", ""))
                .map(line -> line.replaceAll(";", ""))
                .mapToInt(game -> {
                    String[] parts = game.split(" ");
                    for (int i = 3; i < parts.length; i += 2) {
                        Integer limit = cubes.get(parts[i]);
                        if (Integer.parseInt(parts[i - 1]) > limit) {
                            return 0;
                        }
                    }
                    return Integer.parseInt(parts[1]);
                })
                .sum() + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return lines.map(line -> line.replaceAll(":", ""))
                .map(line -> line.replaceAll(",", ""))
                .map(line -> line.replaceAll(";", ""))
                .mapToInt(game -> {
                    Map<String, Integer> cubes = new HashMap<>(3);
                    cubes.put("red", 0);
                    cubes.put("green", 0);
                    cubes.put("blue", 0);
                    String[] parts = game.split(" ");
                    for (int i = 3; i < parts.length; i += 2) {
                        String color = parts[i];
                        int amount = Integer.parseInt(parts[i - 1]);
                        cubes.merge(color, amount, Math::max);

                    }
                    return cubes.values().stream().reduce(1, (a, b) -> a * b);
                })
                .sum() + "";
    }
}
