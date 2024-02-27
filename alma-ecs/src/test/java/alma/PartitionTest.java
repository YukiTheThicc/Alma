package alma;

import alma.api.AlmaComponent;
import alma.utils.AlmaException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestComponent;
import utils.TestUtils;

import static org.junit.jupiter.api.Assertions.*;

class PartitionTest {

    private Partition sut1;
    private Partition sut2;
    private Partition sut3;
    private final AlmaComponent[] c1 = new AlmaComponent[]{new C1()};
    private final AlmaComponent[] c1c2 = new AlmaComponent[]{new C1(), new C2()};

    static class C1 extends TestComponent {
        public C1(int value) {
            super(value);
        }

        public C1() {
            super();
        }
    }

    static class C2 extends TestComponent {
        public C2(int value) {
            super(value);
        }

        public C2() {
            super();
        }
    }

    static class C3 extends TestComponent {
        public C3(int value) {
            super(value);
        }

        public C3() {
            super();
        }
    }

    @BeforeEach
    void setUp() {
        sut1 = new Partition(1, new IdHandler());
        sut2 = new Partition(2, new IdHandler(), 2);
        sut3 = new Partition(3, new IdHandler(), 3);
    }

    @AfterEach
    void tearDown() {
        TestUtils.printTestEnd();
    }

    @Test
    void testAddEntity() {

        TestUtils.printTestHeader("testAddEntity");
        int expectedId1 = 1 << 19;
        int expectedId2 = 1 | 1 << 19;
        int expectedId3 = 2 | 1 << 19;
        int expectedId4 = 2 << 19;
        int expectedId5 = 1 | 2 << 19;
        int expectedId6 = 2 | 2 << 19;
        int actualId1 = sut1.addEntity(c1);
        int actualId2 = sut1.addEntity(c1);
        int actualId3 = sut1.addEntity(c1);
        int actualId4 = sut2.addEntity(c1c2);
        int actualId5 = sut2.addEntity(c1c2);
        int actualId6 = sut2.addEntity(c1c2);

        TestUtils.printTestIteration("P1 Added ID 1", TestUtils.intToBinaryString(expectedId1), TestUtils.intToBinaryString(actualId1));
        TestUtils.printTestIteration("P1 Added ID 2", TestUtils.intToBinaryString(expectedId2), TestUtils.intToBinaryString(actualId2));
        TestUtils.printTestIteration("P1 Added ID 3", TestUtils.intToBinaryString(expectedId3), TestUtils.intToBinaryString(actualId3));
        TestUtils.printTestIteration("P2 Added ID 4", TestUtils.intToBinaryString(expectedId4), TestUtils.intToBinaryString(actualId4));
        TestUtils.printTestIteration("P2 Added ID 5", TestUtils.intToBinaryString(expectedId5), TestUtils.intToBinaryString(actualId5));
        TestUtils.printTestIteration("P2 Added ID 6", TestUtils.intToBinaryString(expectedId6), TestUtils.intToBinaryString(actualId6));
        assertEquals(expectedId1, actualId1);
        assertEquals(expectedId2, actualId2);
        assertEquals(expectedId3, actualId3);
        assertEquals(expectedId4, actualId4);
        assertEquals(expectedId5, actualId5);
        assertEquals(expectedId6, actualId6);
    }

    @Test
    void testSize() {

        TestUtils.printTestHeader("testSize");
        sut1.addEntity(c1);
        sut1.addEntity(c1);
        int expected1 = 2;
        int actual1 = sut1.size();
        sut1.addEntity(c1);
        sut1.addEntity(c1);
        sut1.addEntity(c1);
        sut1.addEntity(c1);
        int expected2 = 6;
        int actual2 = sut1.size();
        TestUtils.printTestIteration("Add 2 size", expected1, actual1);
        TestUtils.printTestIteration("Add 6 size", expected2, actual2);
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    void testRemove() {

        TestUtils.printTestHeader("testRemove");
        AlmaComponent[] expectedC = new AlmaComponent[]{new C1(4), new C2(5), new C3(6)};
        sut3.addEntity(new AlmaComponent[]{new C1(1), new C2(2), new C3(3)});
        int removed = sut3.addEntity(expectedC);
        sut3.addEntity(new AlmaComponent[]{new C1(7), new C2(8), new C3(9)});
        sut3.removeEntity(removed);
        sut3.update();
        int pooled = sut3.addEntity(new AlmaComponent[]{new C1(10), new C2(11), new C3(12)});
        sut3.addEntity(new AlmaComponent[]{new C1(13), new C2(14), new C3(15)});
        int expectedSize = 4;
        int actualSize = sut3.size();
        AlmaComponent[] actualC = sut3.fetchEntityComponents(pooled);
        TestUtils.printTestIteration("Add 5 remove 1", expectedSize, actualSize);
        TestUtils.printTestIteration("Component list", expectedC, actualC);
        assertEquals(expectedSize, actualSize);
        assertArrayEquals(expectedC, actualC);
    }

    @Test
    void testFetchEntityComponents() {
        TestUtils.printTestHeader("testFetchEntityComponents");
        AlmaComponent[] expected = new AlmaComponent[]{new C1(), new C2(), new C3()};
        sut3.addEntity(new AlmaComponent[]{new C1(), new C2(), new C3()});
        int expectedId = sut3.addEntity(expected);
        sut3.addEntity(new AlmaComponent[]{new C1(), new C2(), new C3()});
        AlmaComponent[] actual = sut3.fetchEntityComponents(expectedId);
        TestUtils.printTestIteration("Fetched components", expected, actual);
        assertArrayEquals(expected, actual);
        assertThrows(AlmaException.class, () -> {
            sut2.fetchEntityComponents(expectedId);
        });
    }
}