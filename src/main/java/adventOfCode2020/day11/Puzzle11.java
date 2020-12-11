package adventOfCode2020.day11;

import java.util.List;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle11 extends AbstractPuzzle {

    public static final char FLOOR = '.';
    public static final char EMPTY_SEAT = 'L';
    public static final char OCCUPIED_SEAT = '#';

    enum Direction {
        N(-1, 0), NE(-1, 1), E(0, 1), SE(1, 1), S(1, 0), SW(1, -1), W(0, -1), NW(-1, -1);

        int deltaR;
        int deltaC;

        private Direction(int deltaR, int deltaC) {
            this.deltaR = deltaR;
            this.deltaC = deltaC;
        }
    }

    public Puzzle11(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<String> input = getInput();
        int numRows = input.size();
        int numCols = input.get(0).length();
        char[][] seating = new char[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            seating[i] = input.get(i).toCharArray();
        }

        int round = 0;
        int changedSeats = 0;
        do {
            char[][] nextRound = new char[numRows][numCols];
            changedSeats = 0;
            for (int r = 0; r < numRows; r++) {
                for (int c = 0; c < numCols; c++) {

                    if (seating[r][c] != FLOOR) {
                        int occupiedAdjSeats = 0;
                        for (Direction dir : Direction.values()) {
                            int r1 = r + dir.deltaR;
                            int c1 = c + dir.deltaC;
                            boolean validSeat = isValidPoint(numRows, numCols, r1, c1);
                            if (validSeat && seating[r1][c1] == OCCUPIED_SEAT) {
                                occupiedAdjSeats++;
                            }
                        }

                        if (seating[r][c] == EMPTY_SEAT) {
                            if (occupiedAdjSeats == 0) {
                                nextRound[r][c] = OCCUPIED_SEAT;
                                changedSeats++;
                            } else {
                                nextRound[r][c] = EMPTY_SEAT;
                            }
                        } else if (seating[r][c] == OCCUPIED_SEAT) {
                            if (occupiedAdjSeats >= 4) {
                                nextRound[r][c] = EMPTY_SEAT;
                                changedSeats++;
                            } else {
                                nextRound[r][c] = OCCUPIED_SEAT;
                            }
                        }
                    } else {
                        nextRound[r][c] = FLOOR;
                    }
                }
            }
            seating = nextRound.clone();

            System.out.println("Round " + round + " had " + changedSeats + " changed seats");
            round++;
        } while (changedSeats > 0);

        System.out.println("Final arrangement:");
        int numOccupied = 0;
        for (char[] row : seating) {
            System.out.println(new String(row));
            for (char c : row) {
                if (c == OCCUPIED_SEAT) {
                    numOccupied++;
                }
            }
        }
        return numOccupied;
    }

    @Override
    public Object solve2() {
        List<String> input = getInput();
        int numRows = input.size();
        int numCols = input.get(0).length();
        char[][] seating = new char[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            seating[i] = input.get(i).toCharArray();
        }

        int round = 0;
        int changedSeats = 0;
        do {
            char[][] nextRound = new char[numRows][numCols];
            changedSeats = 0;
            for (int r = 0; r < numRows; r++) {
                for (int c = 0; c < numCols; c++) {

                    if (seating[r][c] != FLOOR) {
                        int occupiedVisibleSeats = 0;
                        for (Direction dir : Direction.values()) {

                            int r1 = r + dir.deltaR;
                            int c1 = c + dir.deltaC;
                            boolean validPoint = isValidPoint(numRows, numCols, r1, c1);
                            while (validPoint && seating[r1][c1] == FLOOR) {
                                r1 += dir.deltaR;
                                c1 += dir.deltaC;
                                validPoint = isValidPoint(numRows, numCols, r1, c1);
                            }

                            if (validPoint && seating[r1][c1] == OCCUPIED_SEAT) {
                                occupiedVisibleSeats++;
                            }
                        }

                        if (seating[r][c] == EMPTY_SEAT) {
                            if (occupiedVisibleSeats == 0) {
                                nextRound[r][c] = OCCUPIED_SEAT;
                                changedSeats++;
                            } else {
                                nextRound[r][c] = EMPTY_SEAT;
                            }
                        } else if (seating[r][c] == OCCUPIED_SEAT) {
                            if (occupiedVisibleSeats >= 5) {
                                nextRound[r][c] = EMPTY_SEAT;
                                changedSeats++;
                            } else {
                                nextRound[r][c] = OCCUPIED_SEAT;
                            }
                        }
                    } else {
                        nextRound[r][c] = FLOOR;
                    }
                }
            }
            seating = nextRound.clone();

            System.out.println("Round " + round + " had " + changedSeats + " changed seats");
            round++;
        } while (changedSeats > 0);

        System.out.println("Final arrangement:");
        int numOccupied = 0;
        for (char[] row : seating) {
            System.out.println(new String(row));
            for (char c : row) {
                if (c == OCCUPIED_SEAT) {
                    numOccupied++;
                }
            }
        }
        return numOccupied;
    }

    protected boolean isValidPoint(int numRows, int numCols, int r1, int c1) {
        return r1 >= 0 && r1 < numRows && c1 >= 0 && c1 < numCols;
    }

}
