package adventOfCode2020.day24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            }
            if (hexGrid.containsKey(current)) {
                String currentColor = hexGrid.get(current);
                currentColor = currentColor.equals(WHITE) ? BLACK : WHITE;
                hexGrid.put(current, currentColor);
            } else {
                hexGrid.put(current, BLACK);
            }
        }

//        System.out.println(hexGrid);
        long count = hexGrid.values().stream().filter(s -> s.equals(BLACK)).count();

        return String.valueOf(count);
    }

    @Override
    public Object solve2() {
        // TODO Auto-generated method stub
        return null;
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
