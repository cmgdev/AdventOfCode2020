package AdventOfCode2020.day05;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import AdventOfCode2020.base.AbstractPuzzle;

public class Puzzle05 extends AbstractPuzzle {

	public static final boolean IS_TEST = false;
	public static final int DAY = 5;

	public Puzzle05() {
		super(IS_TEST, DAY);
	}

	@Override
	public void solve1() {
		Map<String, Integer> allSeatCodes = getAllSeatCodes();
		List<String> seatCodes = getInput();
		int highestSeatId = 0;

		for (String seatCode : seatCodes) {
			int seatId = getSeatId(allSeatCodes, seatCode);
			highestSeatId = Math.max(seatId, highestSeatId);
		}

		System.out.println("Highest Seat Id: " + highestSeatId);
		System.out.println(highestSeatId == getAnswer1());
	}

	private int getSeatId(Map<String, Integer> allSeatCodes, String seatCode) {
		String rowCode = seatCode.substring(0, 7);
		String colCode = seatCode.substring(7);
		int seatId = (allSeatCodes.get(rowCode) * 8) + allSeatCodes.get(colCode);
		return seatId;
	}

	private Map<String, Integer> getAllSeatCodes() {
		Map<String, Integer> allSeatCodes = new HashMap<>();
		for (int i = 0; i < 128; i++) {
			allSeatCodes.put(getCode(i, 0, 127, "F", "B"), i);
		}
		for (int i = 0; i < 8; i++) {
			allSeatCodes.put(getCode(i, 0, 7, "L", "R"), i);
		}
		return allSeatCodes;
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
		Map<String, Integer> allSeatCodes = getAllSeatCodes();
		List<String> seatCodes = getInput();

		List<Integer> seatIds = seatCodes.stream().map(code -> getSeatId(allSeatCodes, code)).sorted().collect(Collectors.toList());
		int yourSeat = 0;
		for (int i = 0; i < seatIds.size() - 1; i++) {
			int seatBefore = seatIds.get(i);
			int seatAfter = seatIds.get(i + 1);
			if (seatAfter - seatBefore == 2) {
				yourSeat = seatBefore + 1;
				System.out.println("Before:" + seatBefore);
				System.out.println("You:" + yourSeat );
				System.out.println("After:" + seatAfter);
				System.out.println("---");
			}
		}
		System.out.println("Your seat: " + yourSeat);
		System.out.println(yourSeat == getAnswer2());
	}

	public static void main(String[] args) {
		AbstractPuzzle.solve(new Puzzle05());
	}

}
