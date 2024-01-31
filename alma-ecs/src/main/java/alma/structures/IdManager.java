package alma.structures;

/**
 * Internal ID manager to handle the issuing of IDs to component pools and components
 */
public record IdManager(int partitionCount, int partitionSize, int partitionMask, int componentMask) {

    // CONSTANTS
    public static final int MAX_BIT_INDEX = 31;                    // The highest index for a bit (32 bit int)
    public static final int PARTITION = 8;
    public static final int MAX_CHUNK_BIT = 16;

    // CONSTRUCTORS
}
