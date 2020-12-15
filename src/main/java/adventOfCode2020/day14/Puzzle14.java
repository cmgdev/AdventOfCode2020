package adventOfCode2020.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.IntStream;

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
                    final String value = StringUtils.leftPad(Integer.toBinaryString(Integer.parseInt(line.substring(line.indexOf("=") + 1).trim())), mask.length(), '0');
                    memory.put(Integer.parseInt(line.substring(line.indexOf("[") + 1, line.indexOf("]"))), IntStream.range(0, mask.length()).mapToObj(i -> mask.charAt(i) == 'X' ? value.charAt(i) : mask.charAt(i)).collect(Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append, StringBuilder::toString)));
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
                    final String memAddr = StringUtils.leftPad(Integer.toBinaryString(Integer.parseInt(line.substring(line.indexOf("[") + 1, line.indexOf("]")))), mask.length(), '0');
                    int value = Integer.parseInt(line.substring(line.indexOf("=") + 1).trim());

                    String maskedAddr = IntStream.range(0, mask.length()).mapToObj(i -> mask.charAt(i) == '0' ? memAddr.charAt(i) : mask.charAt(i)).collect(Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append, StringBuilder::toString));

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
