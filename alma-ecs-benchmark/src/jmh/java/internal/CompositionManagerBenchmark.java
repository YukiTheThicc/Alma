package internal;

import alma.compositions.CompositionManager;
import alma.api.IComponent;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import utils.TestComponent;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
//@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
@Fork(value = 1, warmups = 1)
@State(Scope.Thread)
public class CompositionManagerBenchmark {

    CompositionManager cm;
    IComponent[] components1 = new IComponent[]{new C1(1), new C2(2)};
    IComponent[] components2 = new IComponent[]{new C1(1), new C2(2), new C3(3), new C4(4)};
    IComponent[] components3 = new IComponent[]{new C1(1), new C2(2), new C3(3), new C4(4), new C5(5), new C6(6), new C7(7), new C8(8)};

    static class C1 extends TestComponent {
        public C1(int value) {
            super(value);
        }
    }

    static class C2 extends TestComponent {
        public C2(int value) {
            super(value);
        }
    }

    static class C3 extends TestComponent {
        public C3(int value) {
            super(value);
        }
    }

    static class C4 extends TestComponent {
        public C4(int value) {
            super(value);
        }
    }

    static class C5 extends TestComponent {
        public C5(int value) {
            super(value);
        }
    }

    static class C6 extends TestComponent {
        public C6(int value) {
            super(value);
        }
    }

    static class C7 extends TestComponent {
        public C7(int value) {
            super(value);
        }
    }

    static class C8 extends TestComponent {
        public C8(int value) {
            super(value);
        }
    }

    @Setup(Level.Iteration)
    public void setup() {
        cm = new CompositionManager();
    }

    @Benchmark
    public void queryComposition2ComponentsBenchmark(Blackhole bh) {
        CompositionManager cm = new CompositionManager();
        cm.getComposition(components1);
        bh.consume(cm.getComposition(components1));
    }

    @Benchmark
    public void queryComposition4ComponentsBenchmark(Blackhole bh) {
        CompositionManager cm = new CompositionManager();
        cm.getComposition(components2);
        bh.consume(cm.getComposition(components2));
    }

    @Benchmark
    public void queryComposition8ComponentsBenchmark(Blackhole bh) {
        CompositionManager cm = new CompositionManager();
        cm.getComposition(components3);
        bh.consume(cm.getComposition(components3));
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(
                new String[]{CompositionManagerBenchmark.class.getName().replace('$', '.')}
        );
    }
}