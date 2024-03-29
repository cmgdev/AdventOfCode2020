package adventOfCode2020.day21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle21 extends AbstractPuzzle {

    long answer1;
    String answer2;

    public Puzzle21(boolean isTest) {
        super(isTest);
        solvePuzzle();
    }

    @Override
    public Object solve1() {
        return answer1;
    }

    @Override
    public Object solve2() {
        return answer2;
    }

    public void solvePuzzle() {
        List<String> inputs = getInput();

        Map<String, List<Set<String>>> allergensToRecipes = new HashMap<>();
        Set<Set<String>> distinctIngrediantSets = new HashSet<>();
        for (String input : inputs) {
            String allergenTokens = input.substring(input.indexOf("("));
            String[] allergens = allergenTokens.substring(10, allergenTokens.length() - 1).split(",");

            String[] rawIngrediants = input.substring(0, input.indexOf("(")).split(" ");
            for (String allergen : allergens) {
                allergen = allergen.trim();
                Set<String> ingrediants = new HashSet<>();
                for (String ingrediant : rawIngrediants) {
                    ingrediants.add(ingrediant);
                }
                List<Set<String>> existing = allergensToRecipes.getOrDefault(allergen, new ArrayList<>());
                existing.add(ingrediants);
                distinctIngrediantSets.add(ingrediants);
                allergensToRecipes.put(allergen, existing);
            }
        }

        Set<String> potentialAllergens = new HashSet<>();

        for (Map.Entry<String, List<Set<String>>> allergenRecipes : allergensToRecipes.entrySet()) {
            Set<String> potentialIngrediants = new HashSet<>(allergenRecipes.getValue().get(0));
            for (Set<String> ingrediants : allergenRecipes.getValue()) {
                potentialIngrediants.retainAll(ingrediants);
            }
            potentialAllergens.addAll(potentialIngrediants);
            allergensToRecipes.put(allergenRecipes.getKey(), Arrays.asList(potentialIngrediants));
        }

        answer1 = 0;
        for (Set<String> orig : distinctIngrediantSets) {
            for (String ingr : orig) {
                if (!potentialAllergens.contains(ingr)) {
                    answer1++;
                }
            }
        }

        Map<String, List<String>> reduced = new HashMap<>();
        for (Map.Entry<String, List<Set<String>>> allergenRecipes : allergensToRecipes.entrySet()) {
            reduced.put(allergenRecipes.getKey(), new ArrayList<>(allergenRecipes.getValue().get(0)));
        }

        long countSinglesStart = 0;
        long countSinglesEnd = countSinglesStart + 1;
        do {
            countSinglesStart = reduced.values().stream().filter(s -> s.size() == 1).count();

            for (Map.Entry<String, List<String>> entry : reduced.entrySet()) {
                if (entry.getValue().size() == 1) {
                    for (Map.Entry<String, List<String>> entry2 : reduced.entrySet()) {
                        if (entry2.getValue().size() > 1) {
                            entry2.getValue().remove(entry.getValue().get(0));
                        }
                    }
                }
            }

            countSinglesEnd = reduced.values().stream().filter(s -> s.size() == 1).count();
        } while (countSinglesStart < countSinglesEnd);

        List<String> keys = new ArrayList<>(reduced.keySet());
        answer2 = keys.stream().sorted().map(k -> reduced.get(k).get(0)).collect(Collectors.joining(","));
    }

}
