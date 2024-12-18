package src.advent2015.day5;

import src.PuzzleSolver;

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
        return List.of("ugknbfddgicrmopn\n" +
                "aaa\n" +
                "jchzalrnumimnmhp\n" +
                "haegwjzuvuyypxyu\n" +
                "dvszwmarrgswjxmb");
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(2L);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of("aaa\n" +
                "qjhvhtzxzqqjkmpb\n" +
                "xxyxx\n" +
                "uurcxstgmygtbstg\n" +
                "ieodomkazucvgmuy");
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(2L);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        return lines
                .filter(line -> {
                    Map<Character, Integer> occurence = new HashMap<>();
                    char[] chars = line.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        occurence.merge(chars[i], 1, Integer::sum);
                    }
                    return occurence.getOrDefault('a', 0) +
                            occurence.getOrDefault('e', 0) +
                            occurence.getOrDefault('i', 0) +
                            occurence.getOrDefault('o', 0) +
                            occurence.getOrDefault('u', 0) >= 3;
                })
                .filter(line -> {
                    char[] chars = line.toCharArray();
                    for (int i = 1; i < chars.length; i++) {
                        if (chars[i] == chars[i - 1]) {
                            return true;
                        }
                    }
                    return false;
                })
                .filter(line -> !line.contains("ab"))
                .filter(line -> !line.contains("cd"))
                .filter(line -> !line.contains("pq"))
                .filter(line -> !line.contains("xy"))
                .count();
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        return lines
                .filter(line -> {
                    char[] chars = line.toCharArray();
                    for (int i = 1; i < chars.length; i++) {
                        String substring = line.substring(i - 1, i + 1);
                        if (line.lastIndexOf(substring) - line.indexOf(substring) > 1) {
                            return true;
                        }
                    }
                    return false;
                })
                .filter(line -> {
                    char[] chars = line.toCharArray();
                    for (int i = 2; i < chars.length; i++) {
                        if (chars[i] == chars[i - 2]) {
                            return true;
                        }
                    }
                    return false;
                })
                .count();
    }
}
