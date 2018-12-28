package eu.dirk.haase.identifier;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Ein transient Generator um 32 oder 64 Bit Identifier (= Kennungen) zu erzeugen.
 * <p>
 * Dieser Identifier-Generator h&auml;lt keinen globalen Zustand. Er basiert
 * daher auf einen zuf&auml;lligen (mit {@link SecureRandom}) Anfangszustand.
 * Jede Instanz, auch die serialisierten Instanzen, haben einen anderen
 * Anfangszustand.
 * <p>
 * Dieser Generator liefert mit jedem Aufruf von {@link #nextLong()}
 * eine neue Id. Die generierten Ids sind <b>nicht</b> fortlaufend da
 * sie mit einem Hash-Generator gebildet werden.
 * <p>
 * Jede Instanz dieses Generators erzeugt seine eigene, von anderen Instanzen
 * unabh&auml;ngige, Folge von Ids. Es ist somit mit hoher Wahrscheinlichkeit
 * (= mit der Kollisions-Wahrscheinlichkeit des {@link SipHash24}-Generators)
 * ausgeschlossen das zwei Instanzen dieses Generators gleiche Ids erzeugen.
 * <p>
 * Dieser Generator ist auch bei nebenl&auml;figen Zugriffen sehr sicher.
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
 * Der {@link SipHash24}-Generator implementiert eine keyed kryptographische Hashfunktion
 * und optimiert f&uuml;r kurze Bytefolgen und Geschwindigkeit.
 * Die Wahrscheinlichkeit von Kollisionen wird praktisch vollst&auml;ndig
 * von der Qualit&auml;t des eingesetzten {@link SipHash24}-Generators bestimmt.
 */
public final class TransientIdentifierGenerator implements IdentifierGenerator, Serializable {

    private static final SecureRandom RANDOM = new SecureRandom();
    /**
     * Der globale erste Schl&uuml;ssel mit dem dieser {@link IdentifierGenerator}
     * initialisiert wird.
     */
    private static final long globalKey1 = RANDOM.nextLong();
    private static final long serialVersionUID = 1L;
    /**
     * Z&auml;hler der bei jedem {@link TransientIdentifierGenerator#nextLong()} um Eins
     * erh&ouml;ht wird.
     * <p>
     * Diese Feldvariable ist transient und wird daher nicht serialisiert.
     */
    private transient final AtomicLong localCounter;

    /**
     * Der lokale zweite Schl&uuml;ssel mit dem dieser {@link IdentifierGenerator}
     * initialisiert wird.
     * <p>
     * Diese Feldvariable ist transient und wird daher nicht serialisiert.
     */
    private transient final long localKey2;


    public TransientIdentifierGenerator() {
        this(RANDOM.nextLong(), RANDOM.nextInt());
    }

    private TransientIdentifierGenerator(final long localKey2, final long initialValue) {
        this.localKey2 = localKey2;
        this.localCounter = new AtomicLong(initialValue);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final TransientIdentifierGenerator that = (TransientIdentifierGenerator) other;
        // Sind wir an dieser Stelle angelangt dann handelt es um
        // zwei unterschiedliche TransientIdentifierGenerator-Instanzen.
        // Wenn diese dann auch den gleichen Zustand haben, dann werden
        // sie auch stets die gleiche Folge von Ids erzeugen.
        // Somit sind dann diese zwei TransientIdentifierGenerator-Instanzen
        // aequivalent und daher berechtigterweise gleich.
        // Anzumerken bleibt, das diese equals-Methode nicht atomar ausgefuehrt
        // wird.
        return (this.localKey2 == that.localKey2) && (this.localCounter.get() == that.localCounter.get());
    }

    @Override
    public int hashCode() {
        return (int) (localKey2 ^ (localKey2 >>> 32));
    }

    @Override
    public int lastInt() {
        return (int) lastLong();
    }

    @Override
    public long lastLong() {
        return SipHash24.hash(globalKey1, localKey2, this.localCounter.get());
    }

    @Override
    public int nextInt() {
        return (int) nextLong();
    }

    @Override
    public long nextLong() {
        return SipHash24.hash(globalKey1, localKey2, this.localCounter.incrementAndGet());
    }

    @Override
    public String toString() {
        return CompactIdentifierRepresentation.longToString(lastLong());
    }

    private Object writeReplace()
            throws java.io.ObjectStreamException {
        return new TransientIdentifierGenerator();
    }
}
