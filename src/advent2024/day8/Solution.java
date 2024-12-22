package src.advent2024.day8;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of(
                "............\n" +
                        "........0...\n" +
                        ".....0......\n" +
                        ".......0....\n" +
                        "....0.......\n" +
                        "......A.....\n" +
                        "............\n" +
                        "............\n" +
                        "........A...\n" +
                        ".........A..\n" +
                        "............\n" +
                        "............",
                ".A.\n" +
                        "...\n" +
                        "..A"
        );
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(14L, 0);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(34L, 2);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        char[][] map = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        Map<Character, List<Map.Entry<Integer, Integer>>> antennas = new HashMap<>();
        for (int x = 0; x < map.length; x++) {
            char[] row = map[x];
            for (int y = 0; y < row.length; y++) {
                char field = row[y];
                if (field != '.') {
                    antennas.computeIfAbsent(field, k -> new ArrayList<>()).add(new AbstractMap.SimpleImmutableEntry<>(x, y));
                }
            }
        }

        Set<Map.Entry<Integer, Integer>> antinodes = new HashSet<>();
        for (var antennaLocations : antennas.values()) {
            for (int i = 0; i < antennaLocations.size(); i++) {
                var antenna1 = antennaLocations.get(i);
                for (int j = i + 1; j < antennaLocations.size(); j++) {
                    var antenna2 = antennaLocations.get(j);

                    int x1 = antenna1.getKey() + antenna1.getKey() - antenna2.getKey();
                    int y1 = antenna1.getValue() + antenna1.getValue() - antenna2.getValue();
                    if (isWithinBounds(map, x1, y1)) {
                        antinodes.add(new AbstractMap.SimpleImmutableEntry<>(x1, y1));
                    }

                    int x2 = antenna2.getKey() + antenna2.getKey() - antenna1.getKey();
                    int y2 = antenna2.getValue() + antenna2.getValue() - antenna1.getValue();
                    if (isWithinBounds(map, x2, y2)) {
                        antinodes.add(new AbstractMap.SimpleImmutableEntry<>(x2, y2));
                    }
                }
            }
        }

        return antinodes.size();
    }

    private boolean isWithinBounds(char[][] map, int rowIndex, int columnIndex) {
        return rowIndex >= 0 && rowIndex < map.length && columnIndex >= 0 && columnIndex < map[rowIndex].length;
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        char[][] map = lines.map(String::toCharArray)
                .toArray(char[][]::new);

        Map<Character, List<Map.Entry<Integer, Integer>>> antennas = new HashMap<>();
        for (int x = 0; x < map.length; x++) {
            char[] row = map[x];
            for (int y = 0; y < row.length; y++) {
                char field = row[y];
                if (field != '.') {
                    antennas.computeIfAbsent(field, k -> new ArrayList<>()).add(new AbstractMap.SimpleImmutableEntry<>(x, y));
                }
            }
        }

        Set<Map.Entry<Integer, Integer>> antinodes = new HashSet<>();
        for (var antennaLocations : antennas.values()) {
            for (int i = 0; i < antennaLocations.size(); i++) {
                var antenna1 = antennaLocations.get(i);
                for (int j = i + 1; j < antennaLocations.size(); j++) {
                    var antenna2 = antennaLocations.get(j);

                    int x1Direction = antenna1.getKey() - antenna2.getKey();
                    int y1Direction = antenna1.getValue() - antenna2.getValue();
                    for (int x1 = antenna1.getKey(), y1 = antenna1.getValue(); isWithinBounds(map, x1, y1); x1 += x1Direction, y1 += y1Direction) {
                        antinodes.add(new AbstractMap.SimpleImmutableEntry<>(x1, y1));
                    }

                    int x2Direction = antenna2.getKey() - antenna1.getKey();
                    int y2Direction = antenna2.getValue() - antenna1.getValue();
                    for (int x2 = antenna2.getKey(), y2 = antenna2.getValue(); isWithinBounds(map, x2, y2); x2 += x2Direction, y2 += y2Direction) {
                        antinodes.add(new AbstractMap.SimpleImmutableEntry<>(x2, y2));
                    }
                }
            }
        }

        return antinodes.size();
    }
}
