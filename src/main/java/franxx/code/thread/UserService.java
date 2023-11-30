package franxx.code.thread;

public class UserService {
    final ThreadLocal<String> local = new ThreadLocal<>();

    public void setUser(String user) {
        local.set(user);
    }

    public void doAction() {
        String user = local.get();
        System.out.println(user + ": do action");
    }
}
