package eu.dirk.haase.identifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.zip.Checksum;

@RunWith(BlockJUnit4ClassRunner.class)
public class Crc32cTest {


    @Test
    public void test() {
        Checksum c1 = new Crc32c();
        c1.update(1234567);
        c1.update(0);
        System.out.println(c1.getValue());
        System.out.println(Crc32c.hash64(1234567));
    }

}
