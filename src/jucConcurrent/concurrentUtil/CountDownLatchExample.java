package jucConcurrent.concurrentUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程门栓
 */
public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 3;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        // 创建并启动三个工作线程
        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 正在工作");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println(Thread.currentThread().getName() + " 完成工作");
            }).start();
        }

        System.out.println("主线程等待工作线程完成");
        latch.await();
        System.out.println("所有工作线程已完成，主线程继续执行");

        // 示例2
        testCountDownLatch();
        // 示例3
        testCountDownLatch2();
    }

    public static void testCountDownLatch(){
        long startTime = System.currentTimeMillis();

        AtomicInteger count = new AtomicInteger(0);

        //设置countDownLatch要计数的次数
        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                count.incrementAndGet();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }finally {
                //计数器减1
                countDownLatch.countDown();
            }
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                count.incrementAndGet();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }finally {
                //计数器减1
                countDownLatch.countDown();
            }
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        count.incrementAndGet();
        long endTime = System.currentTimeMillis();
        System.out.println("==次数:"+count.get()+",执行时间:"+(endTime - startTime)+"==");
    }

    public static void testCountDownLatch2(){
        long startTime = System.currentTimeMillis();
        List<String> list = Collections.synchronizedList(new ArrayList<String>());

        //设置countDownLatch要计数的次数
        CountDownLatch countDownLatch = new CountDownLatch(2);

        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                list.add(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                //计数器减1
                countDownLatch.countDown();
            }
        });
        thread1.setName("thread-1");
        thread1.start();

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                list.add(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                //计数器减1
                countDownLatch.countDown();
            }
        });
        thread2.setName("thread-2");
        thread2.start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("集合元素:");
        list.stream().forEach(System.out::println);
        System.out.println("执行时间:");
        System.out.println((endTime - startTime));
    }
}
///**
// * 构造器
// * @param count 计数次数
// */
//public CountDownLatch(int count)
///**
// * 阻塞等待，当计数不为0会一直等待
// */
//public void await()
///**
// * 阻塞等待
// * @param timeout 等待的时间
// * @param unit 时间单位
// */
//public boolean await(long timeout, TimeUnit unit)
///**
// * 将计数减1
// */
//public void countDown()