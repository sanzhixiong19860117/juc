package com.joy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author joy
 * @date 2020/6/7
 */
public class ThreeCreate {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10 ; i++) {
            executorService.execute(()->{
                System.out.println(Thread.currentThread().getName());
            });
        }
        executorService.shutdown();
    }
}
