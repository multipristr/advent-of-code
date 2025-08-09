package src.advent2015.day3;

import src.PuzzleSolver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Integer, Integer> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("^>v<");
    }

    @Override
    public List<Integer> getExampleOutput1() {
        return List.of(4);
    }

    @Override
    public List<Integer> getExampleOutput2() {
        return List.of(3);
    }

    @Override
    public Integer solvePartOne(Stream<String> lines) {
        return lines.mapToInt(line -> {
            Set<String> visited = new HashSet<>();
            int x = 0;
            int y = 0;
            char[] chars = line.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char symbol = chars[i];
                switch (symbol) {
                    case '^':
                        ++y;
                        break;
                    case 'v':
                        --y;
                        break;
                    case '>':
                        ++x;
                        break;
                    case '<':
                        --x;
                        break;
                }
                visited.add(x + "," + y);
            }
            return visited.size();
        }).sum();
    }

    @Override
    public Integer solvePartTwo(Stream<String> lines) {
        return lines.mapToInt(line -> {
            Set<String> visited = new HashSet<>();
            char[] chars = line.toCharArray();
            int x = 0;
            int y = 0;
            for (int i = 0; i < chars.length; i += 2) {
                char symbol = chars[i];
                switch (symbol) {
                    case '^':
                        ++y;
                        break;
                    case 'v':
                        --y;
                        break;
                    case '>':
                        ++x;
                        break;
                    case '<':
                        --x;
                        break;
                }
                visited.add(x + "," + y);
            }
            x = 0;
            y = 0;
            for (int i = 1; i < chars.length; i += 2) {
                char symbol = chars[i];
                switch (symbol) {
                    case '^':
                        ++y;
                        break;
                    case 'v':
                        --y;
                        break;
                    case '>':
                        ++x;
                        break;
                    case '<':
                        --x;
                        break;
                }
                visited.add(x + "," + y);
            }
            return visited.size();
        }).sum();
    }
}
