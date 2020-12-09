package adventOfCode2020.day09;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle09Test {

    AbstractPuzzle puzzleExampleData = new Puzzle09(true);
    AbstractPuzzle puzzleRealData = new Puzzle09(false);

    @Test
    public void testSolve1_exampleData() {
        assertSolve1(true);
    }

    @Test
    public void testSolve1_realData() {
        assertSolve1(false);
    }

    private void assertSolve1(boolean isExampleData) {
        AbstractPuzzle puzzle = isExampleData ? puzzleExampleData : puzzleRealData;
        Object answerObj = puzzle.solve1();
        long answer = (Long) answerObj;
        long expected = puzzle.getExpectedAnswer1();
        assertTrue("Expected " + expected + " but got " + answer, answer == expected);
    }

    private void assertSolve2(boolean isExampleData) {
        AbstractPuzzle puzzle = isExampleData ? puzzleExampleData : puzzleRealData;
        Object answerObj = puzzle.solve2();
        int answer = (Integer) answerObj;
        long expected = puzzle.getExpectedAnswer2();
        assertTrue("Expected " + expected + " but got " + answer, answer == expected);
    }

    @Test
    public void testSolve2_exampleData() {
        assertSolve2(true);
    }

    @Test
    public void testSolve2_realData() {
        assertSolve2(false);
    }

}
