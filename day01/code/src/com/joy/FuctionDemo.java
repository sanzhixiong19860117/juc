package com.joy;


/**
 * 使用锁的方法调用普通方法
 */
public class FuctionDemo implements Runnable {
    @Override
    public void run() {

    }

    //同步分发
    public synchronized void m1(){
        System.out.println(Thread.currentThread().getName()+"m1.start");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"m1.end");
    }

    //非同步方法
    public void m2(){
        System.out.println(Thread.currentThread().getName()+"m2.start");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"m2.end");
    }

    public static void main(String[] args) {
        FuctionDemo fuctionDemo = new FuctionDemo();

        new Thread(fuctionDemo::m1,"t1").start();
        new Thread(fuctionDemo::m2,"t2").start();
    }
}
