package alma;

import alma.structures.Partition;
import alma.utils.UUIDGen;

/**
 * An entity is the representation of an item within Alma. Entities are identifiable externally through an UUID, though
 * for performance reasons they are internally identified with integers. Entities don't store any in-game data nor
 * implement any in-game behaviour.
 *
 * @author santiago.barreiro
 */
public class Entity {

    // ATTRIBUTES
    private Partition partition;
    private int iid;
    private long uuid;

    // CONSTRUCTORS
    public Entity(Partition partition) {
        this.partition = partition;
        this.uuid = UUIDGen.generateUUID();
        this.iid = 0;
    }
}