package adventOfCode2020.day06;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Puzzle06Test {

    Puzzle06 puzzle = new Puzzle06(true); 
    
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
