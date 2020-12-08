package adventOfCode2020.day03;

import java.util.ArrayList;
import java.util.List;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle03 extends AbstractPuzzle {

	public static final boolean IS_TEST = false;

	public static final char OPEN = '.';
	public static final char TREE = '#';

	public Puzzle03(boolean isTest) {
		super(isTest);
	}

	public static void main(String... args) {
		AbstractPuzzle.solve(new Puzzle03(IS_TEST));
	}

	@Override
	public void solve1() {
		List<String> inputMap = readFile("//", false);
		char[][] points = listToMap(inputMap);

		printMap(points);

		int slopeY = 1;
		int slopeX = 3;

		int treesHit = countTreesHitThisSlope(points, slopeY, slopeX);

		System.out.println("Trees Hit: " + treesHit);
		System.out.println(treesHit == getExpectedAnswer1());
	}

	private void printMap(char[][] points) {
		for (int r = 0; r < points.length; r++) {
			for (int c = 0; c < points[r].length; c++) {
				System.out.print(points[r][c]);
			}
			System.out.println();
		}
	}

	private char[][] listToMap(List<String> inputMap) {
		int rows = inputMap.size();
		int cols = inputMap.get(0).length();
		char[][] points = new char[rows][cols];

		for (int r = 0; r < rows; r++) {
			char[] thisRow = inputMap.get(r).toCharArray();
			for (int c = 0; c < cols; c++) {
				points[r][c] = thisRow[c];
			}
		}
		return points;
	}

	private int countTreesHitThisSlope(char[][] points, int slopeY, int slopeX) {
		int curY = 0;
		int curX = 0;
		int treesHit = 0;
		int rows = points.length;
		int cols = points[0].length;
		while (curY < rows) {
			if (points[curY][curX] == TREE) {
				System.out.println("(" + curY + "," + curX + ") is a tree!");
				treesHit++;
			}
			curY += slopeY;
			curX = (curX + slopeX) % cols;
		}

		return treesHit;
	}

	@Override
	public void solve2() {
		List<String> inputMap = readFile("//", false);
		char[][] points = listToMap(inputMap);

		/*
		 * 
    Right 1, down 1.
    Right 3, down 1. (This is the slope you already checked.)
    Right 5, down 1.
    Right 7, down 1.
    Right 1, down 2.
		 */
		int[][] slopes = new int[][] { 
			new int[] {1, 1},
			new int[] {1, 3},
			new int[] {1, 5},
			new int[] {1, 7},
			new int[] {2, 1}
		};

		List<Integer> treesHit = new ArrayList<>();
		long mult = 1;
		for (int[] slope : slopes) {
			int thisSlope = countTreesHitThisSlope(points, slope[0], slope[1]);
			treesHit.add(thisSlope);
			mult *= thisSlope;
		}
		
		System.out.println(treesHit + " => " + mult);
		System.out.println(mult == getExpectedAnswer2());
	}

}
