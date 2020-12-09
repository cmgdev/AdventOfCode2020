package adventOfCode2020.day09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle09 extends AbstractPuzzle {

    int preambleLength;

    public Puzzle09(boolean isTest) {
        super(isTest);
        preambleLength = isTest ? 5 : 25;
    }

    @Override
    public Object solve1() {
        List<Long> encrypted = getInput().stream().map(i -> Long.parseLong(i)).collect(Collectors.toList());

        int result = 0;
        for (int i = 0; i < encrypted.size(); i++) {
            if (!hasSumInRange(encrypted, i, i + preambleLength, i + preambleLength + 1)) {
                result = i + preambleLength + 1;
                break;
            }
        }

        return encrypted.get(result);
    }

    protected boolean hasSumInRange(List<Long> encrypted, int currentMin, int currentMax, int targetIdx) {
        long target = encrypted.get(targetIdx);
        for (int i = currentMin; i < currentMax; i++) {
            for (int j = currentMin + 1; j < currentMax + 1; j++) {
                long a = encrypted.get(i);
                long b = encrypted.get(j);
                if (a != b && a + b == target) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Object solve2() {
        List<Long> encrypted = getInput().stream().map(i -> Long.parseLong(i)).collect(Collectors.toList());

        long target = (Long) solve1();
        int indexOfTarget = encrypted.indexOf(target);
        long sum = 0;
        int currentMinIdx = 0;
        int currentMaxIdx = 1;
        boolean found = false;

        while (!found) {
            for (int i = currentMinIdx; i < indexOfTarget - 1; i++) {
                sum += encrypted.get(i);
                if (i > currentMinIdx) {
                    currentMaxIdx++;
                }
                if (sum > target) {
                    break;
                }
                if (sum == target) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                sum = 0;
                currentMinIdx++;
                currentMaxIdx = currentMinIdx + 1;
            }

            // fail safe
            if (currentMinIdx == indexOfTarget) {
                break;
            }
        }

        System.out.println("nums from " + currentMinIdx + " to " + currentMaxIdx + " = " + sum);
        List<Long> range = new ArrayList<>();
        for (int i = currentMinIdx; i < currentMaxIdx; i++) {
            range.add(encrypted.get(i));
        }
        Collections.sort(range);

        Long low = range.get(0);
        Long high = range.get(range.size() - 1);
        System.out.println("Returning " + low + " + " + high );
        return low + high;
    }

}
