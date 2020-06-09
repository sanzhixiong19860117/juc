package com.joy;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile可见性的一个demo
 */

public class Test3 {
    volatile int count = 0;

    public void m() {
        for (int i = 0; i < 10000; i++) {
            synchronized (this){
                count++;
            }
        }
    }

    public static void main(String[] args) {
        List<Thread> list = new ArrayList<Thread>();
        Test3 test3 = new Test3();
        for (int i = 0; i < 10; i++) {
            list.add(new Thread(test3::m, "t" + i));
        }

        //启动
        list.forEach(o -> {
            o.start();
        });

        for (int i = 0; i < 10; i++) {
            //list的操作
            list.forEach(o -> {
                try {
                    o.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("coout=" + test3.count);
    }
}
