package franxx.code.thread;

import org.junit.jupiter.api.Test;

public class ThreadCommunicationTest {
    private String message = null;

    @Test
    void manual() throws InterruptedException {
        var t1 = new Thread(() -> {
            while (message == null) {
                // wait
            }

            System.out.println(message);
        });

        var t2 = new Thread(() -> {
            message = "Hilmi AM";
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    @Test
    void waitNotify() throws InterruptedException {
        final var lock = new Object();

        var t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        var t2 = new Thread(() -> {
            synchronized (lock) {
                message = "Hilmi AM";
                lock.notify();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    @Test
    void waitNotifyAll() throws InterruptedException {
        final var lock = new Object();

        var t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        var t3 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        var t2 = new Thread(() -> {
            synchronized (lock) {
                message = "Hilmi AM";
                lock.notifyAll();
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
