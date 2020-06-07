package com.joy;

/**
 * @author joy
 * @date 2020/6/7
 */
public class RunnableDemo implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 10 ; i++) {
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        RunnableDemo r1 = new RunnableDemo();
        new Thread(r1).start();

        for (int i = 0; i < 10 ; i++) {
            System.out.println(Thread.currentThread().getName());
        }
    }
    
}
