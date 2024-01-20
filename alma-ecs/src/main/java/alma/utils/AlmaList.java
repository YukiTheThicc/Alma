package alma.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AlmaList<T> implements Iterable<T> {

    // ATTRIBUTES
    T[] data;                   // Actual array of data
    protected int size = 0;     // Current amount of elements stored in the list
    private Iterator<T> i;      // Iterator of the pool

    // CONSTRUCTORS
    public AlmaList() {
        this(64);
    }

    @SuppressWarnings("unchecked")
    public AlmaList(int size) {
        data = (T[]) Array.newInstance(Object.class, size);
    }

    public AlmaList(Class<T> type) {
        this(type, 64);
    }

    @SuppressWarnings("unchecked")
    public AlmaList(Class<T> type, int size) {
        data = (T[]) Array.newInstance(type, size);
    }

    // GETTERS & SETTERS
    /**
     * Gets the current size of the list
     *
     * @return Size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Gets the maximum size of the list before needing to grow.
     *
     * @return Max size of the list
     */
    public int maxSize() {
        return data.length;
    }

    /**
     * Checks if the list is empty
     *
     * @return Returns true if the list is empty
     */
    public boolean isEmpty() {
        return !(size > 0);
    }

    // METHODS

    /**
     * Adds an element to the list. Grows if the list is full
     *
     * @param e Element to add
     */
    public void add(T e) {
        if (size == data.length) {
            data = Arrays.copyOf(data, (int) (size * 1.5));
        }
        data[size++] = e;
    }

    /**
     * Add all elements from a list into this one
     *
     * @param toAdd List of elements to add
     */
    public void addList(AlmaList<T> toAdd) {
        for (int i = 0; i < toAdd.size; i++) {
            add(toAdd.get(i));
        }
    }

    /**
     * Removes an element on the specified index.
     *
     * @param i Index to remove the element from
     */
    public T remove(int i) {
        if (i < data.length) {
            T aux = data[i];
            data[i] = data[--size];
            data[size] = null;
            return aux;
        }
        return null;
    }

    /**
     * Removes the given entity from the list.
     *
     * @param e Element to remove from the list
     * @return True if the element has been found and removed. False otherwise
     */
    public boolean remove(T e) {
        for (int i = 0; i < size; i++) {
            T aux = data[i];
            if (aux.equals(e)) {
                data[i] = data[--size];
                data[size] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the element in the specified index. If the index is out of bounds the pool grows.
     *
     * @param i Index of the element
     * @return Element within the specified index. Null if there is no element in that index
     */
    public T get(int i) {
        if (i <= data.length) {
            changeSize((int) Math.max(size * 1.5, i * 1.5));
        }
        return data[i];
    }

    /**
     * Sets the specified index to the passed element. Grows if the index is out of bounds.
     *
     * @param i Index of the element
     * @param e Element to set in the specified index
     */
    public void set(int i, T e) {
        if (size == data.length) {
            changeSize((int) Math.max(size * 1.5, i * 1.5));
        }
        data[i] = e;
    }

    /**
     * Tests if element is within the list
     *
     * @param e Element to test
     * @return Ture if element is inside the list, false otherwise
     */
    public boolean contains(T e) {
        for (T d : data) {
            if (d.equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Changes the size of the list. Usually to increase it.
     *
     * @param newSize New size of the list.
     */
    public void changeSize(int newSize) {
        data = Arrays.copyOf(data, newSize);
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder m = new StringBuilder("AlmaList(" + data.length + ") : [");
        for (int i = 0; i < size; i++) {
            m.append(data[i]);
            if (i + 1 < size) {
                m.append(", ");
            }
        }
        m.append("]");
        return m.toString();
    }

    private final class ListIterator implements Iterator<T> {

        private int cursor;         // Index of the cursor
        private boolean inBound;    // Cursor is within array bounds

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() throws NoSuchElementException {
            if (cursor == size) {
                throw new NoSuchElementException("Iterated past the last element");
            }
            T e = data[cursor++];
            inBound = true;
            return e;
        }

        @Override
        public void remove() {
            if (!inBound) {
                throw new IllegalStateException();
            }
            inBound = false;
            AlmaList.this.remove(--cursor);
        }
    }
}


