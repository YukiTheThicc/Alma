package alma.compositions;

import alma.Partition;
import alma.api.AlmaComponent;
import alma.utils.AlmaException;

/**
 * A composition is a structure describing the composition of one type of entity. It holds a map of the component classes
 * that make up an entity from this composition.
 *
 * @author Santiago Barreiro
 */
public final class Composition {

    // ATTRIBUTES
    private final Class<?>[] componentTypes;
    private Partition partition = null;
    private final int size;

    // CONSTRUCTORS
    public Composition(Class<?>[] componentTypes) {
        this.componentTypes = componentTypes;
        this.size = componentTypes.length;
    }

    // GETTERS & SETTERS
    public Class<?>[] getComponentTypes() {
        return componentTypes;
    }

    public int getSize() {
        return size;
    }

    public Partition getPartition() {
        return partition;
    }

    // METHODS

    public void setPartition(Partition partition) {
        this.partition = partition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Composition => { ");
        sb.append("componentTypes = [");
        for (int i = 0; i < componentTypes.length; i++) {
            sb.append(componentTypes[i].getSimpleName());
            if (i + 1 < componentTypes.length) sb.append(", ");
        }
        sb.append("] }");
        return sb.toString();
    }
}
