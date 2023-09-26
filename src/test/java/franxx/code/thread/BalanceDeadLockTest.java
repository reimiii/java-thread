package franxx.code.thread;

import org.junit.jupiter.api.Test;

public class BalanceDeadLockTest {
    @Test
    void transfer() throws InterruptedException {
        var a = new Balance(1_000_000L);
        var b = new Balance(1_000_000L);

        var t1 = new Thread(() -> {
            try {
                Balance.transfer(a, b, 500_000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        var t2 = new Thread(() -> {
            try {
                Balance.transfer(b, a, 500_000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Balance A : " + a.getValue());
        System.out.println("Balance B : " + b.getValue());
    }

    @Test
    void transferDeadLock() throws InterruptedException {
        var a = new Balance(1_000_000L);
        var b = new Balance(1_000_000L);

        var t1 = new Thread(() -> {
            try {
                Balance.transferDeadLock(a, b, 500_000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        var t2 = new Thread(() -> {
            try {
                Balance.transferDeadLock(b, a, 500_000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Balance A : " + a.getValue());
        System.out.println("Balance B : " + b.getValue());
    }
}
