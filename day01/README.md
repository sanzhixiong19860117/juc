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

## 线程经常使用的方法

1. sleep()	使用这个方法可以让当前的线程停止一段时间
2. Yield()    把当前线程重新放入等待队列当中
3. join().     停止别的进程，让调用这个方法的进程执行完以后在执行别的继承

下面是实力使用

利用线程打印一个倒序，一个正序的1-10的输出

```java
package com.joy;

/**
 * @author joy
 * @date 2020/6/7
 */
public class SleepDemo implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "--" + i);
        }
    }

    public static void main(String[] args) {
        SleepDemo sleepDemo = new SleepDemo();
        new Thread(sleepDemo).start();

        for (int i = 10; i > 0; i--) {
            try {
                Thread.sleep(1002);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-" + i);
        }
    }
}
```

下面是yield方法

```java
package com.joy;

/**
 * @author joy
 * @date 2020/6/7
 */
public class YieldDemo implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            if (i >= 4) {
                Thread.yield();
            }
            System.out.println(Thread.currentThread().getName() + "==" + i);
        }
    }

    public static void main(String[] args) {
        YieldDemo yieldDemo = new YieldDemo();
        new Thread(yieldDemo).start();
        for (int i = 0; i < 10 ; i++) {
            System.out.println(Thread.currentThread().getName() + "===" + i);
        }
    }
}
```

join的使用

```java
package com.joy;

/**
 * @author joy
 * @date 2020/6/7
 */
public class JoinDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1=" + i);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
              //如果没有这句，它是一起抢张cpu的操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2=" + i);
            }
        });

        t1.start();
        t2.start();
    }
}
```

为什么要使用synchronized

```java
package com.joy.home;

/**
 * @author joy
 * @date 2020/6/7
 */
public class home1 implements Runnable{
    private static int ticker = 5;
    @Override
    public void run() {
        for (int i = 0; i < 10 ; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(ticker>0){
                ticker --;
                System.out.println(ticker);
            }
        }
    }

    public static void main(String[] args) {
        home1 h1 = new home1();
        new Thread(h1).start();
        new Thread(h1).start();
        new Thread(h1).start();
    }
}
```

这个demo出现使用一个共享变量的时候出现混乱的情况

```java
package com.joy.home;

/**
 * @author joy
 * @date 2020/6/7
 */
public class home1 implements Runnable{
    private static int ticker = 5;
    @Override
    public void run() {
        for (int i = 0; i < 10 ; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //修改这里
            synchronized (this){
                if(ticker>0){
                    ticker --;
                    System.out.println(ticker);
                }
            }
        }
    }

    public static void main(String[] args) {
        home1 h1 = new home1();
        new Thread(h1).start();
        new Thread(h1).start();
        new Thread(h1).start();
    }
}
```

```java
package com.joy.home;

/**
 * @author joy
 * @date 2020/6/7
 */
public class home1 implements Runnable{
    private static int ticker = 5;
    @Override
    public void run() {
        update();
    }

    private synchronized void update(){
        for (int i = 0; i < 10 ; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //修改这里
            synchronized (this){
                if(ticker>0){
                    ticker --;
                    System.out.println(ticker);
                }
            }
        }
    }

    public static void main(String[] args) {
        home1 h1 = new home1();
        new Thread(h1).start();
        new Thread(h1).start();
        new Thread(h1).start();
    }
}
```

使用这两种方式可以让你共享的数据保持同步操作。

## 线程的状态

1. 初始化阶段（new出线程没有运行的阶段）
2. 可以运行阶段（start方法执行的时候）
3. 运行阶段（在执行run方法的时候）
4. 阻塞阶段（等待wiat阻塞，同步阻塞，其他阻塞（sleep，join等））
5. 死亡（异常退出，执行完成main方法，生命周期结束）

