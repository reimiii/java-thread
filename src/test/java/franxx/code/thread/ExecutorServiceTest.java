package franxx.code.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {
    @Test
    void testExecutorService() throws InterruptedException {
        var executor = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(1000L);
                    System.out.println("Runnable Thread: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(20, TimeUnit.SECONDS);
    }

    @Test
    void testExecutorServiceFix() throws InterruptedException {
        var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executor.execute(() -> {
                try {
                    Thread.sleep(1000L);
                    System.out.println("Runnable Thread: " + Thread.currentThread().getName() + " - Loop ke: " + finalI);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(20, TimeUnit.SECONDS);
    }

    @Test
    void testExecutorServiceCached() throws InterruptedException {
        var executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executor.execute(() -> {
                try {
                    Thread.sleep(1000L);
                    System.out.println("Runnable Thread: " + Thread.currentThread().getName() + " - Loop ke: " + finalI);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(20, TimeUnit.SECONDS);
    }
}
