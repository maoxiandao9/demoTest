package threadLocal;

import java.util.Random;

/**
 * 每个线程向ThreadLocal设置值，再取值，实现线程之间的隔离
 */
public class ThreadLocalCase1 {
    
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    
    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                int value = random.nextInt(10000);
                threadLocal.set(value);
                System.out.println(Thread.currentThread().getName() + "开始执行，放入值，值为 : " + value);
                System.out.println(Thread.currentThread().getName() + "结束执行，进行取值，值为 : " + threadLocal.get());
            }); 
            thread.setName("thread-" + i); 
            thread.start();
        }
    }
}