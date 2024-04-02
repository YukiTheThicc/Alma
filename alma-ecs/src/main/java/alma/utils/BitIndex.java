package alma.utils;

import java.util.Arrays;

/**
 * BitIndex
 *
 * @author Santiago Barreiro
 */
public final class BitIndex {

    // CONSTANTS
    private final static int INT_SIZE = 32;
    private final static int HASH_FACTOR = 31;

    // ATTRIBUTES
    private final int[] data;
    private final int hash;

    // CONSTRUCTORS
    public BitIndex(int[] indexes, int maxIndex) {
        data = new int[(maxIndex >> 5) + 1];
        int fragment;
        for (int index : indexes) {
            fragment = index >> 5;
            data[fragment] |= 1 << (index - (INT_SIZE * fragment));
        }
        long result = 1;
        for (int dataFrag : data) {
            result = result * HASH_FACTOR + dataFrag;
        }
        hash = (int) (result ^ (result >>> INT_SIZE));
    }

    // METHODS

    public boolean get(int index) {
        return (data[index >> 5] & (1 << (index - (INT_SIZE * (index >> 5))))) != 0;
    }

    @Override
    public int hashCode() {
        return this.hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Arrays.equals(((BitIndex) o).data, data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BitIndex: { ");
        sb.append("\n\thash: ").append(hash).append(", ");
        sb.append("\n\tdata: [");
        for (int fragment : data) {
            sb.append("\n\t\t ").append(String.format("%32s", Integer.toBinaryString(fragment)).replace(" ", "0"));
        }
        sb.append("\n\t]");
        sb.append("\n}");
        return sb.toString();
    }
}
