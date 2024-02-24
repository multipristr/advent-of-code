package src.advent2023.day8;

import src.PuzzleSolver;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    static long lcm(LongStream oneNodeDistances) {
        return oneNodeDistances.reduce(1L, (x, y) -> (x * y) / gcd(x, y));
    }

    static long gcd(long a, long b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("RL\n"
                + "\n"
                + "AAA = (BBB, CCC)\n"
                + "BBB = (DDD, EEE)\n"
                + "CCC = (ZZZ, GGG)\n"
                + "DDD = (DDD, DDD)\n"
                + "EEE = (EEE, EEE)\n"
                + "GGG = (GGG, GGG)\n"
                + "ZZZ = (ZZZ, ZZZ)");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(2L);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of("LR\n"
                + "\n"
                + "11A = (11B, XXX)\n"
                + "11B = (XXX, 11Z)\n"
                + "11Z = (11B, XXX)\n"
                + "22A = (22B, XXX)\n"
                + "22B = (22C, 22C)\n"
                + "22C = (22Z, 22Z)\n"
                + "22Z = (22B, 22B)\n"
                + "XXX = (XXX, XXX)");
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(6L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        String[] linesArray = lines.toArray(String[]::new);
        char[] instructions = linesArray[0].toCharArray();

        Map<String, Entry<String, String>> network = new HashMap<>(linesArray.length - 2);
        for (int i = 2; i < linesArray.length; i++) {
            var tokens = new StringTokenizer(linesArray[i], " ");
            String node = tokens.nextToken();
            tokens.nextToken();
            String left = tokens.nextToken().replace("(", "").replace(",", "");
            String right = tokens.nextToken().replace(")", "");
            network.put(node, new SimpleImmutableEntry<>(left, right));
        }

        String curNode = "AAA";
        int steps = 0;
        int instruction = -1;
        while (!"ZZZ".equals(curNode)) {
            ++steps;
            char step = instructions[++instruction % instructions.length];
            if (step == 'L') {
                curNode = network.get(curNode).getKey();
            } else {
                curNode = network.get(curNode).getValue();
            }
        }

        return steps;
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        String[] linesArray = lines.toArray(String[]::new);
        char[] instructions = linesArray[0].toCharArray();

        Map<String, Entry<String, String>> network = new HashMap<>(linesArray.length - 2);
        for (int i = 2; i < linesArray.length; i++) {
            var tokens = new StringTokenizer(linesArray[i], " ");
            String node = tokens.nextToken();
            tokens.nextToken();
            String left = tokens.nextToken().replace("(", "").replace(",", "");
            String right = tokens.nextToken().replace(")", "");
            network.put(node, new SimpleImmutableEntry<>(left, right));
        }

        var oneNodeDistances = network.keySet().parallelStream()
                .filter(node -> node.endsWith("A"))
                .mapToLong(startNode -> {
                    var curNode = startNode;
                    long steps = 0;
                    while (!curNode.endsWith("Z")) {
                        char step = instructions[(int) (steps % instructions.length)];
                        ++steps;
                        if (step == 'L') {
                            curNode = network.get(curNode).getKey();
                        } else {
                            curNode = network.get(curNode).getValue();
                        }
                    }
                    return steps;
                });

        return lcm(oneNodeDistances);
    }
}
