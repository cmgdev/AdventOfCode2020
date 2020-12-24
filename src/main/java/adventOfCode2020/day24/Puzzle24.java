package adventOfCode2020.day24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle24 extends AbstractPuzzle {

    public static final String WHITE = "w";
    public static final String BLACK = "b";

    public Puzzle24(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<String> input = getInput();
        List<List<String>> directions = getDirections(input);

        Map<Point, String> hexGrid = initGridWithDirections(directions);

        long count = hexGrid.values().stream().filter(s -> s.equals(BLACK)).count();

        return String.valueOf(count);
    }

    protected Map<Point, String> initGridWithDirections(List<List<String>> directions) {
        Map<Point, String> hexGrid = new HashMap<>();
        Point center = new Point(0, 0);
        hexGrid.put(center, WHITE);

        for (List<String> thisDirections : directions) {
            Point current = center;
            for (String dir : thisDirections) {
                // grid system from
                // https://www.redblobgames.com/grids/hexagons/#coordinates-offset
                // odd-r horizontal layout
                if (dir.equals("e")) {
                    current = new Point(current.r, current.c + 1);
                } else if (dir.equals("w")) {
                    current = new Point(current.r, current.c - 1);
                } else if (dir.equals("se")) {
                    current = new Point(current.r + 1, current.c + 1);
                } else if (dir.equals("sw")) {
                    current = new Point(current.r + 1, current.c);
                } else if (dir.equals("ne")) {
                    current = new Point(current.r - 1, current.c);
                } else if (dir.equals("nw")) {
                    current = new Point(current.r - 1, current.c - 1);
                }

                if (!hexGrid.containsKey(current)) {
                    hexGrid.put(current, WHITE);
                }
            }
            if (hexGrid.containsKey(current)) {
                String currentColor = hexGrid.get(current);
                currentColor = currentColor.equals(WHITE) ? BLACK : WHITE;
                hexGrid.put(current, currentColor);
            } else {
                hexGrid.put(current, BLACK);
            }
        }
        return hexGrid;
    }

    protected List<List<String>> getDirections(List<String> input) {
        List<List<String>> directions = new ArrayList<>();

        for (String in : input) {
            List<String> d = new ArrayList<>();
            for (int i = 0; i < in.length(); i++) {
                char c = in.charAt(i);
                if (c == 'e' || c == 'w') {
                    d.add(String.valueOf(c));
                } else {
                    i++;
                    char c2 = in.charAt(i);
                    d.add(new String(new char[] { c, c2 }));
                }
            }
            directions.add(d);
        }
        return directions;
    }

    @Override
    public Object solve2() {
        List<String> input = getInput();
        List<List<String>> directions = getDirections(input);

        Map<Point, String> hexGrid = initGridWithDirections(directions);
        hexGrid = fillGridGaps(hexGrid);

//        Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.
//        Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.

        for (int i = 0; i < 100; i++) {
            List<Point> whiteTiles = new ArrayList<>();
            List<Point> blackTiles = new ArrayList<>();
            Set<Entry<Point, String>> tilesAtStart = new HashSet<>(hexGrid.entrySet());
            for (Map.Entry<Point, String> tile : tilesAtStart) {
                int countAdjacentBlackTiles = countAdjacentBlackTiles(hexGrid, tile.getKey());
                if (tile.getValue().equals(BLACK)) {
                    if (countAdjacentBlackTiles == 0 || countAdjacentBlackTiles > 2) {
                        whiteTiles.add(tile.getKey());
                    } else
                        blackTiles.add(tile.getKey());
                } else if (tile.getValue().equals(WHITE)) {
                    if (countAdjacentBlackTiles == 2) {
                        blackTiles.add(tile.getKey());
                    } else {
                        whiteTiles.add(tile.getKey());
                    }
                }
            }

            for (Point p : whiteTiles) {
                hexGrid.put(p, WHITE);
            }
            for (Point p : blackTiles) {
                hexGrid.put(p, BLACK);
            }

            System.out.println("day " + (i + 1) + ": " + hexGrid.values().stream().filter(s -> s.equals(BLACK)).count());
        }

        long count = hexGrid.values().stream().filter(s -> s.equals(BLACK)).count();

        return String.valueOf(count);
    }

    protected int countAdjacentBlackTiles(Map<Point, String> hexGrid, Point point) {
        int count = 0;

        List<Point> adjacentPoints = Arrays.asList(new Point(point.r, point.c + 1), new Point(point.r, point.c - 1), new Point(point.r + 1, point.c + 1), new Point(point.r + 1, point.c), new Point(point.r - 1, point.c), new Point(point.r - 1, point.c - 1));
        for (Point p : adjacentPoints) {
            if (hexGrid.containsKey(p)) {
                count += hexGrid.get(p).equals(BLACK) ? 1 : 0;
            } else {
                hexGrid.put(p, WHITE);
            }
        }

        return count;
    }

    protected Map<Point, String> fillGridGaps(Map<Point, String> hexGrid) {
        int[][] minMax = getMinMax(hexGrid);
        int minR = minMax[0][0];
        int minC = minMax[0][1];
        int maxR = minMax[1][0];
        int maxC = minMax[1][1];

        for (int r = minR - 1; r < maxR + 1; r++) {
            for (int c = minC - 1; c < maxC + 1; c++) {
                Point current = new Point(r, c);
                String color = hexGrid.getOrDefault(current, WHITE);
                hexGrid.put(current, color);
            }
        }
        return hexGrid;
    }

    public int[][] getMinMax(Map<Point, String> hexGrid) {
        int minR = Integer.MAX_VALUE;
        int minC = Integer.MAX_VALUE;
        int maxR = Integer.MIN_VALUE;
        int maxC = Integer.MIN_VALUE;

        for (Point p : hexGrid.keySet()) {
            minR = Math.min(minR, p.r);
            minC = Math.min(minC, p.c);
            maxR = Math.max(maxR, p.r);
            maxC = Math.max(maxC, p.c);
        }
        return new int[][] { new int[] { minR, minC }, new int[] { maxR, maxC } };
    }

    class Point {
        int r;
        int c;

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + c;
            result = prime * result + r;
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
            if (c != other.c)
                return false;
            if (r != other.r)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Point [r=" + r + ", c=" + c + "]";
//            return "Point (" + c + "," + r + ")";
        }

    }

}
