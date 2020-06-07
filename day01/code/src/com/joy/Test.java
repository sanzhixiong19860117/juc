package com.joy;

/**
 * @author joy
 * @date 2020/6/7
 */
public class Test extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "=i");
        }
    }

    public static void main(String[] args) {
        Test t1 = new Test();
        t1.start();//启动线程
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "===i");
        }
    }
}
