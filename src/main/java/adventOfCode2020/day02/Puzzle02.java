package adventOfCode2020.day02;

import java.util.List;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle02 extends AbstractPuzzle {

	public static final boolean IS_TEST = false;

	public Puzzle02(boolean isTest) {
		super(isTest);
	}

	public static void main(String... args) {
		AbstractPuzzle.solve(new Puzzle02(IS_TEST));
	}

	@Override
	public void solve1() {
		List<String> passwordInputs = getInput();

		int totalValid = 0;
		for (String passwordInput : passwordInputs) {
			String[] passwordParts = passwordInput.split(" ");
			String[] counts = passwordParts[0].split("-");

			int min = Integer.parseInt(counts[0]);
			int max = Integer.parseInt(counts[1]);

			char checked = passwordParts[1].charAt(0);
			char[] password = passwordParts[2].toCharArray();

			int count = 0;
			for (char c : password) {
				if (c == checked) {
					count++;
				}
			}

			if (count >= min && count <= max) {
				System.out.println(passwordParts[2] + " is valid");
				totalValid++;
			}
		}
		System.out.println("Total valid passwords: " + totalValid);
		System.out.println(totalValid == getExpectedAnswer1());
	}

	@Override
	public void solve2() {
		List<String> passwordInputs = getInput();

		int totalValid = 0;
		for (String passwordInput : passwordInputs) {
			String[] passwordParts = passwordInput.split(" ");
			String[] indexes = passwordParts[0].split("-");

			int firstIdx = Integer.parseInt(indexes[0]) - 1;
			int secondIdx = Integer.parseInt(indexes[1]) - 1;

			char checked = passwordParts[1].charAt(0);
			char[] password = passwordParts[2].toCharArray();

			if (password[firstIdx] == checked ^ password[secondIdx] == checked) {
				System.out.println(passwordParts[2] + " is valid");
				totalValid++;
			}
		}

		System.out.println("Total valid passwords: " + totalValid);
		System.out.println(totalValid == getExpectedAnswer2());
	}

}
