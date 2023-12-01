package franxx.code.thread;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ForkJoinTest {
    @Test
    void test() {
        var forkJoin1 = ForkJoinPool.commonPool();
        var forkJoin2 = new ForkJoinPool(1);

        var executor1 = Executors.newWorkStealingPool();
        var executor2 = Executors.newWorkStealingPool(1);
    }

    @Test
    void recursiveAction() throws InterruptedException {
        var pool = ForkJoinPool.commonPool();
        List<Integer> list = IntStream.range(0, 1000).boxed().toList();
        SimpleForkJoinTask task = new SimpleForkJoinTask(list);

        pool.execute(task);
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void recursiveTask() throws InterruptedException, ExecutionException {

        var pool = ForkJoinPool.commonPool();
        List<Integer> list = IntStream.range(0, 1000).boxed().toList();
        TotalRecursiveTask task = new TotalRecursiveTask(list);

        ForkJoinTask<Long> submit = pool.submit(task);
        System.out.println(submit.get());

        long sum = list.stream().mapToLong(value -> value).sum();
        System.out.println(sum);

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
    }

    public static class SimpleForkJoinTask extends RecursiveAction {
        private List<Integer> integers;

        public SimpleForkJoinTask(List<Integer> integers) {
            this.integers = integers;
        }

        @Override
        protected void compute() {
            if (integers.size() <= 10) {
                doCompute();
            } else {
                // fork
                forkCompute();
            }

        }

        private void forkCompute() {
            System.out.println(this.integers.size());
            List<Integer> integers1 = this.integers.subList(0, this.integers.size() / 2);
            System.out.println(List.of(integers1));
            System.out.println("Add to new SimpleForkJoin");
            SimpleForkJoinTask task1 = new SimpleForkJoinTask(integers1);

            System.out.println("New Fork");
            System.out.println(this.integers.size());
            List<Integer> integers2 = this.integers.subList(this.integers.size() / 2, this.integers.size());
            System.out.println(integers2);
            SimpleForkJoinTask task2 = new SimpleForkJoinTask(integers1);

            ForkJoinTask.invokeAll(task1, task2);


        }

        private void doCompute() {
            integers.forEach(integer -> System.out.println(Thread.currentThread().getName() + " : " + integer));
        }
    }

    public static class TotalRecursiveTask extends RecursiveTask<Long> {
        private List<Integer> integers;

        public TotalRecursiveTask(List<Integer> integers) {
            this.integers = integers;
        }

        @Override
        protected Long compute() {
            if (integers.size() <= 10) {
                return doCompute();
            } else {
                return forkComplete();
            }
        }

        private Long forkComplete() {

            List<Integer> integers1 = this.integers.subList(0, this.integers.size() / 2);
            List<Integer> integers2 = this.integers.subList(this.integers.size() / 2, this.integers.size());

            TotalRecursiveTask task = new TotalRecursiveTask(integers1);
            TotalRecursiveTask task1 = new TotalRecursiveTask(integers2);

            ForkJoinTask.invokeAll(task, task1);

            return task.join() + task1.join();
        }

        private Long doCompute() {
            return integers.stream()
                    .mapToLong(value -> value)
                    .peek(value -> {
                        System.out.println(Thread.currentThread().getName() + " : " + value);
                    }).sum();
        }
    }
}
