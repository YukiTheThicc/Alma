package alma.utils;

import java.time.Instant;
import java.util.Random;

public final class UUIDGen {

    public static final int FRAGMENTS = 2;
    public static final int FIRST_FRAG_SIZE = 4;
    public static final int SECOND_FRAG_SIZE = 4;

    public static long generateUUID() {
        int timeFragment = Instant.now().getNano();
        int randomFragment = new Random().nextInt();
        return (((long)timeFragment) << 32) | (randomFragment & 0xffffffffL);
    }
}
