package adventOfCode2020.day05;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Puzzle05Test {

    // there was no answer for this second example this day
    Puzzle05 puzzle = new Puzzle05(false); 
    
    @Test
    public void testSolve1() {
        Object answerObj = puzzle.solve1();
        int answer = (Integer) answerObj;
        assertTrue( answer == puzzle.getExpectedAnswer1() );
    }

    @Test
    public void testSolve2() {
        Object answerObj = puzzle.solve2();
        int answer = (Integer) answerObj;
        assertTrue( answer == puzzle.getExpectedAnswer2() );
    }

}
