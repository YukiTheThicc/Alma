package alma;

import alma.api.AlmaComponent;
import alma.compositions.Composition;
import alma.utils.CompositionHash;
import alma.compositions.CompositionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestComponent;
import utils.TestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CompositionManagerTest {

    static class C1 extends TestComponent {
        public C1() {
            super();
        }
    }

    static class C2 extends TestComponent {
        public C2() {
            super();
        }
    }

    static class C3 extends TestComponent {
        public C3() {
            super();
        }
    }

    static class C4 extends TestComponent {
        public C4() {
            super();
        }
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

        cm.getComposition(new AlmaComponent[]{new C3()});
        cm.getComposition(new AlmaComponent[]{new C1()});
        cm.getComposition(new AlmaComponent[]{new C2()});
        int expectedC1 = 2;
        int expectedC2 = 3;
        int expectedC3 = 1;
        int actualC1 = cm.getClassIndex(C1.class);
        int actualC2 = cm.getClassIndex(C2.class);
        int actualC3 = cm.getClassIndex(C3.class);

        TestUtils.printTestIteration("C1", expectedC1, actualC1);
        TestUtils.printTestIteration("C1", expectedC2, actualC2);
        TestUtils.printTestIteration("C1", expectedC3, actualC3);
        assertAll(
                () -> assertEquals(expectedC1, actualC1),
                () -> assertEquals(expectedC2, actualC2),
                () -> assertEquals(expectedC3, actualC3)
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
        Composition compositionC3C2 = cm.getComposition(new AlmaComponent[] {new C2(), new C3()});
        Composition compositionC2C3 = cm.getComposition(new AlmaComponent[] {new C3(), new C2()});
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