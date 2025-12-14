package src.advent2025.day11;

import src.PuzzleSolver;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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

        Deque<Map.Entry<String, UUID>> open = new ArrayDeque<>(List.of(Map.entry("svr", UUID.randomUUID())));
        Map<String, Long> paths = new HashMap<>();
        Set<Map.Entry<String, UUID>> closed = new HashSet<>(Set.of(open.getFirst()));
        Map<UUID, Set<String>> pathRemainingToVisit = new HashMap<>(Map.of(open.getFirst().getValue(), Set.of("fft", "dac")));
        while (!open.isEmpty()) {
            var devicePath = open.removeFirst();
            var remainingToVisit = pathRemainingToVisit.get(devicePath.getValue());
            var outputs = deviceOutputs.getOrDefault(devicePath.getKey(), List.of());
            var outputIterator = outputs.iterator();
            if (outputIterator.hasNext()) {
                var output = outputIterator.next();
                Set<String> remaining = new HashSet<>(remainingToVisit);
                remaining.remove(output);
                if (remaining.isEmpty()) {
                    paths.merge(output, 1L, Long::sum);
                }
                pathRemainingToVisit.put(devicePath.getValue(), remaining);
                if (closed.add(Map.entry(output, devicePath.getValue()))) {
                    open.addLast(Map.entry(output, devicePath.getValue()));
                }
            }
            while (outputIterator.hasNext()) {
                var output = outputIterator.next();
                Set<String> remaining = new HashSet<>(remainingToVisit);
                remaining.remove(output);
                if (remaining.isEmpty()) {
                    paths.merge(output, 1L, Long::sum);
                }
                var pathId = UUID.randomUUID();
                pathRemainingToVisit.put(pathId, remaining);
                open.addLast(Map.entry(output, pathId));
            }
        }
        return paths.get("out");
    }

}
