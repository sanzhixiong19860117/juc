package com.joy;

/**
 * @author joy
 * @date 2020/6/7
 */
public class SleepDemo implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "--" + i);
        }
    }

    public static void main(String[] args) {
        SleepDemo sleepDemo = new SleepDemo();
        new Thread(sleepDemo).start();

        for (int i = 10; i > 0; i--) {
            try {
                Thread.sleep(1002);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-" + i);
        }
    }
}
