package franxx.code.thread;

public class DaemonApp {
    public static void main(String[] args) {
        // for background job
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("Run Thread");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread.setDaemon(true);
        thread.start();
    }
}
