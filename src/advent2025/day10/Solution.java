package src.advent2025.day10;

import src.PuzzleSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
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
        return List.of();
    }

    @Override
    public Long solvePartOne(Stream<String> lines) {
        return lines.parallel()
                .mapToLong(line -> {
                    var matcher = MACHINE_PATTERN.matcher(line);
                    matcher.find();
                    var indicatorLightsInput = matcher.group("indicatorLights");
                    var buttonWiringSchematicsInput = matcher.group("buttonWiringSchematics");

                    var indicatorLights = new boolean[indicatorLightsInput.length()];
                    for (int i = 0; i < indicatorLightsInput.length(); i++) {
                        indicatorLights[i] = indicatorLightsInput.charAt(i) == '#';
                    }

                    List<int[]> buttonWiringSchematics = new ArrayList<>();
                    var buttonWiringSchematicMatcher = BUTTON_WIRING_SCHEMATIC_PATTERN.matcher(buttonWiringSchematicsInput);
                    while (buttonWiringSchematicMatcher.find()) {
                        var buttonWiringSchematicInput = buttonWiringSchematicMatcher.group("buttonWiringSchematic");
                        var buttonWiringSchematic = Arrays.stream(buttonWiringSchematicInput.split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                        buttonWiringSchematics.add(buttonWiringSchematic);
                    }

                    return 0L;
                })
                .sum();
    }

    private boolean isIndicatorLightsMatching(boolean[] expectedIndicatorLights, boolean[] indicatorLights) {
        for (int i = 0; i < expectedIndicatorLights.length; i++) {
            if (indicatorLights[i] != expectedIndicatorLights[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Long solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
