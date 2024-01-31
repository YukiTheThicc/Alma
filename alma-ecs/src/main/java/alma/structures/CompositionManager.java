package alma.structures;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the compositions of an instance of Alma.
 */
public final class CompositionManager {

    // ATTRIBUTES
    private int index = 1;
    private final Map<IntHash, Composition> compositions = new ConcurrentHashMap<>();
    private final ClassValue<Integer> classMap = new ClassValue<>() {
        @Override
        protected Integer computeValue(Class<?> type) {
            return index++;
        }
    };

    // CONSTRUCTORS
    public CompositionManager() {

    }

    // GETTERS & SETTERS

    // METHODS

    /**
     * Maps a component class to an int
     * @param type New component class to add
     * @return Value of the new component type
     */
    private int registerComponentType(Class<?> type) {
        return classMap.get(type);
    }

    private int[] registerComponentTypes(Class<?>[] types) {
        int[] indexes = new int[types.length];
        for (int i = 0; i < types.length; i++) {
            indexes[i] = registerComponentType(types[i]);
        }
        return indexes;
    }

    /**
     * Gets the composition that matches the component type. Lazily creates a new composition if it doesn't exist.
     * @param component Component type to match the composition
     * @return The matched composition
     */
    public Composition getComposition(Class<?> component) {
        if () {

        }
    }

    /**
     * Gets the composition that matches the component types. Lazily creates a new composition if it doesn't exist.
     * @param components List of component classes to match the composition
     * @return The matched composition
     */
    public Composition getComposition(Class<?>[] components) {

    }

    /**
     *
     */
    public class IntHash {

        // ATTRIBUTES
        private final int hashCode;
        private final byte[] data;

        // CONSTRUCTORS
        public IntHash(int hash) {
            this.hashCode = hash;
            this.data = null;
        }

        // METHODS

    }
}
