package alma.structures;

import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

/**
 * Auxiliary class to store on an off-heap memory structure a set of IDs
 */
public class IdStack implements AutoCloseable{

    // ATTRIBUTES
    private MemorySegment stack;
    private ResourceScope scope;
    private MemoryAddress address;
    private long size;
    private int index;

    // CONSTRUCTORS
    public IdStack(long size) {
        this.scope = ResourceScope.newConfinedScope();
        this.stack = MemorySegment.allocateNative(size, scope);
        this.address = this.stack.address();
        this.size = size;
        this.index = 0;
    }

    // METHODS
    public int pop() {

        return 1;
    }

    private void grow() {
        stack = stack.asSlice(0L, size << 3);
    }

    @Override
    public void close() throws Exception {

    }
}
