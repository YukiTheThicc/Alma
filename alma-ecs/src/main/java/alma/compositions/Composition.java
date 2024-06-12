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
    private AlmaComponent[] template = null;
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

    /**
     * Forms a new template for this composition based on the passed components. Throws an exception if any component type
     * for this composition is not included on the new template.
     * @param components List of components to form the new template
     */
    public void setTemplate(AlmaComponent[] components) {
        this.template = new AlmaComponent[componentTypes.length];
        int cont = 0;
        for (AlmaComponent c : components) {
            for (int i = 0; i < componentTypes.length; i++) {
                if (c.getClass() == componentTypes[i]) {
                    template[i] = c;
                    cont++;
                }
            }
        }
        if (cont != componentTypes.length) {
            throw new AlmaException(this.toString() + ": Tried to set an invalid template for the composition");
        }
    }

    public AlmaComponent[] createEntity() {
        if (template != null) {
            AlmaComponent[] newEntity = new AlmaComponent[template.length];

            return newEntity;
        } else {
            throw new AlmaException(this.toString() + ": Tried to create entity from uninitialized template");
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
