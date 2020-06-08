package com.joy.demo;

/**
 * @author joy
 * @date 2020/6/8
 */
public class T2 {
    private int h = 0;

    public synchronized void h() {
        while (true) {
            h++;
            if (h == 5) {
                int i = 1 / 0;
                System.out.println(i);
            }
            System.out.println(h);
        }
    }

    public static void main(String[] args) {
        T2 t2 = new T2();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                t2.h();
            }
        };
        new Thread(runnable).start();
    }
}
