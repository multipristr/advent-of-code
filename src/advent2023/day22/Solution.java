package src.advent2023.day22;

import src.PuzzleSolver;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Solution extends PuzzleSolver {
    private static final char[] ALPHABET = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};

    public static void main(String[] args) {
        new Solution().run();
    }

    private static long countBricksToFall(Brick lastDisintegratedBrick, Map<Brick, Long> memory) {
        long disintegratedCount = 0;
        Set<Brick> disintegrated = new HashSet<>();
        Deque<Brick> toDisintegrate = new ArrayDeque<>();
        toDisintegrate.addLast(lastDisintegratedBrick);

        while (!toDisintegrate.isEmpty()) {
            var brick = toDisintegrate.pollFirst();

            var found = memory.get(lastDisintegratedBrick);
            if (found != null) {
                disintegratedCount += found;
                continue;
            }

            disintegrated.add(brick);
            for (Brick supporting : brick.getSupporting()) {
                if (disintegrated.containsAll(supporting.getSupportedBy())) {
                    ++disintegratedCount;
                    toDisintegrate.add(supporting);
                }
            }
        }

        memory.put(lastDisintegratedBrick, disintegratedCount);
        return disintegratedCount;
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
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(5L);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(7L);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        AtomicInteger letterIndex = new AtomicInteger();
        List<Brick> bricks = lines.map(line -> line.split("~"))
                .map(intervals -> {
                    long[] start = Arrays.stream(intervals[0].split(",")).mapToLong(Long::parseLong).toArray();
                    long[] end = Arrays.stream(intervals[1].split(",")).mapToLong(Long::parseLong).toArray();
                    return new Brick(start[0], end[0], start[1], end[1], start[2], end[2], ALPHABET[letterIndex.getAndIncrement() % ALPHABET.length]);
                })
                .sorted(Comparator.comparingLong(Brick::getStartZ).thenComparingLong(Brick::getEndZ))
                .collect(Collectors.toList());

        NavigableMap<Long, NavigableMap<Long, Set<Brick>>> xBricks = new TreeMap<>();
        NavigableMap<Long, NavigableMap<Long, Set<Brick>>> yBricks = new TreeMap<>();
        for (Brick brick : bricks) {
            long zMovement = 0;
            for (int i = 1; i < brick.getStartZ(); i++) {
                var xMap = xBricks.getOrDefault(brick.getStartZ() - i, Collections.emptyNavigableMap());
                var yMap = yBricks.getOrDefault(brick.getStartZ() - i, Collections.emptyNavigableMap());
                var belowX = xMap.subMap(brick.getStartX(), true, brick.getEndX(), true)
                        .values().stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
                var belowY = yMap.subMap(brick.getStartY(), true, brick.getEndY(), true)
                        .values().stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
                var bricksBelow = new HashSet<>(belowX);
                bricksBelow.retainAll(belowY);
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
                    xMap.computeIfAbsent(x, k -> new HashSet<>()).add(brick);
                    for (long y = brick.getStartY(); y <= brick.getEndY(); y++) {
                        yMap.computeIfAbsent(y, k -> new HashSet<>()).add(brick);
                    }
                }
            }
        }

        for (Brick brick : bricks) {
            var xMap = xBricks.getOrDefault(brick.getEndZ() + 1, Collections.emptyNavigableMap());
            var yMap = yBricks.getOrDefault(brick.getEndZ() + 1, Collections.emptyNavigableMap());

            var aboveX = xMap.subMap(brick.getStartX(), true, brick.getEndX(), true)
                    .values().stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
            var aboveY = yMap.subMap(brick.getStartY(), true, brick.getEndY(), true)
                    .values().stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
            var bricksAbove = new HashSet<>(aboveX);
            bricksAbove.retainAll(aboveY);
            brick.getSupporting().addAll(bricksAbove);
            bricksAbove.forEach(brickAbove -> brickAbove.getSupportedBy().add(brick));
        }

        return bricks.stream()
                .filter(brick -> brick.getSupporting().stream().noneMatch(supporting -> supporting.getSupportedBy().size() == 1))
                .count();
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        AtomicInteger letterIndex = new AtomicInteger();
        List<Brick> bricks = lines.map(line -> line.split("~"))
                .map(intervals -> {
                    long[] start = Arrays.stream(intervals[0].split(",")).mapToLong(Long::parseLong).toArray();
                    long[] end = Arrays.stream(intervals[1].split(",")).mapToLong(Long::parseLong).toArray();
                    return new Brick(start[0], end[0], start[1], end[1], start[2], end[2], ALPHABET[letterIndex.getAndIncrement() % ALPHABET.length]);
                })
                .sorted(Comparator.comparingLong(Brick::getStartZ).thenComparingLong(Brick::getEndZ))
                .collect(Collectors.toList());

        NavigableMap<Long, NavigableMap<Long, Set<Brick>>> xBricks = new TreeMap<>();
        NavigableMap<Long, NavigableMap<Long, Set<Brick>>> yBricks = new TreeMap<>();
        for (Brick brick : bricks) {
            long zMovement = 0;
            for (int i = 1; i < brick.getStartZ(); i++) {
                var xMap = xBricks.getOrDefault(brick.getStartZ() - i, Collections.emptyNavigableMap());
                var yMap = yBricks.getOrDefault(brick.getStartZ() - i, Collections.emptyNavigableMap());
                var belowX = xMap.subMap(brick.getStartX(), true, brick.getEndX(), true)
                        .values().stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
                var belowY = yMap.subMap(brick.getStartY(), true, brick.getEndY(), true)
                        .values().stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
                var bricksBelow = new HashSet<>(belowX);
                bricksBelow.retainAll(belowY);
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
                    xMap.computeIfAbsent(x, k -> new HashSet<>()).add(brick);
                    for (long y = brick.getStartY(); y <= brick.getEndY(); y++) {
                        yMap.computeIfAbsent(y, k -> new HashSet<>()).add(brick);
                    }
                }
            }
        }

        for (Brick brick : bricks) {
            var xMap = xBricks.getOrDefault(brick.getEndZ() + 1, Collections.emptyNavigableMap());
            var yMap = yBricks.getOrDefault(brick.getEndZ() + 1, Collections.emptyNavigableMap());

            var aboveX = xMap.subMap(brick.getStartX(), true, brick.getEndX(), true)
                    .values().stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
            var aboveY = yMap.subMap(brick.getStartY(), true, brick.getEndY(), true)
                    .values().stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
            var bricksAbove = new HashSet<>(aboveX);
            bricksAbove.retainAll(aboveY);
            brick.getSupporting().addAll(bricksAbove);
            bricksAbove.forEach(brickAbove -> brickAbove.getSupportedBy().add(brick));
        }

        Map<Brick, Long> memory = new ConcurrentHashMap<>();
        return bricks.parallelStream()
                .mapToLong(brick -> countBricksToFall(brick, memory))
                .sum();
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
        private final char letter;

        Brick(long startX, long endX, long startY, long endY, long startZ, long endZ, char letter) {
            this.startX = startX;
            this.endX = endX;
            this.startY = startY;
            this.endY = endY;
            this.startZ = startZ;
            this.endZ = endZ;
            this.letter = letter;
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
            return letter + " " + startX + "," + startY + "," + startZ + "~" + endX + "," + endY + "," + endZ;
        }
    }
}
