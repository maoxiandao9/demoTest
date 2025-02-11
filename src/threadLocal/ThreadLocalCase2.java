package threadLocal;

import java.util.Random;

public class ThreadLocalCase2 {
    
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    private static InheritableThreadLocal<Integer> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        Random random = new Random();
        int value = random.nextInt(10000);
        threadLocal.set(value);
        inheritableThreadLocal.set(value);
        System.out.println(Thread.currentThread().getName() + "放入值，值为 : " + value);
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "进行取值，值为 : " + threadLocal.get());
            System.out.println(Thread.currentThread().getName() + "进行取值，值为 : " + inheritableThreadLocal.get());
        });
        thread.setName("thread-1");
        thread.start();
        System.out.println(Thread.currentThread().getName() + "进行取值，值为 : " + threadLocal.get());
        System.out.println(Thread.currentThread().getName() + "进行取值，值为 : " + inheritableThreadLocal.get());
    }
}