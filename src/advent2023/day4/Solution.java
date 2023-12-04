package src.advent2023.day4;

import src.PuzzleSolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public String getExampleInput1() {
        return "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53\n" +
                "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19\n" +
                "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1\n" +
                "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83\n" +
                "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36\n" +
                "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11";
    }

    @Override
    public String getExampleOutput1() {
        return "13";
    }

    @Override
    public String getExampleOutput2() {
        return "30";
    }

    @Override
    public String solvePartOne(Stream<String> lines) {
        return lines.mapToInt(line -> {
                    StringTokenizer tokens = new StringTokenizer(line, " ");
                    tokens.nextToken();
                    tokens.nextToken();
                    Set<Integer> winningNumbers = new HashSet<>();
                    Set<Integer> myNumbers = new HashSet<>();
                    while (tokens.hasMoreTokens()) {
                        String number = tokens.nextToken();
                        if ("|".equals(number)) {
                            break;
                        }
                        winningNumbers.add(Integer.parseInt(number));
                    }
                    while (tokens.hasMoreTokens()) {
                        String number = tokens.nextToken();
                        myNumbers.add(Integer.parseInt(number));
                    }
                    myNumbers.retainAll(winningNumbers);
                    return myNumbers.isEmpty() ? 0 : (int) Math.pow(2, myNumbers.size() - 1);
                })
                .sum() + "";
    }

    @Override
    public String solvePartTwo(Stream<String> lines) {
        Map<Integer, List<Integer>> cardWinnings = new TreeMap<>();

        lines.forEach(line -> {
            StringTokenizer tokens = new StringTokenizer(line, " ");
            tokens.nextToken();
            int cardNumber = Integer.parseInt(tokens.nextToken().replaceFirst(":", ""));
            Set<Integer> winningNumbers = new HashSet<>();
            while (tokens.hasMoreTokens()) {
                String number = tokens.nextToken();
                if ("|".equals(number)) {
                    break;
                }
                winningNumbers.add(Integer.parseInt(number));
            }
            int copiesWon = 0;
            while (tokens.hasMoreTokens()) {
                String number = tokens.nextToken();
                if (winningNumbers.contains(Integer.parseInt(number))) {
                    ++copiesWon;
                }
            }
            List<Integer> winnings = new ArrayList<>(copiesWon);
            for (int i = 1; i <= copiesWon; i++) {
                winnings.add(cardNumber + i);
            }
            cardWinnings.put(cardNumber, winnings);
        });

        return cardWinnings.keySet().parallelStream()
                .mapToInt(cardNumber -> countCopies(cardWinnings, cardNumber))
                .sum() + "";
    }

    private int countCopies(Map<Integer, List<Integer>> cardWinnings, int cardNumber) {
        return 1 + cardWinnings.get(cardNumber).stream()
                .mapToInt(cardCopyNumber -> countCopies(cardWinnings, cardCopyNumber))
                .sum();
    }
}
