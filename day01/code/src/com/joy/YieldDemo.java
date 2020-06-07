package com.joy;

/**
 * @author joy
 * @date 2020/6/7
 */
public class YieldDemo implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            if (i >= 4) {
                Thread.yield();
            }
            System.out.println(Thread.currentThread().getName() + "==" + i);
        }
    }

    public static void main(String[] args) {
        YieldDemo yieldDemo = new YieldDemo();
        new Thread(yieldDemo).start();
        for (int i = 0; i < 10 ; i++) {
            System.out.println(Thread.currentThread().getName() + "===" + i);
        }
    }
}
