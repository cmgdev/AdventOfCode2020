package adventOfCode2020.day10;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle10 extends AbstractPuzzle {

    public Puzzle10(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<Integer> input = getInput().stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        input.add(0);
        Collections.sort(input);

        int ones = 0;
        int threes = 1;

        for (int i = 0; i < input.size() - 1; i++) {
            int diff = input.get(i + 1) - input.get(i);
            ones += diff == 1 ? 1 : 0;
            threes += diff == 3 ? 1 : 0;
        }
        
        return ones * threes;
    }

    @Override
    public Object solve2() {
        // TODO Auto-generated method stub
        return null;
    }

}
