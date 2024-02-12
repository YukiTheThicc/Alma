package alma.structures;

import java.util.Arrays;

/**
 * Optimized hash class for compositions. Inspired on Enrico Stara Dominion implementation.
 *
 * @author Santiago Barreiro
 */
public class CompositionHash {

    // CONSTANTS
    public static final int HASH_FACTOR = 31;       // Hash factor to obtain the hash from

    // ATTRIBUTES
    private final int hashCode;                     // Hash code obtained for the composition
    private final byte[] compTypes;                 // Array of byte values for the component types of this composition

    // CONSTRUCTORS
    /**
     * Calculates a hash for a composition
     * @param cTypes Array of class int values to calculate the hash for the composition
     */
    public CompositionHash(int[] cTypes) {
        long result = 1;
        int length = cTypes.length;
        compTypes = new byte[length];
        for (int i = 0; i < length; i++) {
            result = result * HASH_FACTOR + cTypes[i];
            compTypes[i] = (byte) cTypes[i];
        }
        hashCode = (int) (result ^ (result >>> 32));
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