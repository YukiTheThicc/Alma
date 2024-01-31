package alma.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositionManagerTest {

    @Test
    void testAddComponentType() {

        CompositionManager cm = new CompositionManager();
        System.out.println(cm.getCompositionIntHash(new Class[]{C1.class}));
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