package alma.archetypes;

import alma.api.IComponent;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Maps the archetypes of an instance of Alma. It does not manage the data stored for each archetype, instead is used
 * for mapping each archetype to the classes it is associated with.
 *
 * @author Santiago Barreiro
 */
public final class ArchetypeMap {

    // ATTRIBUTES
    // Used to map each class to an Integer value
    private final ClassIndex classIndex;
    // Used to map each archetype hash to its archetype
    private final Map<ArchetypeHash, Archetype> archetypes = new ConcurrentHashMap<>();
    // Composition tree
    private final Map<Integer, Map<ArchetypeHash, Archetype>> archetypeTree = new ConcurrentHashMap<>();

    // CONSTRUCTORS
    public ArchetypeMap() {
        this(new ClassIndex());
    }

    public ArchetypeMap(ClassIndex index) {
        this.classIndex = index;
    }

    // GETTERS
    public ClassIndex getClassIndex() {
        return classIndex;
    }

    // METHODS

    /**
     * Gets the archetype that matches the component types. Lazily creates a new archetype if it doesn't exist. If
     * the archetype didn't exist, then the classCompositions map will be updated and the new archetype will be
     * added to the list of archetypes with that class.
     *
     * @param components List of component classes to match the archetype
     * @return The matched archetype
     */
    public Archetype getArchetype(Class<?>[] components) {
        ArchetypeHash cHash = classIndex.getCompositionHash(components);
        Archetype c = archetypes.get(cHash);
        if (c == null) {
            // Lazily create the archetype corresponding to the components
            c = new Archetype(components);
            archetypes.put(cHash, c);
            for (Class<?> type : components) {
                // Add archetype to list of archetypes that contain this class
                Map<ArchetypeHash, Archetype> cList = archetypeTree.get(classIndex.get(type));
                if (cList == null) {
                    // Lazily create the list if did not exist previously
                    cList = new ConcurrentHashMap<>();
                    archetypeTree.put(classIndex.get(type), cList);
                }
                cList.put(cHash, c);
            }
        }
        return c;
    }

    /**
     * Gets the archetype that matches the component instances. Lazily creates a new archetype if it doesn't exist. If
     * the archetype didn't exist, then the classCompositions map will be updated and the new archetype will be
     * added to the list of archetypes with that class.
     * <p>
     * Components array is ordered before creating the Composition. Compositions should not be created randomly at runtime
     * so the overall performance impact of the Bubble sort should not be relevant.
     *
     * @param components List of component classes to match the archetype
     * @return The matched archetype
     */
    public Archetype getArchetype(IComponent[] components) {
        return getArchetype(classIndex.getComponentClasses(components));
    }

    /*
     * Composition query methods. At this moment, no cache is used. A cache could be used as it is common that systems
     * may use more complex queries which results, if not cached, will have to be recalculated every time. This could
     * potentially add unnecessary overhead, or it may be the case that the cache is either overkill, not improve
     * or even worsen performance. These methods should be thoroughly benchmarked with and without a results cache as to
     * understand if it is needed or not
     */

    /**
     * Commodity method to find archetypes that gets the archetypes that include all the received component types.
     *
     * @param components Array of components that want to be found
     * @return Map of archetypes that match the query
     */
    public Map<ArchetypeHash, Archetype> queryCompositionsWith(IComponent[] components) {
        return queryCompositionsWith(classIndex.getComponentClasses(components));
    }

    /**
     * Gets the archetypes that include the passed archetype. In other words, gets the archetypes that have all
     * the received component types.
     *
     * @param components Array of component types
     * @return Map of archetypes that match the query
     */
    public Map<ArchetypeHash, Archetype> queryCompositionsWith(Class<?>[] components) {
        Map<ArchetypeHash, Archetype> archetypes = null;
        for (Class<?> type : components) {
            Map<ArchetypeHash, Archetype> typeCompositions = archetypeTree.get(classIndex.get(type));
            if (typeCompositions == null) return null;
            if (archetypes == null) {
                archetypes = new ConcurrentHashMap<>(typeCompositions);
            } else {
                Set<ArchetypeHash> hashes = archetypes.keySet();
                hashes.removeIf(archetypeHash -> !typeCompositions.containsKey(archetypeHash));
            }
        }
        return archetypes;
    }
}
