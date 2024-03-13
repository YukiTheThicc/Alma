package alma.compositions;

import alma.api.AlmaComponent;

import java.util.Map;
import java.util.Set;
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
    private final ClassValue<Integer> classIndex = new ClassValue<>() {
        @Override
        protected Integer computeValue(Class<?> type) {
            return index++;
        }
    };
    // Used to map each composition hash to its composition
    private final Map<CompositionHash, Composition> compositions = new ConcurrentHashMap<>();
    // Composition tree
    private final Map<Integer, Map<CompositionHash, Composition>> compositionTree = new ConcurrentHashMap<>();

    // CONSTRUCTORS
    public CompositionManager() {

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
    //___ START of BASIC METHODS ___//

    /**
     * Retrieves the index of the type.
     * @param type Class to know the int value of
     * @return The int value of the class
     */
    public int getClassIndex(Class<?> type) {
        return classIndex.get(type);
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
            for (Class<?> type : components) {
                // Add composition to list of compositions that contain this class
                Map<CompositionHash, Composition> cList = compositionTree.get(classIndex.get(type));
                if (cList == null) {
                    // Lazily create the list if did not exist previously
                    cList = new ConcurrentHashMap<>();
                    compositionTree.put(classIndex.get(type), cList);
                }
                cList.put(cHash, c);
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
            int value = classIndex.get(component);
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

    //___ END of BASIC METHODS ___//

    //___ START of COMPOSITION QUERY METHODS ___//

    /*
     * Composition query methods. At this moment, no cache is used. A cache could be used as it is common that systems
     * may use more complex queries which results, if not cached, will have to be recalculated every time. This could
     * potentially add unnecessary overhead, or it may be the case that the cache is either overkill, not improve
     * or even worsen performance. These methods should be thoroughly benchmarked with and without a result cache as to
     * understand if it is needed or not
     */

    /**
     * Commodity method to find compositions that include at least one of the received components types
     * @param components Array of components that want to be found
     * @return Unordered List of compositions that match the query
     */
    public Map<CompositionHash, Composition> queryCompositionsFullJoin(AlmaComponent[] components) {
        return queryCompositionsFullJoin(getComponentClasses(components));
    }

    /**
     * Gets the compositions that include at least one of the received components types
     * @param components Array of component types
     * @return Unordered List of compositions that match the query
     */
    public Map<CompositionHash, Composition> queryCompositionsFullJoin(Class<?>[] components) {
        Map<CompositionHash, Composition> compositions = new ConcurrentHashMap<>();
        for (Class<?> type : components) {
            Map<CompositionHash, Composition> children = compositionTree.get(classIndex.get(type));
            compositions.putAll(children);
        }
        return compositions;
    }

    /**
     * Commodity method to find compositions that gets the compositions that include all the received component types.
     * @param components Array of components that want to be found
     * @return Map of compositions that match the query
     */
    public Map<CompositionHash, Composition> queryCompositionsInnerJoin(AlmaComponent[] components) {
        return queryCompositionsInnerJoin(getComponentClasses(components));
    }

    /**
     * Gets the compositions that include the passed composition. In other words, gets the compositions that have all
     * the received component types.
     * @param components Array of component types
     * @return Map of compositions that match the query
     */
    public Map<CompositionHash, Composition> queryCompositionsInnerJoin(Class<?>[] components) {
        Map<CompositionHash, Composition> compositions = null;
        for (Class<?> type : components) {
            Map<CompositionHash, Composition> typeCompositions = compositionTree.get(classIndex.get(type));
            if (typeCompositions == null) return null;
            if (compositions == null) {
                compositions = new ConcurrentHashMap<>(typeCompositions);
            } else {
                Set<CompositionHash> hashes = compositions.keySet();
                hashes.removeIf(compositionHash -> !typeCompositions.containsKey(compositionHash));
            }
        }
        return compositions;
    }
    //___ END of COMPOSITION QUERY METHODS ___//
}
