package adventOfCode2020.day08;

import java.util.ArrayList;
import java.util.List;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle08 extends AbstractPuzzle {

    private static final String JMP = "jmp";
    private static final String ACC = "acc";
    private static final String NOP = "nop";

    public Puzzle08(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<String> instructions = getInput();
        int currentLine = 0;
        int accumlatorValue = 0;
        List<Integer> executedLines = new ArrayList<>();

        while (!executedLines.contains(currentLine)) {
            String instruction = instructions.get(currentLine);
            String operation = getInstructionOperation(instruction);
            int arg = getInstructionArg(instruction);
            int nextLine = currentLine;

            if (NOP.equals(operation)) {
                nextLine++;
            } else if (ACC.equals(operation)) {
                accumlatorValue += arg;
                nextLine++;
            } else if (JMP.equals(operation)) {
                nextLine += arg;
            }
            executedLines.add(currentLine);
            currentLine = nextLine;
        }

        System.out.println(accumlatorValue);
        return accumlatorValue;
    }

    @Override
    public Object solve2() {
        List<String> instructions = readFile();
        int instructionCount = instructions.size();
        int currentLine = 0;
        int accumlatorValue = 0;
        List<Integer> executedLines = new ArrayList<>();
        List<Integer> modifiedLines = new ArrayList<>();

        int statusExitSuccess = 0;
        int statusExitInfiniteLoop = 1;
        int statusExitIdxOutOfBounds = 2;
        int statusRunning = 4;
        int currentStatus = statusRunning;

        while (currentStatus == statusRunning) {

            // hit infinite loop, reset counters and flip an instruction
            if (executedLines.contains(currentLine)) {
                System.out.println("Hit infinite loop!");
//                currentStatus = statusExitInfiniteLoop;

                currentLine = 0;
                accumlatorValue = 0;
                instructions = readFile();
                executedLines.clear();

                boolean flippedAnOp = false;

                for (int i = 0; i < instructionCount; i++) {
                    String instruction = instructions.get(i);
                    if (NOP.equals(getInstructionOperation(instruction)) && !modifiedLines.contains(i)) {
                        instruction = instruction.replace(NOP, JMP);
                        flippedAnOp = true;
                    } else if (JMP.equals(getInstructionOperation(instruction)) && !modifiedLines.contains(i)) {
                        instruction = instruction.replace(JMP, NOP);
                        flippedAnOp = true;
                    }
                    if (flippedAnOp) {
                        modifiedLines.add(i);
                        instructions.remove(i);
                        instructions.add(i, instruction);
                        break;
                    }
                }

            } 
            
            if (currentLine == instructionCount) {
                currentStatus = statusExitSuccess;
                break;
            } else if (currentLine > instructionCount) {
                System.out.println("Oh no! Ran over the end with line " + currentLine);
                currentStatus = statusExitIdxOutOfBounds;
                break;
            }

            String instruction = instructions.get(currentLine);
            String operation = getInstructionOperation(instruction);
            int arg = getInstructionArg(instruction);
            int nextLine = currentLine;

            if (NOP.equals(operation)) {
                nextLine++;
            } else if (ACC.equals(operation)) {
                accumlatorValue += arg;
                nextLine++;
            } else if (JMP.equals(operation)) {
                nextLine += arg;
            }
            executedLines.add(currentLine);
            currentLine = nextLine;

        }

        System.out.println(accumlatorValue);
        return accumlatorValue;

    }

    protected String getInstructionOperation(String instruction) {
        return instruction.split(" ")[0];
    }

    protected int getInstructionArg(String instruction) {
        return Integer.parseInt(instruction.split(" ")[1]);
    }

}
