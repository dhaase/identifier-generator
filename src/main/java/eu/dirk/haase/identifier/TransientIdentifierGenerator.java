package eu.dirk.haase.identifier;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * Ein Generator um 32 Bit Identifier (= Kennungen) zu erzeugen.
 * <p>
 * Dieser Generator liefert mit jedem Aufruf von {@link #nextLong()}
 * eine neue Id. Die generierten Ids sind <b>nicht</b> fortlaufend da
 * sie mit einem Hash-Generator gebildet werden.
 * <p>
 * Jede Instanz dieses Generators erzeugt seine eigene, von anderen Instanzen
 * unabh&auml;ngige, Folge von Ids. Es ist somit mit hoher Wahrscheinlichkeit
 * (= mit der Kollisions-Wahrscheinlichkeit des Hash-Generators) ausgeschlossen
 * das zwei Instanzen dieses Generators gleiche Ids erzeugen.
 * <p>
 * Dieser Generator ist auch bei nebenl&auml;figen Zugriffen sicher.
 * Allerdings empfiehlt es sich f&uuml;r jeden Thread eine eigene Instanz zu
 * erzeugen, um Wartezyklen bei Ressourcenkonflikt-Situationen zu vermeiden.
 * <p>
 * Dieser Generator ist f&uuml;r sein Haupteinsatzgebiet, den Request- und
 * Call-Ids, ausreichend frei von m&ouml;glichen Kollisionen, sofern diese Ids
 * nur zur manuellen Nachverfolgung einzelner Requests zum Beispiel in Logs dienen.
 * Das bedeutet aber auch, das dieser Generator <b>nicht</b> vollst&auml;ndig frei
 * von Kollisionen ist, daher sind diese Ids stets nur &uuml;ber einen relativ
 * kurzen Zeitraum hinweg eindeutig.
 * <p>
 * Intern wird der {@link SipHash24}-Generator verwendet.
 * Die Wahrscheinlichkeit von Kollisionen wird praktisch vollst&auml;ndig
 * von der Qualit&auml;t des eingesetzten {@link SipHash24}-Generators bestimmt.
 */
public final class TransientIdentifierGenerator implements IdentifierGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final long key1 = RANDOM.nextLong();

    private final AtomicLong localCounter;

    private final long key2;


    public TransientIdentifierGenerator() {
        this.key2 = RANDOM.nextLong();
        this.localCounter = new AtomicLong(0);
    }

    @Override
    public int nextInt() {
        return (int) nextLong();
    }

    @Override
    public long nextLong() {
        return SipHash24.hash(key1, key2, this.localCounter.incrementAndGet());
    }

    @Override
    public int lastInt() {
        return (int) lastLong();
    }

    @Override
    public long lastLong() {
        return SipHash24.hash(key1, key2, this.localCounter.get());
    }

    @Override
    public String toString() {
        return CompactIdentifierTexter.toText(lastLong());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        TransientIdentifierGenerator that = (TransientIdentifierGenerator) other;

        return (this.key1 == that.key1) && (this.key2 == that.key2);
    }

    @Override
    public int hashCode() {
        return (int) (key2 ^ (key2 >>> 32));
    }
}
