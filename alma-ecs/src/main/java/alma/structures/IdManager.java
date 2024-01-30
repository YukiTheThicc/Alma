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
    public IdManager(int chunkBit) {

        int partitionCount = 1 << (MAX_BIT_INDEX - chunkBit);
        this(chunkBit, partitionCount, (1 << (TOTAL_BIT - MAX_BIT_INDEX)) - 1,
                ((1 << (MAX_BIT_INDEX - chunkBit)) - 1) << chunkBit, 1 << Math.min(chunkBit, MAX_BIT_INDEX),
                (1 << chunkBit) - 1
        );
    }

    public int createId(int chunkId, int objectId) {
        return (chunkId & chunkIdBitMask) << chunkBit
                | (objectId & objectIdBitMask);
    }

    public int mergeId(int id, int objectId) {
        return (id & chunkIdBitMaskShifted) | objectId;
    }

    public int fetchChunkId(int id) {
        return (id >>> chunkBit) & chunkIdBitMask;
    }

    public int fetchObjectId(int id) {
        return id & objectIdBitMask;
    }

    public static String toString(int id) {
        return
    }
}
