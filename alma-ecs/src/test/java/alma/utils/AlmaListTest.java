package alma.utils;

import alma.Entity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class AlmaListTest {

    private class TestEntity extends Entity {
        TestEntity() {

        }
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetSize() {

        /*
         * Create a random number of entities, add them to the list and check if the size is equal to the random number
         */
        int expected = new Random().nextInt();
        int actual = 0;
        AlmaList<TestEntity> testList = new AlmaList<TestEntity>();
        for (int i = 0; i < expected; i++) {
            testList.add(new TestEntity());
        }
        actual = testList.getSize();

    }

    @Test
    void testGetMaxSize() {
    }

    @Test
    void testIsEmpty() {
    }

    @Test
    void testAdd() {
    }

    @Test
    void testAddList() {
    }

    @Test
    void testRemove() {
    }

    @Test
    void testTestRemove() {
    }

    @Test
    void testGet() {
    }

    @Test
    void testGetIndexOf() {
    }

    @Test
    void testSet() {
    }

    @Test
    void testContains() {
    }

    @Test
    void lifeCycleTest() {

    }
}