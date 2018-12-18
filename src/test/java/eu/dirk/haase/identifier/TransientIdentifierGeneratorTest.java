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
    public void test_empirical_for_collisions_with_integer() {
        TransientIdentifierGenerator tig1 = new TransientIdentifierGenerator();
        TransientIdentifierGenerator tig2 = new TransientIdentifierGenerator();
        TransientIdentifierGenerator tig3 = new TransientIdentifierGenerator();
        TransientIdentifierGenerator tig4 = new TransientIdentifierGenerator();
        TransientIdentifierGenerator tig5 = new TransientIdentifierGenerator();
        TransientIdentifierGenerator tig6 = new TransientIdentifierGenerator();

        final int loopCount = 2_000_000;
        final int maxElements = loopCount * 6;

        Set<Integer> uniqueIdSet = new HashSet<>(maxElements);
        for (int j = 0; 1 > j; ++j) {
            uniqueIdSet.clear();

            long start = System.nanoTime();
            for (int i = 0; i < loopCount; ++i) {
                uniqueIdSet.add(tig1.nextInt());
                uniqueIdSet.add(tig2.nextInt());
                uniqueIdSet.add(tig3.nextInt());
                uniqueIdSet.add(tig4.nextInt());
                uniqueIdSet.add(tig5.nextInt());
                uniqueIdSet.add(tig6.nextInt());
            }
            long end = System.nanoTime();

            System.out.println("duration " + TimeUnit.NANOSECONDS.toMillis(end - start) + "ms");
            System.out.println("integer collisions: " + Math.abs(uniqueIdSet.size() - maxElements) + " of " + maxElements + " numbers");
        }
    }

}
