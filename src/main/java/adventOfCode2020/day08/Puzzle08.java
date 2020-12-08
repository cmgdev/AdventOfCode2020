package adventOfCode2020.day08;

import java.util.ArrayList;
import java.util.List;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle08 extends AbstractPuzzle {

    public Puzzle08(boolean isTest) {
        super(isTest);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Object solve1() {
        List<String> instructions = getInput();
        int currentLine = 0;
        int accumlatorValue = 0;
        List<Integer> executedLines = new ArrayList<>();

        while (!executedLines.contains(currentLine)) {
            String instruction = instructions.get(currentLine);
            String operation = instruction.split(" ")[0];
            int arg = Integer.parseInt(instruction.split(" ")[1]);
            int nextLine = currentLine;

            if ("nop".equals(operation)) {
                nextLine++;
            } else if ("acc".equals(operation)) {
                accumlatorValue += arg;
                nextLine++;
            } else if ("jmp".equals(operation)) {
                nextLine += arg;
            }

            executedLines.add(currentLine);
            currentLine = nextLine;

        }

        return accumlatorValue;
    }

    @Override
    public Object solve2() {
        // TODO Auto-generated method stub
        return null;
    }

}
