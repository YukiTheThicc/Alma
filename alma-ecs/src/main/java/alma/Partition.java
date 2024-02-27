package alma;

import alma.api.AlmaComponent;
import alma.utils.AlmaException;
import alma.utils.IdStack;

/**
 * A partition is a linked data structure that holds the data from a specific entity composition.
 *
 * @author Santiago Barreiro
 */
public final class Partition {

    // ATTRIBUTES - MAIN
    private final IdHandler idHandler;                  // IdHandler for the partition
    private final IdStack idStack;                      // IdStack of reusable IDs for the partition
    private final int[] entitySlots;                    // List of entities handled by the partition
    private final AlmaComponent[] componentsSlots;      // List of components handled by the partition
    private final int stride;                           // Stride of the composition stored in the partition
    private final int iid;                              // Internal ID of the partition
    private int size;                                   // Current entity size
    private boolean isDirty;                            // Flag for dirty partition

    // ATTRIBUTES - AUXILIARY
    private final IdStack toRemove;

    public Partition(int iid, IdHandler idHandler) {
        this(iid, idHandler, 1);
    }

    public Partition(int iid, IdHandler idHandler, int stride) {
        this.idHandler = idHandler;
        this.idStack = new IdStack(idHandler.invalidValue);
        this.entitySlots = new int[idHandler.itemsPerPartition];
        this.toRemove = new IdStack(idHandler.invalidValue);
        this.componentsSlots = new AlmaComponent[idHandler.itemsPerPartition * stride];
        this.stride = stride;
        this.iid = iid;
        this.size = 0;
        this.isDirty = false;
    }

    // METHODS

    /**
     * UNSAFE. Adds the passed list of components as an entity for this partition. Only checks size of the array
     * @param components Array of component instances
     * @return ID of the new entity
     */
    public int addEntity(AlmaComponent[] components) {
        if (components.length == stride) {
            int id = idStack.pop();
            if (id == idHandler.invalidValue) id = idHandler.generateIID(iid, size);
            int pos = idHandler.getItemId(id);
            for (int i = 0; i < stride; i++) {
                int c_pos = pos * stride + i;
                if (componentsSlots[c_pos] != null) componentsSlots[c_pos].copy(components[i]);
                else componentsSlots[c_pos] = components[i];
            }
            entitySlots[pos] = id;
            size++;
            return id;
        } else {
            throw new AlmaException("Tried to insert wrong amount of components in partition");
        }
    }

    /**
     * Adds an entity ID to the list of entities to remove
     * @param entity ID of the entity to remove
     */
    public void removeEntity(int entity) {
        if (idHandler.getPartitionId(entity) == iid) {
            toRemove.push(entity);
            isDirty = true;
        } else {
            throw new AlmaException("Tried to remove an entity with a different partition id");
        }
    }

    public int size() {
        return size;
    }

    /**
     * Fetches the components from an entity. Throws exception if entity is not from this partition.
     * @param entity ID of the entity to recover the components from
     * @return Array of components from the passed entity
     */
    public AlmaComponent[] fetchEntityComponents(int entity) {
        if (idHandler.getPartitionId(entity) != iid) throw new AlmaException("Tried to retrieve components for an entity of a different composition");
        AlmaComponent[] entityComponents = new AlmaComponent[stride];
        int first = idHandler.getItemId(entity);
        java.lang.System.arraycopy(componentsSlots, first * stride, entityComponents, 0, stride);
        return entityComponents;
    }

    /**
     * Updates the partition lists. To avoid the GC, components inside this partition will not be set to null and will
     * be reused.
     */
    public void update() {
        if (isDirty) {
            int idToRemove = toRemove.pop();
            while(idToRemove != idHandler.invalidValue) {
                int pos = idHandler.getItemId(idToRemove);
                entitySlots[pos] = entitySlots[size--];
                idStack.push(idToRemove);
                idToRemove = toRemove.pop();
            }
        }
    }
}
