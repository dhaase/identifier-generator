package eu.dirk.haase.identifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.math.BigInteger;

@RunWith(BlockJUnit4ClassRunner.class)
public class SipHashInlineTest {

    private static final long BYTE_MASK = 0xFFL;

    private byte nthByteOfLong(int n, long number) {
        return (byte) ((number >>> (8 * n)) & BYTE_MASK);
    }

    @Test
    public void test() {
        final long val = 0xFAFAFAFAFAFAFAFAL;
        BigInteger value = BigInteger.valueOf(val);
        final byte[] data = value.toByteArray();

        System.out.println("A: " + data[4]);
        System.out.println("B: " + nthByteOfLong(1, value.longValue()));

        long hash2 = SipHash24.hash(1L, 3L, value.longValue());
        System.out.println("::" + hash2);
        System.out.println("::" + 6340469858319189276L);
    }

}
