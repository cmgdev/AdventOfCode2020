package adventOfCode2020.day13;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle13Test {

    AbstractPuzzle puzzleExampleData = new Puzzle13(true);
    AbstractPuzzle puzzleRealData = new Puzzle13(false);

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
        long answer = (Long) answerObj;
        long expected = puzzle.getExpectedAnswer1();
        assertTrue("Expected " + expected + " but got " + answer, answer == expected);
    }

    private void assertSolve2(boolean isExampleData) {
        AbstractPuzzle puzzle = isExampleData ? puzzleExampleData : puzzleRealData;
        Object answerObj = puzzle.solve2();
        BigInteger answer = (BigInteger) answerObj;
        BigInteger expected = new BigInteger(puzzle.getExpectedAnswer2String());
        assertEquals(expected, answer);
    }

}