package alma.structures;

import alma.TestUtils;
import alma.api.AlmaComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CompositionManagerTest {

    private record C1() implements AlmaComponent {
    }

    private record C2() implements AlmaComponent {
    }

    private record C3() implements AlmaComponent {
    }

    private record C4() implements AlmaComponent {
    }

    private CompositionManager cm;

    @BeforeEach
    public void setup() {
        cm = new CompositionManager();
    }

    @AfterEach
    public void closeTest() {
        TestUtils.printTestEnd();
    }

    @Test
    public void getIndexTest() {

        TestUtils.printTestHeader("getIndexTest");
        int expectedEmpty = 1;          // Index starts at 1
        int expectedBasic = 4;          // Expected to add 3, so index should be 1 + 3

        int actualEmpty = cm.getIndex();
        cm.getComposition(new AlmaComponent[]{new C1()});
        cm.getComposition(new AlmaComponent[]{new C2()});
        cm.getComposition(new AlmaComponent[]{new C3()});
        int actualBasic = cm.getIndex();

        TestUtils.printTestIteration("Empty CM", expectedEmpty, actualEmpty);
        TestUtils.printTestIteration("Partially filled CM", expectedBasic, actualBasic);
        assertAll(
                () -> assertEquals(expectedEmpty, actualEmpty),
                () -> assertEquals(expectedBasic, actualBasic)
        );
    }

    @Test
    public void getCompositionHashTest() {

        TestUtils.printTestHeader("getCompHashTest");

        int actual1 = 1;
        int actual2 = 30817;
        int actual3 = 34;

        CompositionManager cm = new CompositionManager();
        CompositionHash composition1 = cm.getCompositionHash(new AlmaComponent[]{});
        CompositionHash compositionC1C2C3 = cm.getCompositionHash(new AlmaComponent[] {new C1(), new C2(), new C3()});
        CompositionHash compositionC3 = cm.getCompositionHash(new AlmaComponent[] {new C3()});
        TestUtils.printTestIteration("Empty", actual1, composition1);
        TestUtils.printTestIteration("C1, C2, C3", actual2, compositionC1C2C3);
        TestUtils.printTestIteration("C3", actual3, compositionC3);
        assertEquals(actual1, composition1.hashCode());
        assertEquals(actual2, compositionC1C2C3.hashCode());
        assertEquals(actual3, compositionC3.hashCode());
    }

    @Test
    public void getComponentHashTest() {

        TestUtils.printTestHeader("getCompositionClassTest");
        // Test if manually created composition has same components as composition handled by the manager

        Composition expectedC2C3 = new Composition(new Class[]{C2.class, C3.class});
        Composition expectedC3 = new Composition(new Class[]{C3.class});
        Composition expectedC1 = new Composition(new Class[]{C1.class});
        Composition compositionC2C3 = cm.getComposition(new AlmaComponent[] {new C2(), new C3()});
        Composition compositionC3C2 = cm.getComposition(new AlmaComponent[] {new C3(), new C2()});
        Composition compositionC3 = cm.getComposition(new AlmaComponent[] {new C3()});
        Composition compositionC1 = cm.getComposition(new AlmaComponent[] {new C1()});
        TestUtils.printTestIteration("C2 C3", expectedC2C3, compositionC2C3);
        TestUtils.printTestIteration("C2 C3 different order", expectedC2C3, compositionC3C2);
        TestUtils.printTestIteration("C3", expectedC3, compositionC3);
        TestUtils.printTestIteration("C1", expectedC1, compositionC1);
        assertArrayEquals(expectedC2C3.getComponentTypes(), compositionC2C3.getComponentTypes());
        assertArrayEquals(expectedC2C3.getComponentTypes(), compositionC3C2.getComponentTypes());
        assertArrayEquals(expectedC3.getComponentTypes(), compositionC3.getComponentTypes());
        assertArrayEquals(expectedC1.getComponentTypes(), compositionC1.getComponentTypes());
    }

    @Test
    public void getCompositionClassTest() {

        TestUtils.printTestHeader("getCompositionClassTest");
        // Test if manually created composition has same components as composition handled by the manager

        Composition expectedC2C3 = new Composition(new Class[]{C2.class, C3.class});
        Composition expectedC3 = new Composition(new Class[]{C3.class});
        Composition expectedC1 = new Composition(new Class[]{C1.class});
        Composition compositionC2C3 = cm.getComposition(new AlmaComponent[] {new C2(), new C3()});
        Composition compositionC3C2 = cm.getComposition(new AlmaComponent[] {new C3(), new C2()});
        Composition compositionC3 = cm.getComposition(new AlmaComponent[] {new C3()});
        Composition compositionC1 = cm.getComposition(new AlmaComponent[] {new C1()});
        TestUtils.printTestIteration("C2 C3", expectedC2C3, compositionC2C3);
        TestUtils.printTestIteration("C2 C3 different order", expectedC2C3, compositionC3C2);
        TestUtils.printTestIteration("C3", expectedC3, compositionC3);
        TestUtils.printTestIteration("C1", expectedC1, compositionC1);
        assertArrayEquals(expectedC2C3.getComponentTypes(), compositionC2C3.getComponentTypes());
        assertArrayEquals(expectedC2C3.getComponentTypes(), compositionC3C2.getComponentTypes());
        assertArrayEquals(expectedC3.getComponentTypes(), compositionC3.getComponentTypes());
        assertArrayEquals(expectedC1.getComponentTypes(), compositionC1.getComponentTypes());
    }

    @Test
    public void queryCompositionsFullJoinTest() {

        TestUtils.printTestHeader("queryCompositionsFullJoinTest");
        cm.getComposition(new AlmaComponent[]{new C1(), new C2()});
        cm.getComposition(new AlmaComponent[]{new C2(), new C3()});
        cm.getComposition(new AlmaComponent[]{new C2(), new C3(), new C4()});
        cm.getComposition(new AlmaComponent[]{new C3()});
        cm.getComposition(new AlmaComponent[]{new C1()});

        int expected1 = 2;
        int expected3 = 5;
        Map<CompositionHash, Composition> actual1 = cm.queryCompositionsFullJoin(new AlmaComponent[]{new C1()});
        Map<CompositionHash, Composition> actual3 = cm.queryCompositionsFullJoin(new AlmaComponent[]{new C1(), new C3()});
        TestUtils.printTestIteration("Compositions with C2", expected1, actual1.values());
        TestUtils.printTestIteration("Compositions with C3 or C1", expected3, actual3.values());

        assertEquals(expected1, actual1.size());
        assertEquals(expected3, actual3.size());
    }

    @Test
    public void queryCompositionsInnerJoinTest() {

        TestUtils.printTestHeader("queryCompositionsInnerJoinTest");
        cm.getComposition(new AlmaComponent[]{new C1(), new C2()});
        cm.getComposition(new AlmaComponent[]{new C2(), new C3()});
        cm.getComposition(new AlmaComponent[]{new C2(), new C3(), new C4()});
        cm.getComposition(new AlmaComponent[]{new C3()});
        cm.getComposition(new AlmaComponent[]{new C1()});

        int expected1 = 2;
        int expected3 = 2;
        Map<CompositionHash, Composition> actual1 = cm.queryCompositionsInnerJoin(new AlmaComponent[]{new C1()});
        Map<CompositionHash, Composition> actual3 = cm.queryCompositionsInnerJoin(new AlmaComponent[]{new C2(), new C3()});
        TestUtils.printTestIteration("Compositions with C1", expected1, actual1.values());
        TestUtils.printTestIteration("Compositions with C2 and C3", expected3, actual3.values());

        assertEquals(expected1, actual1.size());
        assertEquals(expected3, actual3.size());
    }
}