package eu.dirk.haase.identifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class CompactIdentifierTexterTest {

    private final static int MASK_6_BIT_I = 0b00000000000000000000000000111111;
    private final static int MASK_2_BIT_I = 0b00000000000000000000000000000011;

    @Test
    public void test() {
        System.out.println(Integer.toBinaryString(-80945497));
        System.out.println(Integer.toBinaryString(-133374297));
        System.out.println(CompactIdentifierTexter.toText(-80945497));
        System.out.println(CompactIdentifierTexter.toText(-133374297));
    }


}
