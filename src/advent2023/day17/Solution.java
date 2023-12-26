package src.advent2023.day17;

import src.PuzzleSolver;

import java.util.*;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("2413432311323\n" +
                "3215453535623\n" +
                "3255245654254\n" +
                "3446585845452\n" +
                "4546657867536\n" +
                "1438598798454\n" +
                "4457876987766\n" +
                "3637877979653\n" +
                "4654967986887\n" +
                "4564679986453\n" +
                "1224686865563\n" +
                "2546548887735\n" +
                "4322674655533");
    }

    @Override
    public List<String> getExampleOutput1() {
        return List.of("102");
    }

    @Override
    public List<String> getExampleOutput2() {
        return List.of();
    }

    @Override
    public String solvePartOne(Stream<String> lines) throws InterruptedException {
        int[][] map = lines.map(line -> line.chars().map(Character::getNumericValue).toArray()).toArray(int[][]::new);
        Map<Integer, Map<Integer, Map<Integer, Map<Integer, NavigableMap<Integer, Long>>>>> searchStates = new HashMap<>(map.length);
        searchStates.computeIfAbsent(0, r -> new HashMap<>(map[0].length))
                .computeIfAbsent(0, c -> new HashMap<>(3))
                .computeIfAbsent(1, rd -> new HashMap<>(3))
                .computeIfAbsent(0, cd -> new TreeMap<>())
                .put(2, 0L);
        searchStates.computeIfAbsent(0, r -> new HashMap<>(map[0].length))
                .computeIfAbsent(0, c -> new HashMap<>(3))
                .computeIfAbsent(0, rd -> new HashMap<>(3))
                .computeIfAbsent(1, cd -> new TreeMap<>())
                .put(2, 0L);
        searchStates.computeIfAbsent(0, r -> new HashMap<>(map[0].length))
                .computeIfAbsent(0, c -> new HashMap<>(3))
                .computeIfAbsent(-1, rd -> new HashMap<>(3))
                .computeIfAbsent(0, cd -> new TreeMap<>())
                .put(2, 0L);
        searchStates.computeIfAbsent(0, r -> new HashMap<>(map[0].length))
                .computeIfAbsent(0, c -> new HashMap<>(3))
                .computeIfAbsent(0, rd -> new HashMap<>(3))
                .computeIfAbsent(-1, cd -> new TreeMap<>())
                .put(2, 0L);

        Queue<SearchState> queue = new PriorityQueue<>();
        queue.add(new SearchState(1, 0, 1, 0, 2, map, 0));
        queue.add(new SearchState(0, 1, 0, 1, 2, map, 0));
        while (!queue.isEmpty()) {
            var searchState = queue.poll();

            NavigableMap<Integer, Long> pathPerRemaining = searchStates.computeIfAbsent(searchState.getRow(), r -> new HashMap<>(map[0].length))
                    .computeIfAbsent(searchState.getColumn(), c -> new HashMap<>(3))
                    .computeIfAbsent(searchState.getRowDirection(), rd -> new HashMap<>(3))
                    .computeIfAbsent(searchState.getColumnDirection(), cd -> new TreeMap<>());
            if (pathPerRemaining.tailMap(searchState.getRemainingInDirection(), true)
                    .values()
                    .stream()
                    .anyMatch(previousPath -> previousPath <= searchState.getPath())) {
                continue;
            }
            pathPerRemaining.put(searchState.getRemainingInDirection(), searchState.getPath());
            if (searchState.getRow() == map.length - 1 && searchState.getColumn() == map[searchState.getRow()].length - 1) {
                continue;
            }

            if (searchState.getRemainingInDirection() <= 0) {
                if (searchState.getRowDirection() == 0) {
                    if (searchState.getRow() - 1 >= 0) {
                        queue.add(new SearchState(searchState.getRow() - 1, searchState.getColumn(), -1, 0, 2, map, searchState.getPath()));
                    }
                    if (searchState.getRow() + 1 < map.length) {
                        queue.add(new SearchState(searchState.getRow() + 1, searchState.getColumn(), 1, 0, 2, map, searchState.getPath()));
                    }
                } else {
                    if (searchState.getColumn() - 1 >= 0) {
                        queue.add(new SearchState(searchState.getRow(), searchState.getColumn() - 1, 0, -1, 2, map, searchState.getPath()));
                    }
                    if (searchState.getColumn() + 1 < map[searchState.getRow()].length) {
                        queue.add(new SearchState(searchState.getRow(), searchState.getColumn() + 1, 0, 1, 2, map, searchState.getPath()));
                    }
                }
            } else {
                if (searchState.getRowDirection() == 0) {
                    if (searchState.getRow() - 1 >= 0) {
                        queue.add(new SearchState(searchState.getRow() - 1, searchState.getColumn(), -1, 0, 2, map, searchState.getPath()));
                    }
                    if (searchState.getRow() + 1 < map.length) {
                        queue.add(new SearchState(searchState.getRow() + 1, searchState.getColumn(), 1, 0, 2, map, searchState.getPath()));
                    }
                    if (searchState.getColumn() - 1 >= 0) {
                        queue.add(new SearchState(searchState.getRow(), searchState.getColumn() - 1, 0, -1, searchState.getRemainingInDirection() - 1, map, searchState.getPath()));
                    }
                    if (searchState.getColumn() + 1 < map[searchState.getRow()].length) {
                        queue.add(new SearchState(searchState.getRow(), searchState.getColumn() + 1, 0, 1, searchState.getRemainingInDirection() - 1, map, searchState.getPath()));
                    }
                } else {
                    if (searchState.getRow() - 1 >= 0) {
                        queue.add(new SearchState(searchState.getRow() - 1, searchState.getColumn(), -1, 0, searchState.getRemainingInDirection() - 1, map, searchState.getPath()));
                    }
                    if (searchState.getRow() + 1 < map.length) {
                        queue.add(new SearchState(searchState.getRow() + 1, searchState.getColumn(), 1, 0, searchState.getRemainingInDirection() - 1, map, searchState.getPath()));
                    }
                    if (searchState.getColumn() - 1 >= 0) {
                        queue.add(new SearchState(searchState.getRow(), searchState.getColumn() - 1, 0, -1, 2, map, searchState.getPath()));
                    }
                    if (searchState.getColumn() + 1 < map[searchState.getRow()].length) {
                        queue.add(new SearchState(searchState.getRow(), searchState.getColumn() + 1, 0, 1, 2, map, searchState.getPath()));
                    }
                }
            }
        }

        return "" + searchStates.get(map.length - 1).get(map[0].length - 1).values().stream()
                .flatMap(m -> m.values().stream())
                .flatMap(m -> m.values().stream())
                .mapToLong(v -> v)
                .min().orElseThrow();
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return "";
    }

    private static class SearchState implements Comparable<SearchState> {
        private final int row;
        private final int column;
        private final int rowDirection;
        private final int columnDirection;
        private final int remainingInDirection;
        private final long lowestEndDistance;
        private final long path;

        SearchState(int row, int column, int rowDirection, int columnDirection, int remainingInDirection, int[][] map, long path) {
            this.row = row;
            this.column = column;
            this.rowDirection = rowDirection;
            this.columnDirection = columnDirection;
            this.remainingInDirection = remainingInDirection;
            this.path = path + map[row][column];
            lowestEndDistance = this.path + Math.abs(row - map.length) + Math.abs(column - map[map.length - 1].length);
        }

        int getRow() {
            return row;
        }

        int getColumn() {
            return column;
        }

        int getRowDirection() {
            return rowDirection;
        }

        int getColumnDirection() {
            return columnDirection;
        }

        int getRemainingInDirection() {
            return remainingInDirection;
        }

        long getPath() {
            return path;
        }

        @Override
        public int compareTo(SearchState that) {
            if (lowestEndDistance != that.lowestEndDistance) {
                return Long.compare(lowestEndDistance, that.lowestEndDistance);
            }
            return remainingInDirection - that.remainingInDirection;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SearchState that = (SearchState) o;
            return remainingInDirection == that.remainingInDirection && lowestEndDistance == that.lowestEndDistance;
        }

        @Override
        public int hashCode() {
            return Objects.hash(remainingInDirection, lowestEndDistance);
        }
    }
}
