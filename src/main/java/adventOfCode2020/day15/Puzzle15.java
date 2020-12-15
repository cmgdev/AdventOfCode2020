package adventOfCode2020.day15;

import java.util.ArrayList;
import java.util.LinkedList;
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

        while (numbers.size() < 2020) {
            int listSize = numbers.size();
            int last = numbers.get(listSize - 1);
            int lastIndex = listSize - 1;
            for (int i = listSize - 2; i >= 0; i--) {
                int candidate = numbers.get(i);
                if (candidate == last) {
                    lastIndex = i;
                    break;
                }
            }
            if (lastIndex == listSize - 1) {
                numbers.add(0);
            } else {
                numbers.add(listSize - 1 - lastIndex);
            }
        }

        return numbers.get(numbers.size() - 1);
    }

    @Override
    public Object solve2() {
        List<String> input = getInput();

        return null;
    }

}
