package alma.structures;

import alma.utils.IntHash;

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
     *
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
     * Gets the composition that matches the component types. Lazily creates a new composition if it doesn't exist.
     *
     * @param components List of component classes to match the composition
     * @return The matched composition
     */
    public Composition getComposition(Class<?>[] components) {
        return null;
    }

    /**
     * Generates the optimized IntHash for the component composition
     *
     * @param components List of components to generate the IntHash
     * @return IntHash that identifies the composition matching the component list
     */
    public IntHash getCompositionIndex(Class<?>[] components) {

        int length = components.length;
        int min = Integer.MAX_VALUE;
        int max = 0;
        boolean[] slots = new boolean[index + length + 1];              // Array of class slots

        for (int i = 0; i < length; i++) {
            int value = classMap.get(components[i]);
            if (slots[value]) {
                throw new IllegalArgumentException("Slot is already occupied");
            } else {
                slots[value] = true;
                registerComponentType(components[i]);
                min = Math.min(value, min);
                max = Math.max(value, max);
            }
        }
        return new IntHash(slots, min, max, length);
    }
}
