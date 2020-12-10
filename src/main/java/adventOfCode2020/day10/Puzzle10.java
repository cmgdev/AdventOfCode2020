package adventOfCode2020.day10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle10 extends AbstractPuzzle {

    public Puzzle10(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<Integer> input = getAllSortedInts();

        int ones = 0;
        int twos = 0;
        int threes = 0;

        for (int i = 0; i < input.size() - 1; i++) {
            int diff = input.get(i + 1) - input.get(i);
            ones += diff == 1 ? 1 : 0;
            twos += diff == 2 ? 1 : 0;
            threes += diff == 3 ? 1 : 0;
        }

        System.out.println("twos: " + twos);
        return ones * threes;
    }

    private List<Integer> getAllSortedInts() {
        List<Integer> input = getInput().stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        input.add(0);
        Collections.sort(input);
        int max = input.get(input.size() - 1);
        input.add(input.size(), max + 3);
        return input;
    }

    @Override
    public Object solve2() {
        List<Integer> input = getAllSortedInts();
        List<List<Integer>> chunks = new ArrayList<>();
        Set<Integer> currentChunk = new HashSet<>();

        // I could have kept just the size of each chunk in one list
        // but this was easier to debug
        for (int i = 0; i < input.size() - 1; i++) {
            Integer b = input.get(i + 1);
            Integer a = input.get(i);
            int diff = b - a;
            if (diff == 1) {
                currentChunk.add(a);
                currentChunk.add(b);
            } else {
                chunks.add(new ArrayList<>(currentChunk));
                currentChunk.clear();
                currentChunk.add(b);
            }
        }

        chunks.add(new ArrayList<>(currentChunk));

        // there is definitely an algorithmic way to figure out how many combos 
        // there are for each contiguous chunk, but I don't know it and these
        // are small enough to work out by hand based on the example data
        long combos = 1;
        for (List<Integer> chunk : chunks) {
            int size = chunk.size();
            if (size == 3) {
                combos *= 2;
            } else if (size == 4) {
                combos *= 4;
            } else if (size == 5) {
                combos *= 7;
            } else if (size > 5) {
                System.out.println("NEED A NEW CALC!!!!!");
                System.out.println("size is " + size);
            }
        }
        return combos;
    }

}
