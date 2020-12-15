package adventOfCode2020.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

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
                    value = StringUtils.leftPad(value, mask.length(), '0');
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

        return memory.values().stream().mapToLong(s -> Long.valueOf(s, 2)).sum();
    }

    @Override
    public Object solve2() {
        List<String> input = getInput();

        Map<Long, Integer> memory = new HashMap<>();

        int currentLine = 0;
        while (currentLine < input.size()) {
            String line = input.get(currentLine);
            if (line.startsWith("mask")) {
                String mask = line.substring(line.indexOf("=") + 1).trim();
                currentLine++;
                line = input.get(currentLine);
                while (line.startsWith("mem")) {
                    String memAddr = Integer.toBinaryString(Integer.parseInt(line.substring(line.indexOf("[") + 1, line.indexOf("]"))));
                    memAddr = StringUtils.leftPad(memAddr, mask.length(), '0');
                    int value = Integer.parseInt(line.substring(line.indexOf("=") + 1).trim());

                    StringBuilder maskedAddr = new StringBuilder("");
                    for (int i = 0; i < mask.length(); i++) {
                        maskedAddr.append(mask.charAt(i) == '0' ? memAddr.charAt(i) : mask.charAt(i));
                    }

                    int xcount = StringUtils.countMatches(maskedAddr, 'X');

                    for (int i = 0; i < Math.pow(2, xcount); i++) {
                        char[] bin = StringUtils.leftPad(Integer.toBinaryString(i), xcount, '0').toCharArray();
                        String unExed = new String(maskedAddr);
                        for (int j = 0; j < bin.length; j++) {
                            unExed = unExed.replaceFirst("X", String.valueOf(bin[j]));
                        }
                        memory.put(Long.parseLong(unExed, 2), value);
                    }

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

        return memory.values().stream().mapToLong(i -> (long) i).sum();
    }

}
