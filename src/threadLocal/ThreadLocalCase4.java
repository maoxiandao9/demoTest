package threadLocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 解决线程复用产生的问题,结果是即使线程池的线程被复用，读取的结果也是正常的
 * <dependency>
 *     <groupId>com.alibaba</groupId>
 *     <artifactId>transmittable-thread-local</artifactId>
 *     <version>2.12.0</version> <!-- 使用最新版本 -->
 * </dependency>
 */
public class ThreadLocalCase4 {
    
    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    private static TransmittableThreadLocal<Integer> threadLocal = new TransmittableThreadLocal<>();
    
    public static void main(String[] args) {
        
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            int value = random.nextInt(10000);
            threadLocal.set(value);
            System.out.println(Thread.currentThread().getName() + "放入值，值为 : " + value);
            executor.execute(TtlRunnable.get(() -> {
                System.out.println(Thread.currentThread().getName() + "进行取值，值为 : " + threadLocal.get());
            }));
            System.out.println(Thread.currentThread().getName() + "进行取值，值为 : " + threadLocal.get());
            threadLocal.remove();
        }
        
    }
}