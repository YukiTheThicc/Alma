package alma;

import alma.api.AlmaComponent;
import alma.utils.AlmaException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestComponent;
import utils.TestUtils;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

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

    @Test
    void testAdding500000_2() {
        TestUtils.printTestHeader("testAdding500000_2 - STRESS TEST");

        final int ITERATIONS = 5;
        for (int i = 1; i <= ITERATIONS; i++) {

            sut2 = new Partition(2, new IdHandler(12, 12), 2);
            int partitionSize = (int) (Math.random() * 500000 + 2001);
            int removeInterval = (int) (Math.random() * 100 + 31);
            int expectedFinalSize = partitionSize - (partitionSize / removeInterval) - (partitionSize % removeInterval != 0 ? 1 : 0);
            int expectedChunksUsed = (int) Math.ceil((float) expectedFinalSize / 4096);

            int randomEntityComponentCheck = (int) (Math.random() * expectedFinalSize + 0);
            int randomComponentCheckEntity = -1;
            AlmaComponent[] randomExpectedComponents = null;
            boolean randomEliminated = false;

            int previousEntity;
            for (int j = 0; j < partitionSize; j++) {
                AlmaComponent[] components = new AlmaComponent[]{new C1((int) (Math.random() * Integer.MAX_VALUE + 1)), new C2((int) (Math.random() * Integer.MAX_VALUE + 1))};
                previousEntity = sut2.addEntity(components);
                if (j % removeInterval == 0) {
                    sut2.removeEntity(previousEntity);
                }
                if (j == randomEntityComponentCheck) {
                    if (j % removeInterval == 0) {
                        randomEliminated = true;
                    } else {
                        randomComponentCheckEntity = previousEntity;
                        randomExpectedComponents = Arrays.copyOf(components, 2);
                    }
                }
            }

            TestUtils.printTestIteration("Final size (ps:" + partitionSize + "|ri:" + removeInterval + ")", expectedFinalSize, sut2.size());
            TestUtils.printTestIteration("Chunks used (ps:" + partitionSize + "|ri:" + removeInterval + ")", expectedChunksUsed, sut2.usedChunks());
            assertEquals(expectedFinalSize, sut2.size());
            assertEquals(expectedChunksUsed, sut2.usedChunks());
            if (!randomEliminated) {
                TestUtils.printTestIteration("Random component check used (ps:" + partitionSize + "|ri:" + removeInterval + ")",
                        Arrays.toString(randomExpectedComponents), Arrays.toString(sut2.fetchEntityComponents(randomComponentCheckEntity)));
                assertArrayEquals(randomExpectedComponents, sut2.fetchEntityComponents(randomComponentCheckEntity));
            }
        }
    }

    @Test
    void testAdding500000_3() {
        TestUtils.printTestHeader("testAdding500000_3 - STRESS TEST");

        final int ITERATIONS = 5;
        for (int i = 1; i <= ITERATIONS; i++) {

            sut3 = new Partition(3, new IdHandler(12, 12), 3);
            int partitionSize = (int) (Math.random() * 500000 + 2001);
            int removeInterval = (int) (Math.random() * 100 + 31);
            int expectedFinalSize = partitionSize - (partitionSize / removeInterval) - (partitionSize % removeInterval != 0 ? 1 : 0);
            int expectedChunksUsed = (int) Math.ceil((float) expectedFinalSize / 4096);

            int randomEntityComponentCheck = (int) (Math.random() * expectedFinalSize + 0);
            int randomComponentCheckEntity = -1;
            AlmaComponent[] randomExpectedComponents = null;
            boolean randomEliminated = false;

            int previousEntity;
            for (int j = 0; j < partitionSize; j++) {
                AlmaComponent[] components = new AlmaComponent[]{
                        new C1((int) (Math.random() * Integer.MAX_VALUE + 1)),
                        new C2((int) (Math.random() * Integer.MAX_VALUE + 1)),
                        new C3((int) (Math.random() * Integer.MAX_VALUE + 1))
                };
                previousEntity = sut3.addEntity(components);
                if (j % removeInterval == 0) {
                    sut3.removeEntity(previousEntity);
                }
                if (j == randomEntityComponentCheck) {
                    if (j % removeInterval == 0) {
                        randomEliminated = true;
                    } else {
                        randomComponentCheckEntity = previousEntity;
                        randomExpectedComponents = Arrays.copyOf(components, 3);
                    }
                }
            }

            TestUtils.printTestIteration("Final size (ps:" + partitionSize + "|ri:" + removeInterval + ")", expectedFinalSize, sut3.size());
            TestUtils.printTestIteration("Chunks used (ps:" + partitionSize + "|ri:" + removeInterval + ")", expectedChunksUsed, sut3.usedChunks());
            assertEquals(expectedFinalSize, sut3.size());
            assertEquals(expectedChunksUsed, sut3.usedChunks());
            if (!randomEliminated) {
                TestUtils.printTestIteration("Random component check used (ps:" + partitionSize + "|ri:" + removeInterval + ")",
                        Arrays.toString(randomExpectedComponents), Arrays.toString(sut3.fetchEntityComponents(randomComponentCheckEntity)));
                assertArrayEquals(randomExpectedComponents, sut3.fetchEntityComponents(randomComponentCheckEntity));
            }
        }
    }

    @Test
    void testPartitionIterator() {
        sut2.addEntity(new AlmaComponent[]{new C1(1), new C2(2)});
        sut2.addEntity(new AlmaComponent[]{new C1(3), new C2(4)});
        sut2.addEntity(new AlmaComponent[]{new C1(5), new C2(6)});

        int toRemove = 0;
        for (int i = 0; i < 10000; i++) {
            if (i == 5000) toRemove = sut1.addEntity(new AlmaComponent[]{new C1(i)});
            else sut1.addEntity(new AlmaComponent[]{new C1(i)});
        }
        sut1.removeEntity(toRemove);

        Iterator<Entity> iTest1 = sut1.iterator();
        Iterator<Entity> iTest2 = sut2.iterator();
        Iterator<Entity> iFailure = sut3.iterator();

        while (iTest1.hasNext()) {
            Entity e = iTest1.next();
            System.out.println(e.components[0].toString());
        }

        while (iTest2.hasNext()) {
            iTest2.next();
        }

        assertThrows(AlmaException.class, iFailure::next);
    }
}