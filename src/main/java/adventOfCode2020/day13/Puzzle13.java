package adventOfCode2020.day13;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
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
                if (nextDeparture < soonestDepature) {
                    soonestDepature = nextDeparture;
                    departureBusId = busId;
                }
            }
        }

        return (soonestDepature - timestamp) * departureBusId;
    }

    @Override
    public Object solve2() {
        List<String> input = getInput();

        String[] busIds = input.get(1).split(",");
        List<Bus> busses = new ArrayList<>();

        for (int i = 0; i < busIds.length; i++) {
            String s = busIds[i];
            if (s.matches("[0-9]+")) {
                int busId = Integer.parseInt(s);
                busses.add(new Bus(BigInteger.valueOf(busId), busId, BigInteger.valueOf(i)));
            }
        }
        
        BigInteger earliestTime = BigInteger.ZERO;
        BigInteger runningProduct = BigInteger.ONE;
        
        for( Bus bus : busses ) {
            while(!earliestTime.add(bus.targetOffset).mod(bus.busId).equals(BigInteger.ZERO)) {
                earliestTime = earliestTime.add(runningProduct);
            }
            runningProduct = runningProduct.multiply(bus.busId);
        }
        
        // Implementation of chinese remainder theorem from
        // https://github.com/donth77/advent-of-code-2020/blob/main/day13/main.py
        // translated to Java by me
        /*
         * earliestTime = 0  # earliest timestamp all bus IDs depart at offsets matching list indices
            runningProduct = 1
            
            # chinese remainder theorem
            # t + index % id === 0 for each element
            #
            # [7, 13, 'x', 'x', 59, 'x', 31, 19]
            # index: 0, id: 7   -- 0 + 0 % 7 == 0
            # index: 1, id: 13  -- 77 + 1 % 13 == 0
            # index: 4, id: 59  -- 350 + 4 % 59 == 0
            # index: 6, id: 31  -- 70147 + 6 % 31  == 0
            # index: 7, id: 19  -- 1068781 + 7 % 19 == 0
            #
            # result is 1068781
            
            for (index, id) in enumerate(serviceIDs):
                if id == 'x':
                    continue
                while((earliestTime + index) % id != 0):
                    earliestTime += runningProduct
                runningProduct *= id
            print(f'\nPart 2\n{earliestTime}')
         */
        return earliestTime;
    }

    // this works on the small example input
    // but looks like it would run a very very long time on the longer input
    public Object solve2_runsForever() {
        List<String> input = getInput();

        String[] busIds = input.get(1).split(",");
        List<Bus> busses = new ArrayList<>();

        for (int i = 0; i < busIds.length; i++) {
            String s = busIds[i];
            if (s.matches("[0-9]+")) {
                int busId = Integer.parseInt(s);
                busses.add(new Bus(BigInteger.valueOf(busId), busId, BigInteger.valueOf(i)));
            }
        }

        BigInteger firstBusLoopNum = BigInteger.ZERO;
        Bus highestBus = busses.get(0);

        BigInteger nextDeparture = highestBus.busId.multiply(firstBusLoopNum);
        boolean linedUp = false;
        BigInteger debugInterval = BigInteger.valueOf(10000);

        while (!linedUp) {
            firstBusLoopNum = firstBusLoopNum.add(BigInteger.ONE);
            final BigInteger tempNextDeparture = highestBus.busId.multiply(firstBusLoopNum);
            if (firstBusLoopNum.mod(debugInterval).equals(BigInteger.ZERO)) {
                System.out.println("loop " + firstBusLoopNum + " with departure " + nextDeparture);
            }
            if (firstBusLoopNum.equals(BigInteger.valueOf(152683))) {
                System.out.println("wth??");
            }

            linedUp = busses.stream().allMatch(bus -> tempNextDeparture.add(bus.targetOffset).mod(bus.busId).equals(BigInteger.ZERO));
            nextDeparture = tempNextDeparture;
        }

        for (Bus bus : busses) {
            System.out.println(nextDeparture.add(bus.targetOffset) + " : " + bus.busId);
        }
        return nextDeparture;
    }

    public class Bus {
        BigInteger busId;
        long nextDeparture;
        BigInteger targetOffset;

        public Bus(BigInteger busId, long nextDeparture, BigInteger targetOffset) {
            this.busId = busId;
            this.nextDeparture = nextDeparture;
            this.targetOffset = targetOffset;
        }

    }

}
