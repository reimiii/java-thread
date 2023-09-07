package franxx.code.thread;

import org.junit.jupiter.api.Test;

public class ThreadTest {
    @Test
    void mainThread() {
        String name = Thread.currentThread().getName();
        System.out.println(name);
    }

    @Test
    void createThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello From Thread: " + Thread.currentThread().getName());
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        System.out.println("Program end...");
    }

    @Test
    void sleepThread() throws InterruptedException {
        Runnable runnable = () -> {
            try {
                Thread.sleep(2_000L);
                System.out.println("Hello From Thread: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        System.out.println("Program end...");

        Thread.sleep(3_000L);
    }

    @Test
    void joinThread() throws InterruptedException {
        Runnable runnable = () -> {
            try {
                Thread.sleep(2_000L);
                System.out.println("Hello From Thread: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Thread thread = new Thread(runnable);

        thread.start();

        System.out.println("Waiting...");

        thread.join();

        System.out.println("Program end...");
    }

    @Test
    void interruptThreadWrong() throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Runnable: " + i);
                try {
                    Thread.sleep(1_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
//                    throw new RuntimeException(e);
                }
            }
        };

        Thread thread = new Thread(runnable);

        thread.start();

        Thread.sleep(5_000);

        thread.interrupt();

        System.out.println("Waiting...");

        thread.join();

        System.out.println("Program end...");
    }

    @Test
    void interruptThreadCorrect() throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Runnable: " + i);
                try {
                    Thread.sleep(1_000L);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };

        Thread thread = new Thread(runnable);

        thread.start();

        Thread.sleep(5_000);

        thread.interrupt();

        System.out.println("Waiting...");

        thread.join();

        System.out.println("Program end...");
    }

    @Test
    void interruptThreadCorrectManual() throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                // manual check
                if (Thread.interrupted()) {
                    return;
                }
                System.out.println("Runnable: " + i);
            }
        };

        Thread thread = new Thread(runnable);

        thread.start();

        Thread.sleep(5_000);

        thread.interrupt();

        System.out.println("Waiting...");

        thread.join();

        System.out.println("Program end...");
    }

    @Test
    void nameThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Run in Thread: " + Thread.currentThread().getName());
            }
        });
        thread.setName("FranXX");
        thread.start();
    }

    @Test
    void stateThread() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getState());
                System.out.println("Run in Thread: " + Thread.currentThread().getName());
            }
        });

        thread.setName("FranXX");

        System.out.println(thread.getState());

        thread.start();
        thread.join();

        System.out.println(thread.getState());
    }
}
