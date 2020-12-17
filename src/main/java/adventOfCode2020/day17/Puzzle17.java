package adventOfCode2020.day17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle17 extends AbstractPuzzle {

    public static final char ACTIVE = '#';
    public static final char INACTIVE = '.';

    public Puzzle17(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<String> input = readFile("//", false);

        int rows = input.size();
        Map<Point, Character> cube = new HashMap<>();

        for (int r = 0; r < rows; r++) {
            String line = input.get(r);
            for (int c = 0; c < line.length(); c++) {
                cube.put(new Point(0, r, c), line.charAt(c));
            }
        }

        int cycleNum = 0;
        printCube(cube, cycleNum);

        while (cycleNum < 6) {
            cycleNum++;
            List<Point> changedPoints = new ArrayList<>();
            expandCube(cube);
            List<Entry<Point, Character>> pointsAtStart = new ArrayList<>(cube.entrySet());

            for (Map.Entry<Point, Character> pointContent : pointsAtStart) {
                Point point = pointContent.getKey();
                char content = pointContent.getValue();
                long activeNeighbors = point.getSurroundingPoints().stream().map(neighborPoint -> getCharAtPoint(cube, neighborPoint)).filter(c -> c == ACTIVE).count();

                if (content == ACTIVE && !(activeNeighbors == 2 || activeNeighbors == 3)) {
                    changedPoints.add(point);
                } else if (content == INACTIVE && activeNeighbors == 3) {
                    changedPoints.add(point);
                }
            }

            for (Point point : changedPoints) {
                char newChar = getCharAtPoint(cube, point) == ACTIVE ? INACTIVE : ACTIVE;
                cube.put(point, newChar);
            }

            printCube(cube, cycleNum);
        }

        return cube.values().stream().filter(c -> c == ACTIVE).count();
    }

    public String getCoordinates(int z, int row, int col) {
        return z + "," + row + "," + col;
    }

    public void expandCube(Map<Point, Character> cube) {
        IntSummaryStatistics zStats = cube.keySet().stream().mapToInt(p -> p.z).summaryStatistics();
        IntSummaryStatistics rStats = cube.keySet().stream().mapToInt(p -> p.row).summaryStatistics();
        IntSummaryStatistics cStats = cube.keySet().stream().mapToInt(p -> p.col).summaryStatistics();

        for (int z = zStats.getMin() - 1; z <= zStats.getMax() + 1; z++) {
            for (int r = rStats.getMin() - 1; r <= rStats.getMax() + 1; r++) {
                for (int c = cStats.getMin() - 1; c <= cStats.getMax() + 1; c++) {
                    getCharAtPoint(cube, z, r, c);
                }
            }
        }
    }

    public void printCube(Map<Point, Character> cube, int cycleNum) {
        IntSummaryStatistics zStats = cube.keySet().stream().mapToInt(p -> p.z).summaryStatistics();
        IntSummaryStatistics rStats = cube.keySet().stream().mapToInt(p -> p.row).summaryStatistics();
        IntSummaryStatistics cStats = cube.keySet().stream().mapToInt(p -> p.col).summaryStatistics();

        System.out.println("After " + cycleNum + " cycles");
        for (int z = zStats.getMin(); z <= zStats.getMax(); z++) {
            System.out.println("z=" + z);
            for (int r = rStats.getMin(); r <= rStats.getMax(); r++) {
                for (int c = cStats.getMin(); c <= cStats.getMax(); c++) {
                    Character content = getCharAtPoint(cube, z, r, c);
                    System.out.print(content);
                }
                System.out.println();
            }
        }
    }

    protected Character getCharAtPoint(Map<Point, Character> cube, int z, int r, int c) {
        return getCharAtPoint(cube, new Point(z, r, c));
    }

    protected Character getCharAtPoint(Map<Point, Character> cube, Point point) {
        return cube.computeIfAbsent(point, p -> INACTIVE);
    }

    @Override
    public Object solve2() {
        List<String> input = getInput();
        return null;
    }

    class Point {
        int z;
        int row;
        int col;

        public Point(int z, int row, int col) {
            this.z = z;
            this.row = row;
            this.col = col;
        }

        public List<Point> getSurroundingPoints() {
            List<Point> points = new ArrayList<>();

            for (int z = this.z - 1; z <= this.z + 1; z++) {
                for (int r = row - 1; r <= row + 1; r++) {
                    for (int c = col - 1; c <= col + 1; c++) {
                        if (!(z == this.z && r == row && c == col)) {
                            points.add(new Point(z, r, c));
                        }
                    }
                }
            }

            return points;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + col;
            result = prime * result + row;
            result = prime * result + z;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Point other = (Point) obj;
            if (col != other.col)
                return false;
            if (row != other.row)
                return false;
            if (z != other.z)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Point [z=" + z + ", row=" + row + ", col=" + col + "]";
        }

    }

}
