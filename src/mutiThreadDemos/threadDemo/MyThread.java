package mutiThreadDemos.threadDemo;

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("MyThread method 1 started");
    }
}