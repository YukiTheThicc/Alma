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
    // Composition tree
    private final Map<Integer, AlmaList<Composition>> cTree = new ConcurrentHashMap<>();

    // CONSTRUCTORS
    public CompositionManager() {

    }

    // GETTERS & SETTERS
    public int getIndex() {
        return index;
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
            c = new Composition(components);
            compositions.put(cHash, c);
            for (Class<?> type : components) {
                // Add composition to list of compositions that contain this class
                AlmaList<Composition> cList = cTree.get(classMap.get(type));
                if (cList == null) {
                    // Lazily create the list if did not exist previously
                    cList = new AlmaList<>();
                    cTree.put(classMap.get(type), cList);
                }
                cList.add(c);
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
     * Generates the optimized composition hash for the specified component composition. Uses component slots based on
     *
     * @param components List of component types to generate the IntHash
     * @return IntHash that identifies the composition matching the component list
     */
    public CompositionHash getCompositionHash(Class<?>[] components) {
        int length = components.length;
        boolean[] classSlots = new boolean[index + length + 1];
        int begin = Integer.MAX_VALUE;
        int end = 0;
        for (Class<?> component : components) {
            int value = classMap.get(component);
            if (classSlots[value]) {
                throw new IllegalArgumentException("Component types can't repeat within one composition is not allowed");
            } else {
                classSlots[value] = true;
            }
            begin = Math.min(value, begin);
            end = Math.max(value, end);
        }
        return new CompositionHash(classSlots, begin, end, length);
    }

    /**
     * Generates the optimized composition hash for the specified component composition
     *
     * @param components List of components to generate the IntHash
     * @return IntHash that identifies the composition matching the component list
     */
    public CompositionHash getCompositionHash(AlmaComponent[] components) {
        return getCompositionHash(getComponentClasses(components));
    }

    /**
     * Commodity method to find compositions with instances of the passed component
     * @param components
     * @return
     */
    public AlmaList<Composition> getCompositionsWithTypes(AlmaComponent[] components) {

    }

    /**
     * Gets the compositions that include at least one of the types of components
     * @param components
     * @return
     */
    public AlmaList<Composition> getCompositionsWithTypes(Class<?>[] components) {

    }

    public AlmaList<Composition> getCompositionsWithTypesExclusive(AlmaComponent[] components) {
        return null;
    }
}
