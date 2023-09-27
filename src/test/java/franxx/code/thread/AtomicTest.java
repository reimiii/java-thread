package franxx.code.thread;

import org.junit.jupiter.api.Test;

public class AtomicTest {
    @Test
    void counter() throws InterruptedException {
        var counter = new CounterAtomic();
        Runnable runnable = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                counter.increment();
            }
        };

        var t1 = new Thread(runnable);
        var t2 = new Thread(runnable);
        var t3 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(counter.getValue());
    }
}
