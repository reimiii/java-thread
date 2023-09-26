package franxx.code.thread;

public class Balance {
    private Long value;

    public Balance(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    // DeadLock
    public static void transferDeadLock(Balance from, Balance to, Long value) throws InterruptedException {
        synchronized (from) {
            Thread.sleep(1000);
            synchronized (to) {
                Thread.sleep(1000);
                from.setValue(from.getValue() - value);
                to.setValue(to.getValue() + value);
            }
        }
    }

    // not a deadlock different algo different solution
    public static void transfer(Balance from, Balance to, Long value) throws InterruptedException {
        synchronized (from) {
            Thread.sleep(1000);
            from.setValue(from.getValue() - value);
        }

        synchronized (to) {
            Thread.sleep(1000);
            to.setValue(to.getValue() + value);
        }
    }
}
