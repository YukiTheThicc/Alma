package alma;

import alma.api.IComponent;
import alma.archetypes.Archetype;
import alma.archetypes.ArchetypeHash;
import alma.archetypes.ArchetypeMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestComponent;
import utils.TestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ArchetypeMapTest {

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
    private ArchetypeMap cm;

    @BeforeEach
    public void setup() {
        cm = new ArchetypeMap();
    }

    @AfterEach
    public void closeTest() {
        TestUtils.printTestEnd();
    }

    @Test
    public void getIndexTest() {

        TestUtils.printTestHeader("getIndexTest");

        cm.getArchetype(new IComponent[]{new C3()});
        cm.getArchetype(new IComponent[]{new C1()});
        cm.getArchetype(new IComponent[]{new C2()});
        int expectedC1 = 2;
        int expectedC2 = 3;
        int expectedC3 = 1;
        int actualC1 = cm.getClassIndex().get(C1.class);
        int actualC2 = cm.getClassIndex().get(C2.class);
        int actualC3 = cm.getClassIndex().get(C3.class);

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

        ArchetypeMap cm = new ArchetypeMap();
        ArchetypeHash composition1 = cm.getClassIndex().getCompositionHash(new IComponent[]{});
        ArchetypeHash compositionC1C2C3 = cm.getClassIndex().getCompositionHash(new IComponent[] {new C1(), new C2(), new C3()});
        ArchetypeHash compositionC3 = cm.getClassIndex().getCompositionHash(new IComponent[] {new C3()});
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

        Archetype expectedC2C3 = new Archetype(new Class[]{C2.class, C3.class});
        Archetype expectedC3 = new Archetype(new Class[]{C3.class});
        Archetype expectedC1 = new Archetype(new Class[]{C1.class});
        Archetype compositionC3C2 = cm.getArchetype(new IComponent[] {new C2(), new C3()});
        Archetype compositionC2C3 = cm.getArchetype(new IComponent[] {new C3(), new C2()});
        Archetype compositionC3 = cm.getArchetype(new IComponent[] {new C3()});
        Archetype compositionC1 = cm.getArchetype(new IComponent[] {new C1()});
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

        Archetype expectedC2C3 = new Archetype(new Class[]{C2.class, C3.class});
        Archetype expectedC3 = new Archetype(new Class[]{C3.class});
        Archetype expectedC1 = new Archetype(new Class[]{C1.class});
        Archetype compositionC2C3 = cm.getArchetype(new IComponent[] {new C2(), new C3()});
        Archetype compositionC3C2 = cm.getArchetype(new IComponent[] {new C3(), new C2()});
        Archetype compositionC3 = cm.getArchetype(new IComponent[] {new C3()});
        Archetype compositionC1 = cm.getArchetype(new IComponent[] {new C1()});
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
        cm.getArchetype(new IComponent[]{new C1(), new C2()});
        cm.getArchetype(new IComponent[]{new C2(), new C3()});
        cm.getArchetype(new IComponent[]{new C2(), new C3(), new C4()});
        cm.getArchetype(new IComponent[]{new C3()});
        cm.getArchetype(new IComponent[]{new C1()});

        int expected1 = 2;
        int expected3 = 2;
        Map<ArchetypeHash, Archetype> actual1 = cm.queryCompositionsWith(new IComponent[]{new C1()});
        Map<ArchetypeHash, Archetype> actual3 = cm.queryCompositionsWith(new IComponent[]{new C2(), new C3()});
        TestUtils.printTestIteration("Compositions with C1", expected1, actual1.values());
        TestUtils.printTestIteration("Compositions with C2 and C3", expected3, actual3.values());

        assertEquals(expected1, actual1.size());
        assertEquals(expected3, actual3.size());
    }
}