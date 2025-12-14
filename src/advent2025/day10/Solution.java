package src.advent2025.day10;

import src.PuzzleSolver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver<Long, Long> {
    private static final Pattern MACHINE_PATTERN = Pattern.compile("\\[(?<indicatorLights>[.#]+)] (?<buttonWiringSchematics>(.)+) \\{(?<joltageRequirements>[\\d,?]+)}");
    private static final Pattern BUTTON_WIRING_SCHEMATIC_PATTERN = Pattern.compile("\\((?<buttonWiringSchematic>[\\d,]+)\\)");

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}\n" +
                "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}\n" +
                "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}");
    }

    @Override
    public List<Long> getExampleOutput1() {
        return List.of(7L);
    }

    @Override
    public List<Long> getExampleOutput2() {
        return List.of(33L);
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        return lines.mapToLong(line -> {
                    var matcher = MACHINE_PATTERN.matcher(line);
                    matcher.find();
                    var indicatorLightsInput = matcher.group("indicatorLights");
                    var buttonWiringSchematicsInput = matcher.group("buttonWiringSchematics");

                    List<int[]> buttonWiringSchematics = new ArrayList<>();
                    var buttonWiringSchematicMatcher = BUTTON_WIRING_SCHEMATIC_PATTERN.matcher(buttonWiringSchematicsInput);
                    while (buttonWiringSchematicMatcher.find()) {
                        var buttonWiringSchematicInput = buttonWiringSchematicMatcher.group("buttonWiringSchematic");
                        var buttonWiringSchematic = Arrays.stream(buttonWiringSchematicInput.split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                        buttonWiringSchematics.add(buttonWiringSchematic);
                    }

                    return calculateIndicatorLightsFewestButtonPresses(indicatorLightsInput.toCharArray(), buttonWiringSchematics);
                })
                .sum();
    }

    private long calculateIndicatorLightsFewestButtonPresses(char[] expectedIndicatorLights, List<int[]> buttonWiringSchematics) {
        long buttonPresses = 1;
        Set<String> closed = new HashSet<>();
        var initialIndicatorLights = new char[expectedIndicatorLights.length];
        Arrays.fill(initialIndicatorLights, '.');
        Deque<char[]> open = new ArrayDeque<>(List.of(initialIndicatorLights));
        for (; buttonPresses < Long.MAX_VALUE && !open.isEmpty(); ++buttonPresses) {
            Deque<char[]> nextOpen = new ArrayDeque<>();
            while (!open.isEmpty()) {
                var indicatorLights = open.removeFirst();
                for (var buttonWiringSchematic : buttonWiringSchematics) {
                    var indicatorLightsCopy = Arrays.copyOf(indicatorLights, indicatorLights.length);
                    for (var button : buttonWiringSchematic) {
                        indicatorLightsCopy[button] = indicatorLights[button] == '.' ? '#' : '.';
                    }
                    if (isIndicatorLightsMatching(expectedIndicatorLights, indicatorLightsCopy)) {
                        return buttonPresses;
                    } else if (closed.add(new String(indicatorLightsCopy))) {
                        nextOpen.add(indicatorLightsCopy);
                    }
                }
            }
            open = nextOpen;
        }
        throw new IllegalStateException(buttonPresses
                + ": [" + new String(expectedIndicatorLights) + "] " +
                buttonWiringSchematics.stream().map(buttonWiringSchematic -> Arrays.stream(buttonWiringSchematic).mapToObj(Integer::toString).collect(Collectors.joining(",", "(", ")"))).collect(Collectors.joining(" ")));
    }

    private boolean isIndicatorLightsMatching(char[] expectedIndicatorLights, char[] indicatorLights) {
        for (int i = 0; i < expectedIndicatorLights.length; i++) {
            if (indicatorLights[i] != expectedIndicatorLights[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        return lines.parallel()
                .mapToLong(line -> {
                    var matcher = MACHINE_PATTERN.matcher(line);
                    matcher.find();
                    var buttonWiringSchematicsInput = matcher.group("buttonWiringSchematics");
                    var joltageRequirementsInput = matcher.group("joltageRequirements");

                    List<int[]> buttonWiringSchematics = new ArrayList<>();
                    var buttonWiringSchematicMatcher = BUTTON_WIRING_SCHEMATIC_PATTERN.matcher(buttonWiringSchematicsInput);
                    while (buttonWiringSchematicMatcher.find()) {
                        var buttonWiringSchematicInput = buttonWiringSchematicMatcher.group("buttonWiringSchematic");
                        var buttonWiringSchematic = Arrays.stream(buttonWiringSchematicInput.split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                        buttonWiringSchematics.add(buttonWiringSchematic);
                    }

                    var joltageRequirements = Arrays.stream(joltageRequirementsInput.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

                    return calculateJoltageLevelsFewestButtonPresses(joltageRequirements, buttonWiringSchematics);
                })
                .sum();
    }

    private long calculateJoltageLevelsFewestButtonPresses(long[] expectedJoltageLevels, List<int[]> buttonWiringSchematics) {
        long buttonPresses = 1;
        Set<String> closed = new HashSet<>();
        var initialJoltageLevels = new long[expectedJoltageLevels.length];
        Deque<long[]> open = new ArrayDeque<>(List.of(initialJoltageLevels));
        for (; buttonPresses < Long.MAX_VALUE && !open.isEmpty(); ++buttonPresses) {
            Deque<long[]> nextOpen = new ArrayDeque<>();
            while (!open.isEmpty()) {
                var joltageLevels = open.removeFirst();
                outerLoop:
                for (var buttonWiringSchematic : buttonWiringSchematics) {
                    var joltageLevelsCopy = Arrays.copyOf(joltageLevels, joltageLevels.length);
                    for (var button : buttonWiringSchematic) {
                        var nextJoltageLevel = joltageLevels[button] + 1;
                        if (nextJoltageLevel > expectedJoltageLevels[button]) {
                            continue outerLoop;
                        }
                        joltageLevelsCopy[button] = nextJoltageLevel;
                    }
                    if (isJoltageLevelsMatching(expectedJoltageLevels, joltageLevelsCopy)) {
                        System.out.println(buttonPresses + ": "
                                + buttonWiringSchematics.stream().map(wiringSchematic -> Arrays.stream(wiringSchematic).mapToObj(Integer::toString).collect(Collectors.joining(",", "(", ")"))).collect(Collectors.joining(" "))
                                + " {" + Arrays.stream(expectedJoltageLevels).mapToObj(Long::toString).collect(Collectors.joining(",")) + '}');
                        return buttonPresses;
                    }
                    var joltage = Arrays.stream(joltageLevelsCopy).mapToObj(Long::toString).collect(Collectors.joining(","));
                    if (closed.add(joltage)) {
                        nextOpen.add(joltageLevelsCopy);
                    }
                }
            }
            open = nextOpen;
        }
        throw new IllegalStateException(buttonPresses + ": "
                + buttonWiringSchematics.stream().map(wiringSchematic -> Arrays.stream(wiringSchematic).mapToObj(Integer::toString).collect(Collectors.joining(",", "(", ")"))).collect(Collectors.joining(" "))
                + " {" + Arrays.stream(expectedJoltageLevels).mapToObj(Long::toString).collect(Collectors.joining(",")) + '}');
    }

    private boolean isJoltageLevelsMatching(long[] expectedJoltageLevels, long[] joltageLevels) {
        for (int i = 0; i < expectedJoltageLevels.length; i++) {
            if (joltageLevels[i] != expectedJoltageLevels[i]) {
                return false;
            }
        }
        return true;
    }
}
