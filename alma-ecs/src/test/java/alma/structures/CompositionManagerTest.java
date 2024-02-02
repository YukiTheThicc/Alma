package alma.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositionManagerTest {

    @Test
    void testAddComponentType() {

        CompositionManager cm = new CompositionManager();
        System.out.println(cm.getCompositionIndex(new Class[]{C1.class}));
        System.out.println(cm.getCompositionIndex(new Class[]{C2.class}));
        System.out.println(cm.getCompositionIndex(new Class[]{C4.class}));
        System.out.println(cm.getCompositionIndex(new Class[]{C1.class, C2.class}));
        System.out.println(cm.getCompositionIndex(new Class[]{C1.class, C2.class, C3.class}));

        assertThrows(IllegalArgumentException.class, () -> cm.getCompositionIndex(new Class[]{C1.class, C2.class, C2.class}));
    }

    @Test
    void testAddComponentTypes() {
    }

    private record C4() {
    }

    private record C1() {
    }

    private record C2() {
    }

    private record C3() {
    }
}