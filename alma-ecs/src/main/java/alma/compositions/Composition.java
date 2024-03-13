package alma.compositions;

import alma.Partition;
import alma.api.AlmaComponent;

/**
 * A composition is a structure describing the composition of one type of entity. It holds a map of the component classes
 * that make up an entity from this composition.
 *
 * @author Santiago Barreiro
 */
public final class Composition {

    // ATTRIBUTES
    private final Class<?>[] componentTypes;
    private final AlmaComponent[] template;
    private Partition partition;
    private final int size;

    // CONSTRUCTORS
    public Composition(Class<?>[] componentTypes) {
        this.componentTypes = componentTypes;
        this.template = new AlmaComponent[componentTypes.length];
        this.size = componentTypes.length;
    }

    // GETTERS & SETTERS
    public Class<?>[] getComponentTypes() {
        return componentTypes;
    }

    public int getSize() {
        return size;
    }

    // METHODS

    public void setPartition(Partition partition) {
        this.partition = partition;
    }

    public void setTemplate(AlmaComponent[] components) {
        for (AlmaComponent c : components) {
            for (int i = 0; i < componentTypes.length; i++) {
                if (c.getClass() == componentTypes[i]) template[i] = c;
            }
        }
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
