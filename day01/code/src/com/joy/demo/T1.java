package com.joy.demo;

/**
 * @author joy
 * @date 2020/6/8
 */
public class T1 implements Runnable {
    @Override
    public void run() {}

    public synchronized void m1() {
        System.out.println("m1 start");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();//进入m2同步方法里面
        System.out.println("m1 end");
    }

    public synchronized void m2() {
        System.out.println("m2 start");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2 end");
    }

    public static void main(String[] args) {
        new T1().m1();//执行m1方法
    }
}
