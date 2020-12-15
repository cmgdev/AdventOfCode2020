package adventOfCode2020.day15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle15 extends AbstractPuzzle {

    public Puzzle15(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        String[] input = getInput().get(0).split(",");
        List<Integer> numbers = new ArrayList<>();
        for (String i : input) {
            numbers.add(Integer.valueOf(i));
        }

        return getNthNumber(numbers, 2020);
    }

    protected Object getNthNumber(List<Integer> numbers, int nth) {
        Map<Integer, List<Integer>> indexesWhereSeen = new HashMap<>();
        for (int i = 0; i < numbers.size(); i++) {
            List<Integer> indexes = new ArrayList<>();
            indexes.add(i);
            indexesWhereSeen.put(numbers.get(i), indexes);
        }
        
        int numSeen = numbers.size();
        int lastSeen = numbers.get(numSeen - 1);

        while (numSeen < nth) {
            int lastIndex = numSeen - 1;
            
            List<Integer> previousIndexes = indexesWhereSeen.getOrDefault(lastSeen, new ArrayList<>());
            if(previousIndexes.size() == 0) {
                previousIndexes.add(lastIndex);
                indexesWhereSeen.put(lastSeen, previousIndexes);
                lastSeen = 0;
            }
            else {
                int previousIndex = previousIndexes.get(previousIndexes.size() - 1);
                previousIndexes.add(lastIndex);
                indexesWhereSeen.put(lastSeen, previousIndexes);
                lastSeen = lastIndex - previousIndex;
            }
            
            numSeen++;
        }

        return lastSeen;
    }

    @Override
    public Object solve2() {
        String[] input = getInput().get(0).split(",");
        List<Integer> numbers = new ArrayList<>();
        for (String i : input) {
            numbers.add(Integer.valueOf(i));
        }

        return getNthNumber(numbers, 30000000);
    }

}
