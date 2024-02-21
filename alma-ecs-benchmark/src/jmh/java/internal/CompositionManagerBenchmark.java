package internal;

import alma.Composition;
import alma.CompositionManager;
import alma.api.AlmaComponent;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

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
    AlmaComponent[] components1 = new AlmaComponent[]{new C1(1), new C2(2)};
    AlmaComponent[] components2 = new AlmaComponent[]{new C1(1), new C2(2), new C3(3), new C4(4)};
    AlmaComponent[] components3 = new AlmaComponent[]{new C1(1), new C2(2), new C3(3), new C4(4), new C5(5), new C6(6), new C7(7), new C8(8)};
    Composition composition1;
    Composition composition2;
    Composition composition3;

    record C1(int id) implements AlmaComponent {
    }

    record C2(int id) implements AlmaComponent {
    }

    record C3(int id) implements AlmaComponent {
    }

    record C4(int id) implements AlmaComponent {
    }

    record C5(int id) implements AlmaComponent {
    }

    record C6(int id) implements AlmaComponent {
    }

    record C7(int id) implements AlmaComponent {
    }

    record C8(int id) implements AlmaComponent {
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