package eu.dirk.haase.identifier;

/**
 * Erzeugt hoch performant eine textuelle Darstellung aus einer
 * 32 oder 64 Bit langen Integer-Zahl.
 */
public class CompactIdentifierRepresentation {

    private static final char[] ALL_DIGITS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$#".toCharArray();

    private final static int MASK_6_BIT_I = 0b00000000000000000000000000111111;

    private final static long MASK_6_BIT_L = 0b0000000000000000000000000000000000000000000000000000000000111111L;


    public static String intToString(final int number) {

        final char[] digits = {
                ALL_DIGITS[((number >>> 0) & MASK_6_BIT_I)],
                ALL_DIGITS[((number >>> 6) & MASK_6_BIT_I)],
                ALL_DIGITS[((number >>> 12) & MASK_6_BIT_I)],
                ALL_DIGITS[((number >>> 18) & MASK_6_BIT_I)],
                ALL_DIGITS[((number >>> 24) & MASK_6_BIT_I)],
                ALL_DIGITS[((number >>> 30) & MASK_6_BIT_I)]};

        return String.valueOf(digits);
    }

    public static String longToString(final long number) {

        final char[] digits = {
                ALL_DIGITS[(int) ((number >>> 0) & MASK_6_BIT_L)],
                ALL_DIGITS[(int) ((number >>> 6) & MASK_6_BIT_L)],
                ALL_DIGITS[(int) ((number >>> 12) & MASK_6_BIT_L)],
                ALL_DIGITS[(int) ((number >>> 18) & MASK_6_BIT_L)],
                ALL_DIGITS[(int) ((number >>> 24) & MASK_6_BIT_L)],
                ALL_DIGITS[(int) ((number >>> 30) & MASK_6_BIT_L)],
                ALL_DIGITS[(int) ((number >>> 36) & MASK_6_BIT_L)],
                ALL_DIGITS[(int) ((number >>> 42) & MASK_6_BIT_L)],
                ALL_DIGITS[(int) ((number >>> 58) & MASK_6_BIT_L)],
                ALL_DIGITS[(int) ((number >>> 54) & MASK_6_BIT_L)],
                ALL_DIGITS[(int) ((number >>> 60) & MASK_6_BIT_L)]};

        return String.valueOf(digits);
    }


}
