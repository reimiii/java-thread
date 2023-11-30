package franxx.code.thread;

import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class BlockingQueueTest {
    @Test
    void arrayBlockingQueueTest() throws InterruptedException {
        final var queue = new ArrayBlockingQueue<String>(5);
        final var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    queue.put("Data");
                    System.out.println("Finish put data");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    String take = queue.take();
                    System.out.println("Reciving data: " + take);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void linkedBlockingQueueTest() throws InterruptedException {
        final var queue = new LinkedBlockingQueue<String>();
        final var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    queue.put("Data");
                    System.out.println("Finish put data");
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    String take = queue.take();
                    System.out.println("Reciving data: " + take);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void priorityBlockingQueueTest() throws InterruptedException {
        final var queue = new PriorityBlockingQueue<Integer>(10, Comparator.reverseOrder());
        final var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.execute(() -> {
                queue.put(index);
                System.out.println("Finish put data");
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    Integer take = queue.take();
                    System.out.println("Reciving data: " + take);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void delayedQueueTest() throws InterruptedException {
        final var queue = new DelayQueue<ScheduledFuture<String>>();
        final var executor = Executors.newFixedThreadPool(10);
        final var executorSchedule = Executors.newScheduledThreadPool(10);

        for (int i = 0; i <= 10; i++) {
            final int index = i;
            queue.put(executorSchedule.schedule(() -> "Data: " + index, i, TimeUnit.SECONDS));
        }

        executor.execute(() -> {
            while (true) {
                try {
                    var take = queue.take();
                    System.out.println("Reciving data: " + take.get());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void synchronousQueueTest() throws InterruptedException {
        final var queue = new SynchronousQueue<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.execute(() -> {
                try {
                    queue.put("Data-" + index);
                    System.out.println("Finish put data" + index);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                    var take = queue.take();
                    System.out.println("Reciving data: " + take);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void blockingDequeTest() throws InterruptedException {
        final var queue = new LinkedBlockingDeque<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.execute(() -> {
                try {
                    queue.putLast("Data-" + index);
                    System.out.println("Finish put data" + index);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                    var take = queue.takeLast();
                    System.out.println("Reciving data: " + take);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void transferQueueTest() throws InterruptedException {
        final var queue = new LinkedTransferQueue<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.execute(() -> {
                try {
                    queue.transfer("Data-" + index);
                    System.out.println("Finish put data" + index);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                    var take = queue.take();
                    System.out.println("Reciving data: " + take);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}