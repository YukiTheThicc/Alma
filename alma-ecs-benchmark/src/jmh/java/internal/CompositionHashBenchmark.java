package internal;

import alma.utils.CompositionHash;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * CompositionHashBenchmark
 *
 * @author Santiago Barreiro
 */

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
//@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
@Fork(value = 1, warmups = 1)
@State(Scope.Thread)
public class CompositionHashBenchmark {

    CompositionHash ch;
    boolean[] classSlots = new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};

    @Setup(Level.Iteration)
    public void setup() {
        ch = new CompositionHash(classSlots, 0, 15, 16);
    }

    @Benchmark
    public CompositionHash queryComposition2ComponentsBenchmark(Blackhole bh) {
        return new CompositionHash(classSlots, 0, 15, 16);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(
                new String[]{CompositionHashBenchmark.class.getName().replace('$', '.')}
        );
    }
}
