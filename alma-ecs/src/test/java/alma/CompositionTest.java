package alma;

import alma.compositions.Composition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import static org.junit.jupiter.api.Assertions.*;

class CompositionTest {

    static class C1 {
    }

    static class C2 {
    }

    static class C3 {
    }

    Composition sut;

    @BeforeEach
    void setUp() {
        sut = new Composition(new Class[]{C1.class, C2.class, C3.class});
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
        TestUtils.printTestIteration("Composition C1C2C3", expected, actual);
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