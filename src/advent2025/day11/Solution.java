package src.advent2025.day11;

import src.PuzzleSolver;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {
    private static final Pattern DEVICE_PATTERN = Pattern.compile("(?<device>\\w+): (?<outputs>[\\w\\s]+)");

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("aaa: you hhh\n" +
                "you: bbb ccc\n" +
                "bbb: ddd eee\n" +
                "ccc: ddd eee fff\n" +
                "ddd: ggg\n" +
                "eee: out\n" +
                "fff: out\n" +
                "ggg: out\n" +
                "hhh: ccc fff iii\n" +
                "iii: out");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(5L);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of("svr: aaa bbb\n" +
                "aaa: fft\n" +
                "fft: ccc\n" +
                "bbb: tty\n" +
                "tty: ccc\n" +
                "ccc: ddd eee\n" +
                "ddd: hub\n" +
                "hub: fff\n" +
                "eee: dac\n" +
                "dac: fff\n" +
                "fff: ggg hhh\n" +
                "ggg: out\n" +
                "hhh: out");
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(2L);
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        var deviceOutputs = lines.map(line -> {
            var matcher = DEVICE_PATTERN.matcher(line);
            matcher.find();
            return matcher;
        }).collect(Collectors.groupingBy(
                matcher -> matcher.group("device"),
                Collectors.flatMapping(
                        matcher -> Arrays.stream(matcher.group("outputs").split("\\s")),
                        Collectors.toList()
                )
        ));

        Deque<String> open = new ArrayDeque<>(List.of("you"));
        Map<String, Long> paths = new HashMap<>();
        Set<String> closed = new HashSet<>();
        while (!open.isEmpty()) {
            var device = open.removeFirst();
            var devicePaths = paths.getOrDefault(device, 1L);
            var outputs = deviceOutputs.getOrDefault(device, List.of());
            for (var output : outputs) {
                paths.merge(output, devicePaths, Long::sum);
                if (closed.add(output)) {
                    open.addLast(output);
                }
            }
        }
        return paths.get("out");
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        var deviceOutputs = lines.map(line -> {
            var matcher = DEVICE_PATTERN.matcher(line);
            matcher.find();
            return matcher;
        }).collect(Collectors.groupingBy(
                matcher -> matcher.group("device"),
                Collectors.flatMapping(
                        matcher -> Arrays.stream(matcher.group("outputs").split("\\s")),
                        Collectors.toList()
                )
        ));

        var open = new ArrayDeque<>(List.of(new Path("svr")));
        Map<Path, Long> paths = new HashMap<>();
        var closed = new HashSet<>(Set.of(open.getFirst()));
        while (!open.isEmpty()) {
            var path = open.removeFirst();
            var devicePaths = paths.getOrDefault(path, 1L);
            var outputs = deviceOutputs.getOrDefault(path.device, List.of());
            for (var output : outputs) {
                var newRemainingDevices = new HashSet<>(path.remainingProblematicDevices);
                newRemainingDevices.remove(output);
                var newPath = new Path(output, newRemainingDevices);
                paths.merge(newPath, devicePaths, Long::sum);
                if (closed.add(newPath)) {
                    open.addLast(newPath);
                }
            }
        }
        return paths.get(new Path("out", Set.of()));
    }

    private static final class Path {
        private final String device;
        private final Set<String> remainingProblematicDevices;

        Path(String device) {
            this(device, Set.of("dac", "fft"));
        }

        public Path(String device, Set<String> remainingProblematicDevices) {
            this.remainingProblematicDevices = remainingProblematicDevices;
            this.device = device;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Path path = (Path) o;
            return Objects.equals(remainingProblematicDevices, path.remainingProblematicDevices) && Objects.equals(device, path.device);
        }

        @Override
        public int hashCode() {
            return Objects.hash(remainingProblematicDevices, device);
        }
    }

}
