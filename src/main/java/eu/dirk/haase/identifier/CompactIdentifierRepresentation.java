package eu.dirk.haase.identifier;

public class CompactIdentifierRepresentation {


    private final static char[] ALL_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '$', '#'};

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
