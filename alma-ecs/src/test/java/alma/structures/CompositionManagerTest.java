package alma.structures;

import alma.Alma;
import alma.TestUtils;
import alma.api.AlmaComponent;
import alma.utils.AlmaList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    private record C5() implements AlmaComponent {
    }

    private record C6() implements AlmaComponent {
    }

    private record C7() implements AlmaComponent {
    }

    private record C8() implements AlmaComponent {
    }

    @BeforeEach
    public void setup() {

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

        CompositionManager cm = new CompositionManager();
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

        CompositionHash actual1 = new CompositionHash(new boolean[] {false, false}, 1 ,1, 0);
        CompositionHash actual2 = new CompositionHash(new boolean[] {false, true, true, true}, 1 ,3, 3);
        CompositionHash actual3 = new CompositionHash(new boolean[] {false, false, false, true}, 3 ,3, 1);

        CompositionManager cm = new CompositionManager();
        CompositionHash composition1 = cm.getCompositionHash(new AlmaComponent[]{});
        CompositionHash compositionC1C2C3 = cm.getCompositionHash(new AlmaComponent[] {new C1(), new C2(), new C3()});
        CompositionHash compositionC3 = cm.getCompositionHash(new AlmaComponent[] {new C3()});
        TestUtils.printTestIteration("Empty", actual1, composition1);
        TestUtils.printTestIteration("C1, C2, C3", actual2, compositionC1C2C3);
        TestUtils.printTestIteration("C3", actual3, compositionC3);
    }

    @Test
    public void getComponentHashTest() {

        TestUtils.printTestHeader("getCompositionClassTest");
        // Test if manually created composition has same components as composition handled by the manager

        Composition expectedC2C3 = new Composition(new Class[]{C2.class, C3.class});
        Composition expectedC3 = new Composition(new Class[]{C3.class});
        Composition expectedC1 = new Composition(new Class[]{C1.class});

        CompositionManager cm = new CompositionManager();
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

        CompositionManager cm = new CompositionManager();
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


}