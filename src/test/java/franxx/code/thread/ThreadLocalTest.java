package franxx.code.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {
    @Test
    void test() throws InterruptedException {
        final var random = new Random();
        final var user = new UserService();
        final var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 50; i++) {
            final var index = i;
            executor.execute(() -> {
                try {
                    user.setUser("User-" + index);
                    Thread.sleep(1000 + random.nextInt(2000));
                    user.doAction();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
