package adventOfCode2020.day12;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle12Test {

    AbstractPuzzle puzzleExampleData = new Puzzle12(true);
    AbstractPuzzle puzzleRealData = new Puzzle12(false);

    @Test
    public void testSolve1_exampleData() {
        assertSolve1(true);
    }

    @Test
    public void testSolve1_realData() {
        assertSolve1(false);
    }
    
    @Test
    public void testSolve2_exampleData() {
        assertSolve2(true);
    }

    @Test
    public void testSolve2_realData() {
        assertSolve2(false);
    }

    private void assertSolve1(boolean isExampleData) {
        AbstractPuzzle puzzle = isExampleData ? puzzleExampleData : puzzleRealData;
        Object answerObj = puzzle.solve1();
        int answer = (Integer) answerObj;
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

}
