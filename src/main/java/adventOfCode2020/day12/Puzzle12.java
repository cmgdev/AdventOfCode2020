package adventOfCode2020.day12;

import java.util.List;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle12 extends AbstractPuzzle {

    public Puzzle12(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<String> input = getInput();

        int row = 0;
        int col = 0;

        int currentAngle = 0;

        for (String instruction : input) {
            char action = instruction.charAt(0);
            int value = Integer.parseInt(instruction.substring(1));

            /*
             * N S E W L R F
             */
            if (action == 'N') {
                row += value;
            } else if (action == 'S') {
                row -= value;
            } else if (action == 'E') {
                col += value;
            } else if (action == 'W') {
                col -= value;
            } else if (action == 'L') {
                currentAngle = (currentAngle - value) % 360;
            } else if (action == 'R') {
                currentAngle = (currentAngle + value) % 360;
            } else if (action == 'F') {
                // EAST
                if (currentAngle == 0) {
                    col += value;
                }
                // SOUTH
                else if (currentAngle == 90) {
                    row -= value;
                }
                // WEST
                else if (currentAngle == 180) {
                    col -= value;
                }
                // NORTH
                else if (currentAngle == 270) {
                    row += value;
                }
            }
            if (currentAngle < 0) {
                currentAngle += 360;
            }
            System.out.println("Row [" + row + "] Col [" + col + "] with angle [" + currentAngle + "]");
        }

        return Math.abs(row) + Math.abs(col);
    }

    @Override
    public Object solve2() {
        List<String> input = getInput();
        return null;
    }

}
