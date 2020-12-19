package adventOfCode2020.day19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle19 extends AbstractPuzzle {

    public Puzzle19(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<String> inputs = getInput();

        Map<Integer, String> rules = getRules(inputs);
        List<String> messages = inputs.stream().filter(i -> !i.contains(":")).collect(Collectors.toList());

        String regex = getRegex(0, rules);

        System.out.println(regex);

        long matches = 0;
        for (String message : messages) {
            if (message.matches(regex)) {
                matches++;
            }
        }

        return matches;
    }

    protected Map<Integer, String> getRules(List<String> inputs) {
        Map<Integer, String> rules = new HashMap<>();
        for (String input : inputs) {
            int i = input.indexOf(":");
            if (i > -1) {
                rules.put(Integer.parseInt(input.substring(0, i)), input.substring(i + 2).replaceAll("\"", ""));
            }
        }
        return rules;
    }

    protected String getRegex(int ruleNum, Map<Integer, String> rules) {
        StringBuilder sb = new StringBuilder("(");

        String rule = rules.get(ruleNum);
        String[] tokens = rule.split(" ");
        for (String token : tokens) {
            if (token.matches("[a-z]")) {
                return token;
            } else if (token.equals("|")) {
                sb.append("|");
            } else {
                sb.append(getRegex(Integer.parseInt(token), rules));
            }
        }

        sb.append(")");
        return sb.toString();
    }

    @Override
    public Object solve2() {
        List<String> inputs = getInput();
        return null;
    }

}
