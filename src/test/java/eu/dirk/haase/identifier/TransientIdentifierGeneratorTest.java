package eu.dirk.haase.identifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(BlockJUnit4ClassRunner.class)
public class TransientIdentifierGeneratorTest {

    @Test
    public void test_empirical_for_collisions() {
        TransientIdentifierGenerator tig1 = new TransientIdentifierGenerator();
        TransientIdentifierGenerator tig2 = new TransientIdentifierGenerator();
        TransientIdentifierGenerator tig3 = new TransientIdentifierGenerator();
        TransientIdentifierGenerator tig4 = new TransientIdentifierGenerator();
        TransientIdentifierGenerator tig5 = new TransientIdentifierGenerator();
        TransientIdentifierGenerator tig6 = new TransientIdentifierGenerator();

        final int loopCount = 5_000;
        final int maxElements = loopCount * 6;

        Set<Long> uniqueIdSet = new HashSet<>(maxElements);
        long start = System.nanoTime();
        for (int i = 0; i < loopCount; ++i) {
            uniqueIdSet.add(tig1.nextLong());
            uniqueIdSet.add(tig2.nextLong());
            uniqueIdSet.add(tig3.nextLong());
            uniqueIdSet.add(tig4.nextLong());
            uniqueIdSet.add(tig5.nextLong());
            uniqueIdSet.add(tig6.nextLong());
        }
        long end = System.nanoTime();

        System.out.println("" + TimeUnit.NANOSECONDS.toMillis(end - start));
        System.out.println("collisions: " + Math.abs(uniqueIdSet.size() - maxElements));

        System.out.println("base: " + convFrmDecToBase(1234567890, DIGITS.length));
    }

    private final static char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','P','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','0','p','q','r','s','t','u','v','p','x','y','z'};

    static String convFrmDecToBase(int num, int base) {

        String res = "";
        int rem;
        // Convert input number is given base by repeatedly
        // dividing it by base and taking remainder
        while (num > base) {
            rem = num % base;
            res += DIGITS[rem];
            num /= base;
        }
        // Reverse the result
        return res + num;
    }
}
