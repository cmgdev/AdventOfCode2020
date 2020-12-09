package adventOfCode2020.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BootCodeRunner {

    Instructions instructions;
    CurrentRuntime currentRuntime;
    StatusCode currentStatus;
    boolean stopOnInfiniteLoop = false;

    public BootCodeRunner(List<String> instructions) {
        this.currentStatus = StatusCode.RUNNING;
        this.instructions = new Instructions(instructions);
        this.currentRuntime = new CurrentRuntime(instructions.size());
    }

    public void setStopOnInfiniteLoop(boolean stopOnInfiniteLoop) {
        this.stopOnInfiniteLoop = stopOnInfiniteLoop;
    }

    public void run() {
        instructions.run(currentRuntime, stopOnInfiniteLoop);
    }

    public int getAccumulatorValue() {
        return this.currentRuntime.getAccumlatorValue();
    }

    enum Operation {
        ACC, JMP, NOP;
    }

    enum StatusCode {
        EXIT_SUCCESS, EXIT_INFINITE_LOOP, EXIT_INDEX_OUT_OF_BOUNDS, RUNNING;
    }

    class Instructions {
        List<String> originalInstructions = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();
        Set<Integer> modifiedLines = new HashSet<>();

        public Instructions(List<String> instructions) {
            this.originalInstructions = Collections.unmodifiableList(instructions);
            resetInstructions();
        }

        public void run(CurrentRuntime currentRuntime, boolean stopOnInfiniteLoop) {
            while (!currentRuntime.finishedInstructions()) {
                if (currentRuntime.currentLineAlreadyExecuted()) {
                    if (stopOnInfiniteLoop) {
                        return;
                    }
                    flipInstructions();
                    currentRuntime.reset();
                }
                instructions.get(currentRuntime.getCurrentLine()).run(currentRuntime);
            }
        }

        protected void resetInstructions() {
            this.instructions.clear();
            originalInstructions.forEach(i -> this.instructions.add(new Instruction(i)));
        }

        public void flipInstructions() {
            resetInstructions();

            for (int i = 0; i < instructions.size(); i++) {
                Instruction instruction = instructions.get(i);
                if (Operation.NOP.equals(instruction.operation) && !modifiedLines.contains(i)) {
                    instruction.operation = Operation.JMP;
                    modifiedLines.add(i);
                    return;
                } else if (Operation.JMP.equals(instruction.operation) && !modifiedLines.contains(i)) {
                    instruction.operation = Operation.NOP;
                    modifiedLines.add(i);
                    return;
                }
            }
        }
    }

    class Instruction {
        Operation operation;
        int argument;

        public Instruction(String instruction) {
            this.operation = getInstructionOperation(instruction);
            this.argument = getInstructionArg(instruction);
        }

        protected Operation getInstructionOperation(String instruction) {
            return Operation.valueOf(instruction.split(" ")[0].toUpperCase());
        }

        protected int getInstructionArg(String instruction) {
            return Integer.parseInt(instruction.split(" ")[1]);
        }

        public void run(CurrentRuntime runtime) {
            if (Operation.NOP.equals(operation)) {
                currentRuntime.incrementCurrentLine(1);
            } else if (Operation.ACC.equals(operation)) {
                currentRuntime.incrementAccumlator(argument);
                currentRuntime.incrementCurrentLine(1);
            } else if (Operation.JMP.equals(operation)) {
                currentRuntime.incrementCurrentLine(argument);
            }
        }
    }

    class CurrentRuntime {

        int currentLine = 0;
        int totalLines = 0;
        int accumlatorValue = 0;
        Set<Integer> executedLines = new HashSet<>();

        public CurrentRuntime(int totalLines) {
            this.totalLines = totalLines;
        }

        public void reset() {
            this.currentLine = 0;
            this.accumlatorValue = 0;
            this.executedLines.clear();
        }

        public void incrementCurrentLine(int amount) {
            this.executedLines.add(this.currentLine);
            this.currentLine += amount;
        }

        public void incrementAccumlator(int amount) {
            this.accumlatorValue += amount;
        }

        public int getCurrentLine() {
            return currentLine;
        }

        public int getAccumlatorValue() {
            return this.accumlatorValue;
        }

        public boolean currentLineAlreadyExecuted() {
            return this.executedLines.contains(this.currentLine);
        }

        public boolean finishedInstructions() {
            return this.currentLine == totalLines;
        }

    }
}
