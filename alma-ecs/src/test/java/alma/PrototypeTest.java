package alma;

import alma.api.IClassIndex;
import alma.api.IComponent;
import alma.architecture.Partition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestComponent;
import utils.TestUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * PrototypeTest
 *
 * @author Santiago Barreiro
 */
public class PrototypeTest {

        private Partition sut;
        private final IComponent[] c1 = new IComponent[]{new alma.PartitionTest.C1()};
        private final IComponent[] c1c2 = new IComponent[]{new alma.PartitionTest.C1(), new alma.PartitionTest.C2()};
        private final IClassIndex index = new IClassIndex() {
            private int index = 1;
            // Used to map each class to an Integer value
            private final ClassValue<Integer> classIndex = new ClassValue<>() {
                @Override
                protected Integer computeValue(Class<?> type) {
                    return index++;
                }
            };

            @Override
            public int get(Class<?> type) {
                return classIndex.get(type);
            }

            @Override
            public int[] getIndexArray(Class<?>[] types) {
                return new int[0];
            }

            @Override
            public Class<?>[] getComponentClasses(Object[] components) {
                return new Class[0];
            }

            @Override
            public IntKey getCompositionHash(Class<?>[] components) {
                return null;
            }

            @Override
            public IntKey getCompositionHash(Object[] components) {
                return null;
            }
        };

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
            sut = new Partition(1, new IdHandler(), index, 1, new int[]{-1, 0});
        }

        @AfterEach
        void tearDown() {
            TestUtils.printTestEnd();
        }

        @Test
        void testProto() {

        }
}
