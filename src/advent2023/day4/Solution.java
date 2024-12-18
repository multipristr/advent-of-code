package src.advent2023.day4;

import src.PuzzleSolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53\n" +
                "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19\n" +
                "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1\n" +
                "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83\n" +
                "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36\n" +
                "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11");
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(13L);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of(30L);
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
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
                .sum();
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        Map<Integer, Integer> cardWinnings = new HashMap<>();
        return lines.mapToInt(line -> {
            StringTokenizer tokens = new StringTokenizer(line, " ");
            tokens.nextToken();
            int cardNumber = Integer.parseInt(tokens.nextToken().replaceFirst(":", ""));
            int cardAmount = cardWinnings.merge(cardNumber, 1, Integer::sum);
            Set<Integer> winningNumbers = new HashSet<>();
            for (String token = tokens.nextToken(); tokens.hasMoreTokens() && !"|".equals(token); token = tokens.nextToken()) {
                winningNumbers.add(Integer.parseInt(token));
            }
            int copiesWon = 0;
            while (tokens.hasMoreTokens()) {
                String number = tokens.nextToken();
                if (winningNumbers.contains(Integer.parseInt(number))) {
                    ++copiesWon;
                    cardWinnings.merge(cardNumber + copiesWon, cardAmount, Integer::sum);
                }
            }
            return cardAmount;
        }).sum();
    }
}
