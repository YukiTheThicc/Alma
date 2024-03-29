package alma;

import alma.api.AlmaComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestComponent;

class AlmaPoolTest {

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

    AlmaPool sut;

    @BeforeEach
    void setUp() {
        sut = AlmaPool.Factory.create();
        AlmaComponent[] composition1 = new AlmaComponent[]{new C1(1)};
        AlmaComponent[] composition12 = new AlmaComponent[]{new C1(2), new C2(3)};
        AlmaComponent[] composition123 = new AlmaComponent[]{new C1(4), new C2(5), new C3(6)};
        sut.createEntity(composition1);
        sut.createEntity(composition12);
        sut.createEntity(composition123);
    }

    @Test
    void testCreateEntity() {

    }

    @Test
    void testQueryEntitiesFullJoin() {
        sut.queryEntitiesInnerJoin(new Class<?>[] {C1.class, C2.class}).forEach(result -> {
            System.out.println("" + result);
        });
    }
}