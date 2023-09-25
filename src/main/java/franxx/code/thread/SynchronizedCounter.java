package franxx.code.thread;

public class SynchronizedCounter {
    private Long value = 0L;

    // synchronized method - not a low level
//    public synchronized void increment() {
//        value++;
//    }

    // synchronized statement
    public synchronized void increment() {
        // multiple thread

        synchronized (this) {
            value++; // single thread
        }

        // multiple thread
    }

    public Long getValue() {
        return value;
    }
}
