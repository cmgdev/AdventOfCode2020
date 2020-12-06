package day03;

import java.util.List;

import base.AbstractPuzzle;

public class Puzzle03 extends AbstractPuzzle {

	public static final boolean IS_TEST = false;
	public static final int DAY = 3;

	public static final char OPEN = '.';
	public static final char TREE = '#';

	public Puzzle03() {
		super(IS_TEST, DAY);
	}

	public static void main(String... args) {
		AbstractPuzzle.solve(new Puzzle03());
	}

	@Override
	public void solve1() {
		List<String> inputMap = readFile("//");
		int rows = inputMap.size();
		int cols = inputMap.get(0).length();
		char[][] points = new char[rows][cols];

		for (int r = 0; r < rows; r++) {
			char[] thisRow = inputMap.get(r).toCharArray();
			for (int c = 0; c < cols; c++) {
				points[r][c] = thisRow[c];
			}
		}

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				System.out.print(points[r][c]);
			}
			System.out.println();
		}

		int curY = 0;
		int curX = 0;
		int slopeY = 1;
		int slopeX = 3;
		int treesHit = 0;

		while (curY < rows) {
			if (points[curY][curX] == TREE) {
				System.out.println("(" + curY + "," + curX + ") is a tree!");
				treesHit++;
			}
			curY += slopeY;
			curX = (curX + slopeX) % cols;
		}

		System.out.println("Trees Hit: " + treesHit);
		System.out.println(treesHit == getAnswer1());
	}

	@Override
	public void solve2() {
		// TODO Auto-generated method stub

	}

}
