package eu.dirk.haase.identifier;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * Ein Generator um 32 Bit Identifier (= Kennungen) zu erzeugten.
 * <p>
 * Dieser Generator liefert mit jedem Aufruf von {@link #nextLong()}
 * eine neue Id. Die generierten Ids sind <b>nicht</b> fortlaufend da
 * sie mit einem {@link Checksum}-Generator gebildet werden.
 * <p>
 * Jede Instanz dieses Generators erzeugt seine eigene, von anderen Instanzen
 * unabh&auml;ngige, Folge von Ids. Es ist somit mit hoher Wahrscheinlichkeit
 * (= mit der Kollisions-Wahrscheinlichkeit von CRC32) ausgeschlossen das zwei
 * Instanzen dieses Generators gleiche Ids erzeugen.
 * <p>
 * Dieser Generator ist auch bei nebenl&auml;figen Zugriffen sicher.
 * Allerdings empfiehlt es sich f&uuml;r jeden Thread eine eigene Instanz zu
 * erzeugen, um Wartezyklen durch Ressourcenkonflikt-Situationen zu vermeiden.
 * <p>
 * Dieser Generator ist f&uuml;r sein Haupteinsatzgebiet, den Request- und
 * Call-Ids, ausreichend frei von m&ouml;glichen Kollisionen, sofern diese Ids
 * nur zur manuellen Nachverfolgung einzelner Requests zum Beispiel in Logs dienen.
 * Das bedeutet aber auch, das dieser Generator <b>nicht</b> vollst&auml;ndig frei
 * von Kollisionen ist, daher sind diese Ids stets nur &uuml;ber einen relativ
 * kurzen Zeitraum hinweg eindeutig.
 * <p>
 * Intern wird der {@link Checksum}-Generator {@link CRC32} verwendet.
 * Die Wahrscheinlichkeit von Kollisionen wird praktisch vollst&auml;ndig
 * von der Qualit&auml;t des eingesetzten {@link Checksum}-Generators bestimmt.
 */
public final class TransientIdentifierGenerator implements IdentifierGenerator {

    private static final long init1 = new SecureRandom().nextLong();

    private final AtomicLong localCounter;

    private final long init2;


    public TransientIdentifierGenerator() {
        this.init2 = System.nanoTime();
        this.localCounter = new AtomicLong(0);
    }

    @Override
    public int nextInt() {
        return (int) nextLong();
    }

    @Override
    public long nextLong() {
        return SipHashInline.hash24(init1, init2, this.localCounter.incrementAndGet());
    }

}
