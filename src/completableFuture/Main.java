package completableFuture;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        try {
            testFutureTask();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行step 1");
            return "step1 result";
        }, executor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行step 2");
            return "step2 result";
        }, executor);
        cf1.thenCombine(cf2, (result1, result2) -> {
            System.out.println(result1 + " : " + result2);
            System.out.println("执行step 3");
            return "step3 result";
        }).thenAccept(result3 -> System.out.println(result3));
    }

    public static void testFutureTask() throws ExecutionException, InterruptedException {
        FutureTask task = new FutureTask(() -> {
            System.out.println("执行异步call方法");
            return 1;
        });
        new Thread(task).start();
        System.out.println("异步结果:"+task.get());
    }
}