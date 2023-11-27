package franxx.code.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class PhaserTest {
    @Test
    void countDownLatchTest() throws InterruptedException {
        final var phaser = new Phaser();
        final var executor =  Executors.newFixedThreadPool(10);

        phaser.bulkRegister(5);

        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                try {
                    System.out.println("Start Task");
                    Thread.sleep(2000);
                    System.out.println("Task End");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    phaser.arrive();
                }
            });
        }

        executor.execute(() ->  {
            phaser.awaitAdvance(0);
            System.out.println("All Task Done");
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void cyclicBarrierTest() {
        final var phaser = new Phaser();
        final var executor =  Executors.newFixedThreadPool(10);

        phaser.bulkRegister(5);

        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                phaser.arriveAndAwaitAdvance();
                System.out.println("Done");
            });
        }
    }
}
