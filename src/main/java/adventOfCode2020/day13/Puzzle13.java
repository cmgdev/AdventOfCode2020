package adventOfCode2020.day13;

import java.util.List;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle13 extends AbstractPuzzle {

    public Puzzle13(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<String> input = getInput();

        long timestamp = Long.parseLong(input.get(0));
        long soonestDepature = Long.MAX_VALUE;
        int departureBusId = 0;

        for (String s : input.get(1).split(",")) {
            if (s.matches("[0-9]+")) {
                int busId = Integer.parseInt(s);
                long loops = (timestamp / busId) + 1;
                long nextDeparture = busId * loops;
                if( nextDeparture < soonestDepature) {
                    soonestDepature = nextDeparture;
                    departureBusId = busId;
                }
            }
        }

        return (soonestDepature - timestamp) * departureBusId;
    }

    @Override
    public Object solve2() {
        // TODO Auto-generated method stub
        return null;
    }

}
