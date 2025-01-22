package jucConcurrent.concurrentUtil;

import java.util.concurrent.CountDownLatch;

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
    }
}
