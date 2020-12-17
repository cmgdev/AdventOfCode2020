package adventOfCode2020.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle16 extends AbstractPuzzle {

    private Pattern fieldLineMatcher = Pattern.compile("^([a-z0-9 ]+): ([0-9-]+) or ([0-9-]+)$");

    public Puzzle16(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<String> inputs = getInput();
        List<String> nearbyTickets = getNearbyTickets(inputs);
        Map<String, List<int[]>> fieldRules = getFieldRules(inputs);

        Collection<List<int[]>> ruleSets = fieldRules.values();
        long errorRate = 0;
        for (String nearbyTicket : nearbyTickets) {
            String[] fieldValues = nearbyTicket.split(",");
            for (String valueStr : fieldValues) {
                int value = Integer.parseInt(valueStr);
                if (!matchesARule(ruleSets, value)) {
                    errorRate += value;
                }
            }
        }

        return errorRate;
    }

    protected boolean matchesARule(Collection<List<int[]>> ruleSets, int value) {
        boolean matchedRule = false;
        for (List<int[]> rules : ruleSets) {
            for (int[] rule : rules) {
                if (matchesRule(value, rule)) {
                    matchedRule = true;
                    continue;
                }
            }
        }
        return matchedRule;
    }

    protected boolean matchesRule(int value, int[] rule) {
        return value >= rule[0] && value <= rule[1];
    }

    @Override
    public Object solve2() {
        List<String> inputs = getInput();

        List<String> nearbyTickets = getNearbyTickets(inputs);
        Map<String, List<int[]>> fieldRules = getFieldRules(inputs);
        List<String> validTickets = new ArrayList<>();

        for (String nearbyTicket : nearbyTickets) {
            String[] fieldValues = nearbyTicket.split(",");
            boolean validTicket = true;

            for (String valueStr : fieldValues) {
                int value = Integer.parseInt(valueStr);
                if (!matchesARule(fieldRules.values(), value)) {
                    validTicket = false;
                    break;
                }
            }
            if (validTicket) {
                validTickets.add(nearbyTicket);
            }
        }

        int numFields = fieldRules.keySet().size();

        Map<String, List<Integer>> rulesByIndex = new HashMap<>();
        for (String field : fieldRules.keySet()) {
            List<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < numFields; i++) {
                indexes.add(i);
            }
            rulesByIndex.put(field, indexes);
        }

        for (String ticket : validTickets) {
            System.out.println("evaluating ticket " + ticket);
            String[] fieldValues = ticket.split(",");
            for (int i = 0; i < numFields; i++) {
                for (Map.Entry<String, List<int[]>> fieldRule : fieldRules.entrySet()) {
                    List<Integer> applicableIndexes = rulesByIndex.get(fieldRule.getKey());
                    removeSingleIndexFromOthers(rulesByIndex, fieldRule, applicableIndexes);
                    if (applicableIndexes.size() > 1) {
                        boolean ruleMatches = false;
                        for (int[] rule : fieldRule.getValue()) {
                            if (matchesRule(Integer.parseInt(fieldValues[i]), rule)) {
                                ruleMatches = true;
                                break;
                            }
                        }
                        if (!ruleMatches) {
                            int id = applicableIndexes.indexOf(i);
                            if (id > -1) {
                                System.out.println("removing " + i + " from " + applicableIndexes);
                                applicableIndexes.remove(id);
                            }
                            removeSingleIndexFromOthers(rulesByIndex, fieldRule, applicableIndexes);
                        }
                    }
                }
                for (Map.Entry<String, List<int[]>> fieldRule : fieldRules.entrySet()) {
                    List<Integer> applicableIndexes = rulesByIndex.get(fieldRule.getKey());
                    removeSingleIndexFromOthers(rulesByIndex, fieldRule, applicableIndexes);
                }
            }
        }
        
        System.out.println(rulesByIndex);

        long myDeparture = 0;
        return myDeparture;
    }

    protected void removeSingleIndexFromOthers(Map<String, List<Integer>> rulesByIndex, Map.Entry<String, List<int[]>> fieldRule, List<Integer> applicableIndexes) {
        if (applicableIndexes.size() == 1) {
            final int thisId = applicableIndexes.get(0);
            for (Map.Entry<String, List<Integer>> ruleByIndex : rulesByIndex.entrySet()) {
                if (!ruleByIndex.getKey().equals(fieldRule.getKey())) {
                    int id = ruleByIndex.getValue().indexOf(thisId);
                    if (id > -1) {
                        System.out.println("removing " + thisId + " from " + ruleByIndex.getValue());
                        ruleByIndex.getValue().remove(id);
                    }
                }
            }
//            System.out.println("removed " + thisId + " from other indexes ");
//            System.out.println(rulesByIndex);
        }
    }

    protected Map<String, List<int[]>> getFieldRules(List<String> inputs) {
        Map<String, List<int[]>> fieldRules = new HashMap<>();
        for (int i = 0; i < inputs.size(); i++) {
            String input = inputs.get(i);
            Matcher matcher = fieldLineMatcher.matcher(input);
            if (matcher.matches()) {
                String field = matcher.group(1);
                String[] rules1 = matcher.group(2).split("-");
                String[] rules2 = matcher.group(3).split("-");
                List<int[]> rules = Arrays.asList(new int[] { Integer.parseInt(rules1[0]), Integer.parseInt(rules1[1]) }, new int[] { Integer.parseInt(rules2[0]), Integer.parseInt(rules2[1]) });
                fieldRules.put(field, rules);
            }
        }
        return fieldRules;
    }

    protected List<String> getNearbyTickets(List<String> inputs) {
        List<String> nearbyTickets = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            String input = inputs.get(i);

            if (input.matches("nearby tickets:")) {
                i++;
                while (i < inputs.size()) {
                    nearbyTickets.add(inputs.get(i));
                    i++;
                }
            }
        }

        return nearbyTickets;
    }

}
