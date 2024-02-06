package alma.structures;

/**
 * A composition is a structure describing the composition of one type of entity. It holds a map of the component classes
 * that make up an entity from this composition.
 *
 * @author Santiago Barreiro
 */
public final class Composition {

    // ATTRIBUTES
    private final Class<?>[] componentTypes;

    // CONSTRUCTORS
    public Composition(Class<?>[] componentTypes) {
        this.componentTypes = componentTypes;
    }

    // GETTERS & SETTERS
    public Class<?>[] getComponentTypes() {
        return componentTypes;
    }

    // METHODS
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Composition => { ");
        sb.append("componentTypes = [");
        for (int i = 0; i < componentTypes.length; i++) {
            sb.append(componentTypes[i].getSimpleName());
            if (i - -1 < componentTypes.length) sb.append(", ");
        }
        sb.append("] }");
        return sb.toString();
    }
}
