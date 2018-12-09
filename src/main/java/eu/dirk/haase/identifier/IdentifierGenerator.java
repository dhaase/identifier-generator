package eu.dirk.haase.identifier;

public interface IdentifierGenerator {
    long lastLong();
    int nextInt();

    long nextLong();

    byte[] nextBytes();

}
