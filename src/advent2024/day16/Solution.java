package src.advent2024.day16;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of(
                "###############\n" +
                        "#.......#....E#\n" +
                        "#.#.###.#.###.#\n" +
                        "#.....#.#...#.#\n" +
                        "#.###.#####.#.#\n" +
                        "#.#.#.......#.#\n" +
                        "#.#.#####.###.#\n" +
                        "#...........#.#\n" +
                        "###.#.#####.#.#\n" +
                        "#...#.....#.#.#\n" +
                        "#.#.#.###.#.#.#\n" +
                        "#.....#...#.#.#\n" +
                        "#.###.#.#.#.#.#\n" +
                        "#S..#.....#...#\n" +
                        "###############",
                "#################\n" +
                        "#...#...#...#..E#\n" +
                        "#.#.#.#.#.#.#.#.#\n" +
                        "#.#.#.#...#...#.#\n" +
                        "#.#.#.#.###.#.#.#\n" +
                        "#...#.#.#.....#.#\n" +
                        "#.#.#.#.#.#####.#\n" +
                        "#.#...#.#.#.....#\n" +
                        "#.#.#####.#.###.#\n" +
                        "#.#.#.......#...#\n" +
                        "#.#.###.#####.###\n" +
                        "#.#.#...#.....#.#\n" +
                        "#.#.#.#####.###.#\n" +
                        "#.#.#.........#.#\n" +
                        "#.#.#.#########.#\n" +
                        "#S#.............#\n" +
                        "#################"
        );
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(7036L, 11048L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(45L, 64L);
    }

    @Override
    public long solvePartOne(Stream<String> lines) {
        char[][] map = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        int startTileX = 0;
        int startTileY = 0;
        int endTileX = 0;
        int endTileY = 0;
        for (int y = 0; y < map.length; y++) {
            char[] tile = map[y];
            for (int x = 0; x < tile.length; x++) {
                char mark = tile[x];
                if (mark == 'S') {
                    startTileX = x;
                    startTileY = y;
                } else if (mark == 'E') {
                    endTileX = x;
                    endTileY = y;
                }
            }
        }

        var startMove = new Move(startTileX, startTileY, 1, 0, 0);
        var open = new PriorityQueue<Move>();
        open.add(startMove);
        var closed = new HashMap<Move, Integer>();
        closed.put(startMove, startMove.getScore());
        while (!open.isEmpty()) {
            var current = open.poll();
            if (current.getX() == endTileX && current.getY() == endTileY) {
                return current.getScore();
            }

            if (isStepToEmpty(map, current.getX() + current.getDirectionX(), current.getY() + current.getDirectionY())) {
                var nextStep = new Move(
                        current.getX() + current.getDirectionX(), current.getY() + current.getDirectionY(),
                        current.getDirectionX(), current.getDirectionY(),
                        current.getScore() + 1);
                if (nextStep.getScore() < closed.getOrDefault(nextStep, Integer.MAX_VALUE)) {
                    closed.put(nextStep, nextStep.getScore());
                    open.add(nextStep);
                }
            }
            if (current.getDirectionX() == 0) {
                if (isStepToEmpty(map, current.getX() - 1, current.getY())) {
                    var leftTurn = new Move(
                            current.getX() - 1, current.getY(),
                            -1, 0,
                            current.getScore() + 1_001);
                    if (leftTurn.getScore() < closed.getOrDefault(leftTurn, Integer.MAX_VALUE)) {
                        closed.put(leftTurn, leftTurn.getScore());
                        open.add(leftTurn);
                    }
                }
                if (isStepToEmpty(map, current.getX() + 1, current.getY())) {
                    var rightTurn = new Move(
                            current.getX() + 1, current.getY(),
                            1, 0,
                            current.getScore() + 1_001);
                    if (rightTurn.getScore() < closed.getOrDefault(rightTurn, Integer.MAX_VALUE)) {
                        closed.put(rightTurn, rightTurn.getScore());
                        open.add(rightTurn);
                    }
                }
            } else {
                if (isStepToEmpty(map, current.getX(), current.getY() - 1)) {
                    var upTurn = new Move(
                            current.getX(), current.getY() - 1,
                            0, -1,
                            current.getScore() + 1_001);
                    if (upTurn.getScore() < closed.getOrDefault(upTurn, Integer.MAX_VALUE)) {
                        closed.put(upTurn, upTurn.getScore());
                        open.add(upTurn);
                    }
                }
                if (isStepToEmpty(map, current.getX(), current.getY() + 1)) {
                    var downTurn = new Move(
                            current.getX(), current.getY() + 1,
                            0, 1,
                            current.getScore() + 1_001);
                    if (downTurn.getScore() < closed.getOrDefault(downTurn, Integer.MAX_VALUE)) {
                        closed.put(downTurn, downTurn.getScore());
                        open.add(downTurn);
                    }
                }
            }
        }

        throw new IllegalStateException("No path found for:" + System.lineSeparator() + Arrays.stream(map).map(Arrays::toString).collect(Collectors.joining(System.lineSeparator())));
    }

    @Override
    public long solvePartTwo(Stream<String> lines) {
        char[][] map = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        int startTileX = 0;
        int startTileY = 0;
        int endTileX = 0;
        int endTileY = 0;
        for (int y = 0; y < map.length; y++) {
            char[] tile = map[y];
            for (int x = 0; x < tile.length; x++) {
                char mark = tile[x];
                if (mark == 'S') {
                    startTileX = x;
                    startTileY = y;
                } else if (mark == 'E') {
                    endTileX = x;
                    endTileY = y;
                }
            }
        }

        int bestScore = Integer.MAX_VALUE;
        var bestTiles = new HashMap<MoveWithVisited, List<MoveWithVisited>>();

        var startMove = new MoveWithVisited(startTileX, startTileY, 1, 0, 0, null);
        var open = new PriorityQueue<MoveWithVisited>();
        open.add(startMove);
        var closed = new HashMap<MoveWithVisited, Integer>();
        closed.put(startMove, startMove.getScore());
        while (!open.isEmpty()) {
            var current = open.poll();
            if (current.getScore() > bestScore) {
                break;
            }
            if (current.getX() == endTileX && current.getY() == endTileY) {
                bestScore = current.getScore();
                continue;
            }

            if (isStepToEmpty(map, current.getX() + current.getDirectionX(), current.getY() + current.getDirectionY())) {
                var nextStep = new MoveWithVisited(
                        current.getX() + current.getDirectionX(), current.getY() + current.getDirectionY(),
                        current.getDirectionX(), current.getDirectionY(),
                        current.getScore() + 1, current);
                if (nextStep.getScore() < closed.getOrDefault(nextStep, Integer.MAX_VALUE)) {
                    closed.put(nextStep, nextStep.getScore());
                    open.add(nextStep);
                    bestTiles.put(nextStep, new ArrayList<>(List.of(nextStep.getPrevious())));
                } else if (nextStep.getScore() == closed.getOrDefault(nextStep, Integer.MAX_VALUE)) {
                    bestTiles.get(nextStep).add(nextStep.getPrevious());
                }
            }
            if (current.getDirectionX() == 0) {
                if (isStepToEmpty(map, current.getX() - 1, current.getY())) {
                    var leftTurn = new MoveWithVisited(
                            current.getX() - 1, current.getY(),
                            -1, 0,
                            current.getScore() + 1_001, current);
                    if (leftTurn.getScore() < closed.getOrDefault(leftTurn, Integer.MAX_VALUE)) {
                        closed.put(leftTurn, leftTurn.getScore());
                        open.add(leftTurn);
                        bestTiles.put(leftTurn, new ArrayList<>(List.of(leftTurn.getPrevious())));
                    } else if (leftTurn.getScore() == closed.getOrDefault(leftTurn, Integer.MAX_VALUE)) {
                        bestTiles.get(leftTurn).add(leftTurn.getPrevious());
                    }
                }
                if (isStepToEmpty(map, current.getX() + 1, current.getY())) {
                    var rightTurn = new MoveWithVisited(
                            current.getX() + 1, current.getY(),
                            1, 0,
                            current.getScore() + 1_001, current);
                    if (rightTurn.getScore() < closed.getOrDefault(rightTurn, Integer.MAX_VALUE)) {
                        closed.put(rightTurn, rightTurn.getScore());
                        open.add(rightTurn);
                        bestTiles.put(rightTurn, new ArrayList<>(List.of(rightTurn.getPrevious())));
                    } else if (rightTurn.getScore() == closed.getOrDefault(rightTurn, Integer.MAX_VALUE)) {
                        bestTiles.get(rightTurn).add(rightTurn.getPrevious());
                    }
                }
            } else {
                if (isStepToEmpty(map, current.getX(), current.getY() - 1)) {
                    var upTurn = new MoveWithVisited(
                            current.getX(), current.getY() - 1,
                            0, -1,
                            current.getScore() + 1_001, current);
                    if (upTurn.getScore() < closed.getOrDefault(upTurn, Integer.MAX_VALUE)) {
                        closed.put(upTurn, upTurn.getScore());
                        open.add(upTurn);
                        bestTiles.put(upTurn, new ArrayList<>(List.of(upTurn.getPrevious())));
                    } else if (upTurn.getScore() == closed.getOrDefault(upTurn, Integer.MAX_VALUE)) {
                        bestTiles.get(upTurn).add(upTurn.getPrevious());
                    }
                }
                if (isStepToEmpty(map, current.getX(), current.getY() + 1)) {
                    var downTurn = new MoveWithVisited(
                            current.getX(), current.getY() + 1,
                            0, 1,
                            current.getScore() + 1_001, current);
                    if (downTurn.getScore() < closed.getOrDefault(downTurn, Integer.MAX_VALUE)) {
                        closed.put(downTurn, downTurn.getScore());
                        open.add(downTurn);
                        bestTiles.put(downTurn, new ArrayList<>(List.of(downTurn.getPrevious())));
                    } else if (downTurn.getScore() == closed.getOrDefault(downTurn, Integer.MAX_VALUE)) {
                        bestTiles.get(downTurn).add(downTurn.getPrevious());
                    }
                }
            }
        }

        int finalEndTileX = endTileX;
        int finalEndTileY = endTileY;
        var remainingTiles = bestTiles.keySet()
                .stream()
                .filter(moveWithVisited -> moveWithVisited.getX() == finalEndTileX && moveWithVisited.getY() == finalEndTileY)
                .collect(Collectors.toCollection(ArrayDeque::new));
        var bestPathTiles = new HashSet<Map.Entry<Integer, Integer>>();
        while (!remainingTiles.isEmpty()) {
            var current = remainingTiles.pollFirst();
            bestPathTiles.add(new AbstractMap.SimpleImmutableEntry<>(current.getX(), current.getY()));
            remainingTiles.addAll(bestTiles.getOrDefault(current, List.of()));
        }
        return bestPathTiles.size();
    }

    private boolean isStepToEmpty(char[][] map, int x, int y) {
        if (y < 0 || y >= map.length || x < 0) {
            return false;
        }
        char[] tile = map[y];
        return x < tile.length && tile[x] != '#';
    }

    private static class MoveWithVisited extends Move {

        private final MoveWithVisited previous;

        private MoveWithVisited(int x, int y, int directionX, int directionY, int score, MoveWithVisited previous) {
            super(x, y, directionX, directionY, score);
            this.previous = previous;
        }

        public MoveWithVisited getPrevious() {
            return previous;
        }

    }

    private static class Move implements Comparable<Move> {

        private final int x;
        private final int y;
        private final int directionX;
        private final int directionY;
        private final int score;

        private Move(int x, int y, int directionX, int directionY, int score) {
            this.y = y;
            this.x = x;
            this.directionY = directionY;
            this.directionX = directionX;
            this.score = score;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getDirectionX() {
            return directionX;
        }

        public int getDirectionY() {
            return directionY;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(Move that) {
            return score - that.score;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Move move = (Move) o;
            return x == move.x && y == move.y && directionX == move.directionX && directionY == move.directionY;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, directionX, directionY);
        }

    }

}
