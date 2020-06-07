# 单机高并发应该掌握的线程基础：线程状态，异常与锁等

## 基础概念

程序：是指一组指示计算机每一步动作的指令，通常用某种程序设计语言编写，运行于某种目标体系结构上。

进程：进程就是一段程序的执行过程。

线程：是操作系统能夠進行運算调度的最小單位。

## 创建线程的方式

继承Thread

```java
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
```

继承Runnable

```java
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
```

使用线程池启动

```java
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
```