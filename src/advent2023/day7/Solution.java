package src.advent2023.day7;

import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import src.PuzzleSolver;

public class Solution extends PuzzleSolver {
  private static final Map<Character, Integer> CARD_ORDER1 = new HashMap<>(13);
  private static final Map<Character, Integer> CARD_ORDER2 = new HashMap<>(13);
  static {
    CARD_ORDER1.put('A', 1);
    CARD_ORDER1.put('K', 2);
    CARD_ORDER1.put('Q', 3);
    CARD_ORDER1.put('J', 4);
    CARD_ORDER1.put('T', 5);
    CARD_ORDER1.put('9', 6);
    CARD_ORDER1.put('8', 7);
    CARD_ORDER1.put('7', 8);
    CARD_ORDER1.put('6', 9);
    CARD_ORDER1.put('5', 10);
    CARD_ORDER1.put('4', 11);
    CARD_ORDER1.put('3', 12);
    CARD_ORDER1.put('2', 13);

    CARD_ORDER2.put('A', 1);
    CARD_ORDER2.put('K', 2);
    CARD_ORDER2.put('Q', 3);
    CARD_ORDER2.put('T', 4);
    CARD_ORDER2.put('9', 5);
    CARD_ORDER2.put('8', 6);
    CARD_ORDER2.put('7', 7);
    CARD_ORDER2.put('6', 8);
    CARD_ORDER2.put('5', 9);
    CARD_ORDER2.put('4', 10);
    CARD_ORDER2.put('3', 11);
    CARD_ORDER2.put('2', 12);
    CARD_ORDER2.put('J', 13);
  }

  public static void main(String[] args) {
    new Solution().run();
  }

  @Override
  public String getExampleInput1() {
    return "32T3K 765\n"
        + "T55J5 684\n"
        + "KK677 28\n"
        + "KTJJT 220\n"
        + "QQQJA 483\n";
  }

  @Override
  public String getExampleInput2() {
    return "2345A 2\n"
        + "2345J 5\n"
        + "J345A 3\n"
        + "32T3K 7\n"
        + "T55J5 17\n"
        + "KK677 11\n"
        + "KTJJT 23\n"
        + "QQQJA 19\n"
        + "JJJJJ 29\n"
        + "JAAAA 37\n"
        + "AAAAJ 43\n"
        + "AAAAA 53\n"
        + "2AAAA 13\n"
        + "2JJJJ 41\n"
        + "JJJJ2 31";
  }

  @Override
  public String getExampleOutput1() {
    return "6440";
  }

  @Override
  public String getExampleOutput2() {
    return "3667";
  }

  @Override
  public String solvePartOne(Stream<String> lines) {
    AtomicInteger index = new AtomicInteger(1);
    return lines.map(CardHand1::new).sorted().mapToInt(cardHand -> cardHand.getBid() * index.getAndIncrement()).sum() + "";
  }

  @Override
  public String solvePartTwo(Stream<String> lines) {
    AtomicInteger index = new AtomicInteger(1);
    List<CardHand2> cards = lines.map(CardHand2::new).sorted().collect(Collectors.toList());
    return cards.stream().mapToInt(cardHand -> cardHand.getBid() * index.getAndIncrement()).sum() + "";
  }

  private static final class CardHand1 implements Comparable<CardHand1> {
    private final char[] cards;
    private final int handTypeOrder;
    private final int bid;

    CardHand1(String line) {
      String[] parts = line.split("\\s");
      cards = parts[0].toCharArray();
      Map<Character, Long> cardOccurrence = parts[0].chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
      if (cardOccurrence.containsValue(5L)) {
        handTypeOrder = 1;
      } else if (cardOccurrence.containsValue(4L)) {
        handTypeOrder = 2;
      } else if (cardOccurrence.containsValue(3L)) {
        if (cardOccurrence.containsValue(2L)) {
          handTypeOrder = 3;
        } else {
          handTypeOrder = 4;
        }
      } else if (cardOccurrence.containsValue(2L)) {
        handTypeOrder = (int) (7 - cardOccurrence.values().stream().filter(occurrence -> occurrence == 2).count());
      } else {
        handTypeOrder = 7;
      }
      bid = Integer.parseInt(parts[1]);
    }

    public int getBid() {
      return bid;
    }

    @Override
    public int compareTo(CardHand1 that) {
      if (handTypeOrder != that.handTypeOrder) {
        return that.handTypeOrder - handTypeOrder;
      }
      for (int i = 0; i < cards.length; i++) {
        char thisCard = cards[i];
        char thatCard = that.cards[i];
        if (thisCard != thatCard) {
          return CARD_ORDER1.get(thatCard) - CARD_ORDER1.get(thisCard);
        }
      }
      return 0;
    }

    @Override
    public String toString() {
      return CharBuffer.wrap(cards) + " " + bid;
    }
  }

  private static final class CardHand2 implements Comparable<CardHand2> {
    private final char[] cards;
    private final int handTypeOrder;
    private final int bid;

    CardHand2(String line) {
      String[] parts = line.split("\\s");
      cards = parts[0].toCharArray();
      Map<Character, Long> cardOccurrence = parts[0].chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
      long jokerCount = cardOccurrence.getOrDefault('J', 0L);
      if (cardOccurrence.containsValue(5L)) {
        handTypeOrder = 1;
      } else if (cardOccurrence.containsValue(4L)) {
        handTypeOrder = jokerCount > 0 ? 1 : 2;
      } else if (cardOccurrence.containsValue(3L)) {
        if (cardOccurrence.containsValue(2L)) {
          handTypeOrder = jokerCount > 0 ? 1 : 3;
        } else {
          handTypeOrder = jokerCount > 0 ? 2 : 4;
        }
      } else if (cardOccurrence.containsValue(2L)) {
        var pairs = cardOccurrence.entrySet().stream()
            .filter(entry -> entry.getValue() == 2L)
            .map(Entry::getKey)
            .collect(Collectors.toSet());
        if (pairs.size() == 1) {
          handTypeOrder = jokerCount > 0 ? 4 : 6;
        } else {
          if (pairs.contains('J')) {
            handTypeOrder = 2;
          } else {
            handTypeOrder = jokerCount > 0 ? 3 : 5;
          }
        }
      } else {
        handTypeOrder = jokerCount > 0 ? 6 : 7;
      }
      bid = Integer.parseInt(parts[1]);
    }

    public int getBid() {
      return bid;
    }

    @Override
    public int compareTo(CardHand2 that) {
      if (handTypeOrder != that.handTypeOrder) {
        return that.handTypeOrder - handTypeOrder;
      }
      for (int i = 0; i < cards.length; i++) {
        char thisCard = cards[i];
        char thatCard = that.cards[i];
        if (thisCard != thatCard) {
          return CARD_ORDER2.get(thatCard) - CARD_ORDER2.get(thisCard);
        }
      }
      return 0;
    }

    @Override
    public String toString() {
      return CharBuffer.wrap(cards) + " " + bid;
    }
  }
}
