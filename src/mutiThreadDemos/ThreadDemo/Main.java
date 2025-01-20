package mutiThreadDemos.ThreadDemo;

import com.sun.xml.internal.ws.addressing.WsaActionUtil;

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

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("MyThread method 1 started");
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("MyRunnable method 2 started" + Thread.currentThread().getName());
    }
}

class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
//        Thread.sleep(10000);
        System.out.println("MyCallable method 3 started");
        return 1;
    }
}