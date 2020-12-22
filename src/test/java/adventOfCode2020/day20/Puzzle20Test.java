package adventOfCode2020.day20;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import adventOfCode2020.base.AbstractPuzzle;
import adventOfCode2020.day20.Puzzle20.Tile;

public class Puzzle20Test {

    AbstractPuzzle puzzleExampleData = new Puzzle20(true);
    AbstractPuzzle puzzleRealData = new Puzzle20(false);

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
        long answer = (Long) answerObj;
        long expected = puzzle.getExpectedAnswer2();
        assertTrue("Expected " + expected + " but got " + answer, answer == expected);
    }

    @Test
    public void testRotate() {
        List<String> image = Arrays.asList("abc", "def", "ghi");

        Tile tile = ((Puzzle20) puzzleExampleData).new Tile(1, image);
        char[][] expectedStart = new char[][] { 
            new char[] { 'a', 'b', 'c' },
            new char[] { 'd', 'e', 'f' }, 
            new char[] { 'g', 'h', 'i' } };
        char[][] expectedRot1 = new char[][] { 
            new char[] { 'g', 'd', 'a' },
            new char[] { 'h', 'e', 'b' }, 
            new char[] { 'i', 'f', 'c' } };
        char[][] expectedRot2 = new char[][] { 
            new char[] { 'i', 'h', 'g' },
            new char[] { 'f', 'e', 'd' }, 
            new char[] { 'c', 'b', 'a' } };
        char[][] expectedRot3 = new char[][] { 
            new char[] { 'c', 'f', 'i' },
            new char[] { 'b', 'e', 'h' }, 
            new char[] { 'a', 'd', 'g' } };
        char[][] expectedRot4 = new char[][] { 
            new char[] { 'a', 'b', 'c' },
            new char[] { 'd', 'e', 'f' }, 
            new char[] { 'g', 'h', 'i' } };
        assertArrayEquals(expectedStart, tile.getImage());
     
        tile.rotate(1);
        assertArrayEquals(expectedRot1, tile.getImage());
        
        tile.rotate(2);
        assertArrayEquals(expectedRot3, tile.getImage());

        tile.rotate(3);
        assertArrayEquals(expectedRot2, tile.getImage());
        
        tile.rotate(2);
        assertArrayEquals(expectedRot4, tile.getImage());
    }
    
    @Test
    public void testFlipHorizontal() {
        List<String> image = Arrays.asList("abc", "def", "ghi");

        Tile tile = ((Puzzle20) puzzleExampleData).new Tile(1, image);
        char[][] expectedStart = new char[][] { 
            new char[] { 'a', 'b', 'c' },
            new char[] { 'd', 'e', 'f' }, 
            new char[] { 'g', 'h', 'i' } };
        char[][] expectedFlip = new char[][] { 
            new char[] { 'g', 'h', 'i' },
            new char[] { 'd', 'e', 'f' }, 
            new char[] { 'a', 'b', 'c' } };
            
        assertArrayEquals(expectedStart, tile.getImage());
        
        tile.flip(true);
        assertArrayEquals(expectedFlip, tile.getImage());
    }
 
    @Test
    public void testFlipVertical() {
        List<String> image = Arrays.asList("abc", "def", "ghi");
        
        Tile tile = ((Puzzle20) puzzleExampleData).new Tile(1, image);
        char[][] expectedStart = new char[][] { 
            new char[] { 'a', 'b', 'c' },
            new char[] { 'd', 'e', 'f' }, 
            new char[] { 'g', 'h', 'i' } };
        char[][] expectedFlip = new char[][] { 
            new char[] { 'c', 'b', 'a' },
            new char[] { 'f', 'e', 'd' }, 
            new char[] { 'i', 'h', 'g' } };
                
        assertArrayEquals(expectedStart, tile.getImage());
        
        tile.flip(false);
        assertArrayEquals(expectedFlip, tile.getImage());
    }

}
