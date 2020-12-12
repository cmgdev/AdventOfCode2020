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
        int shipRow = 0;
        int shipCol = 0;
        int waypointRow = 1;
        int waypointCol = 10;

        for (String instruction : input) {
            char action = instruction.charAt(0);
            int value = Integer.parseInt(instruction.substring(1));
            int waypointAngle = 0;

            if (action == 'N') {
                waypointRow += value;
            } else if (action == 'S') {
                waypointRow -= value;
            } else if (action == 'E') {
                waypointCol += value;
            } else if (action == 'W') {
                waypointCol -= value;
            } else if (action == 'F') {
                int rowDiff = waypointRow - shipRow;
                int colDiff = waypointCol - shipCol;
                
                for (int i = 0; i < value; i++) {
                    shipRow += rowDiff;
                    waypointRow += rowDiff;
                    shipCol += colDiff;
                    waypointCol += colDiff;
                }
            } else if (action == 'L') {
                waypointAngle = value;
            } else if (action == 'R') {
                waypointAngle = 360 - value;
            }

            if (waypointAngle != 0) {
                double sin = Math.sin(Math.toRadians(waypointAngle));
                double cos = Math.cos(Math.toRadians(waypointAngle));
                int rowDiff = waypointRow - shipRow;
                int colDiff = waypointCol - shipCol;

                // https://en.wikipedia.org/wiki/Rotation_%28mathematics%29#Two_dimensions
                // x' = xcosθ - ysinθ
                // y' = xsinθ + ycosθ
                double newWaypointCol = (colDiff * cos) - (rowDiff * sin);
                double newWaypointRow = (colDiff * sin) + (rowDiff * cos);
                waypointCol = (int) (Math.round(newWaypointCol) + shipCol);
                waypointRow = (int) (Math.round(newWaypointRow) + shipRow);
            }

            System.out.println("----------------");
            System.out.println("Instruction: " + instruction );
            System.out.println("Wypt: Row [" + waypointRow + "] Col [" + waypointCol + "]");
            System.out.println("Ship: Row [" + shipRow + "] Col [" + shipCol + "]");
            System.out.println("----------------");
        }

        return Math.abs(shipRow) + Math.abs(shipCol);
    }

}
