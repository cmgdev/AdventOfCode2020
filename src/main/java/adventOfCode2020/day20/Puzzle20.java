package adventOfCode2020.day20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle20 extends AbstractPuzzle {

    public Puzzle20(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
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

        for (int i = 0; i < tiles.size() - 1; i++) {
            Tile ti = tiles.get(i);
            for (int j = i + 1; j < tiles.size(); j++) {
                Tile tj = tiles.get(j);
                if (!Collections.disjoint(ti.edges, tj.edges) || !Collections.disjoint(ti.getFlippedEdges(), tj.edges)) {
                    ti.neighbors.add(tj.id);
                    tj.neighbors.add(ti.id);
                }
            }
        }

        List<Integer> cornerIds = tiles.stream().filter(t -> t.neighbors.size() == 2).map(t -> t.id).collect(Collectors.toList());
        long result = 1;
        if (cornerIds.size() == 4) {
            for (int id : cornerIds) {
                result *= id;
            }
        }

        System.out.println(tiles);
        return result;
    }

    @Override
    public Object solve2() {
        // TODO Auto-generated method stub
        return null;
    }

    class Tile {
        int id;
        List<String> edges = new ArrayList<>();
        Set<Integer> neighbors = new HashSet<>();

        public Tile(int id, List<String> rows) {
            this.id = id;

            String topEdge = rows.get(0);
            String bottomEdge = rows.get(rows.size() - 1);
            StringBuilder rightEdge = new StringBuilder();
            StringBuilder leftEdge = new StringBuilder();
            int colSize = topEdge.length();

            for (String row : rows) {
                leftEdge.append(row.charAt(0));
                rightEdge.append(row.charAt(colSize - 1));
            }
            edges.addAll(Arrays.asList(topEdge, rightEdge.toString(), bottomEdge, leftEdge.toString()));
        }

        List<String> getFlippedEdges() {
            return edges.stream().map(e -> StringUtils.reverse(e)).collect(Collectors.toList());
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((edges == null) ? 0 : edges.hashCode());
            result = prime * result + id;
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
            if (edges == null) {
                if (other.edges != null)
                    return false;
            } else if (!edges.equals(other.edges))
                return false;
            if (id != other.id)
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
            return "Tile [id=" + id + ", edges=" + edges + ", neighbors=" + neighbors + "]";
        }
    }

}
