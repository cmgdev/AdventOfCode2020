package day01;

import java.util.List;

import base.AbstractPuzzle;

public class Puzzle01 extends AbstractPuzzle {

	public static final boolean IS_TEST = false;
	public static final int DAY = 1;

	public Puzzle01() {
		super(IS_TEST, DAY);
	}

	public static void main(String... args) {
		AbstractPuzzle.solve( new Puzzle01() );
	}

	@Override
	public void solve1() {
		int sumTarget = 2020;
		int[] nums = getIntArray();

		int first = 0;
		int second = 0;
		for (int i = 0; i < nums.length - 1; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				int a = nums[i];
				int b = nums[j];
				int sum = a + b;
				if (sum == sumTarget) {
					first = a;
					second = b;
				}
			}
			if (first + second > 0) {
				break;
			}
		}

		System.out.println(first + " + " + second + " = " + (first + second));
		System.out.println(first + " * " + second + " = " + (first * second));
		System.out.println(getAnswer1() == (first * second));
	}

	private int[] getIntArray() {
		List<String> input = getInput();

		int[] nums = new int[input.size()];
		for (int i = 0; i < input.size(); i++) {
			nums[i] = Integer.parseInt(input.get(i));
		}
		return nums;
	}

	@Override
	public void solve2() {
		int sumTarget = 2020;
		int[] nums = getIntArray();

		int first = 0;
		int second = 0;
		int third = 0;
		for (int i = 0; i < nums.length - 2; i++) {
			for (int j = i + 1; j < nums.length - 1; j++) {
				for (int k = j + 1; k < nums.length; k++) {
					int a = nums[i];
					int b = nums[j];
					int c = nums[k];
					int sum = a + b + c;
					if (sum == sumTarget) {
						first = a;
						second = b;
						third = c;
					}
				}
				if (first + second + third > 0) {
					break;
				}
			}
			if (first + second + third > 0) {
				break;
			}
		}

		System.out.println(first + " + " + second + " + " + third + " = " + (first + second + third));
		System.out.println(first + " * " + second + " * " + third + " = " + (first * second * third));
		System.out.println(getAnswer2() == (first * second * third));
	}

}
