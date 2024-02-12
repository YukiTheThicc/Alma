package alma.structures;

import alma.api.AlmaComponent;
import alma.utils.AlmaList;

import java.util.Arrays;
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

    /**
     * Internal function to get the classes from a list of component instances.
     *
     * @param components Array of components instances
     * @return Array of component types
     */
    private Class<?>[] getComponentClasses(AlmaComponent[] components) {
        Class<?>[] componentTypes = new Class<?>[components.length];
        for (int i = 0; i < components.length; i++) componentTypes[i] = components[i].getClass();
        return componentTypes;
    }

    /**
     * Use Bubble sort to order by index the array of classes.
     *
     * @param types Array of component classes
     * @return A sorted copy of the array
     */
    private Class<?>[] sortCompositionClasses(Class<?>[] types) {
        int length = types.length;
        Class<?>[] sorted = Arrays.copyOf(types, length);
        Class<?> aux;
        for (int i = 1; i < length - 1; i++) {
            for (int j = 0; j < length - i; j++) {
                if (classMap.get(sorted[j]) > classMap.get(sorted[j + 1])) {
                    aux = sorted[j];
                    sorted[j] = sorted[j + 1];
                    sorted[j + 1] = aux;
                }
            }
        }
        return sorted;
    }

    /**
     * Gets the composition that matches the component types. Lazily creates a new composition if it doesn't exist. If
     * the composition didn't exist, then the classCompositions map will be updated and the new composition will be
     * added to the list of compositions with that class.
     * <p>
     * Components array is ordered before creating the Composition. Compositions should not be created randomly at runtime
     * so the overall performance impact of the Bubble sort should not be relevant.
     *
     * @param components List of component classes to match the composition
     * @return The matched composition
     */
    public Composition getComposition(Class<?>[] components) {
        CompositionHash cHash = getCompositionHash(components);
        Composition c = compositions.get(cHash);
        if (c == null) {
            // Lazily create the composition corresponding to the components
            c = new Composition(sortCompositionClasses(components));
            compositions.put(cHash, c);
            for (Class<?> type : components) {
                // Lazily create the list of compositions that the type is in
                if (classCompositions.get(classMap.get(type)) == null) {
                    classCompositions.put(classMap.get(type), new AlmaList<>());
                }
                classCompositions.get(classMap.get(type)).add(c);
            }
        }
        return c;
    }

    /**
     * Gets the composition that matches the component instances. Lazily creates a new composition if it doesn't exist. If
     * the composition didn't exist, then the classCompositions map will be updated and the new composition will be
     * added to the list of compositions with that class.
     * <p>
     * Components array is ordered before creating the Composition. Compositions should not be created randomly at runtime
     * so the overall performance impact of the Bubble sort should not be relevant.
     *
     * @param components List of component classes to match the composition
     * @return The matched composition
     */
    public Composition getComposition(AlmaComponent[] components) {
        return getComposition(getComponentClasses(components));
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

    /**
     * Gets the list of compositions that contain the passed type.
     *
     * @param type Component type.
     * @return The not null list of compositions that contain the type
     */
    public AlmaList<Composition> getCompositionsWithClass(Class<?> type) {
        // Lazily create the list of compositions if it was not instanced
        int classValue = classMap.get(type);
        if (classCompositions.get(classValue) == null) classCompositions.put(classValue, new AlmaList<>());
        return classCompositions.get(classValue);
    }
}
