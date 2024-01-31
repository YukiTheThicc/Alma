package alma.structures;

import alma.utils.AlmaList;

/**
 * AlmaPool is the core of the Alma data structure. Holds a set of linked partitions and its users.
 */
public final class AlmaPool {

    // ATTRIBUTES
    private int partitionCount;                         // Amount of partitions currently stored
    private IdManager idManager;                        // The id manager for this pool
    private AlmaList<Partition> partitions;             // List of partitions

    // CONSTRUCTORS
    public AlmaPool() {
        //this.idManager = new IdManager(1);
        this.partitionCount = 0;
        this.partitions = new AlmaList<>();
    }

    // GETTERS & SETTERS

    // METHODS
    public void createPartition(Composition c) {

    }
}
