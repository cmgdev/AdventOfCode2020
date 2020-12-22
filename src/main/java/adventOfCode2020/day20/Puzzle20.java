package adventOfCode2020.day20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle20 extends AbstractPuzzle {

    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;

    //                  # 
    //#    ##    ##    ###
    // #  #  #  #  #  #   

    String monsterLine1 = "(.|#){18}#(.|#){1}";
    String monsterLine2 = "#(.|#){4}##(.|#){4}##(.|#){4}###";
    String monsterLine3 = "(.|#){1}#(.|#){2}#(.|#){2}#(.|#){2}#(.|#){2}#(.|#){2}#(.|#){3}";
    int monsterPixels = 15;

    public Puzzle20(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<Tile> tiles = getTiles();

        findNeighbors(tiles);

        List<Integer> cornerIds = tiles.stream().filter(t -> t.neighbors.size() == 2).map(t -> t.id).collect(Collectors.toList());
        long result = 1;
        if (cornerIds.size() == 4) {
            for (int id : cornerIds) {
                result *= id;
            }
        }

        return result;
    }

    protected void findNeighbors(List<Tile> tiles) {
        for (int i = 0; i < tiles.size() - 1; i++) {
            Tile ti = tiles.get(i);
            for (int j = i + 1; j < tiles.size(); j++) {
                Tile tj = tiles.get(j);
                if (!Collections.disjoint(ti.getEdges(), tj.getEdges()) || !Collections.disjoint(ti.getFlippedEdges(), tj.getEdges())) {
                    ti.neighbors.add(tj.id);
                    tj.neighbors.add(ti.id);
                }
            }
        }
    }

    protected List<Tile> getTiles() {
        List<String> input = readFile("//", false);

        String header = "Tile [0-9]+:";
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            String in = input.get(i);

            int id = 0;
            List<String> rows = new ArrayList<>();

            if (in.matches(header)) {
                id = Integer.parseInt(in.replaceAll("[^\\d]", ""));
                while (i < input.size() - 1 && !input.get(i + 1).matches(header)) {
                    i++;
                    rows.add(input.get(i));
                }
            }
            tiles.add(new Tile(id, rows));
        }
        return tiles;
    }

    @Override
    public Object solve2() {
        List<Tile> tiles = getTiles();

        findNeighbors(tiles);

        List<Integer> cornerIds = tiles.stream().filter(t -> t.neighbors.size() == 2).map(t -> t.id).collect(Collectors.toList());

        int numTiles = tiles.size();
        int[][] fullImage = new int[numTiles / 2][numTiles / 2];
        int row = 0;
        int col = 0;

        // orient top left tile
        Tile current = getById(tiles, cornerIds.get(0));
        fullImage[row][col] = current.id;

        List<Tile> neighbors = getNeighborTiles(tiles, current);
        List<Integer> edgeIndexes = new ArrayList<>();
        List<String> currentEdges = current.getEdges();
        for (int n = 0; n < neighbors.size(); n++) {
            Tile neighbor = neighbors.get(n);
            for (int i = 0; i < 4; i++) {
                String currentEdge = currentEdges.get(i);
                List<String> neighborEdges = neighbor.getEdges();
                if (neighborEdges.contains(currentEdge)) {
                    edgeIndexes.add(i);
                    break;
                }
            }
        }

        int rotationsNeeded = 0;
        if (edgeIndexes.size() == 2) {
            List<Integer> neededIndexes = Arrays.asList(RIGHT, BOTTOM);
            while (!edgeIndexes.containsAll(neededIndexes)) {
                rotationsNeeded++;
                for (int i = 0; i < edgeIndexes.size(); i++) {
                    edgeIndexes.set(i, edgeIndexes.get(i) + 1);
                }
            }
        }
        else {
            System.out.println("something went horribly wrong!!! there should be 2 indexes: " + edgeIndexes);
            return 0;
        }

        current.rotate(rotationsNeeded);

        boolean moreTiles = true;
        while (moreTiles) {
            // get and orient tiles in this row going right
            doRow(tiles, cornerIds, fullImage, row, current);

            System.out.println("Finished row " + row);
            
            row++;
            // find the start of the next row
            Tile leftNextRow = findAndOrientNextTile(tiles, current, BOTTOM);
            if (leftNextRow == null) {
                moreTiles = false;
            } else {
                fullImage[row][0] = leftNextRow.id;
                current = leftNextRow;
            }
        }
        
        System.out.println("finished connecting tiles!");
        printTilesWithEdges(fullImage, tiles);

        // get map without edges and ids
        List<String> map = getMap(fullImage, tiles);
        int monsters = 0;
        for (int i = 0; i <= 8; i++) {
            System.out.println("looking for monsters itr " + i);
            for (int r = 0; r < map.size() - 2; r++) {
                String mapRow1 = map.get(r);
                String mapRow2 = map.get(r + 1);
                String mapRow3 = map.get(r + 2);
                for (int start = 0; start < mapRow1.length() - 20; start++) {
                    int end = start + 20;
                    if (mapRow1.substring(start, end).matches(monsterLine1) && mapRow2.substring(start, end).matches(monsterLine2) && mapRow3.substring(start, end).matches(monsterLine3)) {
                        monsters++;
                        start += 20;
                    }
                }
            }
            if (monsters > 0) {
                break;
            }
            if (i % 2 == 0) {
                map = rotateMap(map);
            } else {
                map = flipMap(map);
            }
        }
        
        int waves = 0;
        for (String m : map) {
            waves += StringUtils.countMatches(m, "#");
        }

        System.out.println(monsters);

        long seaRoughness = waves - (monsterPixels * monsters);
        return seaRoughness;
    }

    protected void doRow(List<Tile> tiles, List<Integer> cornerIds, int[][] fullImage, int row, Tile start) {
        int col = 0;
        List<Tile> neighbors = getNeighborTiles(tiles, start);
        Tile rightNeighbor = neighbors.get(0);
        Tile current = start;
        do {
            rightNeighbor = findAndOrientNextTile(tiles, current, RIGHT);
            col++;
            if (rightNeighbor != null) {
                fullImage[row][col] = rightNeighbor.id;
                current = rightNeighbor;
            }
        } while (rightNeighbor != null);
    }

    protected Tile findAndOrientNextTile(List<Tile> tiles, Tile current, int direction) {
        List<Tile> neighbors = getNeighborTiles(tiles, current);
        Tile candidate = null;
        String rightEdge = current.getEdges().get(direction);
        for (Tile neighbor : neighbors) {
            int idx = neighbor.getEdges().indexOf(rightEdge);
            if (idx == -1) {
                idx = neighbor.getFlippedEdges().indexOf(rightEdge);
                if (idx == -1) {
                    continue;
                } else {
                    candidate = neighbor;
                    break;
                }
            } else {
                candidate = neighbor;
            }
        }

        if (candidate != null) {
            int idx = candidate.getEdges().indexOf(rightEdge);
            int oppositeIdx = (direction + 2) % 4;
            while (idx != oppositeIdx) {
                if (idx == -1) {
                    if (rightEdge.equals(StringUtils.reverse(candidate.getEdges().get(oppositeIdx)))) {
                        candidate.flip(false);
                        idx = candidate.getEdges().indexOf(rightEdge);
                        continue;
                    }
                }
                candidate.rotate(1);
                idx = candidate.getEdges().indexOf(rightEdge);
            }
            System.out.println("found and oriented tile " + candidate.id);
        }
        return candidate;
    }

    protected List<Tile> getNeighborTiles(List<Tile> tiles, Tile current) {
        return current.neighbors.stream().map(n -> getById(tiles, n)).collect(Collectors.toList());
    }

    protected void printTilesWithEdges(int[][] tileIds, List<Tile> tiles) {
        int tileHeight = tiles.get(0).image.length;
        for (int r = 0; r < tileIds.length; r++) {
            if (tileIds[r] != null) {
                int[] row = tileIds[r];
                List<Tile> thisRow = IntStream.range(0, row.length).filter(col -> row[col] > 0).mapToObj(col -> getById(tiles, row[col])).collect(Collectors.toList());
                for (Tile t : thisRow) {
                    System.out.print("Tile " + t.id + "\t");
                }
                System.out.println();
                for (int i = 0; i < tileHeight; i++) {
                    for (Tile t : thisRow) {
                        System.out.print(new String(t.image[i]) + "\t");
                    }
                    System.out.println();
                }
            }
        }
    }

    protected List<String> getMap(int[][] tileIds, List<Tile> tiles) {
        List<String> fullMap = new ArrayList<>();

        int tileHeight = tiles.get(0).image.length;
        for (int r = 0; r < tileIds.length; r++) {
            if (tileIds[r] != null) {
                int[] row = tileIds[r];
                List<Tile> thisRow = IntStream.range(0, row.length).filter(col -> row[col] > 0).mapToObj(col -> getById(tiles, row[col])).collect(Collectors.toList());
                for (int i = 1; i < tileHeight - 1; i++) {
                    StringBuilder rowString = new StringBuilder();
                    for (Tile t : thisRow) {
                        String s = new String(t.image[i]);
                        rowString.append(s.substring(1, s.length() - 1));
                    }
                    if (StringUtils.isNotBlank(rowString)) {
                        fullMap.add(rowString.toString().trim());
                    }
                }
            }
        }

        return fullMap;
    }

    Tile getById(List<Tile> tiles, int id) {
        return tiles.stream().filter(t -> t.id == id).findFirst().get();
    }

    public List<String> rotateMap(List<String> map) {
        char[][] image = new char[map.size()][map.get(0).length()];
        for (int r = 0; r < image.length; r++) {
            image[r] = map.get(r).toCharArray();
        }

        image = rotate(1, image);
        List<String> newMap = new ArrayList<>();
        for (char[] r : image) {
            newMap.add(new String(r));
        }
        return newMap;
    }

    public List<String> flipMap(List<String> map) {
        char[][] image = new char[map.size()][map.get(0).length()];
        for (int r = 0; r < image.length; r++) {
            image[r] = map.get(r).toCharArray();
        }

        image = flip(false, image);
        List<String> newMap = new ArrayList<>();
        for (char[] r : image) {
            newMap.add(new String(r));
        }
        return newMap;
    }

    public char[][] rotate(int times, char[][] image) {
        int rows = image.length;
        int cols = image[0].length;

        for (int t = 0; t < times; t++) {
            char[][] rotated = new char[rows][cols];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    rotated[r][c] = image[cols - c - 1][r];
                }
            }
            image = rotated;
        }
        return image;
    }

    public char[][] flip(boolean horizontal, char[][] image) {
        int rows = image.length;

        // flip -- axis
        if (horizontal) {
            for (int r = 0; r < rows / 2; r++) {
                char[] top = image[r];
                char[] bottom = image[rows - r - 1];
                image[r] = bottom;
                image[rows - r - 1] = top;
            }
        }
        // flip | axis
        else {
            for (int r = 0; r < rows; r++) {
                image[r] = StringUtils.reverse(new String(image[r])).toCharArray();
            }
        }
        return image;
    }

    public class Tile {
        int id;
        Set<Integer> neighbors = new HashSet<>();
        char[][] image;

        public Tile(int id, List<String> rows) {
            this.id = id;

            image = new char[rows.size()][rows.get(0).length()];

            for (int i = 0; i < rows.size(); i++) {
                image[i] = rows.get(i).toCharArray();
            }
        }

        List<String> getEdges() {
            int rows = image.length;
            int cols = image[0].length;

            String topEdge = new String(image[0]);
            String bottomEdge = new String(image[rows - 1]);
            StringBuilder rightEdge = new StringBuilder();
            StringBuilder leftEdge = new StringBuilder();

            for (char[] row : image) {
                leftEdge.append(row[0]);
                rightEdge.append(row[cols - 1]);
            }

            return Arrays.asList(topEdge, rightEdge.toString(), bottomEdge, leftEdge.toString());
        }

        List<String> getFlippedEdges() {
            return getEdges().stream().map(e -> StringUtils.reverse(e)).collect(Collectors.toList());
        }

        public void rotate(int times) {
            this.image = Puzzle20.this.rotate(times, image);
        }

        public void flip(boolean horizontal) {
            this.image = Puzzle20.this.flip(horizontal, image);
        }

        public char[][] getImage() {
            return image;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + id;
            result = prime * result + Arrays.deepHashCode(image);
            result = prime * result + ((neighbors == null) ? 0 : neighbors.hashCode());
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
            Tile other = (Tile) obj;
            if (id != other.id)
                return false;
            if (!Arrays.deepEquals(image, other.image))
                return false;
            if (neighbors == null) {
                if (other.neighbors != null)
                    return false;
            } else if (!neighbors.equals(other.neighbors))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Tile [id=" + id + ", neighbors=" + neighbors + ", image=" + Arrays.toString(image) + "]";
        }

    }

}
