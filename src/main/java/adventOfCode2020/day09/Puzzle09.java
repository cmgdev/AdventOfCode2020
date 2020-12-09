package adventOfCode2020.day09;

import java.util.List;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle09 extends AbstractPuzzle {

    int preambleLength;

    public Puzzle09(boolean isTest) {
        super(isTest);
        preambleLength = isTest ? 5 : 25;
    }

    @Override
    public Object solve1() {
        List<String> input = getInput();

        int result = 0;
        for (int i = 0; i < input.size(); i++) {
            if (!hasSumInRange(input, i, i + preambleLength, i + preambleLength + 1)) {
                result = i + preambleLength + 1;
                break;
            }
        }

        return Long.parseLong(input.get(result));
    }

    protected boolean hasSumInRange(List<String> input, int currentMin, int currentMax, int targetIdx) {
        long target = Long.parseLong(input.get(targetIdx));
        for (int i = currentMin; i < currentMax; i++) {
            for (int j = currentMin + 1; j < currentMax + 1; j++) {
                long a = Long.parseLong(input.get(i));
                long b = Long.parseLong(input.get(j));
                if (a != b && a + b == target) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Object solve2() {
        List<String> input = getInput();

        return null;
    }

}
