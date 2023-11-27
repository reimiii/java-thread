package franxx.code.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantLock;

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

    String message;

    @Test
    void conditionAll() throws InterruptedException {
        var lock = new ReentrantLock();
        var condition = lock.newCondition();

        var t1 = new Thread(() -> {
            try {
                lock.lock();
                condition.await();
                System.out.println(message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        var t3 = new Thread(() -> {
            try {
                lock.lock();
                condition.await();
                System.out.println(message.toUpperCase());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        var t2 = new Thread(() -> {
            try {
                lock.lock();
                Thread.sleep(1000);
                message = "Hilmi AM";
                condition.signalAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        t1.start();
        t3.start();
        t2.start();

        t1.join();
        t3.join();
        t2.join();
    }
}
