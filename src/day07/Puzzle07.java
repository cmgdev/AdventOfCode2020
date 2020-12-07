package day07;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import base.AbstractPuzzle;

public class Puzzle07 extends AbstractPuzzle {

	public static final boolean IS_TEST = false;
	public static final int DAY = 7;

	public Puzzle07() {
		super(IS_TEST, DAY);
	}

	@Override
	public void solve1() {
		List<String> input = getInput();
		Map<String, Map<String, Integer>> bags = new HashMap<>();
		String targetBag = "shiny gold";

		for (String rule : input) {
			String outerBag = rule.substring(0, rule.indexOf("bags") - 1);

			String[] innerBags = rule.split("contain")[1].split(",");
			if (innerBags[0].contains("no other bags")) {
				bags.put(outerBag, new HashMap<>());
			} else {
				Map<String, Integer> innerBagMap = new HashMap<>();
				for (String innerBag : innerBags) {
					String[] bagDescr = innerBag.trim().split(" ");
					innerBagMap.put(bagDescr[1] + " " + bagDescr[2], Integer.parseInt(bagDescr[0]));
				}
				bags.put(outerBag, innerBagMap);
			}
		}

		Set<String> bagsWithTargetBag = getBagsWithTargetBag(bags, targetBag);
		int bagSize = 0;
		while (bagsWithTargetBag.size() > bagSize) {
			bagSize = bagsWithTargetBag.size();
			Set<String> nextBags = new HashSet<>();
			for (String nextBag : bagsWithTargetBag) {
				nextBags.addAll(getBagsWithTargetBag(bags, nextBag));
			}
			bagsWithTargetBag.addAll(nextBags);
		}

		System.out.println(bagsWithTargetBag.size());
		System.out.println(bagsWithTargetBag.size() == getAnswer1());
	}

	private Set<String> getBagsWithTargetBag(Map<String, Map<String, Integer>> bags, String targetBag) {
		Set<String> bagsWithTargetBag = new HashSet<>();
		for (Map.Entry<String, Map<String, Integer>> outerBag : bags.entrySet()) {

			if (outerBag.getValue().containsKey(targetBag)) {
				bagsWithTargetBag.add(outerBag.getKey());
			}
		}
		return bagsWithTargetBag;
	}

	@Override
	public void solve2() {
		List<String> input = getInput();

//		System.out.println(answer == getAnswer1());
	}

	public static void main(String[] args) {
		AbstractPuzzle.solve(new Puzzle07());
	}

}
