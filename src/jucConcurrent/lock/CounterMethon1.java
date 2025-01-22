package jucConcurrent.lock;

public class CounterMethon1 {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}
