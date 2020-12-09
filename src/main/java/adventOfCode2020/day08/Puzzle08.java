package adventOfCode2020.day08;

import java.util.List;

import adventOfCode2020.base.AbstractPuzzle;
import adventOfCode2020.base.BootCodeRunner;

public class Puzzle08 extends AbstractPuzzle {

    public Puzzle08(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<String> instructions = getInput();
        
        BootCodeRunner bootCodeRunner = new BootCodeRunner(instructions);
        bootCodeRunner.setStopOnInfiniteLoop(true);
        bootCodeRunner.run();
        
        int accumlatorValue = bootCodeRunner.getAccumulatorValue();

        System.out.println(accumlatorValue);
        return accumlatorValue;
    }

    @Override
    public Object solve2() {
        List<String> instructions = readFile();

        BootCodeRunner bootCodeRunner = new BootCodeRunner(instructions);
        bootCodeRunner.run();
        
        int accumlatorValue = bootCodeRunner.getAccumulatorValue();
        System.out.println(accumlatorValue);
        return accumlatorValue;

    }

}
