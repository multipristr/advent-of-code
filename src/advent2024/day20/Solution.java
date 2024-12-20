package src.advent2024.day20;

import src.PuzzleSolver;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("###############\n" +
                "#...#...#.....#\n" +
                "#.#.#.#.#.###.#\n" +
                "#S#...#.#.#...#\n" +
                "#######.#.#.###\n" +
                "#######.#.#...#\n" +
                "#######.#.###.#\n" +
                "###..E#...#...#\n" +
                "###.#######.###\n" +
                "#...###...#...#\n" +
                "#.#####.#.###.#\n" +
                "#.#...#.#.#...#\n" +
                "#.#.#.#.#.#.###\n" +
                "#...#...#...###\n" +
                "###############");
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(0);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(0);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        char[][] racetrackMap = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;
        for (int y = 0; y < racetrackMap.length; y++) {
            char[] row = racetrackMap[y];
            for (int x = 0; x < row.length; x++) {
                char mark = row[x];
                if (mark == 'S') {
                    startX = x;
                    startY = y;
                } else if (mark == 'E') {
                    endX = x;
                    endY = y;
                }
            }
        }

        int finalStartX = startX;
        int finalStartY = startY;
        int finalEndX = endX;
        int finalEndY = endY;
        return findPathFasterThan(racetrackMap, startX, startY, endX, endY, Integer.MAX_VALUE)
                .stream()
                .mapToInt(fastestMove -> {
                    int timeLimit = fastestMove.getPicoseconds() - 100;
                    int savingCheats = 0;
                    for (char[] row : racetrackMap) {
                        for (int x = 0; x < row.length; x++) {
                            if (row[x] == '#') {
                                row[x] = '.';
                                if (findPathFasterThan(racetrackMap, finalStartX, finalStartY, finalEndX, finalEndY, timeLimit).isPresent()) {
                                    ++savingCheats;
                                }
                                row[x] = '#';
                            }
                        }
                    }
                    return savingCheats;
                })
                .findAny().orElseThrow();
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Optional<Move> findPathFasterThan(char[][] racetrackMap, int startX, int startY, int endX, int endY, int timeLimit) {
        byte[] directions = {-1, 0, 0, -1, 0, 1, 1, 0};
        boolean[][] closed = new boolean[racetrackMap.length][racetrackMap[0].length];
        closed[startY][startX] = true;
        var startMove = new Move(endX, endY, startX, startY, null, 0);
        Queue<Move> open = new PriorityQueue<>();
        open.add(startMove);
        while (!open.isEmpty()) {
            var current = open.poll();
            if (current.getX() == endX && current.getY() == endY) {
                return Optional.of(current);
            }

            for (byte i = 1; i < directions.length; i += 2) {
                var nextY = current.getY() + directions[i - 1];
                if (nextY < 0 || nextY >= racetrackMap.length) {
                    continue;
                }
                var nextX = current.getX() + directions[i];
                if (nextX < 0) {
                    continue;
                }
                var racetrackRow = racetrackMap[nextY];
                if (nextX >= racetrackRow.length || racetrackRow[nextX] == '#') {
                    continue;
                }
                boolean[] closedRow = closed[nextY];
                if (!closedRow[nextX]) {
                    Move nextMove = new Move(endX, endY, nextX, nextY, current, current.getPicoseconds() + 1);
                    if (nextMove.getRemainingDistance() + nextMove.getPicoseconds() <= timeLimit) {
                        closedRow[nextX] = true;
                        open.add(nextMove);
                    }
                }
            }
        }

        return Optional.empty();
    }

    private static class Move implements Comparable<Move> {

        private final int x;
        private final int y;
        private final Move previous;
        private final int picoseconds;
        private final int remainingDistance;

        private Move(int endX, int endY, int x, int y, Move previous, int picoseconds) {
            this.y = y;
            this.x = x;
            this.picoseconds = picoseconds;
            this.previous = previous;
            remainingDistance = Math.abs(x - endX) + Math.abs(endY - y);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getPicoseconds() {
            return picoseconds;
        }

        public Optional<Move> getPrevious() {
            return Optional.ofNullable(previous);
        }

        public int getRemainingDistance() {
            return remainingDistance;
        }

        @Override
        public int compareTo(final Move o) {
            return (remainingDistance + picoseconds) - (o.remainingDistance + o.picoseconds);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Move move = (Move) o;
            return x == move.x && y == move.y && picoseconds == move.picoseconds;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, picoseconds);
        }

    }

}
