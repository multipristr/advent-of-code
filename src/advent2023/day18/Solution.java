package src.advent2023.day18;

import src.PuzzleSolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    private static long parseSteps(String color) {
        return Long.parseLong(color.substring(2, color.length() - 2), 16);
    }

    private static boolean isInside(Map<Long, Map<Long, String>> digPlan, long row, long column, int rowDirection, int columnDirection, long rowStart, long rowEnd, long columnStart, long columnEnd) {
        if (digPlan.getOrDefault(row, Map.of()).get(column) != null) {
            return true;
        }
        long intersections1 = 0;
        long intersections2 = 0;
        while (
                row <= rowEnd &&
                        row >= rowStart &&
                        column <= columnEnd &&
                        column >= columnStart
        ) {
            if (digPlan.getOrDefault(row, Map.of()).get(column) != null) {
                if (rowDirection == 0) {
                    if (row - 1 >= rowStart
                            && digPlan.getOrDefault(row - 1, Map.of()).get(column) != null) {
                        ++intersections1;
                    }
                    if (row + 1 <= rowEnd
                            && digPlan.getOrDefault(row + 1, Map.of()).get(column) != null) {
                        ++intersections2;
                    }
                } else {
                    if (column - 1 >= columnStart
                            && digPlan.getOrDefault(row, Map.of()).get(column - 1) != null) {
                        ++intersections1;
                    }
                    if (column + 1 <= columnEnd
                            && digPlan.getOrDefault(row, Map.of()).get(column + 1) != null) {
                        ++intersections2;
                    }
                }
            }
            row += rowDirection;
            column += columnDirection;
        }
        return (intersections1 & 1L) != 0L &&
                (intersections2 & 1L) != 0L;
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("R 6 (#70c710)\n" +
                "D 5 (#0dc571)\n" +
                "L 2 (#5713f0)\n" +
                "D 2 (#d2c081)\n" +
                "R 2 (#59c680)\n" +
                "D 2 (#411b91)\n" +
                "L 5 (#8ceee2)\n" +
                "U 2 (#caa173)\n" +
                "L 1 (#1b58a2)\n" +
                "U 2 (#caa171)\n" +
                "R 2 (#7807d2)\n" +
                "U 3 (#a77fa3)\n" +
                "L 2 (#015232)\n" +
                "U 2 (#7a21e3)");
    }

    @Override
    public List<String> getExampleOutput1() {
        return List.of("62");
    }

    @Override
    public List<String> getExampleOutput2() {
        return List.of("952408144115");
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        AtomicLong row = new AtomicLong();
        AtomicLong column = new AtomicLong();
        Map<Long, Map<Long, String>> digPlan = new HashMap<>();
        long[] rowStart = new long[1];
        long[] rowEnd = new long[1];
        long[] columnStart = new long[1];
        long[] columnEnd = new long[1];

        lines.map(line -> line.split(" "))
                .forEach(parts -> {
                    if ("R".equals(parts[0])) {
                        for (int i = 0; i < Integer.parseInt(parts[1]); ++i) {
                            digPlan.computeIfAbsent(row.get(), k -> new HashMap<>()).put(column.getAndIncrement(), parts[2]);
                            columnEnd[0] = Math.max(columnEnd[0], column.get());
                        }
                    } else if ("L".equals(parts[0])) {
                        for (int i = 0; i < Integer.parseInt(parts[1]); ++i) {
                            digPlan.computeIfAbsent(row.get(), k -> new HashMap<>()).put(column.getAndDecrement(), parts[2]);
                            columnStart[0] = Math.min(columnStart[0], column.get());
                        }
                    } else if ("D".equals(parts[0])) {
                        for (int i = 0; i < Integer.parseInt(parts[1]); ++i) {
                            digPlan.computeIfAbsent(row.getAndIncrement(), k -> new HashMap<>()).put(column.get(), parts[2]);
                            rowEnd[0] = Math.max(rowEnd[0], row.get());
                        }
                    } else if ("U".equals(parts[0])) {
                        for (int i = 0; i < Integer.parseInt(parts[1]); ++i) {
                            digPlan.computeIfAbsent(row.getAndDecrement(), k -> new HashMap<>()).put(column.get(), parts[2]);
                            rowStart[0] = Math.min(rowStart[0], row.get());
                        }
                    }
                });

        long lava = 0;
        for (long r = rowStart[0]; r <= rowEnd[0]; ++r) {
            for (long c = columnStart[0]; c <= columnEnd[0]; ++c) {
                if (
                        isInside(digPlan, r, c, 0, 1, rowStart[0], rowEnd[0], columnStart[0], columnEnd[0]) &&
                                isInside(digPlan, r, c, 0, -1, rowStart[0], rowEnd[0], columnStart[0], columnEnd[0]) &&
                                isInside(digPlan, r, c, 1, 0, rowStart[0], rowEnd[0], columnStart[0], columnEnd[0]) &&
                                isInside(digPlan, r, c, -1, 0, rowStart[0], rowEnd[0], columnStart[0], columnEnd[0])) {
                    //System.out.print("#");
                    ++lava;
                } else {
                    //System.out.print(".");
                }
            }
            //	System.out.println("");
        }

        return lava + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        AtomicLong row = new AtomicLong();
        AtomicLong column = new AtomicLong();
        Map<Long, Map<Long, String>> digPlan = new HashMap<>();
        long[] rowStart = new long[1];
        long[] rowEnd = new long[1];
        long[] columnStart = new long[1];
        long[] columnEnd = new long[1];

        lines.map(line -> line.split(" "))
                .forEach(parts -> {
                    char direction = parts[2]
                            .charAt(parts[2]
                                    .length() - 2);
                    if (direction == '0') {
                        for (long i = 0; i < parseSteps(parts[2]); ++i) {
                            digPlan.computeIfAbsent(row.get(), k -> new HashMap<>()).put(column.getAndIncrement(), parts[2]);
                            columnEnd[0] = Math.max(columnEnd[0], column.get());
                        }
                    } else if (direction == '2') {
                        for (long i = 0; i < parseSteps(parts[2]); ++i) {
                            digPlan.computeIfAbsent(row.get(), k -> new HashMap<>()).put(column.getAndDecrement(), parts[2]);
                            columnStart[0] = Math.min(columnStart[0], column.get());
                        }
                    } else if (direction == '1') {
                        for (long i = 0; i < parseSteps(parts[2]); ++i) {
                            digPlan.computeIfAbsent(row.getAndIncrement(), k -> new HashMap<>()).put(column.get(), parts[2]);
                            rowEnd[0] = Math.max(rowEnd[0], row.get());
                        }
                    } else if (direction == '3') {
                        for (long i = 0; i < parseSteps(parts[2]); ++i) {
                            digPlan.computeIfAbsent(row.getAndDecrement(), k -> new HashMap<>()).put(column.get(), parts[2]);
                            rowStart[0] = Math.min(rowStart[0], row.get());
                        }
                    }
                });

        return LongStream.rangeClosed(rowStart[0], rowEnd[0])
                .parallel()
                .map(r ->
                        LongStream.rangeClosed(columnStart[0], columnEnd[0])
                                .parallel()
                                .filter(c -> isInside(digPlan, r, c, 0, 1, rowStart[0], rowEnd[0], columnStart[0], columnEnd[0]) &&
                                        isInside(digPlan, r, c, 0, -1, rowStart[0], rowEnd[0], columnStart[0], columnEnd[0]) &&
                                        isInside(digPlan, r, c, 1, 0, rowStart[0], rowEnd[0], columnStart[0], columnEnd[0]) &&
                                        isInside(digPlan, r, c, -1, 0, rowStart[0], rowEnd[0], columnStart[0], columnEnd[0])).count()
                ).sum() + "";
    }
}
