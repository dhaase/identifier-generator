package eu.dirk.haase.identifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class CompactIdentifierTexterTest {

    private final static char[] ALL_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '$', '#'};
    private final static int MASK_2_BIT_I = 0b00000000000000000000000000000011;
    private final static int MASK_5_BIT_I = 0b00000000000000000000000000011111;
    private final static int MASK_6_BIT_I = 0b00000000000000000000000000111111;
    private final static int MASK_7_BIT_I = 0b00000000000000000000000001111111;

    private void print(int n) {
        for (int i = 0; 32 > i; i = i + 6) {
            final int i1 = (n >>> i) & MASK_6_BIT_I;
            System.out.println(i + ": " + toBinaryString32(i1) + ": -> " + i1);
        }
    }

    //string collisions: 58849109 vs 57014101: LTVP30
    @Test
    public void test() {
        print(57014101);
        System.out.println(toBinaryString32(57014101) + ": " + ALL_DIGITS[25]);
        print(58849109);
        System.out.println(toBinaryString32(58849109) + ": " + ALL_DIGITS[32]);
        System.out.println(CompactIdentifierRepresentation.intToString(Integer.MIN_VALUE | Integer.MAX_VALUE));
        System.out.println(CompactIdentifierRepresentation.intToString(343865888));
    }

    private String toBinaryString32(int i) {
        String binaryWithOutLeading0 = Integer.toBinaryString(i);
        return "00000000000000000000000000000000"
                .substring(binaryWithOutLeading0.length())
                + binaryWithOutLeading0;
    }

}
