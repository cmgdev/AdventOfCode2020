package day05;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.AbstractPuzzle;

public class Puzzle05 extends AbstractPuzzle {

	public static final boolean IS_TEST = false;
	public static final int DAY = 5;

	public Puzzle05() {
		super(IS_TEST, DAY);
	}

	@Override
	public void solve1() {
		Map<String, Integer> allSeatCodes = new HashMap<>();
		for (int i = 0; i < 128; i++) {
			allSeatCodes.put(getCode(i, 0, 127, "F", "B"), i);
		}
		for (int i = 0; i < 8; i++) {
			allSeatCodes.put(getCode(i, 0, 7, "L", "R"), i);
		}

		List<String> seatCodes = getInput();
		int highestSeatId = 0;
		for (String seatCode : seatCodes) {
			String rowCode = seatCode.substring(0, 7);
			String colCode = seatCode.substring(7);
			int seatId = (allSeatCodes.get(rowCode) * 8) + allSeatCodes.get(colCode);
			highestSeatId = Math.max(seatId, highestSeatId);
		}

		System.out.println("Highest Seat Id: " + highestSeatId);
		System.out.println(highestSeatId == getAnswer1());
	}

	public String getCode(int number, int lowIdx, int highIdx, String lowCode, String highCode) {
		if (highIdx - lowIdx == 1) {
			return number == lowIdx ? lowCode : highCode;
		}
		int midPoint = (lowIdx + highIdx) / 2;
		if (number <= midPoint) {
			return lowCode + getCode(number, lowIdx, midPoint, lowCode, highCode);
		} else {
			return highCode + getCode(number, midPoint + 1, highIdx, lowCode, highCode);
		}
	}

	@Override
	public void solve2() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		AbstractPuzzle.solve(new Puzzle05());
	}

}
