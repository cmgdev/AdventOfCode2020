package adventOfCode2020.day07;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Puzzle07Test {

    Puzzle07 puzzle = new Puzzle07(true); 
    
    @Test
    public void testSolve1() {
        Object answerObj = puzzle.solve1();
        int answer = (Integer) answerObj;
        assertTrue( answer == puzzle.getExpectedAnswer1() );
    }

    @Test
    public void testSolve2() {
        Object answerObj = puzzle.solve2();
        long answer = (Long) answerObj;
        assertTrue( answer == puzzle.getExpectedAnswer2() );
    }

}
