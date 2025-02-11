package jucConcurrent.concurrentUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 获得了许可");
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " 释放了许可");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // 示例2
        SemaphoreTest();
    }

    public static String getThreadName(Integer i){
        return "thread-" + i;
    }
    public static void SemaphoreTest(){
        ExecutorService executorService = Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        //设置信号量为5个
        Semaphore semaphore = new Semaphore(5);

        List<Runnable> list = new ArrayList<>();
        for (int i = 1 ; i <= 10 ; i++) {
            int temporaryVariate = i;
            list.add(() -> {
                try{
                    semaphore.acquire();
                    System.out.println("==="+getThreadName(temporaryVariate)+"拿到了信号量====");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    System.out.println("==="+getThreadName(temporaryVariate)+"归还了信号量====");
                    semaphore.release();
                }
            });
        }
        for (Runnable runnable : list) {
            executorService.execute(runnable);
        }
    }
}

///**
// * 构造器
// * @param permits 信号量的数量
// */
//public Semaphore(int permits)
///**
// * 构造器
// * @param permits 信号量的数量
// * @param fair 是否支持公平性
// */
//public Semaphore(int permits, boolean fair)
///**
// * 获取信号量
// */
//public void acquire()
///**
// * 获取信号量
// * @param permits 获取信号量的数量
// */
//public void acquire(int permits)
///**
// * 尝试获取信号量
// */
//public boolean tryAcquire()
///**
// * 尝试获取信号量
// * @param timeout 尝试获取的时间
// * @param unit 时间单位
// */
//public boolean tryAcquire(long timeout, TimeUnit unit)
///**
// * 释放信号量
// */
//public void release()