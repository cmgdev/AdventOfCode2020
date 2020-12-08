package adventOfCode2020.day08;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Puzzle08Test {

    Puzzle08 puzzleExampleData = new Puzzle08(true); 
    Puzzle08 puzzleRealData = new Puzzle08(false); 
    
    @Test
    public void testSolve1_exampleData() {
        Object answerObj = puzzleExampleData.solve1();
        int answer = (Integer) answerObj;
        long expected = puzzleRealData.getExpectedAnswer1();
        assertTrue( "Expected " + expected + " but got " + answer, answer == expected );
    }

    @Test
    public void testSolve1_realData() {
        Object answerObj = puzzleRealData.solve1();
        int answer = (Integer) answerObj;
        long expected = puzzleRealData.getExpectedAnswer1();
        assertTrue( "Expected " + expected + " but got " + answer, answer == expected );
    }

    @Test
    public void testSolve2_exampleData() {
        Object answerObj = puzzleExampleData.solve2();
        long answer = (Long) answerObj;
        long expected = puzzleRealData.getExpectedAnswer2();
        assertTrue( "Expected " + expected + " but got " + answer, answer == expected );
    }
    
    @Test
    public void testSolve2_realData() {
        Object answerObj = puzzleRealData.solve2();
        long answer = (Long) answerObj;
        long expected = puzzleRealData.getExpectedAnswer2();
        assertTrue( "Expected " + expected + " but got " + answer, answer == expected );
    }

}
