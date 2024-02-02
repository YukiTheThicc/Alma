package alma.utils;

import java.util.Arrays;

/**
 *
 */
public class IntHash {

    // ATTRIBUTES
    private final int hashCode;
    private final byte[] data;

    // CONSTRUCTORS
    public IntHash(int hash) {
        this.hashCode = hash;
        this.data = null;
    }

    // METHODS
    public IntHash(boolean[] slots, int min, int max, int length) {
        long result = 1;
        int idx = 0;
        int i = min;
        data = new byte[length];
        for (; i <= max; i++) {
            if (slots[i]) {
                result = result * 31 + i;
                data[idx++] = (byte) (i ^ (i >>> 8));
            }
        }
        hashCode = (int) (result ^ (result >>> 32));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Arrays.equals(((IntHash) o).data, data);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "IntHash{" +
                "hashCode=" + hashCode +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}