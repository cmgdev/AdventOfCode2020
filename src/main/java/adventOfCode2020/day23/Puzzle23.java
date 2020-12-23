package adventOfCode2020.day23;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle23 extends AbstractPuzzle {

    public Puzzle23(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        String startingString = getInput().get(0);

        List<Integer> cups = new ArrayList<>();
        for (char c : startingString.toCharArray()) {
            cups.add(Integer.parseInt(String.valueOf(c)));
        }

        int numMoves = 100;
        int numCups = cups.size();
        int currentIndex = 0;

        for (int move = 1; move <= numMoves; move++) {
            int pickup1 = (currentIndex + 1) % numCups;
            int pickup2 = (currentIndex + 2) % numCups;
            int pickup3 = (currentIndex + 3) % numCups;
            int currentCup = cups.get(currentIndex);

            System.out.println("-- move " + move + " --");
            System.out.println("cups: " + cups);

            List<Integer> pickedUp = new ArrayList<>();
            pickedUp.add(cups.get(pickup1));
            pickedUp.add(cups.get(pickup2));
            pickedUp.add(cups.get(pickup3));
            System.out.println("pick up: " + pickedUp);
            System.out.println("current cup: " + currentCup);

            int destinationCup = (cups.get(currentIndex) - 1) % numCups;
            destinationCup = destinationCup > 0 ? destinationCup : numCups;
            while (pickedUp.contains(destinationCup)) {
                destinationCup = (destinationCup - 1) % numCups;
                destinationCup = destinationCup > 0 ? destinationCup : numCups;
            }
            System.out.println("destination: " + destinationCup);

            cups.removeAll(pickedUp);
            int insertIndex = (cups.indexOf(destinationCup) + 1) % numCups;
            cups.addAll(insertIndex, pickedUp);
            int newCurrentCupIndex = cups.indexOf(currentCup);
            currentIndex = (newCurrentCupIndex + 1) % numCups;
        }

        while (cups.indexOf(1) != 0) {
            Collections.rotate(cups, 1);
        }
        String finalOrder = cups.subList(1, cups.size()).stream().map(i -> String.valueOf(i)).collect(Collectors.joining());
        System.out.println(finalOrder);
        return finalOrder;
    }

    @Override
    public Object solve2() {
        // TODO Auto-generated method stub
        return null;
    }

}
