package src.advent2023.day15;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Integer, Integer> {

    public static void main(String[] args) {
        new Solution().run();
    }

    private static int hash(String word) {
        int currentValue = 0;
        for (int i = 0; i < word.length(); ++i) {
            currentValue += word.charAt(i);
            currentValue *= 17;
            currentValue = currentValue % 256;
        }
        return currentValue;
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("HASH", "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7");
    }

    @Override
    public List<Integer> getExampleOutput1() {
        return List.of(52, 1320);
    }

    @Override
    public List<Integer> getExampleOutput2() {
        return List.of(145);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7");
    }

    @Override
    public Integer solvePartOne(Stream<String> lines) {
        return lines.parallel()
                .flatMap(line -> Arrays.stream(line.split(",")))
                .mapToInt(Solution::hash)
                .sum();
    }

    @Override
    public Integer solvePartTwo(Stream<String> lines) {
        Map<Integer, Map<String, Integer>> lenses = new HashMap<>(256);
        lines.flatMap(line -> Arrays.stream(line.split(",")))
                .forEach(word -> {
                    if (word.contains("=")) {
                        var parts = word.split("=");
                        var label = parts[0];
                        var hash = hash(label);
                        lenses.computeIfAbsent(hash, k -> new LinkedHashMap<>()).put(label, Integer.parseInt(parts[1]));
                    } else if (word.contains("-")) {
                        var parts = word.split("-");
                        var label = parts[0];
                        var hash = hash(label);
                        lenses.getOrDefault(hash, new HashMap<>(0)).remove(label);
                    }
                });

        return lenses.entrySet().stream()
                .mapToInt(entry -> {
                    int focusingPower = 0;
                    int slot = 1;
                    for (var pair : entry.getValue().entrySet()) {
                        focusingPower += (entry.getKey() + 1) * slot++ * pair.getValue();
                    }
                    return focusingPower;
                })
                .sum();
    }
}
