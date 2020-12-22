package adventOfCode2020.day22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle22 extends AbstractPuzzle {

    public Puzzle22(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<String> input = getInput();
        LinkedList<Integer> deck1 = new LinkedList<>();
        LinkedList<Integer> deck2 = new LinkedList<>();
        LinkedList<Integer> currentDeck = deck1;

        for (int i = 1; i < input.size(); i++) {
            String card = input.get(i);
            if (card.matches("^[0-9]+$")) {
                currentDeck.add(Integer.parseInt(card));
            } else {
                currentDeck = deck2;
            }
        }

        int round = 1;
        while (!deck1.isEmpty() && !deck2.isEmpty()) {
            System.out.println("-- Round " + round + " --");
            System.out.println("Player 1's deck: " + deck1);
            System.out.println("Player 2's deck: " + deck2);

            int card1 = deck1.remove();
            int card2 = deck2.remove();

            System.out.println("Player 1 plays: " + card1);
            System.out.println("Player 2 plays: " + card2);
            if (card1 > card2) {
                System.out.println("Player 1 wins the round!");
                deck1.add(card1);
                deck1.add(card2);
            } else {
                System.out.println("Player 2 wins the round!");
                deck2.add(card2);
                deck2.add(card1);
            }

            round++;
        }

        return getScoreOfWinningDeck(deck1, deck2);
    }

    protected long getScoreOfWinningDeck(LinkedList<Integer> deck1, LinkedList<Integer> deck2) {
        LinkedList<Integer> winningDeck = deck1.size() == 0 ? deck2 : deck1;

        int points = 1;
        long total = 0;
        for (int i = winningDeck.size() - 1; i >= 0; i--) {
            total += (points * winningDeck.get(i));
            points++;
        }

        return total;
    }

    @Override
    public Object solve2() {
        List<String> input = getInput();
        LinkedList<Integer> deck1 = new LinkedList<>();
        LinkedList<Integer> deck2 = new LinkedList<>();
        LinkedList<Integer> currentDeck = deck1;

        for (int i = 1; i < input.size(); i++) {
            String card = input.get(i);
            if (card.matches("^[0-9]+$")) {
                currentDeck.add(Integer.parseInt(card));
            } else {
                currentDeck = deck2;
            }
        }

        int winner = playCrabCombat(deck1, deck2);
        System.out.println("Player " + winner + " won!");

        return getScoreOfWinningDeck(deck1, deck2);
    }

    public int playCrabCombat(LinkedList<Integer> deck1, LinkedList<Integer> deck2) {
        Set<List<List<Integer>>> allHands = new HashSet<>();

        while (!deck1.isEmpty() && !deck2.isEmpty()) {

            List<List<Integer>> thisHand = Arrays.asList(new ArrayList<>(deck1), new ArrayList<>(deck2));
            if (allHands.contains(thisHand)) {
                return 1;
            }
            allHands.add(thisHand);

            int card1 = deck1.remove();
            int card2 = deck2.remove();

            boolean player1Wins = false;
            if (card1 > deck1.size() || card2 > deck2.size() ) {
                player1Wins = card1 > card2;
            }

            else {
                LinkedList<Integer> newDeck1 = new LinkedList<>();
                LinkedList<Integer> newDeck2 = new LinkedList<>();
                for (int i = 0; i < card1; i++) {
                    newDeck1.add(deck1.get(i));
                }
                for (int i = 0; i < card2; i++) {
                    newDeck2.add(deck2.get(i));
                }
                player1Wins = playCrabCombat(newDeck1, newDeck2) == 1;
            }

            if (player1Wins) {
                deck1.add(card1);
                deck1.add(card2);
            } else {
                deck2.add(card2);
                deck2.add(card1);
            }

        }

        return deck1.isEmpty() ? 2 : 1;
    }

}
