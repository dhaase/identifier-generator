package eu.dirk.haase.identifier;

import java.util.Arrays;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.Checksum;


public final class TransientIdentifierGenerator implements IdentifierGenerator {

    private static final int LONG_BYTE_LEN = 8;
    private static final int BIT_LEN = 8;

    private static final int BITS_0 = BIT_LEN * 0;
    private static final int BITS_8 = BIT_LEN * 1;
    private static final int BITS_16 = BIT_LEN * 2;
    private static final int BITS_24 = BIT_LEN * 3;
    private static final int BITS_32 = BIT_LEN * 4;
    private static final int BITS_40 = BIT_LEN * 5;
    private static final int BITS_48 = BIT_LEN * 6;
    private static final int BITS_56 = BIT_LEN * 7;

    private static final long BYTE_MASK = 0xFFL;

    private final Checksum checksum;

    private long localCounter;

    private final byte[] counterBytes;

    public TransientIdentifierGenerator() {
        this.checksum = new CRC32();
        this.counterBytes = new byte[LONG_BYTE_LEN];
        init();
    }

    @Override
    public long lastLong() {
        return localCounter;
    }

    @Override
    public int nextInt() {
        return (int) nextLong();
    }

    @Override
    public long nextLong() {
        synchronized (this.checksum) {
            this.checksum.reset();
            this.checksum.update(longToBytes(++localCounter), 0, LONG_BYTE_LEN);
            return this.checksum.getValue();
        }
    }

    @Override
    public byte[] nextBytes() {
        return Arrays.copyOf(longToBytes(nextLong()), LONG_BYTE_LEN);
    }

    private void init() {
        final UUID uuid = UUID.randomUUID();
        this.checksum.reset();
        this.checksum.update(longToBytes(uuid.getLeastSignificantBits()), 0, LONG_BYTE_LEN);
        this.checksum.update(longToBytes(uuid.getMostSignificantBits()), 0, LONG_BYTE_LEN);
        this.localCounter = this.checksum.getValue();
    }

    private byte[] longToBytes(long counter) {
        counterBytes[7] = (byte) ((counter >>> BITS_0) & BYTE_MASK);
        counterBytes[6] = (byte) ((counter >>> BITS_8) & BYTE_MASK);
        counterBytes[5] = (byte) ((counter >>> BITS_16) & BYTE_MASK);
        counterBytes[4] = (byte) ((counter >>> BITS_24) & BYTE_MASK);
        counterBytes[3] = (byte) ((counter >>> BITS_32) & BYTE_MASK);
        counterBytes[2] = (byte) ((counter >>> BITS_40) & BYTE_MASK);
        counterBytes[1] = (byte) ((counter >>> BITS_48) & BYTE_MASK);
        counterBytes[0] = (byte) ((counter >>> BITS_56) & BYTE_MASK);
        return counterBytes;
    }

}
