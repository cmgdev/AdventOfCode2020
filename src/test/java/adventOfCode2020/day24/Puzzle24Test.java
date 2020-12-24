package adventOfCode2020.day24;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle24Test {

    AbstractPuzzle puzzleExampleData = new Puzzle24(true);
    AbstractPuzzle puzzleRealData = new Puzzle24(false);

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
        String answer = (String) answerObj;
        String expected = puzzle.getExpectedAnswer1String();
        assertEquals(expected, answer);
    }

    private void assertSolve2(boolean isExampleData) {
        AbstractPuzzle puzzle = isExampleData ? puzzleExampleData : puzzleRealData;
        Object answerObj = puzzle.solve2();
        String answer = (String) answerObj;
        String expected = puzzle.getExpectedAnswer2String();
        assertEquals(expected, answer);
    }

}
