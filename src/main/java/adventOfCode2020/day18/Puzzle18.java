package adventOfCode2020.day18;

import java.util.LinkedList;
import java.util.List;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle18 extends AbstractPuzzle {

    public Puzzle18(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        return doMath(false);
    }

    protected long doMath(boolean advanced) {
        List<String> inputs = getInput();
        long total = 0;
        for (String input : inputs) {
            char[] tokens = input.toCharArray();
            LinkedList<String> queue = new LinkedList<>();
            for (char token : tokens) {
                if (Character.isWhitespace(token)) {
                    continue;
                } else {
                    queue.add(String.valueOf(token));
                }
            }

            total += doMath(queue, advanced);
        }
        return total;
    }

    protected long doMath(LinkedList<String> queue, boolean advanced) {
//        System.out.println("Queue at start: " + queue);
        doParenthesis(queue, advanced);

        while (queue.size() > 1) {
            long first = Long.parseLong(queue.removeFirst());
            String operand = queue.removeFirst();
            long second = Long.parseLong(queue.removeFirst());
            if (operand.equals("*")) {
                if (advanced) {
                    queue.addFirst(String.valueOf(second));
                    queue.addFirst(String.valueOf(first * doMath(queue, advanced)));
                } else {
                    queue.addFirst(String.valueOf(first * second));
                }
            } else if (operand.equals("+")) {
                queue.addFirst(String.valueOf(first + second));
            }
        }

        return Long.parseLong(queue.removeFirst());
    }

    protected void doParenthesis(LinkedList<String> queue, boolean advanced) {
        for (int i = 0; i < queue.size(); i++) {
            int parens = 0;
            if (queue.get(i).equals("(")) {
                int startIndex = i;
                parens++;
                while (parens > 0) {
                    i++;
                    if (queue.get(i).equals("(")) {
                        parens++;
                    } else if (queue.get(i).equals(")")) {
                        parens--;
                    }
                }
                int endIndex = i;
                LinkedList<String> innerQueue = new LinkedList<>(queue.subList(startIndex + 1, endIndex));
                long innerQueueResult = doMath(innerQueue, advanced);
                queue.subList(startIndex, endIndex + 1).clear();
                queue.add(startIndex, String.valueOf(innerQueueResult));
                i = 0;
            }
        }
    }

    @Override
    public Object solve2() {
        return doMath(true);
    }

}
