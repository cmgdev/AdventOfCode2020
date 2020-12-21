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

        int processedTiles = 1;
        Tile current = getById(tiles, cornerIds.get(0));
        fullImage[row][col] = current.id;

        List<Tile> neighbors = current.neighbors.stream().map(n -> getById(tiles, n)).collect(Collectors.toList());
        Tile neighbor = neighbors.get(0);
        for (int i = 0; i < 4; i++) {
            String currentEdge = current.getEdges().get(i);
            if (neighbor.getEdges().contains(currentEdge)) {
                current.quarterTurns = i;
                neighbor.quarterTurns = (neighbor.getEdges().indexOf(currentEdge) + 1) % 4;
                fullImage[row][col + 1] = neighbor.id;
                processedTiles++;
                break;
            }
        }

        printTilesWithEdges(fullImage, tiles);

        int seaRoughness = 0;
        return seaRoughness;
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

    Tile getById(List<Tile> tiles, int id) {
        return tiles.stream().filter(t -> t.id == id).findFirst().get();
    }

    class Tile {
        int id;
        Set<Integer> neighbors = new HashSet<>();
        char[][] image;
        boolean isFlipped = false;
        int quarterTurns = 0;

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
