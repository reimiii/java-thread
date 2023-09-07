package franxx.code.thread;

public class MainApplication {
    public static void main(String[] args) {
        String name = Thread.currentThread().getName();
        System.out.println(name);
    }
}
