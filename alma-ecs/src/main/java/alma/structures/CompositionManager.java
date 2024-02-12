package alma.structures;

import alma.api.AlmaComponent;
import alma.utils.AlmaList;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the compositions of an instance of Alma. It does not manage the data stored for each composition, instead is
 * only used for management of component types and the registered compositions.
 *
 * @author Santiago Barreiro
 */
public final class CompositionManager {

    // ATTRIBUTES
    private int index = 1;
    // Used to map each class to an Integer value
    private final ClassValue<Integer> classMap = new ClassValue<>() {
        @Override
        protected Integer computeValue(Class<?> type) {
            return index++;
        }
    };
    // Used to map each composition hash to its composition
    private final Map<CompositionHash, Composition> compositions = new ConcurrentHashMap<>();
    // Cache for storing what compositions are contained within others
    private final Map<CompositionHash, AlmaList<Composition>> compositionCache = new ConcurrentHashMap<>();
    private final Map<Integer, AlmaList<Composition>> classCompositions = new ConcurrentHashMap<>();

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
    private Class<?>[] getComponentClasses(AlmaComponent[] components) {
        Class<?>[] componentTypes = new Class<?>[components.length];
        for(int i = 0; i < components.length; i++) componentTypes[i] = components[i].getClass();
        return componentTypes;
    }

    /**
     * Gets the composition that matches the component types. Lazily creates a new composition if it doesn't exist. If
     * the composition didn't exist, then the classCompositions map will be updated and the new composition will be
     * added to the list of compositions with that class.
     *
     * @param components List of component classes to match the composition
     * @return The matched composition
     */
    public Composition getComposition(Class<?>[] components) {
        CompositionHash cHash = getCompositionHash(components);
        Composition c = compositions.get(cHash);
        if (c == null) {
            // Lazily create the composition corresponding to the components
            c = new Composition(components);
            compositions.put(cHash, c);

        }
        return c;
    }

    /**
     * Gets the composition that matches the component instances.
     *
     * @param components List of component classes to match the composition
     * @return The matched composition
     */
    public Composition getComposition(AlmaComponent[] components) {
        return getComposition(getComponentClasses(components));
    }

    /**
     * Gets other compositions that contains the matched composition.
     * @param types
     * @return
     */
    public Composition[] getCompositionsWithComponents(Class<?>[] types) {
        CompositionHash hash = getCompositionHash(types);
        AlmaList<Composition> cachedCompositions = compositionCache.get(hash);
        // Lazily create the list of cached compositions for the matched composition
        if (cachedCompositions == null) {
            cachedCompositions = new AlmaList<>();
            compositionCache.put(hash, cachedCompositions);
        }
        return null;
    }

    public Composition[] getCompositionsWithComponents(AlmaComponent[] components) {
        return getCompositionsWithComponents(getComponentClasses(components));
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
                throw new IllegalArgumentException("Component types can't repeat within one composition is not allowed");
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
