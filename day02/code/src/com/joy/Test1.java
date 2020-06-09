package com.joy;

import java.util.concurrent.TimeUnit;

public class Test1 {
    volatile boolean isRuning = true; //是否在运行
    public void m() {
        System.out.println("m start");
        while (isRuning) {
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        //创建对象
        Test1 test1 = new Test1();
        new Thread(test1::m,"t1").start();

        //停止一秒钟
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //停止操作
        test1.isRuning = false;
    }
}
