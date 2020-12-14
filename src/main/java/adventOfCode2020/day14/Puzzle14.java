package adventOfCode2020.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle14 extends AbstractPuzzle {

    public Puzzle14(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<String> input = getInput();

        Map<Integer, String> memory = new HashMap<>();
        int currentLine = 0;
        while (currentLine < input.size()) {
            String line = input.get(currentLine);
            if (line.startsWith("mask")) {
                String mask = line.substring(line.indexOf("=") + 1).trim();
                currentLine++;
                line = input.get(currentLine);
                while (line.startsWith("mem")) {
                    int memAddr = Integer.parseInt(line.substring(line.indexOf("[") + 1, line.indexOf("]")));
                    String value = Integer.toBinaryString(Integer.parseInt(line.substring(line.indexOf("=") + 1).trim()));
                    while (value.length() < mask.length()) {
                        value = "0" + value;
                    }
                    StringBuilder newValue = new StringBuilder("");
                    for (int i = 0; i < mask.length(); i++) {
                        newValue.append(mask.charAt(i) == 'X' ? value.charAt(i) : mask.charAt(i));
                    }
                    memory.put(memAddr, newValue.toString());
                    currentLine++;
                    if (currentLine >= input.size()) {
                        break;
                    }
                    line = input.get(currentLine);
                }
            } else {
                currentLine++;
            }
        }

        long sum = memory.values().stream().mapToLong(s -> Long.valueOf(s, 2)).sum();

        return sum;
    }

    @Override
    public Object solve2() {
        // TODO Auto-generated method stub
        return null;
    }

}
