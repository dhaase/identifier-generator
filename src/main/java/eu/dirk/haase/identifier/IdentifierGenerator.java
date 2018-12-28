package eu.dirk.haase.identifier;

/**
 * Ein Generator um 32 oder 64 Bit Identifier (= Kennungen) zu erzeugen.
 */
public interface IdentifierGenerator {

    int lastInt();

    long lastLong();

    int nextInt();

    long nextLong();

}
