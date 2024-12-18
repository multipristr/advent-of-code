package src.advent2023.day3;

import src.PuzzleSolver;

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
        return List.of("467..114..\n" +
                "...*......\n" +
                "..35..633.\n" +
                "......#...\n" +
                "617*......\n" +
                ".....+.58.\n" +
                "..592.....\n" +
                "......755.\n" +
                "...$.*....\n" +
                ".664.598..");
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(4361L);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(467835L);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        char[][] schematic = lines.map(String::toCharArray)
                .toArray(char[][]::new);
        int partNumberSum = 0;
        for (int row = 0; row < schematic.length; row++) {
            char[] schematicRow = schematic[row];
            StringBuilder partNumber = new StringBuilder(schematic[0].length);
            boolean adjacentToSymbol = false;
            for (int column = 0; column < schematicRow.length; column++) {
                char entry = schematicRow[column];
                if (Character.isDigit(entry)) {
                    partNumber.append(entry);
                    adjacentToSymbol |= isSymbolAt(schematic, row - 1, column - 1) ||
                            isSymbolAt(schematic, row - 1, column) ||
                            isSymbolAt(schematic, row - 1, column + 1) ||
                            isSymbolAt(schematic, row, column - 1) ||
                            isSymbolAt(schematic, row + 1, column - 1) ||
                            isSymbolAt(schematic, row + 1, column) ||
                            isSymbolAt(schematic, row + 1, column + 1);
                } else if (entry == '.') {
                    if (adjacentToSymbol && partNumber.length() > 0) {
                        partNumberSum += Integer.parseInt(partNumber.toString());
                    }
                    adjacentToSymbol = false;
                    partNumber.setLength(0);
                } else {
                    if (partNumber.length() > 0) {
                        partNumberSum += Integer.parseInt(partNumber.toString());
                    }
                    adjacentToSymbol = false;
                    partNumber.setLength(0);
                }
            }
            if (adjacentToSymbol && partNumber.length() > 0) {
                partNumberSum += Integer.parseInt(partNumber.toString());
            }
        }
        return partNumberSum;
    }

    private boolean isSymbolAt(char[][] schematic, int row, int column) {
        if (row < 0 || row >= schematic.length || column < 0) {
            return false;
        }
        char[] schematicRow = schematic[row];
        if (column >= schematicRow.length) {
            return false;
        }
        char entry = schematicRow[column];
        return !Character.isDigit(entry) && entry != '.';
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        char[][] schematic = lines.map(String::toCharArray)
                .toArray(char[][]::new);
        Map<String, List<Integer>> gearAdjacent = new HashMap<>();
        for (int row = 0; row < schematic.length; row++) {
            char[] schematicRow = schematic[row];
            StringBuilder partNumber = new StringBuilder(schematic[0].length);
            Set<String> adjacentGears = new HashSet<>();
            for (int column = 0; column < schematicRow.length; column++) {
                char entry = schematicRow[column];
                if (Character.isDigit(entry)) {
                    partNumber.append(entry);
                    addGearAt(adjacentGears, schematic, row - 1, column - 1);
                    addGearAt(adjacentGears, schematic, row - 1, column);
                    addGearAt(adjacentGears, schematic, row - 1, column + 1);
                    addGearAt(adjacentGears, schematic, row, column - 1);
                    addGearAt(adjacentGears, schematic, row + 1, column - 1);
                    addGearAt(adjacentGears, schematic, row + 1, column);
                    addGearAt(adjacentGears, schematic, row + 1, column + 1);
                } else if (entry == '*') {
                    if (partNumber.length() > 0) {
                        adjacentGears.add(row + "," + column);
                        adjacentGears.forEach(gear -> gearAdjacent.computeIfAbsent(gear, k -> new ArrayList<>()).add(Integer.parseInt(partNumber.toString())));
                        partNumber.setLength(0);
                    }
                    adjacentGears.clear();
                } else {
                    if (partNumber.length() > 0) {
                        adjacentGears.forEach(gear -> gearAdjacent.computeIfAbsent(gear, k -> new ArrayList<>()).add(Integer.parseInt(partNumber.toString())));
                        partNumber.setLength(0);
                    }
                    adjacentGears.clear();
                }
            }
            if (partNumber.length() > 0) {
                adjacentGears.forEach(gear -> gearAdjacent.computeIfAbsent(gear, k -> new ArrayList<>()).add(Integer.parseInt(partNumber.toString())));
            }
        }

        return gearAdjacent.values().stream()
                .filter(values -> values.size() == 2)
                .mapToInt(values -> values.get(0) * values.get(1))
                .sum();
    }

    private void addGearAt(Set<String> adjacentGears, char[][] schematic, int row, int column) {
        if (row < 0 || row >= schematic.length || column < 0) {
            return;
        }
        char[] schematicRow = schematic[row];
        if (column >= schematicRow.length) {
            return;
        }
        if (schematicRow[column] == '*') {
            adjacentGears.add(row + "," + column);
        }
    }
}
