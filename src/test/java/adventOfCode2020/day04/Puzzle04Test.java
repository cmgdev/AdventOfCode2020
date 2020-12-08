package adventOfCode2020.day04;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Puzzle04Test {

    Puzzle04 puzzle = new Puzzle04(true); 
    
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
