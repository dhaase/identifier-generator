package eu.dirk.haase.identifier;

public class CompactIdentifierTexter {


    private final static char[] ALL_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'P', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'p', 'x', 'y', 'z',
            '$', '#'};

    private final static int MASK_6_BIT_I = 0b00000000000000000000000000111111;
    private final static int MASK_2_BIT_I = 0b00000000000000000000000000000011;

    private final static long MASK_6_BIT_L = 0b0000000000000000000000000000000000000000000000000000000000111111L;
    private final static long MASK_4_BIT_L = 0b0000000000000000000000000000000000000000000000000000000000001111L;


    public static String toText(final int number) {
        final char[] digits = {'0', '0', '0', '0', '0', '0'};
        int idx = 0;

        digits[idx++] = ALL_DIGITS[(number >>> 26) & MASK_6_BIT_I];
        digits[idx++] = ALL_DIGITS[(number >>> 20) & MASK_6_BIT_I];
        digits[idx++] = ALL_DIGITS[(number >>> 14) & MASK_6_BIT_I];
        digits[idx++] = ALL_DIGITS[(number >>> 8) & MASK_6_BIT_I];
        digits[idx++] = ALL_DIGITS[(number >>> 2) & MASK_6_BIT_I];
        digits[idx++] = ALL_DIGITS[number & MASK_2_BIT_I];

        return String.valueOf(digits);
    }


    public static String toText(final long number) {
        final char[] digits = {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};
        int idx = 0;

        digits[idx++] = ALL_DIGITS[(int) ((number >>> 58) & MASK_6_BIT_L)];
        digits[idx++] = ALL_DIGITS[(int) ((number >>> 52) & MASK_6_BIT_L)];
        digits[idx++] = ALL_DIGITS[(int) ((number >>> 46) & MASK_6_BIT_L)];
        digits[idx++] = ALL_DIGITS[(int) ((number >>> 40) & MASK_6_BIT_L)];
        digits[idx++] = ALL_DIGITS[(int) ((number >>> 34) & MASK_6_BIT_L)];
        digits[idx++] = ALL_DIGITS[(int) ((number >>> 28) & MASK_6_BIT_L)];
        digits[idx++] = ALL_DIGITS[(int) ((number >>> 22) & MASK_6_BIT_L)];
        digits[idx++] = ALL_DIGITS[(int) ((number >>> 16) & MASK_6_BIT_L)];
        digits[idx++] = ALL_DIGITS[(int) ((number >>> 10) & MASK_6_BIT_L)];
        digits[idx++] = ALL_DIGITS[(int) ((number >>> 4) & MASK_6_BIT_L)];
        digits[idx++] = ALL_DIGITS[(int) (number & MASK_4_BIT_L)];

        return String.valueOf(digits);
    }


}
