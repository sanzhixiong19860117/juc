package com.joy;

import java.util.concurrent.TimeUnit;

/**
 * 所出现问题之锁换对象
 */
public class Test4 {
    //其中的一个对象
    final Object object = new Object();

    public void m() {
        synchronized (object) {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //打印线程
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {

        Test4 test4 = new Test4();

        new Thread(test4::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        test4.object = new Object();//创建了另一个object对象

        new Thread(test4::m, "t2").start();
    }
}
