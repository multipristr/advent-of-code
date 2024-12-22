package src.advent2024.day9;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("2333133121414131402", "234567874874903342482349");
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(1928L, 8250);
    }

    @Override
    public List<String> getExampleInput2() {
        return List.of("2333133121414131402");
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(2858L);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        String diskMap = lines.collect(Collectors.joining());
        List<Integer> blockFiles = new ArrayList<>();
        for (int i = 0; i * 2 < diskMap.length(); i++) {
            int files = Character.getNumericValue(diskMap.charAt(i * 2));
            blockFiles.addAll(Collections.nCopies(files, i));
        }

        long checksum = 0;
        int position = 0;
        int head = 0;
        int tail = blockFiles.size() - 1;
        outer:
        for (int i = 1; i < diskMap.length(); i += 2) {
            int files = Character.getNumericValue(diskMap.charAt(i - 1));
            for (int file = 0; file < files; file++) {
                checksum += (long) position * blockFiles.get(head);
                ++head;
                if (head > tail) {
                    break outer;
                }
                ++position;
            }

            int emptySpaces = Character.getNumericValue(diskMap.charAt(i));
            for (int emptySpace = 0; emptySpace < emptySpaces; emptySpace++) {
                checksum += (long) position * blockFiles.get(tail);
                --tail;
                if (head > tail) {
                    break outer;
                }
                ++position;
            }
        }

        return checksum;
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        String diskMap = lines.collect(Collectors.joining());
        List<Map.Entry<Integer, Integer>> blockFiles = new ArrayList<>();
        for (int i = 0; i * 2 < diskMap.length(); i++) {
            int files = Character.getNumericValue(diskMap.charAt(i * 2));
            blockFiles.add(new AbstractMap.SimpleImmutableEntry<>(i, files));
        }

        long checksum = 0;
        int position = 0;
        for (int i = 1; i < diskMap.length(); i += 2) {
            int files = Character.getNumericValue(diskMap.charAt(i - 1));
            var headBlockFiles = blockFiles.get(0);
            if (headBlockFiles.getKey() == i / 2) {
                blockFiles.remove(0);
                for (int file = 0; file < headBlockFiles.getValue(); file++) {
                    checksum += (long) position * headBlockFiles.getKey();
                    ++position;
                }
                if (blockFiles.isEmpty()) {
                    break;
                }
            } else {
                position += files;
            }

            int emptySpaces = Character.getNumericValue(diskMap.charAt(i));
            while (emptySpaces > 0) {
                var tailIterator = blockFiles.listIterator(blockFiles.size());
                var tailBlockFiles = tailIterator.previous();
                while (tailBlockFiles.getValue() > emptySpaces && tailIterator.hasPrevious()) {
                    tailBlockFiles = tailIterator.previous();
                }
                if (tailBlockFiles.getValue() > emptySpaces) {
                    break;
                }
                tailIterator.remove();
                for (int emptySpace = 0; emptySpace < tailBlockFiles.getValue(); emptySpace++) {
                    checksum += (long) position * tailBlockFiles.getKey();
                    ++position;
                }
                emptySpaces -= tailBlockFiles.getValue();
                if (blockFiles.isEmpty()) {
                    break;
                }
            }
            position += emptySpaces;
        }

        return checksum;
    }

}
