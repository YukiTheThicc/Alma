package alma.utils;

import alma.api.IClassIndex;

import java.util.Arrays;

/**
 * Optimized hash class for compositions. Based on Enrico Stara Dominion implementation.
 *
 * @author Santiago Barreiro
 */
public final class CompositionHash implements IClassIndex.IntKey {

    // CONSTANTS
    public static final int HASH_FACTOR = 31;       // Hash factor to obtain the hash from

    // ATTRIBUTES
    private final int hashCode;                     // Hash code obtained for the composition
    private final byte[] compTypes;                 // Array of byte values for the component types of this composition

    // CONSTRUCTORS

    /**
     * Calculates the hash for a composition based on the class slots that the composition contains. This is done to
     * ensure that the order of components is not relevant when calculating the hash value for a component array.
     * components remain irrelevant when calculating a composition hash.
     * @param classSlots Array of booleans representing the class slots used by this composition
     * @param begin Minimum class index of the components for the composition
     * @param end Maximum class index of the components for the composition
     * @param size Amount of components for the composition
     */
    public CompositionHash(boolean[] classSlots, int begin, int end, int size) {
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

    // GETTERS
    public byte[] getCompTypes() {
        return compTypes;
    }

    // METHODS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Arrays.equals(((CompositionHash) o).compTypes, compTypes);
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