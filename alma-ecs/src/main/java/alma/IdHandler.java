package alma;

/**
 * Class to manage IDs for partition members. Generates IID (Internal ID) based on integers to optimize component and
 * entity localization. It also manages UUID generation allow entities to be universally identifiable between instances
 * of Alma.
 *
 * @author Santiago Barreiro
 */
public final class IdHandler {

    // CONSTANTS
    public static final int MAX_BITS = 31;                      // Max bits for an ID (31 -> signed integer)
    public static final int DEFAULT_PARTITION_BITS = 12;        // How many bits are reserved for the partition identification
    public static final int DEFAULT_LINK_CAPACITY_BITS = 12;    // Default partition link capacity (2^capacity)

    // ATTRIBUTES
    public final int maxPartitions;                             // Max amount of partitions
    public final int itemsPerPartition;                         // Max items per partition
    public final int partitionBits;                             // Bits reserved fot the partition segment
    public final int partitionBitShift;                         // Bits reserved fot the partition segment
    public final int partitionMask;                             // Masks other bits so only the partition segment is visible
    public final int partitionChunkCapacityBits;                // Partition chunk capacity bits
    public final int partitionChunkCapacity;                    // Partition chunk capacity
    public final int partitionChunkMask;                        // Masks the partition chunk bits
    public final int itemMask;                                  // Masks other bits so only the item segment is visible
    public final int invalidValue = 1 << MAX_BITS;              // Value indicating a non valid ID

    // CONSTRUCTORS
    public IdHandler() {
        this(DEFAULT_PARTITION_BITS);
    }

    public IdHandler(int partitionBits) {
        this(partitionBits, DEFAULT_LINK_CAPACITY_BITS);
    }

    public IdHandler(int partitionBits, int linkBits) {
        this.partitionBits = partitionBits;
        this.partitionBitShift = (MAX_BITS - partitionBits);
        this.maxPartitions = (1 << partitionBits) - 1;
        this.partitionChunkCapacityBits = linkBits;
        this.partitionChunkCapacity = (1 << linkBits);
        this.partitionChunkMask = ((1 << (linkBits)) - 1);
        this.itemsPerPartition = (1 << partitionBitShift) - 1;
        this.partitionMask = maxPartitions << partitionBitShift;
        this.itemMask = (1 << partitionBitShift) - 1;
    }

    // METHODS
    /**
     * Generates an ID using the partition and item ID within one single integer
     * @param partition ID of the partition
     * @param item ID of the item
     * @return The complete ID with both smaller IDs merged
     */
    public int generateIID(int partition, int item) {
        return (partition << partitionBitShift & partitionMask) | item & itemMask;
    }

    /**
     * Gets the partition ID from an elements ID
     * @param id ID of the element
     * @return ID of the partition
     */
    public int getPartitionId(int id) {
        return (id & partitionMask) >>> partitionBitShift;
    }

    /**
     * Gets the item ID from an elements ID
     * @param id ID of the element
     * @return ID of the item
     */
    public int getItemId(int id) {
        return id & itemMask;
    }

    public int getPartitionChunkPos(int id) {
        return id & partitionChunkMask;
    }

    public int getPartitionChunk(int id) {
        return (id & itemMask) >> partitionChunkCapacityBits;
    }
}
