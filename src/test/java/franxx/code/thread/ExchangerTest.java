package franxx.code.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class ExchangerTest {

    @Test
    void exampleTest() throws InterruptedException {
        final var excahanger = new Exchanger<String>();
        final var executor = Executors.newFixedThreadPool(10);

        executor.execute(() -> {
            try {
                System.out.println("Thread 1 : send = First");
                Thread.sleep(1000);
                var result = excahanger.exchange("First");
                System.out.println("Thread 1 : recive = " + result);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        executor.execute(() -> {

            try {
                System.out.println("Thread 2 : send = Second");
                Thread.sleep(1000);
                var result = excahanger.exchange("Second");
                System.out.println("Thread 2 : recive = " + result);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}
