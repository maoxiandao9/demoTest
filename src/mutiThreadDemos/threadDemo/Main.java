package mutiThreadDemos.threadDemo;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("start!!!");
        // 1、继承Thread类
        MyThread myThread = new MyThread();
        myThread.start();
        // 2、实现Runnable接口
        Thread thread = new Thread(new MyRunnable());
        thread.start();
        // 3、实现Callable接口与Futuretask
        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(myCallable);
        new Thread(futureTask).start();

        try {
            Integer result = futureTask.get();
            System.out.println("result: " + result);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 4、使用线程池（Executor框架）
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executor.execute(new MyRunnable());
        }
        executor.shutdown();
        System.out.println("end!!!");
    }
}