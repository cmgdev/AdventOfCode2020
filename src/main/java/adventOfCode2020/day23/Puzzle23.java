package adventOfCode2020.day23;

import java.math.BigInteger;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle23 extends AbstractPuzzle {

    public Puzzle23(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        String startingString = getInput().get(0);

        Cup first = new Cup(Integer.parseInt(String.valueOf(startingString.charAt(0))));
        int numCups = 1;
        Cup previous = first;
        for (int i = 1; i < startingString.length(); i++) {
            Cup next = new Cup(Integer.parseInt(String.valueOf(startingString.charAt(i))));
            previous.next = next;
            previous = next;
            numCups++;
        }
        previous.next = first;

        Cup current = first;
        for (int i = 1; i <= numCups; i++) {
            int previousValue = current.value - 1;
            previousValue = previousValue > 0 ? previousValue : numCups;
            previous = current.next;
            while (previous.value != previousValue) {
                previous = previous.next;
            }
            current.minusOne = previous;
            current = current.next;
        }

        int numMoves = 100;
        first = runCrabGame(first, numMoves, numCups);

        while (first.value != 1) {
            first = first.next;
        }
        first = first.next;
        StringBuilder finalOrder = new StringBuilder();
        while (first.value != 1) {
            finalOrder.append(first.value);
            first = first.next;
        }
        System.out.println(finalOrder);
        return finalOrder.toString();
    }

    protected Cup runCrabGame(Cup first, int numMoves, int numCups) {
        Cup current = first;
        for (int move = 1; move <= numMoves; move++) {

            Cup pickup1 = current.next;
            Cup pickup2 = pickup1.next;
            Cup pickup3 = pickup2.next;
            Cup currentNextNext = pickup3.next;

            Cup destinationCup = current.minusOne;
            while (destinationCup.value == pickup1.value || destinationCup.value == pickup2.value || destinationCup.value == pickup3.value) {
                destinationCup = destinationCup.minusOne;
            }

            Cup temp = destinationCup.next;
            destinationCup.next = pickup1;
            pickup3.next = temp;
            current.next = currentNextNext;

            current = current.next;
        }

        return current;
    }

    @Override
    public Object solve2() {
        String startingString = getInput().get(0);

        Cup first = new Cup(Integer.parseInt(String.valueOf(startingString.charAt(0))));
        Cup nineCup = first;
        Cup oneCup = first;
        int numCups = 1;
        Cup previous = first;
        for (int i = 1; i < startingString.length(); i++) {
            Cup next = new Cup(Integer.parseInt(String.valueOf(startingString.charAt(i))));
            if (next.value == 9) {
                nineCup = next;
            }
            if (next.value == 1) {
                oneCup = next;
            }
            previous.next = next;
            previous = next;
            numCups++;
        }

        previous.next = first;

        Cup current = first;
        for (int i = 1; i < numCups; i++) {
            int previousValue = current.value - 1;
            previousValue = previousValue > 0 ? previousValue : numCups;
            previous = current.next;
            while (previous.value != previousValue) {
                previous = previous.next;
            }
            current.minusOne = previous;
            current = current.next;
        }

        previous = current;
        int previousValue = previous.value - 1;
        Cup findMinusOne = previous.next;
        while (findMinusOne.value != previousValue) {
            findMinusOne = findMinusOne.next;
        }
        previous.minusOne = findMinusOne;

        for (int i = numCups + 1; i <= 1000000; i++) {
            Cup next = new Cup(i);
            previous.next = next;
            if (i == 10) {
                next.minusOne = nineCup;
            } else {
                next.minusOne = previous;
            }
            previous = next;
            numCups++;
        }
        previous.next = first;
        oneCup.minusOne = previous;

        int numMoves = 10000000;
        first = runCrabGame(first, numMoves, numCups);

        while (first.value != 1) {
            first = first.next;
        }

        BigInteger next1 = BigInteger.valueOf(first.next.value);
        BigInteger next2 = BigInteger.valueOf(first.next.next.value);

        System.out.println("next 2: " + next1 + " " + next2);
        BigInteger result = next1.multiply(next2);
        String resultStr = result.toString();
        System.out.println(resultStr);
        return resultStr;
    }

    class Cup {
        int value;
        Cup next;
        Cup minusOne;

        public Cup(int value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + value;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Cup other = (Cup) obj;
            if (value != other.value)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

    }

}
