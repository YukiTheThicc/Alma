package alma.archetypes;

import alma.api.IClassIndex;

import java.util.Arrays;

/**
 * Optimized hash class for archetypes. Based on Enrico Stara Dominion implementation.
 *
 * @author Santiago Barreiro
 */
public final class ArchetypeHash implements IClassIndex.IntKey {

    // CONSTANTS
    public static final int HASH_FACTOR = 31;       // Hash factor to obtain the hash from

    // ATTRIBUTES
    private final int hashCode;                     // Hash code obtained for the archetype
    private final byte[] compTypes;                 // Array of byte values for the component types of this archetype

    // CONSTRUCTORS

    /**
     * Calculates the hash for an archetype based on the class slots that the archetype contains. This is done to
     * ensure that the order of components is not relevant when calculating the hash value for a component array.
     * components remain irrelevant when calculating an archetype hash.
     * @param classSlots Array of booleans representing the class slots used by this archetype
     * @param begin Minimum class index of the components for the archetype
     * @param end Maximum class index of the components for the archetype
     * @param size Amount of components for the archetype
     */
    public ArchetypeHash(boolean[] classSlots, int begin, int end, int size) {
        long result = 1;
        int index = 0;
        compTypes = new byte[size];
        for (int i = begin; i <= end; i++) {
            if (classSlots[i]) {
                result = result * HASH_FACTOR + i;
                compTypes[index++] = (byte) (i ^ (i >>> 8));
            }
        }
        hashCode = (int) (result ^ (result >>> 32));
    }

    // METHODS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Arrays.equals(((ArchetypeHash) o).compTypes, compTypes);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "CompositionHash => { " +
                "hashCode = " + hashCode + ", " +
                "data = " + Arrays.toString(compTypes) +
                " }";
    }
}