package alma;

import alma.api.AlmaComponent;

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
    private final Composition composition;              // List of components handled by the partition
    private final int stride;                           // Stride of the composition stored in the partition
    private final int iid;                              // Internal ID of the partition
    private int index;                                  // Current entity index
    private boolean isDirty;                            // Flag for dirty partition

    // ATTRIBUTES - AUXILIARY
    private final IdStack toRemove;

    public Partition(int iid, IdHandler idHandler, Composition composition) {
        this(iid, idHandler, composition, 1);
    }

    public Partition(int iid, IdHandler idHandler, Composition composition, int stride) {
        this.idHandler = idHandler;
        this.idStack = new IdStack(idHandler.invalidValue);
        this.entitySlots = new int[idHandler.itemsPerPartition];
        this.toRemove = new IdStack(idHandler.invalidValue);
        this.componentsSlots = new AlmaComponent[idHandler.itemsPerPartition * stride];
        this.composition = composition;
        this.stride = composition.getSize();
        this.iid = iid;
        this.index = -1;
        this.isDirty = false;
    }

    // METHODS

    public int addEntity(AlmaComponent[] components) {
        int id = idStack.pop();
        if (id == idHandler.invalidValue) id = idHandler.generateIID(iid, index++);
        int pos = idHandler.getItemId(id);
        for (int i = 0; i < stride; i++) {
            componentsSlots[pos + i] = components[i];
        }
        return id;
    }

    public void removeEntity(int entity) {
        toRemove.push(entity);
        isDirty = true;
    }

    public AlmaComponent[] fetchEntityComponents(int entity) {
        AlmaComponent[] entityComponents = new AlmaComponent[stride];
        int first = idHandler.getItemId(entity);
        java.lang.System.arraycopy(componentsSlots, first, entityComponents, 0, stride);
        return entityComponents;
    }

    /**
     * Updates the partition lists and
     */
    public void flush() {
        if (isDirty) {
            int idToRemove = toRemove.pop();
            while(idToRemove != idHandler.invalidValue) {
                int pos = idHandler.getItemId(idToRemove);
                entitySlots[pos] = entitySlots[index--];
                for (int i = pos; i < pos + stride; i++) {
                    componentsSlots[i] = null;
                }
                idStack.push(idToRemove);
                idToRemove = toRemove.pop();
            }
        }
    }
}
