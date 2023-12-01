package franxx.code.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class ReactiveStreamTest {

    @Test
    void publish() throws InterruptedException {
        var publisher = new SubmissionPublisher<String>();
        var subscriber1 = new PrintSubscriber("A", 1000L);
        var subscriber2 = new PrintSubscriber("A", 500L);

        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);

        var service = Executors.newFixedThreadPool(10);
        service.execute(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(2000);
                    publisher.submit("Mee-" + i);
                    System.out.println(Thread.currentThread().getName() + ": Send Mee-" + i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        Thread.sleep(100 * 1000);
    }

    @Test
    void buffer() throws InterruptedException {
        var publisher = new SubmissionPublisher<String>(Executors.newFixedThreadPool(10), 50);
        var subscriber1 = new PrintSubscriber("A", 1000L);
        var subscriber2 = new PrintSubscriber("A", 500L);

        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);

        var service = Executors.newFixedThreadPool(10);
        service.execute(() -> {
            for (int i = 0; i < 100; i++) {
                publisher.submit("Mee-" + i);
                System.out.println(Thread.currentThread().getName() + ": Send Mee-" + i);
            }
        });

        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        Thread.sleep(100 * 1000);
    }

    @Test
    void processor() throws InterruptedException {
        var publisher = new SubmissionPublisher<String>();
        var processor = new HelloProcessor();

        publisher.subscribe(processor);

        PrintSubscriber subscriber = new PrintSubscriber("C", 1000L);

        processor.subscribe(subscriber);

        var service = Executors.newFixedThreadPool(10);
        service.execute(() -> {
            for (int i = 0; i < 100; i++) {
                publisher.submit("Mee-" + i);
                System.out.println(Thread.currentThread().getName() + ": Send Mee-" + i);
            }
        });

        Thread.sleep(100 * 1000);

    }

    public static class PrintSubscriber implements Flow.Subscriber<String> {
        private Flow.Subscription subscription;
        private String name;
        private Long sleep;

        public PrintSubscriber(String name, Long sleep) {
            this.name = name;
            this.sleep = sleep;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {

            this.subscription = subscription;
            this.subscription.request(1);

        }

        @Override
        public void onNext(String item) {

            try {
                Thread.sleep(sleep);
                System.out.println(Thread.currentThread().getName() + " : " + item);
                this.subscription.request(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void onError(Throwable throwable) {

            throwable.printStackTrace();

        }

        @Override
        public void onComplete() {

            System.out.println(Thread.currentThread().getName() + ": DONE");

        }
    }

    public static class HelloProcessor
            extends SubmissionPublisher<String>
            implements Flow.Processor<String, String> {
        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {

            this.subscription = subscription;
            this.subscription.request(1);

        }

        @Override
        public void onNext(String item) {

            submit("Hello" + item);
            this.subscription.request(1);

        }

        @Override
        public void onError(Throwable throwable) {

            throwable.printStackTrace();

        }

        @Override
        public void onComplete() {
            close();
        }
    }
}
