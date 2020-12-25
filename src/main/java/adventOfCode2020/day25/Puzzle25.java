package adventOfCode2020.day25;

import java.util.List;

import adventOfCode2020.base.AbstractPuzzle;

public class Puzzle25 extends AbstractPuzzle {

    static final long SUBJECT = 7;
    static final long MOD = 20201227;

    public Puzzle25(boolean isTest) {
        super(isTest);
    }

    @Override
    public Object solve1() {
        List<String> input = getInput();
        long cardPublicKey = Long.parseLong(input.get(0));
        long doorPublicKey = Long.parseLong(input.get(1));

        int cardLoopSize = getLoopSize(cardPublicKey);
        int doorLoopSize = getLoopSize(doorPublicKey);
        
        System.out.println("card loop size: " + cardLoopSize);
        System.out.println("door loop size: " + doorLoopSize);
        
        long encryptionKey = getEncryptionKey(doorPublicKey, cardLoopSize);
        System.out.println(encryptionKey);

        return String.valueOf(encryptionKey);
    }

    protected int getLoopSize(long publicKey) {
        long subject = 1;
        int loops = 0;
        while (subject != publicKey) {
            subject *= SUBJECT;
            subject %= MOD;
            loops++;
        }
        return loops;
    }
    
    protected long getEncryptionKey( long publicKey, int loopSize) {
        long subject = 1;
        for( int i = 0; i < loopSize; i++) {
            subject *= publicKey;
            subject %= MOD;
        }
        return subject;
    }

    @Override
    public Object solve2() {
        // TODO Auto-generated method stub
        return null;
    }

}
