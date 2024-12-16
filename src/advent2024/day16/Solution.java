package src.advent2024.day16;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
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

        Move startMove = new Move(endTileX, endTileY, startTileX, startTileY, 1, 0, 0);
        var open = new PriorityQueue<Move>();
        open.add(startMove);
        Set<Move> closed = new HashSet<>();
        closed.add(startMove);
        while (!open.isEmpty()) {
            var current = open.poll();
            if (current.getX() == endTileX && current.getY() == endTileY) {
                return current.getScore();
            }

            if (isStepToEmpty(map, current.getX() + current.getDirectionX(), current.getY() + current.getDirectionY())) {
                var nextStep = new Move(endTileX, endTileY,
                        current.getX() + current.getDirectionX(), current.getY() + current.getDirectionY(),
                        current.getDirectionX(), current.getDirectionY(),
                        current.getScore() + 1);
                if (closed.add(nextStep)) {
                    open.add(nextStep);
                }
            }
            if (current.getDirectionX() == 0) {
                var leftTurn = new Move(endTileX, endTileY,
                        current.getX(), current.getY(),
                        -1, 0,
                        current.getScore() + 1_000);
                if (closed.add(leftTurn)) {
                    open.add(leftTurn);
                }
                var rightTurn = new Move(endTileX, endTileY,
                        current.getX(), current.getY(),
                        1, 0,
                        current.getScore() + 1_000);
                if (closed.add(rightTurn)) {
                    open.add(rightTurn);
                }
            } else {
                var upTurn = new Move(endTileX, endTileY,
                        current.getX(), current.getY(),
                        0, -1,
                        current.getScore() + 1_000);
                if (closed.add(upTurn)) {
                    open.add(upTurn);
                }
                var downTurn = new Move(endTileX, endTileY,
                        current.getX(), current.getY(),
                        0, 1,
                        current.getScore() + 1_000);
                if (closed.add(downTurn)) {
                    open.add(downTurn);
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

        MoveWithVisited startMove = new MoveWithVisited(endTileX, endTileY, startTileX, startTileY, 1, 0, 0, null);
        var open = new PriorityQueue<MoveWithVisited>();
        open.add(startMove);
        Set<MoveWithVisited> closed = new HashSet<>();
        closed.add(startMove);

        int bestScore = Integer.MAX_VALUE;
        var bestTiles = new HashSet<Map.Entry<Integer, Integer>>();

        while (!open.isEmpty()) {
            var current = open.poll();
            if (current.getX() == endTileX && current.getY() == endTileY) {
                if (current.getScore() > bestScore) {
                    break;
                }
                bestScore = current.getScore();
                var cur = current;
                while (cur != null) {
                    bestTiles.add(new AbstractMap.SimpleImmutableEntry<>(cur.getX(), cur.getY()));
                    cur = cur.getPrevious();
                }
            }

            if (isStepToEmpty(map, current.getX() + current.getDirectionX(), current.getY() + current.getDirectionY())) {
                var nextStep = new MoveWithVisited(endTileX, endTileY,
                        current.getX() + current.getDirectionX(), current.getY() + current.getDirectionY(),
                        current.getDirectionX(), current.getDirectionY(),
                        current.getScore() + 1, current);
                if (closed.add(nextStep)) {
                    open.add(nextStep);
                }
            }
            if (current.getDirectionX() == 0) {
                var leftTurn = new MoveWithVisited(endTileX, endTileY,
                        current.getX(), current.getY(),
                        -1, 0,
                        current.getScore() + 1_000, current);
                if (closed.add(leftTurn)) {
                    open.add(leftTurn);
                }
                var rightTurn = new MoveWithVisited(endTileX, endTileY,
                        current.getX(), current.getY(),
                        1, 0,
                        current.getScore() + 1_000, current);
                if (closed.add(rightTurn)) {
                    open.add(rightTurn);
                }
            } else {
                var upTurn = new MoveWithVisited(endTileX, endTileY,
                        current.getX(), current.getY(),
                        0, -1,
                        current.getScore() + 1_000, current);
                if (closed.add(upTurn)) {
                    open.add(upTurn);
                }
                var downTurn = new MoveWithVisited(endTileX, endTileY,
                        current.getX(), current.getY(),
                        0, 1,
                        current.getScore() + 1_000, current);
                if (closed.add(downTurn)) {
                    open.add(downTurn);
                }
            }
        }

        return bestTiles.size();
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

        private MoveWithVisited(int endTileX, int endTileY, int x, int y, int directionX, int directionY, int score, MoveWithVisited previous) {
            super(endTileX, endTileY, x, y, directionX, directionY, score);
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
        private final int remainingDistance;

        private Move(int endTileX, int endTileY, int x, int y, int directionX, int directionY, int score) {
            this.y = y;
            this.x = x;
            this.directionY = directionY;
            this.directionX = directionX;
            this.score = score;
            remainingDistance = Math.abs(x - endTileX) + Math.abs(y - endTileY);
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
            return (score) - (that.score);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Move move = (Move) o;
            return x == move.x && y == move.y && directionX == move.directionX && directionY == move.directionY && score == move.score;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, directionX, directionY, score);
        }

    }

}
