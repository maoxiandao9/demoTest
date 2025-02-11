package threadLocal;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 由于InheritableThreadLocal会保证子线程能读取父线程中的数据，但线程池中的核心线程是复用的，所以有可能会发生重复读取的情况。
 */
public class ThreadLocalCase3 {
    private static ExecutorService executor = Executors.newFixedThreadPool(2);
    private static InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            int value = random.nextInt(10000);
            threadLocal.set(value);
            System.out.println(Thread.currentThread().getName() + "放入值，值为 : " + value);
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "进行取值，值为 : " + threadLocal.get());
            });
            System.out.println(Thread.currentThread().getName() + "进行取值，值为 : " + threadLocal.get());
            threadLocal.remove();
        }
    }
}