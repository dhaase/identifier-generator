package eu.dirk.haase.identifier;

/**
 * Ein Generator um 32 oder 64 Bit Identifier (= Kennungen) zu erzeugen.
 */
public interface IdentifierGenerator {

    int nextInt();

    long nextLong();

    int lastInt();

    long lastLong();

}
