package com.joy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ATomic基础应用
 */
public class Test5 {
    //初始化原子数据
    AtomicInteger atomicInteger = new AtomicInteger(0);

    public void m() {
        for (int i = 0; i < 10000; i++) {
            atomicInteger.incrementAndGet();//atomicInteger++
        }
    }

    public static void main(String[] args) {
        Test5 test5 = new Test5();
        List<Thread> list = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            list.add(new Thread(test5::m, "t" + i));
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
        System.out.println("atomicInteger=" + test5.atomicInteger);
    }
}
