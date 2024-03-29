package adventOfCode2020.day06;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle06 extends AbstractPuzzle {

	public static final boolean IS_TEST = false;

	public Puzzle06(boolean isTest) {
		super(isTest);
	}

	public static void main(String[] args) {
		AbstractPuzzle.solve(new Puzzle06(IS_TEST));
	}

	@Override
	public Object solve1() {
		List<String> allAnswers = readFile("//", true);
		List<String> answersByGroup = new ArrayList<>();

		String currentGroupAnswers = "";
		int sum = 0;
		for (String answer : allAnswers) {
			if (!answer.isBlank()) {
				currentGroupAnswers += answer;
			} else {
				answersByGroup.add(currentGroupAnswers);

				Set<Character> uniqueAnswers = new HashSet<>();
				for (char c : currentGroupAnswers.toCharArray()) {
					uniqueAnswers.add(c);
				}
				sum += uniqueAnswers.size();

				currentGroupAnswers = "";
			}
		}

		System.out.println("Sum is: " + sum);
		System.out.println(sum == getExpectedAnswer1());
		return sum;
	}

	@Override
	public Object solve2() {
		List<String> allAnswers = readFile("//", true);

		List<List<Character>> currentAnswerList = new ArrayList<>();
		int sum = 0;

		for (String answer : allAnswers) {
			if (!answer.isBlank()) {
				List<Character> thisAnswer = new ArrayList<>();
				for (char c : answer.toCharArray()) {
					thisAnswer.add(c);
				}
				currentAnswerList.add(thisAnswer);
			} else {
				List<Character> retainedAnswers = currentAnswerList.get(0);
				for (List<Character> thisAnswer : currentAnswerList) {
					if (!retainedAnswers.isEmpty()) {
						retainedAnswers.retainAll(thisAnswer);
					}
				}
				sum += retainedAnswers.size();
				currentAnswerList.clear();
			}
		}

		System.out.println("Sum is: " + sum);
		System.out.println(sum == getExpectedAnswer2());
		return sum;
	}

}
