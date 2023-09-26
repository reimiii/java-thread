package franxx.code.thread;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutureTest {
    @Test
    void testFuture() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            Thread.sleep(5000);
            return "Hiii :3";
        };

        Future<String> submit = executor.submit(callable);
        System.out.println("Done Future");

        while (!submit.isDone()) {
            System.out.println("Waiting...");
            Thread.sleep(2000);
        }

        String s = submit.get();
        System.out.println(s);
    }


    @Test
    void testFutureCancel() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            Thread.sleep(5000);
            return "Hiii :3";
        };

        Future<String> submit = executor.submit(callable);
        System.out.println("Done Future");

        Thread.sleep(2000);
        submit.cancel(true);

        System.out.println(submit.isCancelled());
        String s = submit.get();
        System.out.println(s);
    }

    @Test
    void invokeAll() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(10);

        List<Callable<String>> callables = IntStream.range(1, 11)
                .mapToObj(value -> (Callable<String>) () -> {
            Thread.sleep(value * 500L);
            return String.valueOf(value);
        }).collect(Collectors.toList());

        List<Future<String>> futures = service.invokeAll(callables);

        for (Future<String> stringFuture : futures) {
            System.out.println(stringFuture.get());
        }
    }

    @Test
    void invokeAny() throws InterruptedException, ExecutionException {
        var executor = Executors.newFixedThreadPool(10);

        List<Callable<String>> callables = IntStream.range(1, 11).mapToObj(value -> (Callable<String>) () -> {
            Thread.sleep(value * 500L);
            return String.valueOf(value);
        }).collect(Collectors.toList());

        var value = executor.invokeAny(callables);
        System.out.println(value);
    }
    // 2:40:53 fast 2.36
}
