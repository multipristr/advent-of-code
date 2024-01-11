package src.advent2023.day12;

import src.PuzzleSolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    private static long calculateArrangements(char[] springs, int[] groupSizes, Map<Integer, Long> calculated, int springIndex, int groupIndex, int remainingSprings, boolean inGroup) {
        var hash = toKey(springIndex, groupIndex, remainingSprings, inGroup);
        var found1 = calculated.get(hash);
        if (found1 != null) {
            return found1;
        }
        for (; springIndex < springs.length; ++springIndex) {
            char spring = springs[springIndex];
            if (spring == '.') {
                if (remainingSprings <= 0) {
                    inGroup = false;
                    if (groupIndex < groupSizes.length - 1) {
                        ++groupIndex;
                        remainingSprings = groupSizes[groupIndex];
                    }
                }
                if (inGroup) {
                    hash = toKey(springIndex, groupIndex, remainingSprings, inGroup);
                    calculated.put(hash, 0L);
                    return 0;
                }
            } else if (spring == '#') {
                if (remainingSprings <= 0) {
                    hash = toKey(springIndex, groupIndex, remainingSprings, inGroup);
                    calculated.put(hash, 0L);
                    return 0;
                }
                inGroup = true;
                --remainingSprings;
            } else if (spring == '?') {
                if (remainingSprings <= 0) {
                    inGroup = false;
                    if (groupIndex < groupSizes.length - 1) {
                        ++groupIndex;
                        remainingSprings = groupSizes[groupIndex];
                    }
                } else if (inGroup && remainingSprings > 0) {
                    --remainingSprings;
                } else {
                    hash = toKey(springIndex, groupIndex, remainingSprings, inGroup);
                    long arrangements = calculateArrangements(springs, groupSizes, calculated, springIndex + 1, groupIndex, remainingSprings - 1, true) + calculateArrangements(springs, groupSizes, calculated,
                            springIndex + 1, groupIndex,
                            remainingSprings, false);
                    calculated.put(hash, arrangements);
                    return arrangements;
                }
            }
        }
        hash = toKey(springIndex, groupIndex, remainingSprings, inGroup);
        long arrangements = remainingSprings > 0 || groupIndex < groupSizes.length - 1 ? 0 : 1;
        calculated.put(hash, arrangements);
        return arrangements;
    }

    private static int toKey(int springIndex, int groupIndex, int remainingSprings, boolean inGroup) {
        return Objects.hash(springIndex, groupIndex, remainingSprings, inGroup);
        //return springIndex + "|" + groupIndex + "|" + remainingSprings + "|" + inGroup;
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("???.### 1,1,3\n" +
                ".??..??...?##. 1,1,3\n" +
                "?#?#?#?#?#?#?#? 1,3,1,6\n" +
                "????.#...#... 4,1,1\n" +
                "????.######..#####. 1,6,5\n" +
                "?###???????? 3,2,1");
    }

    @Override
    public List<String> getExampleOutput1() {
        return List.of("21");
    }

    @Override
    public List<String> getExampleOutput2() {
        return List.of("525152");
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        return lines.parallel()
                .map(line -> line.split("\\s"))
                .mapToLong(parts -> {
                    var springs = parts[0].toCharArray();
                    var groupSizes = Arrays.stream(parts[1].split(",")).mapToInt(Integer::parseInt).toArray();
                    Map<Integer, Long> calculated = new HashMap<>();
                    var arrangements = calculateArrangements(springs, groupSizes, calculated, 0, 0, groupSizes[0], false);
                    //System.out.println(arrangements);
                    return arrangements;
                })
                .sum() + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        return lines.parallel()
                .map(line -> line.split("\\s"))
                .mapToLong(parts -> {
                    var springs = String.join("?", Collections.nCopies(5, parts[0])).toCharArray();
                    var groupSizes = Arrays.stream(String.join(",", Collections.nCopies(5, parts[1])).split(",")).mapToInt(Integer::parseInt).toArray();
                    Map<Integer, Long> calculated = new HashMap<>();
                    var arrangements = calculateArrangements(springs, groupSizes, calculated, 0, 0, groupSizes[0], false);
                    //System.out.println(arrangements);
                    return arrangements;
                })
                .sum() + "";
    }

}
