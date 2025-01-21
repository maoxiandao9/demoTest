package mutiThreadDemos.ThreadDemo;

import java.util.concurrent.Callable;

class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
//        Thread.sleep(10000);
        System.out.println("MyCallable method 3 started");
        return 1;
    }
}