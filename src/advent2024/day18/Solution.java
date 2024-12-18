package src.advent2024.day18;

import src.PuzzleSolver;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("5,4\n" +
                "4,2\n" +
                "4,5\n" +
                "3,0\n" +
                "2,1\n" +
                "6,3\n" +
                "2,4\n" +
                "1,5\n" +
                "0,6\n" +
                "3,3\n" +
                "2,6\n" +
                "5,1\n" +
                "1,2\n" +
                "5,5\n" +
                "2,5\n" +
                "6,5\n" +
                "1,4\n" +
                "0,4\n" +
                "6,4\n" +
                "1,1\n" +
                "6,1\n" +
                "1,0\n" +
                "0,5\n" +
                "1,6\n" +
                "2,0");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(22L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(61L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        byte coordinatesRange = 70;
        short fallenBytes = 1024;

        var memorySpace = new boolean[coordinatesRange + 1][coordinatesRange + 1];
        lines.limit(fallenBytes)
                .map(line -> line.split(","))
                .forEach(bytes -> memorySpace[Integer.parseInt(bytes[1])][Integer.parseInt(bytes[0])] = true);
        return calculateMinimumSteps(memorySpace).orElseThrow();
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        byte coordinatesRange = 70;

        var memorySpace = new boolean[coordinatesRange + 1][coordinatesRange + 1];
        for (String line : lines.toArray(String[]::new)) {
            int commaPosition = line.indexOf(",");
            int byteX = Integer.parseInt(line.substring(0, commaPosition));
            int byteY = Integer.parseInt(line.substring(commaPosition + 1));
            memorySpace[byteY][byteX] = true;
            if (calculateMinimumSteps(memorySpace).isEmpty()) {
                System.out.println(line);
                return byteX * 10L + byteY;
            }
        }

        throw new IllegalStateException("Exit is always reachable with coordinates range " + coordinatesRange);
    }

    private Optional<Integer> calculateMinimumSteps(boolean[][] memorySpace) {
        byte[] directions = {-1, 0, 0, -1, 0, 1, 1, 0};

        var closed = Arrays.stream(memorySpace).map(boolean[]::clone).toArray(boolean[][]::new);
        closed[0][0] = true;
        Queue<Step> open = new ArrayDeque<>();
        open.add(new Step(0, 0, 0));
        while (!open.isEmpty()) {
            var current = open.poll();
            for (byte i = 1; i < directions.length; i += 2) {
                var nextY = current.getY() + directions[i - 1];
                if (nextY < 0 || nextY >= memorySpace.length) {
                    continue;
                }
                var nextX = current.getX() + directions[i];
                if (nextX < 0 || nextX >= memorySpace[nextY].length || closed[nextY][nextX]) {
                    continue;
                }
                if (nextX == memorySpace.length - 1 && nextY == memorySpace[nextY].length - 1) {
                    return Optional.of(current.getPathSteps() + 1);
                }
                closed[nextY][nextX] = true;
                open.add(new Step(nextX, nextY, current.getPathSteps() + 1));
            }
        }
        return Optional.empty();
    }

    private static final class Step {

        private final int x;
        private final int y;
        private final int pathSteps;

        private Step(int x, int y, int pathSteps) {
            this.x = x;
            this.y = y;
            this.pathSteps = pathSteps;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getPathSteps() {
            return pathSteps;
        }

    }

}
