package adventOfCode2020.day17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
        Map<ThreeDPoint, Character> cube = new HashMap<>();

        for (int r = 0; r < rows; r++) {
            String line = input.get(r);
            for (int c = 0; c < line.length(); c++) {
                cube.put(new ThreeDPoint(0, r, c), line.charAt(c));
            }
        }

        int cycleNum = 0;
        printCube(cube, cycleNum);

        while (cycleNum < 6) {
            cycleNum++;
            List<ThreeDPoint> changedPoints = new ArrayList<>();
            expandCube(cube);
            List<Entry<ThreeDPoint, Character>> pointsAtStart = new ArrayList<>(cube.entrySet());

            for (Map.Entry<ThreeDPoint, Character> pointContent : pointsAtStart) {
                ThreeDPoint point = pointContent.getKey();
                char content = pointContent.getValue();
                long activeNeighbors = point.getSurroundingPoints().stream().map(neighborPoint -> getCharAtPoint(cube, neighborPoint)).filter(c -> c == ACTIVE).count();

                if (content == ACTIVE && !(activeNeighbors == 2 || activeNeighbors == 3)) {
                    changedPoints.add(point);
                } else if (content == INACTIVE && activeNeighbors == 3) {
                    changedPoints.add(point);
                }
            }

            for (ThreeDPoint point : changedPoints) {
                char newChar = getCharAtPoint(cube, point) == ACTIVE ? INACTIVE : ACTIVE;
                cube.put(point, newChar);
            }

            printCube(cube, cycleNum);
        }

        return cube.values().stream().filter(c -> c == ACTIVE).count();
    }

    public void expandCube(Map<ThreeDPoint, Character> cube) {
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

    public void expandHyperCube(Map<FourDPoint, Character> hypercube) {
        IntSummaryStatistics zStats = hypercube.keySet().stream().mapToInt(p -> p.z).summaryStatistics();
        IntSummaryStatistics wStats = hypercube.keySet().stream().mapToInt(p -> p.w).summaryStatistics();
        IntSummaryStatistics rStats = hypercube.keySet().stream().mapToInt(p -> p.row).summaryStatistics();
        IntSummaryStatistics cStats = hypercube.keySet().stream().mapToInt(p -> p.col).summaryStatistics();

        for (int z = zStats.getMin() - 1; z <= zStats.getMax() + 1; z++) {
            for (int w = wStats.getMin() - 1; w <= wStats.getMax() + 1; w++) {
                for (int r = rStats.getMin() - 1; r <= rStats.getMax() + 1; r++) {
                    for (int c = cStats.getMin() - 1; c <= cStats.getMax() + 1; c++) {
                        getCharAtHyperPoint(hypercube, z, w, r, c);
                    }
                }
            }
        }
    }

    public void printCube(Map<ThreeDPoint, Character> cube, int cycleNum) {
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

    public void printHyperCube(Map<FourDPoint, Character> hypercube, int cycleNum) {
        IntSummaryStatistics zStats = hypercube.keySet().stream().mapToInt(p -> p.z).summaryStatistics();
        IntSummaryStatistics wStats = hypercube.keySet().stream().mapToInt(p -> p.w).summaryStatistics();
        IntSummaryStatistics rStats = hypercube.keySet().stream().mapToInt(p -> p.row).summaryStatistics();
        IntSummaryStatistics cStats = hypercube.keySet().stream().mapToInt(p -> p.col).summaryStatistics();

        System.out.println("After " + cycleNum + " cycles");
        for (int z = zStats.getMin(); z <= zStats.getMax(); z++) {
            for (int w = wStats.getMin(); w <= wStats.getMax(); w++) {
                System.out.println("z=" + z + ", w=" + w);
                for (int r = rStats.getMin(); r <= rStats.getMax(); r++) {
                    for (int c = cStats.getMin(); c <= cStats.getMax(); c++) {
                        Character content = getCharAtHyperPoint(hypercube, z, w, r, c);
                        System.out.print(content);
                    }
                    System.out.println();
                }
            }
        }
    }

    protected Character getCharAtPoint(Map<ThreeDPoint, Character> cube, int z, int r, int c) {
        return getCharAtPoint(cube, new ThreeDPoint(z, r, c));
    }

    protected Character getCharAtPoint(Map<ThreeDPoint, Character> cube, ThreeDPoint point) {
        return cube.computeIfAbsent(point, p -> INACTIVE);
    }

    protected Character getCharAtHyperPoint(Map<FourDPoint, Character> hypercube, int z, int w, int r, int c) {
        return getCharAtHyperPoint(hypercube, new FourDPoint(z, w, r, c));
    }

    protected Character getCharAtHyperPoint(Map<FourDPoint, Character> cube, FourDPoint point) {
        return cube.computeIfAbsent(point, p -> INACTIVE);
    }

    @Override
    public Object solve2() {
        List<String> input = readFile("//", false);

        int rows = input.size();
        Map<FourDPoint, Character> hypercube = new HashMap<>();

        for (int r = 0; r < rows; r++) {
            String line = input.get(r);
            for (int c = 0; c < line.length(); c++) {
                hypercube.put(new FourDPoint(0, 0, r, c), line.charAt(c));
            }
        }

        int cycleNum = 0;
        printHyperCube(hypercube, cycleNum);

        while (cycleNum < 6) {
            cycleNum++;
            List<FourDPoint> changedPoints = new ArrayList<>();
            expandHyperCube(hypercube);
            List<Entry<FourDPoint, Character>> pointsAtStart = new ArrayList<>(hypercube.entrySet());

            for (Map.Entry<FourDPoint, Character> pointContent : pointsAtStart) {
                FourDPoint point = pointContent.getKey();
                char content = pointContent.getValue();
                long activeNeighbors = point.getSurrounding4DPoints().stream().map(neighborPoint -> getCharAtHyperPoint(hypercube, neighborPoint)).filter(c -> c == ACTIVE).count();

                if (content == ACTIVE && !(activeNeighbors == 2 || activeNeighbors == 3)) {
                    changedPoints.add(point);
                } else if (content == INACTIVE && activeNeighbors == 3) {
                    changedPoints.add(point);
                }
            }

            for (FourDPoint point : changedPoints) {
                char newChar = getCharAtHyperPoint(hypercube, point) == ACTIVE ? INACTIVE : ACTIVE;
                hypercube.put(point, newChar);
            }

        }

        printHyperCube(hypercube, cycleNum);
        return hypercube.values().stream().filter(c -> c == ACTIVE).count();
    }

    class ThreeDPoint {
        int z;
        int row;
        int col;

        public ThreeDPoint(int z, int row, int col) {
            this.z = z;
            this.row = row;
            this.col = col;
        }

        public List<ThreeDPoint> getSurroundingPoints() {
            List<ThreeDPoint> points = new ArrayList<>();

            for (int z = this.z - 1; z <= this.z + 1; z++) {
                for (int r = row - 1; r <= row + 1; r++) {
                    for (int c = col - 1; c <= col + 1; c++) {
                        if (!(z == this.z && r == row && c == col)) {
                            points.add(new ThreeDPoint(z, r, c));
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
            ThreeDPoint other = (ThreeDPoint) obj;
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
            return "ThreeDPoint [z=" + z + ", row=" + row + ", col=" + col + "]";
        }

    }

    class FourDPoint extends ThreeDPoint {
        int w;

        public FourDPoint(int z, int w, int row, int col) {
            super(z, row, col);
            this.w = w;
        }

        public List<FourDPoint> getSurrounding4DPoints() {
            List<FourDPoint> points = new ArrayList<>();

            for (int z = this.z - 1; z <= this.z + 1; z++) {
                for (int w = this.w - 1; w <= this.w + 1; w++) {
                    for (int r = row - 1; r <= row + 1; r++) {
                        for (int c = col - 1; c <= col + 1; c++) {
                            if (!(z == this.z && w == this.w && r == row && c == col)) {
                                points.add(new FourDPoint(z, w, r, c));
                            }
                        }
                    }
                }
            }

            return points;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + w;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!super.equals(obj))
                return false;
            if (getClass() != obj.getClass())
                return false;
            FourDPoint other = (FourDPoint) obj;
            if (w != other.w)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "FourDPoint [z=" + z + ", w=" + w + ", row=" + row + ", col=" + col + "]";
        }

    }

}
