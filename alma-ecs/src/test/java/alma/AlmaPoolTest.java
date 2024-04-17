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
        AlmaComponent[] composition231 = new AlmaComponent[]{new C2(12), new C3(13), new C1(14)};
        AlmaComponent[] composition123 = new AlmaComponent[]{new C1(4), new C2(5), new C3(6)};
        AlmaComponent[] composition23 = new AlmaComponent[]{new C2(7), new C3(8)};
        AlmaComponent[] composition312 = new AlmaComponent[]{new C3(9), new C1(10), new C2(11)};
        int e1 = sut.createEntity(composition1);
        int e2 = sut.createEntity(composition12);
        int e3 = sut.createEntity(composition231);
        int e4 = sut.createEntity(composition123);
        int e5 = sut.createEntity(composition23);
        int e6 = sut.createEntity(composition312);
    }

    @Test
    void testCreateEntity() {

    }

    @Test
    void testQueryEntitiesFullJoin() {
        sut.queryEntitiesWith(new Class<?>[]{C1.class, C2.class}).forEachEntity(result -> {
            System.out.println(result.id());
            for (int i = 0; i < result.components().length; i++) {
                System.out.println(result.components()[i]);
            }
        });
    }
}