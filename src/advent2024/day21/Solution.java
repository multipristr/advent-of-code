package src.advent2024.day21;

import src.PuzzleSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("029A\n" +
                "980A\n" +
                "179A\n" +
                "456A\n" +
                "379A");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(126384L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(154115708116294L);
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        return calculateSumOfComplexities(lines, 2);
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        return calculateSumOfComplexities(lines, 25);
    }

    private long calculateSumOfComplexities(Stream<String> lines, int directionalKeypads) {
        char[][] numericKeypad = {
                {'7', '8', '9'},
                {'4', '5', '6'},
                {'1', '2', '3'},
                {' ', '0', 'A'},
        };
        var numericKeypadSequences = populateAllSequences(numericKeypad);

        char[][] directionalKeypad = {
                {' ', '^', 'A'},
                {'<', 'v', '>'},
        };
        var directionalKeypadSequences = populateAllSequences(directionalKeypad);

        var memory = IntStream.rangeClosed(0, directionalKeypads).mapToObj(i -> new HashMap<String, Long>()).toArray(Map[]::new);
        return lines.mapToLong(line -> {
                    char from = 'A';
                    long sequenceLength = 0;
                    for (int i = 0; i < line.length(); i++) {
                        char to = line.charAt(i);
                        sequenceLength += numericKeypadSequences.get(from).get(to).stream()
                                .mapToLong(s -> calculateShortestSequenceLength(memory, directionalKeypadSequences, s, directionalKeypads))
                                .min().orElseThrow();
                        from = to;
                    }
                    return sequenceLength * Long.parseLong(line.replace("A", ""));
                })
                .sum();
    }

    private long calculateShortestSequenceLength(Map<String, Long>[] memory, Map<Character, Map<Character, List<String>>> directionalKeypadSequences,
                                                 String sequence, int remainingDirectionalKeypads) {
        if (remainingDirectionalKeypads == 0) {
            return sequence.length();
        }
        var keypadMemory = memory[remainingDirectionalKeypads];
        var memorizedSequenceLength = keypadMemory.get(sequence);
        if (memorizedSequenceLength != null) {
            return memorizedSequenceLength;
        }

        char from = 'A';
        long sequenceLength = 0;
        for (int i = 0; i < sequence.length(); i++) {
            char to = sequence.charAt(i);
            sequenceLength += directionalKeypadSequences.get(from).get(to).stream()
                    .mapToLong(s -> calculateShortestSequenceLength(memory, directionalKeypadSequences, s, remainingDirectionalKeypads - 1))
                    .min().orElseThrow();
            from = to;
        }
        keypadMemory.put(sequence, sequenceLength);
        return sequenceLength;
    }

    private Map<Character, Map<Character, List<String>>> populateAllSequences(char[][] keypad) {
        Map<Character, Map<Character, List<String>>> keypadSequences = new HashMap<>();
        for (int y1 = 0; y1 < keypad.length; y1++) {
            char[] row1 = keypad[y1];
            for (int x1 = 0; x1 < row1.length; x1++) {
                char fromButton = row1[x1];
                if (fromButton == ' ') {
                    continue;
                }
                Map<Character, List<String>> toSequences = new HashMap<>();
                for (int y2 = 0; y2 < keypad.length; y2++) {
                    char[] row2 = keypad[y2];
                    for (int x2 = 0; x2 < row2.length; x2++) {
                        char toButton = row2[x2];
                        if (toButton != ' ') {
                            var shortestSequences = findShortestSequences(keypad, x1, y1, x2, y2);
                            toSequences.put(toButton, shortestSequences);
                        }
                    }
                }
                keypadSequences.put(fromButton, toSequences);
            }
        }
        return keypadSequences;
    }

    private List<String> findShortestSequences(char[][] keypad, int startX, int startY, int endX, int endY) {
        var closed = new long[keypad.length][keypad[0].length];
        closed[startY][startX] = -1;
        var startMove = new Press(endX, endY, startX, startY, "");
        Queue<Press> open = new PriorityQueue<>();
        open.add(startMove);
        List<String> shortestSequences = new ArrayList<>();
        while (!open.isEmpty()) {
            var current = open.poll();
            if (current.getX() == endX && current.getY() == endY) {
                shortestSequences.add(current.getSequence() + 'A');
            }

            if (isButton(keypad, current.getX(), current.getY() - 1)
                    && (closed[current.getY() - 1][current.getX()] == 0 || closed[current.getY() - 1][current.getX()] >= current.getSequence().length() + 1)) {
                Press next = new Press(endX, endY, current.getX(), current.getY() - 1, current.getSequence() + '^');
                closed[next.getY()][next.getX()] = next.getSequence().length();
                open.add(next);
            }
            if (isButton(keypad, current.getX() - 1, current.getY())
                    && (closed[current.getY()][current.getX() - 1] == 0 || closed[current.getY()][current.getX() - 1] >= current.getSequence().length() + 1)) {
                Press next = new Press(endX, endY, current.getX() - 1, current.getY(), current.getSequence() + '<');
                closed[next.getY()][next.getX()] = next.getSequence().length();
                open.add(next);
            }
            if (isButton(keypad, current.getX() + 1, current.getY())
                    && (closed[current.getY()][current.getX() + 1] == 0 || closed[current.getY()][current.getX() + 1] >= current.getSequence().length() + 1)) {
                Press next = new Press(endX, endY, current.getX() + 1, current.getY(), current.getSequence() + '>');
                closed[next.getY()][next.getX()] = next.getSequence().length();
                open.add(next);
            }
            if (isButton(keypad, current.getX(), current.getY() + 1)
                    && (closed[current.getY() + 1][current.getX()] == 0 || closed[current.getY() + 1][current.getX()] >= current.getSequence().length() + 1)) {
                Press next = new Press(endX, endY, current.getX(), current.getY() + 1, current.getSequence() + 'v');
                closed[next.getY()][next.getX()] = next.getSequence().length();
                open.add(next);
            }
        }

        return shortestSequences;
    }

    private boolean isButton(char[][] keypad, int x, int y) {
        return y >= 0 && y < keypad.length && x >= 0 && x < keypad[y].length && keypad[y][x] != ' ';
    }

    private static final class Press implements Comparable<Press> {

        private final int x;
        private final int y;
        private final String sequence;
        private final int remainingDistance;

        private Press(int endX, int endY, int x, int y, String sequence) {
            this.y = y;
            this.x = x;
            this.sequence = sequence;
            remainingDistance = Math.abs(x - endX) + Math.abs(endY - y);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public String getSequence() {
            return sequence;
        }

        @Override
        public int compareTo(Press o) {
            return (sequence.length() + remainingDistance) - (o.sequence.length() + o.remainingDistance);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Press press = (Press) o;
            return x == press.x && y == press.y && Objects.equals(sequence, press.sequence);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, sequence);
        }
    }

}
