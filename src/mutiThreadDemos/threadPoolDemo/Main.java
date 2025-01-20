package mutiThreadDemos.threadPoolDemo;

import java.util.concurrent.*;

class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // 示例1 这里只是为了演示使用，推荐使用 `ThreadPoolExecutor` 构造方法来创建线程池。
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Future<String> submit = executorService.submit(() -> {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });

//        System.out.println(Thread.currentThread().getName() + " start : " + new Date());
//        Thread.sleep(10000L);
//        System.out.println(Thread.currentThread().getName() + " end : " + new Date());
        String s = submit.get();
        System.out.println(s);
        executorService.shutdown();

        // 示例2
        executorService = Executors.newFixedThreadPool(3);

        submit = executorService.submit(() -> {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });

        s = submit.get(3, TimeUnit.SECONDS);
        System.out.println(s);
        executorService.shutdown();
    }
}