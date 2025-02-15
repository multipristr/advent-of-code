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
                        "#################",
                "###########################\n" +
                        "#######################..E#\n" +
                        "######################..#.#\n" +
                        "#####################..##.#\n" +
                        "####################..###.#\n" +
                        "###################..##...#\n" +
                        "##################..###.###\n" +
                        "#################..####...#\n" +
                        "################..#######.#\n" +
                        "###############..##.......#\n" +
                        "##############..###.#######\n" +
                        "#############..####.......#\n" +
                        "############..###########.#\n" +
                        "###########..##...........#\n" +
                        "##########..###.###########\n" +
                        "#########..####...........#\n" +
                        "########..###############.#\n" +
                        "#######..##...............#\n" +
                        "######..###.###############\n" +
                        "#####..####...............#\n" +
                        "####..###################.#\n" +
                        "###..##...................#\n" +
                        "##..###.###################\n" +
                        "#..####...................#\n" +
                        "#.#######################.#\n" +
                        "#S........................#\n" +
                        "###########################",
                "####################################################\n" +
                        "#......................................#..........E#\n" +
                        "#......................................#...........#\n" +
                        "#....................#.................#...........#\n" +
                        "#....................#.................#...........#\n" +
                        "#....................#.................#...........#\n" +
                        "#....................#.................#...........#\n" +
                        "#....................#.................#...........#\n" +
                        "#....................#.................#...........#\n" +
                        "#....................#.................#...........#\n" +
                        "#....................#.................#...........#\n" +
                        "#....................#.............................#\n" +
                        "#S...................#.............................#\n" +
                        "####################################################",
                "########################################################\n" +
                        "#.........#.........#.........#.........#.........#...E#\n" +
                        "#.........#.........#.........#.........#.........#....#\n" +
                        "#....#....#....#....#....#....#....#....#....#....#....#\n" +
                        "#....#....#....#....#....#....#....#....#....#....#....#\n" +
                        "#....#....#....#....#....#....#....#....#....#....#....#\n" +
                        "#....#....#....#....#....#....#....#....#....#....#....#\n" +
                        "#....#.........#.........#.........#.........#.........#\n" +
                        "#S...#.........#.........#.........#.........#.........#\n" +
                        "########################################################",
                "##########################################################################################################\n" +
                        "#.........#.........#.........#.........#.........#.........#.........#.........#.........#.........#...E#\n" +
                        "#.........#.........#.........#.........#.........#.........#.........#.........#.........#.........#....#\n" +
                        "#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#\n" +
                        "#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#\n" +
                        "#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#\n" +
                        "#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#....#\n" +
                        "#....#.........#.........#.........#.........#.........#.........#.........#.........#.........#.........#\n" +
                        "#S...#.........#.........#.........#.........#.........#.........#.........#.........#.........#.........#\n" +
                        "##########################################################################################################",
                "#######E#######\n" +
                        "#...#...#######\n" +
                        "#.#...#.......#\n" +
                        "#.###########.#\n" +
                        "#S............#\n" +
                        "###############"
        );
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(7036L, 11048L, 21148L, 5078L, 21110L, 41210L, 3022);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(45L, 64L, 149L, 413L, 264L, 514L, 23);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
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

        var startMove = new Move(endTileX, endTileY, startTileX, startTileY, 1, 0, 0);
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
                var nextStep = new Move(endTileX, endTileY,
                        current.getX() + current.getDirectionX(), current.getY() + current.getDirectionY(),
                        current.getDirectionX(), current.getDirectionY(), current.getScore() + 1);
                if (nextStep.getScore() < closed.getOrDefault(nextStep, Integer.MAX_VALUE)) {
                    closed.put(nextStep, nextStep.getScore());
                    open.add(nextStep);
                }
            }
            if (current.getDirectionX() == 0) {
                if (isStepToEmpty(map, current.getX() - 1, current.getY())) {
                    var leftTurn = new Move(endTileX, endTileY,
                            current.getX() - 1, current.getY(),
                            -1, 0, current.getScore() + 1_001);
                    if (leftTurn.getScore() < closed.getOrDefault(leftTurn, Integer.MAX_VALUE)) {
                        closed.put(leftTurn, leftTurn.getScore());
                        open.add(leftTurn);
                    }
                }
                if (isStepToEmpty(map, current.getX() + 1, current.getY())) {
                    var rightTurn = new Move(endTileX, endTileY,
                            current.getX() + 1, current.getY(),
                            1, 0, current.getScore() + 1_001);
                    if (rightTurn.getScore() < closed.getOrDefault(rightTurn, Integer.MAX_VALUE)) {
                        closed.put(rightTurn, rightTurn.getScore());
                        open.add(rightTurn);
                    }
                }
            } else {
                if (isStepToEmpty(map, current.getX(), current.getY() - 1)) {
                    var upTurn = new Move(endTileX, endTileY,
                            current.getX(), current.getY() - 1,
                            0, -1, current.getScore() + 1_001);
                    if (upTurn.getScore() < closed.getOrDefault(upTurn, Integer.MAX_VALUE)) {
                        closed.put(upTurn, upTurn.getScore());
                        open.add(upTurn);
                    }
                }
                if (isStepToEmpty(map, current.getX(), current.getY() + 1)) {
                    var downTurn = new Move(endTileX, endTileY,
                            current.getX(), current.getY() + 1,
                            0, 1, current.getScore() + 1_001);
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
    public Comparable<?> solvePartTwo(Stream<String> lines) {
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
        var remainingTiles = new ArrayDeque<MoveWithVisited>();

        var startMove = new MoveWithVisited(endTileX, endTileY, startTileX, startTileY, 1, 0, 0, null);
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
                remainingTiles.addLast(current);
                continue;
            }

            if (isStepToEmpty(map, current.getX() + current.getDirectionX(), current.getY() + current.getDirectionY())) {
                var nextStep = new MoveWithVisited(endTileX, endTileY,
                        current.getX() + current.getDirectionX(), current.getY() + current.getDirectionY(),
                        current.getDirectionX(), current.getDirectionY(), current.getScore() + 1, current);
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
                    var leftTurn = new MoveWithVisited(endTileX, endTileY,
                            current.getX() - 1, current.getY(),
                            -1, 0, current.getScore() + 1_001, current);
                    if (leftTurn.getScore() < closed.getOrDefault(leftTurn, Integer.MAX_VALUE)) {
                        closed.put(leftTurn, leftTurn.getScore());
                        open.add(leftTurn);
                        bestTiles.put(leftTurn, new ArrayList<>(List.of(leftTurn.getPrevious())));
                    } else if (leftTurn.getScore() == closed.getOrDefault(leftTurn, Integer.MAX_VALUE)) {
                        bestTiles.get(leftTurn).add(leftTurn.getPrevious());
                    }
                }
                if (isStepToEmpty(map, current.getX() + 1, current.getY())) {
                    var rightTurn = new MoveWithVisited(endTileX, endTileY,
                            current.getX() + 1, current.getY(),
                            1, 0, current.getScore() + 1_001, current);
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
                    var upTurn = new MoveWithVisited(endTileX, endTileY,
                            current.getX(), current.getY() - 1,
                            0, -1, current.getScore() + 1_001, current);
                    if (upTurn.getScore() < closed.getOrDefault(upTurn, Integer.MAX_VALUE)) {
                        closed.put(upTurn, upTurn.getScore());
                        open.add(upTurn);
                        bestTiles.put(upTurn, new ArrayList<>(List.of(upTurn.getPrevious())));
                    } else if (upTurn.getScore() == closed.getOrDefault(upTurn, Integer.MAX_VALUE)) {
                        bestTiles.get(upTurn).add(upTurn.getPrevious());
                    }
                }
                if (isStepToEmpty(map, current.getX(), current.getY() + 1)) {
                    var downTurn = new MoveWithVisited(endTileX, endTileY,
                            current.getX(), current.getY() + 1,
                            0, 1, current.getScore() + 1_001, current);
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

        var bestPathTiles = new HashSet<Map.Entry<Integer, Integer>>();
        var closed2 = new HashSet<>(remainingTiles);
        while (!remainingTiles.isEmpty()) {
            var current = remainingTiles.pollFirst();
            bestPathTiles.add(new AbstractMap.SimpleImmutableEntry<>(current.getX(), current.getY()));
            for (var visited : bestTiles.getOrDefault(current, List.of())) {
                if (closed2.add(visited)) {
                    remainingTiles.add(visited);
                }
            }
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

    private static final class MoveWithVisited extends Move {

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
            remainingDistance = Math.abs(x - endTileX) + Math.abs(endTileY - y);
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
            return (score + remainingDistance) - (that.score + that.remainingDistance);
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
