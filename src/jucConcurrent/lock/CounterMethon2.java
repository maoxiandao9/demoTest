package jucConcurrent.lock;

public class CounterMethon2 {
    private Object lock = new Object();
    private int count = 0;

    public void increment() {
        synchronized (lock) {
            count++;
        }
    }
}
