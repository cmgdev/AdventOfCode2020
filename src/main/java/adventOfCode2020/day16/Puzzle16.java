package adventOfCode2020.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle16 extends AbstractPuzzle {

    private Pattern fieldLineMatcher = Pattern.compile("^([a-z0-9 ]+): ([0-9-]+) or ([0-9-]+)$");

    public Puzzle16(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<String> inputs = getInput();

        Map<String, List<int[]>> fieldRules = new HashMap<>();
        List<String> nearbyTickets = new ArrayList<>();

        for (int i = 0; i < inputs.size(); i++) {
            String input = inputs.get(i);
            Matcher matcher = fieldLineMatcher.matcher(input);
            if (matcher.matches()) {
                String field = matcher.group(1);
                String[] rules1 = matcher.group(2).split("-");
                String[] rules2 = matcher.group(3).split("-");
                List<int[]> rules = Arrays.asList(new int[] { Integer.parseInt(rules1[0]), Integer.parseInt(rules1[1]) }, new int[] { Integer.parseInt(rules2[0]), Integer.parseInt(rules2[1]) });
                fieldRules.put(field, rules);
            } else if (input.matches("nearby tickets:")) {
                i++;
                while (i < inputs.size()) {
                    nearbyTickets.add(inputs.get(i));
                    i++;
                }
            }
        }

        Collection<List<int[]>> ruleSets = fieldRules.values();
        long errorRate = 0;
        for (String nearbyTicket : nearbyTickets) {
            String[] fieldValues = nearbyTicket.split(",");
            for (String valueStr : fieldValues) {
                int value = Integer.parseInt(valueStr);
                boolean matchedRule = false;
                for (List<int[]> rules : ruleSets) {
                    for (int[] rule : rules) {
                        if (value >= rule[0] && value <= rule[1]) {
                            matchedRule = true;
                            continue;
                        }
                    }
                }
                if (!matchedRule) {
                    errorRate += value;
                }
            }
        }

        return errorRate;
    }

    @Override
    public Object solve2() {
        List<String> input = getInput();
        return null;
    }

}
