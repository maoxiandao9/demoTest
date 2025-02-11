package jucConcurrent.concurrentUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        int numberOfThreads = 3;
        CyclicBarrier barrier = new CyclicBarrier(numberOfThreads, () -> {
            System.out.println("所有线程都达到了屏障，继续执行后续操作");
        });

        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 正在运行");
                    Thread.sleep(1000);
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " 已经通过屏障");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // 示例2
        cyclicBarrierTest1();
    }

    public static String getThreadName(Integer i){
        return "thread-" + i;
    }
    public static void cyclicBarrierTest1(){

        ExecutorService executorService =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        //设置要在栅栏等待数量为5个
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5,() -> {
            System.out.println("===所有的线程都冲过了栅栏===");
        });

        List<Runnable> list = new ArrayList<>();
        for (int i = 1 ; i <= 5 ; i++) {
            int temporaryVariate = i;
            list.add(() -> {
                try{
                    System.out.println("==="+getThreadName(temporaryVariate)+"在栅栏等待====");
                    cyclicBarrier.await();
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("==="+getThreadName(temporaryVariate)+"冲过了栅栏====");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
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
// * @param parties 要在栅栏处等待的线程数量
// */
//public CyclicBarrier(int parties)
///**
// * 构造器
// * @param parties 要在栅栏处等待的线程数量
// * @param barrierAction 设置的栅栏处等待的线程都通过后执行的任务
// */
//public CyclicBarrier(int parties, Runnable barrierAction)
///**
// * 调用此方法表示到达了栅栏在此等待
// */
//public int await()
///**
// * 调用此方法表示到达了栅栏在此等待
// * @param timeout 在栅栏处等待的时间
// * @param unit 在栅栏处等待的时间单位
// */
//public int await(long timeout, TimeUnit unit)