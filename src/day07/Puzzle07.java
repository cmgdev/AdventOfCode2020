package day07;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import base.AbstractPuzzle;

public class Puzzle07 extends AbstractPuzzle {

    public static final boolean IS_TEST = false;
    public static final int DAY = 7;

    public static final String TARGET_BAG = "shiny gold";

    public Puzzle07() {
        super(IS_TEST, DAY);
    }

    @Override
    public void solve1() {
        Map<String, Map<String, Integer>> bags = buildBagMap();

        Set<String> bagsWithTargetBag = getBagsWithTargetBag(bags, TARGET_BAG);
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

    private Map<String, Map<String, Integer>> buildBagMap() {
        List<String> input = getInput();
        Map<String, Map<String, Integer>> bags = new HashMap<>();

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
        return bags;
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
        Map<String, Map<String, Integer>> bags = buildBagMap();

        long count = -1;
        LinkedList<String> queue = new LinkedList<>();
        queue.add(TARGET_BAG);
        while (!queue.isEmpty()) {
            String bag = queue.removeFirst();
            count++;
            Map<String, Integer> innerBags = bags.get(bag);
            for (Map.Entry<String, Integer> innerBag : innerBags.entrySet()) {
                for (int i = 0; i < innerBag.getValue(); i++) {
                    queue.add(innerBag.getKey());
                }
            }
        }

        System.out.println("Total bags: " + count);
        System.out.println(count == getAnswer2());
    }

//    public long countInnerBags(String bag, Map<String, Map<String, Integer>> bags) {
//        Map<String, Integer> bagContents = bags.get(bag);
//        if (bagContents.isEmpty()) {
//            return 1;
//        }
//        long count = 0;
//        for (Map.Entry<String, Integer> innerBag : bagContents.entrySet()) {
//            Integer thisSize = innerBag.getValue();
//            long innerBagCount = countInnerBags(innerBag.getKey(), bags);
//            count += thisSize * innerBagCount;
//        }
//        return count;
//    }

    public static void main(String[] args) {
        AbstractPuzzle.solve(new Puzzle07());
    }

}
