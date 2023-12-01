package franxx.code.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {
    @Test
    void stream() {
        Stream<Integer> stream = IntStream.range(0, 1000).boxed();
        stream.parallel()
                .forEach(integer -> {
                    System.out.println(Thread.currentThread().getName() + " : " + integer);
                });
    }

    @Test
    void streamPool() {
        ForkJoinPool pool = ForkJoinPool.commonPool();

        ForkJoinTask<?> submit = pool.submit(() -> {
            Stream<Integer> stream = IntStream.range(0, 1000).boxed();
            stream.parallel()
                    .forEach(integer -> {
                        System.out.println(Thread.currentThread().getName() + " : " + integer);
                    });
        });

        submit.join();
    }
}
