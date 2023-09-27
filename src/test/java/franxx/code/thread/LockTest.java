package franxx.code.thread;

import org.junit.jupiter.api.Test;

public class LockTest {
    @Test
    void testLock() throws InterruptedException {
        var counter = new CounterLock();
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

    @Test
    void testReadWriteLock() throws InterruptedException {
        var counter = new CounterReadWriteLock();
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
