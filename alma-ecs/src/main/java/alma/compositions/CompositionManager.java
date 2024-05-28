package alma.compositions;

import alma.api.AlmaComponent;
import alma.utils.CompositionHash;

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
    // Used to map each class to an Integer value
    private final ClassIndex classIndex;
    // Used to map each composition hash to its composition
    private final Map<CompositionHash, Composition> compositions = new ConcurrentHashMap<>();
    // Composition tree
    private final Map<Integer, Map<CompositionHash, Composition>> compositionTree = new ConcurrentHashMap<>();

    // CONSTRUCTORS
    public CompositionManager() {
        this(new ClassIndex());
    }

    public CompositionManager(ClassIndex index) {
        this.classIndex = index;
    }

    // GETTERS
    public ClassIndex getClassIndex() {
        return classIndex;
    }

    // METHODS

    /**
     * Gets the composition that matches the component types. Lazily creates a new composition if it doesn't exist. If
     * the composition didn't exist, then the classCompositions map will be updated and the new composition will be
     * added to the list of compositions with that class.
     *
     * @param components List of component classes to match the composition
     * @return The matched composition
     */
    public Composition getComposition(Class<?>[] components) {
        CompositionHash cHash = classIndex.getCompositionHash(components);
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
        return getComposition(classIndex.getComponentClasses(components));
    }

    /*
     * Composition query methods. At this moment, no cache is used. A cache could be used as it is common that systems
     * may use more complex queries which results, if not cached, will have to be recalculated every time. This could
     * potentially add unnecessary overhead, or it may be the case that the cache is either overkill, not improve
     * or even worsen performance. These methods should be thoroughly benchmarked with and without a results cache as to
     * understand if it is needed or not
     */

    /**
     * Commodity method to find compositions that gets the compositions that include all the received component types.
     *
     * @param components Array of components that want to be found
     * @return Map of compositions that match the query
     */
    public Map<CompositionHash, Composition> queryCompositionsWith(AlmaComponent[] components) {
        return queryCompositionsWith(classIndex.getComponentClasses(components));
    }

    /**
     * Gets the compositions that include the passed composition. In other words, gets the compositions that have all
     * the received component types.
     *
     * @param components Array of component types
     * @return Map of compositions that match the query
     */
    public Map<CompositionHash, Composition> queryCompositionsWith(Class<?>[] components) {
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
}
