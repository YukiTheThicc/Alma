package alma.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositionManagerTest {

    @Test
    void testAddComponentType() {

        CompositionManager cm = new CompositionManager();
        cm.addComponentType(C1.class);
        cm.addComponentType(C2.class);
        cm.addComponentType(C3.class);
    }

    @Test
    void testAddComponentTypes() {
    }

    private record C1() {
    }

    private record C2() {
    }

    private record C3() {
    }
}