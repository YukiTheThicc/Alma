package alma;

import alma.archetypes.Archetype;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import static org.junit.jupiter.api.Assertions.*;

class ArchetypeTest {

    static class C1 {
    }

    static class C2 {
    }

    static class C3 {
    }

    Archetype sut;

    @BeforeEach
    void setUp() {
        sut = new Archetype(new Class[]{C1.class, C2.class, C3.class});
    }

    @AfterEach
    void tearDown() {
        TestUtils.printTestEnd();
    }

    @Test
    void testGetComponentTypes() {
        TestUtils.printTestHeader("testGetComponentTypes");
        Class<?>[] expected = new Class[]{C1.class, C2.class, C3.class};
        Class<?>[] actual = sut.getComponentTypes();
        StringBuilder expectedString = new StringBuilder("[");
        StringBuilder actualString = new StringBuilder("[");
        for (int i = 0; i < expected.length; i++) {
            expectedString.append(expected[i].getSimpleName());
            if (i + 1 < expected.length) expectedString.append(", ");
        }
        expectedString.append("]");
        for (int i = 0; i < actual.length; i++) {
            actualString.append(actual[i].getSimpleName());
            if (i + 1 < actual.length) actualString.append(", ");
        }
        actualString.append("]");
        TestUtils.printTestIteration("Composition C1C2C3", expectedString.toString(), actualString.toString());
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetSize() {
        TestUtils.printTestHeader("testGetSize");
        int expected = 3;
        int actual = sut.getSize();
        TestUtils.printTestIteration("Composition C1C2C3", expected, actual);
        assertEquals(expected, actual);
    }
}