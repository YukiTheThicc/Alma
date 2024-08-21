package alma.utils;

import java.util.Arrays;

/**
 * BitIndex
 *
 * @author Santiago Barreiro
 */
public final class BitFlag {

    // CONSTANTS
    private final static int INT_SIZE = 31;
    private final static int LEFT_MOST_BIT = 1 << 31;
    private final static int CHUNK_INDEX_SHIFT = 5;

    // ATTRIBUTES
    private int[] data;
    private int bitsSet;

    // CONSTRUCTORS
    public BitFlag(int[] indexes) {
        data = new int[1];
        for (int index : indexes) {
            setFlag(index, true);
        }
    }

    public BitFlag(int size) {
        data = new int[size];
    }

    // GETTER

    public int getBitsSet() {
        return bitsSet;
    }


    // METHODS

    private void checkSize(int pos) {
        if ((pos >> CHUNK_INDEX_SHIFT) >= data.length) data = Arrays.copyOf(data, (pos >> CHUNK_INDEX_SHIFT) + 1);
    }

    public int getSegmentCount() {
        return data.length;
    }

    public int getSegment(int i) {
        return data[i];
    }

    public void clearSegment(int segment) {
        data[segment] = 0;
    }

    public boolean getFlag(int pos) {
        checkSize(pos);
        return (data[pos >> CHUNK_INDEX_SHIFT] & (LEFT_MOST_BIT >>> (pos & (INT_SIZE)))) != 0;
    }

    public void setFlag(int pos, boolean value) {
        checkSize(pos);
        if (value) {
            data[pos >> CHUNK_INDEX_SHIFT] |= LEFT_MOST_BIT >>> (pos & (INT_SIZE));
            bitsSet++;
        } else{
            data[pos >> CHUNK_INDEX_SHIFT] &= ~LEFT_MOST_BIT >>> (pos & (INT_SIZE));
            bitsSet--;
        }
    }

    public void flipFlag(int pos) {
        checkSize(pos);
        int mask = LEFT_MOST_BIT >>> (pos & (INT_SIZE));
        if ((data[pos >> CHUNK_INDEX_SHIFT] & mask) != 0) bitsSet--; else bitsSet++;
        data[pos >> CHUNK_INDEX_SHIFT] ^= mask;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BitIndex: { ");
        sb.append("\n\tdata: [");
        for (int fragment : data) {
            sb.append("\n\t\t ").append(String.format("%32s", Integer.toBinaryString(fragment)).replace(" ", "0"));
        }
        sb.append("\n\t]");
        sb.append("\n}");
        return sb.toString();
    }
}
