package alma.structures;

import alma.api.AlmaComponent;
import alma.api.AlmaModifier;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the compositions of an instance of Alma.
 *
 * @author Santiago Barreiro
 */
public final class CompositionManager {

    // ATTRIBUTES
    private int index = 1;
    private final ClassValue<Integer> classMap = new ClassValue<>() {
        @Override
        protected Integer computeValue(Class<?> type) {
            return index++;
        }
    };
    private final Map<CompositionHash, Composition> compositions = new ConcurrentHashMap<>();
    private final Map<Class<?>, AlmaModifier> additionModifiers = new ConcurrentHashMap<>();
    private final Map<Class<?>, AlmaModifier> removalModifiers = new ConcurrentHashMap<>();

    // CONSTRUCTORS
    public CompositionManager() {

    }

    // GETTERS & SETTERS
    public int getIndex() {
        return index;
    }

    public Composition[] getCompositions() {
        return compositions.values().toArray(new Composition[0]);
    }

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

    /**
     * Gets the composition that matches the component types. Lazily creates a new composition if it doesn't exist.
     *
     * @param components List of component classes to match the composition
     * @return The matched composition
     */
    public Composition getComposition(Class<?>[] components) {
        CompositionHash cHash = getCompositionHash(components);
        Composition c = compositions.get(cHash);
        if (c == null) {
            c = new Composition(components);
            compositions.put(cHash, c);
        }
        return c;
    }

    /**
     * Gets the composition that matches the components. Lazily creates a new composition if it doesn't exist.
     *
     * @param components List of component classes to match the composition
     * @return The matched composition
     */
    public Composition getComposition(AlmaComponent[] components) {
        Class<?>[] componentTypes = new Class<?>[components.length];
        for(int i = 0; i < components.length; i++) componentTypes[i] = components[i].getClass();
        CompositionHash cHash = getCompositionHash(componentTypes);
        Composition c = compositions.get(cHash);
        if (c == null) {
            c = new Composition(componentTypes);
            compositions.put(cHash, c);
        }
        return c;
    }

    /**
     * Generates the optimized composition hash for the specified component composition
     *
     * @param components List of component types to generate the IntHash
     * @return IntHash that identifies the composition matching the component list
     */
    public CompositionHash getCompositionHash(Class<?>[] components) {
        int length = components.length;
        boolean[] repetitionCheck = new boolean[index + length + 1];
        int[] registeredComponents = new int[length];
        for (int i = 0; i < length; i++) {
            int value = classMap.get(components[i]);
            if (repetitionCheck[value]) {
                throw new IllegalArgumentException("Component repetition within one composition is not allowed");
            } else {
                registeredComponents[i] = value;
                repetitionCheck[value] = true;
            }
        }
        return new CompositionHash(registeredComponents);
    }

    /**
     * Generates the optimized composition hash for the specified component composition
     *
     * @param components List of components to generate the IntHash
     * @return IntHash that identifies the composition matching the component list
     */
    public CompositionHash getCompositionHash(AlmaComponent[] components) {
        int length = components.length;
        boolean[] repetitionCheck = new boolean[index + length + 1];
        int[] registeredComponents = new int[length];
        for (int i = 0; i < length; i++) {
            int value = classMap.get(components[i].getClass());
            if (repetitionCheck[value]) {
                throw new IllegalArgumentException("Component repetition within one composition is not allowed");
            } else {
                registeredComponents[i] = value;
                repetitionCheck[value] = true;
            }
        }
        return new CompositionHash(registeredComponents);
    }
}
