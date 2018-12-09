package eu.dirk.haase.identifier;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(BlockJUnit4ClassRunner.class)
public class TransientIdentifierGeneratorTest {

    @Test
    public void test_empirical_for_collisions() {
        TransientIdentifierGenerator tig1 = new TransientIdentifierGenerator();
        final int max = 10_000_000;
        Set<Integer> uniqueIds = new HashSet<>(max);
        long start = System.nanoTime();
        for (int i = 0; i < max; ++i) {
            uniqueIds.add(tig1.nextInt());
        }
        long end = System.nanoTime();
        System.out.println("" + TimeUnit.NANOSECONDS.toMillis(end - start));
        System.out.println("collisions: " + Math.abs(uniqueIds.size() - max));
    }

    @Test
    @Ignore
    public void test_random() {
        Random ran = new Random(System.currentTimeMillis());
        final int max = 10000000;
        Set<Integer> uniqueIds = new HashSet<>(max);
        long start = System.nanoTime();
        for (int i = 0; i < max; ++i) {
            uniqueIds.add(ran.nextInt());
        }
        long end = System.nanoTime();
        System.out.println("" + TimeUnit.NANOSECONDS.toMillis(end - start));
        System.out.println("collisions: " + Math.abs(uniqueIds.size() - max));
    }

}
