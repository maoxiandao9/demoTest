package mutiThreadDemos.threadDemo;

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("MyRunnable method 2 started" + Thread.currentThread().getName());
    }
}