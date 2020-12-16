package adventOfCode2020.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        int[] indexesWhereSeen = new int[nth];
        Arrays.fill(indexesWhereSeen, -1);
        for (int i = 0; i < numbers.size(); i++) {
            indexesWhereSeen[numbers.get(i)] = i;
        }

        int lastSeen = numbers.get(numbers.size() - 1);

        for (int numSeen = numbers.size(); numSeen < nth; numSeen++) {
            int lastIndex = numSeen - 1;
            int previousIndex = indexesWhereSeen[lastSeen];
            indexesWhereSeen[lastSeen] = lastIndex;
            lastSeen = previousIndex == -1 ? 0 : lastIndex - previousIndex;
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
