package src.advent2023.day22;

import src.PuzzleSolver;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("1,0,1~1,2,1\n" +
                "0,0,2~2,0,2\n" +
                "0,2,3~2,2,3\n" +
                "0,0,4~0,2,4\n" +
                "2,0,5~2,2,5\n" +
                "0,1,6~2,1,6\n" +
                "1,1,8~1,1,9");
    }

    @Override
    public List<String> getExampleOutput1() {
        return List.of("5");
    }

    @Override
    public List<String> getExampleOutput2() {
        return List.of();
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        NavigableMap<Long, NavigableMap<Long, Brick>> xBricks = new TreeMap<>();
        NavigableMap<Long, NavigableMap<Long, Brick>> yBricks = new TreeMap<>();
        List<Brick> bricks = lines.map(line -> line.split("~"))
                .map(intervals -> {
                    long[] start = Arrays.stream(intervals[0].split(",")).mapToLong(Long::parseLong).toArray();
                    long[] end = Arrays.stream(intervals[1].split(",")).mapToLong(Long::parseLong).toArray();
                    Brick brick = new Brick(start[0], end[0], start[1], end[1], start[2], end[2]);
                    long zMovement = 0;
                    for (int i = 1; i < brick.getStartZ(); i++) {
                        var xMap = xBricks.getOrDefault(brick.getStartZ() - i, Collections.emptyNavigableMap());
                        var yMap = yBricks.getOrDefault(brick.getStartZ() - i, Collections.emptyNavigableMap());
                        var belowX = xMap.subMap(brick.getStartX(), true, brick.getEndX(), true);
                        var belowY = yMap.subMap(brick.getStartY(), true, brick.getEndY(), true);
                        var bricksBelow = new HashSet<>(belowX.values());
                        bricksBelow.retainAll(belowY.values());
                        if (!bricksBelow.isEmpty()) {
                            break;
                        } else {
                            ++zMovement;
                        }
                    }
                    brick.moveDown(zMovement);
                    for (long z = brick.getStartZ(); z <= brick.getEndZ(); z++) {
                        var xMap = xBricks.computeIfAbsent(z, k -> new TreeMap<>());
                        var yMap = yBricks.computeIfAbsent(z, k -> new TreeMap<>());
                        for (long x = brick.getStartX(); x <= brick.getEndX(); x++) {
                            xMap.put(x, brick);
                            for (long y = brick.getStartY(); y <= brick.getEndY(); y++) {
                                yMap.put(y, brick);
                            }
                        }
                    }
                    return brick;
                }).collect(Collectors.toList());

        for (Brick brick : bricks) {
            var xMap = xBricks.getOrDefault(brick.getEndZ() + 1, Collections.emptyNavigableMap());
            var yMap = yBricks.getOrDefault(brick.getEndZ() + 1, Collections.emptyNavigableMap());

            var aboveX = xMap.subMap(brick.getStartX(), true, brick.getEndX(), true);
            var aboveY = yMap.subMap(brick.getStartY(), true, brick.getEndY(), true);
            var bricksAbove = new HashSet<>(aboveX.values());
            bricksAbove.retainAll(aboveY.values());
            brick.getSupporting().addAll(bricksAbove);
            bricksAbove.forEach(brickAbove -> brickAbove.getSupportedBy().add(brick));
        }

        return "" + bricks.stream()
                .filter(brick -> brick.getSupporting().stream().noneMatch(supporting -> supporting.getSupportedBy().size() == 1))
                //.peek(b -> System.out.println(b))
                .count();
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return "";
    }

    private static class Brick {
        private final long startX;
        private final long endX;
        private final long startY;
        private final long endY;
        private final Set<Brick> supportedBy = new HashSet<>();
        private final Set<Brick> supporting = new HashSet<>();
        private long startZ;
        private long endZ;

        Brick(long startX, long endX, long startY, long endY, long startZ, long endZ) {
            this.startX = startX;
            this.endX = endX;
            this.startY = startY;
            this.endY = endY;
            this.startZ = startZ;
            this.endZ = endZ;
        }

        long getStartX() {
            return startX;
        }

        long getEndX() {
            return endX;
        }

        long getStartY() {
            return startY;
        }

        long getEndY() {
            return endY;
        }

        long getStartZ() {
            return startZ;
        }

        long getEndZ() {
            return endZ;
        }

        void moveDown(long zMovement) {
            startZ -= zMovement;
            endZ -= zMovement;
        }

        Set<Brick> getSupportedBy() {
            return supportedBy;
        }

        Set<Brick> getSupporting() {
            return supporting;
        }

        @Override
        public String toString() {
            return startX + "," + startY + "," + startZ + "~" + endX + "," + endY + "," + endZ;
        }
    }
}
