package com.joy.home;

/**
 * @author joy
 * @date 2020/6/8
 * Run方法
 */
public class RunnableRun implements Runnable {
    //同步run方法
    private int count = 10;
    public synchronized void run() {
        for (int i = 0; i < 10; i++) {
            count--;
            System.out.println(Thread.currentThread().getName() + "=" + count);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            RunnableRun runnableRun = new RunnableRun();
            new Thread(runnableRun, "hello" + i).start();
        }
    }
}
