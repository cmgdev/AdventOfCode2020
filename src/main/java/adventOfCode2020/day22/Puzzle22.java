package adventOfCode2020.day22;

import java.util.LinkedList;
import java.util.List;

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

        int totalCards = deck1.size() + deck2.size();
        boolean keepPlaying = true;
        int round = 1;

        while (keepPlaying) {
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
            if (deck1.size() == totalCards || deck2.size() == totalCards) {
                keepPlaying = false;
            }
        }

        LinkedList<Integer> winningDeck = deck1.size() == totalCards ? deck1 : deck2;

        int points = 1;
        long total = 0;
        for( int i = winningDeck.size() - 1; i >= 0; i--) {
            total += (points * winningDeck.get(i));
            points++;
        }

        return total;
    }

    @Override
    public Object solve2() {
        // TODO Auto-generated method stub
        return null;
    }

}
